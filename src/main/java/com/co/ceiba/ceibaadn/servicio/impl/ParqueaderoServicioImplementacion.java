package com.co.ceiba.ceibaadn.servicio.impl;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.enums.DiasHabilitadosPlacaA;
import com.co.ceiba.ceibaadn.dominio.enums.EstadoCobro;
import com.co.ceiba.ceibaadn.dominio.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoInternalServerErrorException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.dominio.util.ModelToDto;
import com.co.ceiba.ceibaadn.repositorio.CobroRepositorio;
import com.co.ceiba.ceibaadn.repositorio.ParqueaderoRepositorio;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@Service
public class ParqueaderoServicioImplementacion implements ParqueaderoServicio {

	@Autowired
	ParqueaderoRepositorio parqueaderoRepositorio;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@Autowired
	CobroRepositorio cobroRepositorio;
	
	@Autowired
	FactoryVehiculo factoryVehiculo;
		
	@Autowired
	CapacidadServicio capacidadServicio;
	
	@Autowired
	TipoVehiculoServicio tipoVehiculoServicio;
	
	@Autowired
	ModelToDto modelToDto;
	
	private Parqueadero parqueadero;
	
	private static final String ERROR_ARCHIVO_PROPIEDADES = "Fallo al intentar cargar valores desde archivo de propiedades";
	
	private static final Logger LOGGER = Logger.getLogger(ParqueaderoServicioImplementacion.class);
	
	public Parqueadero getParqueadero() {
		actualizarRangos();
		return parqueadero;
	}

	public ParqueaderoServicioImplementacion(ParqueaderoRepositorio parqueaderoRepositorio,
			VehiculoServicio vehiculoServicio, CobroRepositorio cobroRepositorio, FactoryVehiculo factoryVehiculo,
			CapacidadServicio capacidadServicio, ModelToDto modelToDto, TipoVehiculoServicio tipoVehiculoServicio) {
		this.parqueaderoRepositorio = parqueaderoRepositorio;
		this.vehiculoServicio = vehiculoServicio;
		this.cobroRepositorio = cobroRepositorio;
		this.factoryVehiculo = factoryVehiculo;
		this.capacidadServicio = capacidadServicio;
		this.modelToDto = modelToDto;
		this.tipoVehiculoServicio = tipoVehiculoServicio;
	}

	@Override
	public Set<VehiculoDto> actualizarRangos() {
		try {
			inicializarParqueadero();
			parqueadero = ((List<Parqueadero>) parqueaderoRepositorio.findAll()).stream().findFirst().orElse(new Parqueadero());
			parqueadero.setMinimoHorasDia(capacidadServicio.obtenerMinimoHorasDiaParqueo());
			parqueadero.setMaximoHorasDia(capacidadServicio.obtenerMaximoHorasDiaParqueo());		
			Set<Capacidad> capacidadesParqueadero = factoryVehiculo.getCapacidadesParqueadero();
			for (Capacidad capacidad : capacidadesParqueadero) {
				capacidad.setParqueadero(parqueadero);
				capacidadServicio.guardarCapacidad(capacidad);
			}
			parqueadero.setCapacidades(capacidadesParqueadero);	
			parqueadero = guardarParqueadero(parqueadero);			
			return modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos());
		}catch(ParqueaderoInternalServerErrorException e) {
			LOGGER.error(ERROR_ARCHIVO_PROPIEDADES, e);
			throw new ParqueaderoInternalServerErrorException();
		}
	}

	@Override
	public Set<VehiculoDto> ingresarVehiculo(VehiculoDto vehiculoDto) {
		try {
			actualizarRangos();
			String stringVacio = "";
			if(vehiculoDto == null || vehiculoDto.getTipoVehiculo() == null || vehiculoDto.getPlaca() == null) {
				throw new VehiculoIngresoNoPosibleException();
			}
			if(tipoVehiculoServicio.obtenerPorNombre(vehiculoDto.getTipoVehiculo()) == null)
			{
				throw new VehiculoIngresoNoPosibleException();
			}
			if(stringVacio.equals(vehiculoDto.getPlaca()) || stringVacio.equals(vehiculoDto.getTipoVehiculo())
					|| vehiculoDto.getPlaca().length() > 6 ) {
				throw new VehiculoIngresoNoPosibleException();
			}
			verificarVehiculoEnDiaNoHabilitado(vehiculoDto.getPlaca());	
			Vehiculo vehiculo = vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca());
			if( vehiculo == null) {
				vehiculo = vehiculoServicio.guardarVehiculo(modelToDto.vehiculoDtoToVehiculo(vehiculoDto));
			} else {
				if (vehiculo.getParqueadero() != null) {
					throw new VehiculoYaExisteException();
				}
			}
			agregarCobroVehiculo(vehiculo);
			vehiculo.setParqueadero(parqueadero);
			parqueadero.getVehiculos().add(vehiculo);
			guardarParqueadero(parqueadero);
		}catch(ParqueaderoIngresoNoPosibleException e) {
			LOGGER.error(e);
			throw new ParqueaderoIngresoNoPosibleException();
		}
		return modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos());
	}

	public void agregarCobroVehiculo(Vehiculo vehiculo) {
		for (Capacidad capacidad : parqueadero.getCapacidades()) {
			if (capacidad.getTipoVehiculo().equals(vehiculo.getTipoVehiculo())) {
				int contadorVehiculosTipo = 
						(int) parqueadero.getVehiculos().stream()
						.filter(vehiculoEnParqueadero -> vehiculoEnParqueadero.getTipoVehiculo().equals(capacidad.getTipoVehiculo())).count();
				if (capacidad.getLimite() > contadorVehiculosTipo) {
					parqueadero.getVehiculos().add(vehiculo);
					Cobro cobro = new Cobro();
					cobro.setEstado(EstadoCobro.PENDIENTE.toString());
					cobro.setVehiculo(vehiculo);
					cobro.setInicioParqueo(LocalDateTime.now());
					cobroRepositorio.save(cobro);
					parqueadero.getCobros().add(cobro);
					guardarParqueadero(parqueadero);	
				}else {
					throw new ParqueaderoIngresoNoPosibleException();
				}
			}
		}
	}
	
	@Override
	public Set<VehiculoDto> retirarVehiculo(Long idVehiculo) {
		actualizarRangos();
		Vehiculo vehiculo = vehiculoServicio.obtenerVehiculoPorId(idVehiculo);
		if (vehiculo == null || vehiculo.getParqueadero() == null
				|| cobroRepositorio.findByVehiculo(vehiculo).getEstado()
					.equals(EstadoCobro.PENDIENTE.toString())) {
			throw new ParqueaderoRetiroVehiculoNoPosibleException();
		}
		parqueadero.getVehiculos().remove(vehiculo);
		vehiculoServicio.guardarVehiculo(vehiculo);
		guardarParqueadero(parqueadero);
		return modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos());
	}
	
	@Override
	public Set<VehiculoDto> obtenerVehiculosParqueados() {
		inicializarParqueadero();
		return modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos());
	}

	@Override
	public void pagarParqueadero(Long idVehiculo) {
		try {
			actualizarRangos();
			Vehiculo vehiculo = vehiculoServicio.obtenerVehiculoPorId(idVehiculo);
			if(vehiculo == null) {
				throw new CobroNoPosibleException();
			}
			Cobro cobro = cobroRepositorio.findByVehiculo(vehiculo);
			cobro.setEstado(EstadoCobro.PAGADO.toString());
			cobroRepositorio.save(cobro);
			parqueadero.getCobros().add(cobro);
			guardarParqueadero(parqueadero);
		} catch(CobroNoPosibleException e) {
			LOGGER.error(e);
			throw new CobroNoPosibleException();
		}
	}

	@Override
	public Parqueadero guardarParqueadero(Parqueadero parqueadero) {
		return parqueaderoRepositorio.save(parqueadero);
	}
	
	public void verificarVehiculoEnDiaNoHabilitado(String placaVehiculo) {
		String diaActual = LocalDate.now().getDayOfWeek().name();
		if(placaVehiculo.startsWith("A") || placaVehiculo.startsWith("a")) {
			boolean continuarComparacion = false;
			int i = 0;
			while (!continuarComparacion && i < DiasHabilitadosPlacaA.values().length) {
				if(diaActual.equals(DiasHabilitadosPlacaA.values()[i].toString())) {
					continuarComparacion = true;	
				}
				i++;
			}
			if(!continuarComparacion) {
				throw new ParqueaderoIngresoNoPosibleException();
			}
		}
	}
	
	public void inicializarParqueadero() {
		if (!parqueaderoRepositorio.findAll().iterator().hasNext()) {
			parqueadero = new Parqueadero();
			parqueadero.setVehiculos(new HashSet<>());
			parqueadero.setCobros(new HashSet<>());
			guardarParqueadero(parqueadero);
		}
	}
	
}
