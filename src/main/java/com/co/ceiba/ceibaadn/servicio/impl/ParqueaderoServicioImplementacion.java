package com.co.ceiba.ceibaadn.servicio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.controlador.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoInternalServerErrorException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.enums.DiasHabilitadosPlacaA;
import com.co.ceiba.ceibaadn.dominio.enums.EstadoCobro;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.ParqueaderoRepositorio;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;
import com.co.ceiba.ceibaadn.servicio.CobroServicio;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@Service
public class ParqueaderoServicioImplementacion implements ParqueaderoServicio {

	@Autowired
	Environment env;
	
	@Autowired
	ParqueaderoRepositorio parqueaderoRepositorio;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@Autowired
	CobroServicio cobroServicio;
	
	@Autowired
	FactoryVehiculo factoryVehuculo;
		
	@Autowired
	CapacidadServicio capacidadServicio;
	
	public static final String ERROR_ARCHIVO_PROPIEDADES = "Fallo al intentar cargar valores desde archivo de propiedades";
	
	public static final Logger LOGGER = Logger.getLogger(ParqueaderoServicioImplementacion.class);
	
	@Override
	public Set<Vehiculo> actualizarRangos() {
		
		try {
			//No se permiten actualizaciones administrativas del parqueadero si existen vehículos estacionados
			if (Parqueadero.getInstance().getVehiculos().isEmpty())
			{
				Parqueadero.getInstance().setId(1L);
				Parqueadero.getInstance().setMinimoHorasDia(Integer.parseInt(env.getProperty("horas.minimo.dia")));
				Parqueadero.getInstance().setMaximoHorasDia(Integer.parseInt(env.getProperty("horas.maximo.dia")));
				guardarCambios(Parqueadero.getInstance());
				
				Set<Capacidad> capacidadesParqueadero = factoryVehuculo.getCapacidadesParqueadero();
				for (Capacidad capacidad : capacidadesParqueadero) {
					capacidad.setParqueadero(Parqueadero.getInstance());
					capacidadServicio.guardarCapacidad(capacidad);
				}
				Parqueadero.getInstance().setCapacidades(capacidadesParqueadero);
				guardarCambios(Parqueadero.getInstance());
			}
		}catch(ParqueaderoInternalServerErrorException e) {
			LOGGER.error(ERROR_ARCHIVO_PROPIEDADES);
			throw new ParqueaderoInternalServerErrorException();
		}
		return Parqueadero.getInstance().getVehiculos();
	}

	@Override
	public Set<Vehiculo> agregarVehiculo(Vehiculo vehiculo) {
		try {
			verificarVehiculoEnDiaNoHabilitado(vehiculo.getPlaca());
			for (Capacidad capacidad : Parqueadero.getInstance().getCapacidades()) {
				if (capacidad.getTipoVehiculo().equals(vehiculo.getTipoVehiculo())) {
					int contadorVehiculosTipo = 
							(int) Parqueadero.getInstance().getVehiculos().stream()
							.filter(vehiculoEnParqueadero -> vehiculoEnParqueadero.getTipoVehiculo().equals(capacidad.getTipoVehiculo())).count();
					if (capacidad.getLimite() < contadorVehiculosTipo && !Parqueadero.getInstance().getVehiculos().contains(vehiculo)) {
						Parqueadero.getInstance().getVehiculos().add(vehiculo);
						
						Cobro cobro = new Cobro();
						cobro.setEstado(EstadoCobro.PENDIENTE.toString());
						cobro.setVehiculo(vehiculo);
						cobro.setInicioParqueo(LocalDateTime.now());
						cobroServicio.guardarCobro(cobro);
						Parqueadero.getInstance().getCobros().add(cobro);
						guardarCambios(Parqueadero.getInstance());	
					}else {
						throw new ParqueaderoIngresoNoPosibleException();
					}
				}
			}
		}catch(ParqueaderoIngresoNoPosibleException e) {
			throw new ParqueaderoIngresoNoPosibleException();
		}
		return Parqueadero.getInstance().getVehiculos();
	}

	@Override
	public Set<Vehiculo> retirarVehiculo(Vehiculo vehiculo) {
		if (cobroServicio.obtenerCobroPorVehiculo(vehiculo).getEstado().equals(EstadoCobro.PENDIENTE.toString())
				|| !Parqueadero.getInstance().getVehiculos().contains(vehiculo)) {
			throw new ParqueaderoRetiroVehiculoNoPosibleException();
		}
		Parqueadero.getInstance().getVehiculos().remove(vehiculo);
		return Parqueadero.getInstance().getVehiculos();
	}
	
	@Override
	public Set<Vehiculo> obtenerVehiculosParqueados() {
		return Parqueadero.getInstance().getVehiculos();
	}

	@Override
	public void pagarParqueadero(Long idVehiculo) {
		try {
			Cobro cobro = cobroServicio.obtenerCobroPorVehiculo(vehiculoServicio.obtenerVehiculoPorId(idVehiculo));
			cobro.setEstado(EstadoCobro.PAGADO.toString());
			cobroServicio.guardarCobro(cobro);
			Parqueadero.getInstance().getCobros().add(cobro);
			guardarCambios(Parqueadero.getInstance());
		} catch(CobroNoPosibleException e) {
			throw new CobroNoPosibleException();
		}
	}

	@Override
	public void guardarCambios(Parqueadero parqueadero) {
		parqueaderoRepositorio.save(parqueadero);
	}
	
	public void verificarVehiculoEnDiaNoHabilitado(String placaVehiculo) {
		String diaActual = LocalDate.now().getDayOfWeek().name();
		if(placaVehiculo.toUpperCase().startsWith("A") && 
				(!diaActual.equals(DiasHabilitadosPlacaA.SUNDAY.toString()) 
						|| !diaActual.equals(DiasHabilitadosPlacaA.MONDAY.toString()))) {
			LOGGER.error(new ParqueaderoIngresoNoPosibleException().getMessage()
					+ " vehiculo " + placaVehiculo + " dia actual " + diaActual);
			throw new ParqueaderoIngresoNoPosibleException();	
		}
	}
}
