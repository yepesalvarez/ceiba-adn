package com.co.ceiba.ceibaadn.servicio.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@Service
public class VehiculoServicioImplementacion implements VehiculoServicio {
	
	@Autowired
	VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	TipoVehiculoServicio tipoVehiculoServicio;
	
	@Autowired
	private FactoryVehiculo factoryVehiculo;
	
	private static final Logger LOGGER = Logger.getLogger(VehiculoServicioImplementacion.class);
	
	public VehiculoServicioImplementacion(VehiculoRepositorio vehiculoRepositorio,
			TipoVehiculoServicio tipoVehiculoServicio, FactoryVehiculo factoryVehiculo) {
		this.vehiculoRepositorio = vehiculoRepositorio;
		this.tipoVehiculoServicio = tipoVehiculoServicio;
		this.factoryVehiculo = factoryVehiculo;
	}

	@Override
	public Vehiculo obtenerVehiculoPorPlaca(String placa) {
		return vehiculoRepositorio.findByPlaca(placa);
	}

	@Override
	public Vehiculo obtenerVehiculoPorId(Long idVehiculo) {
		return vehiculoRepositorio.findById(idVehiculo).orElse(null);
	}

	@Override
	public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
		return vehiculoRepositorio.save(vehiculo);
	}
	
	@Override
	public Vehiculo guardarVehiculo(VehiculoDto vehiculoDto) {	
		try {
			String placa = vehiculoDto.getPlaca();
			String tipoVehiculoString = vehiculoDto.getTipoVehiculo();
			TipoVehiculo tipoVehiculo = tipoVehiculoServicio.obtenerPorNombre(tipoVehiculoString);
			if ((placa.equals("") || placa.length() > 6) || tipoVehiculo == null) {
				throw new VehiculoBadRequestException();
			}		
			Vehiculo vehiculo;
			vehiculo = obtenerVehiculoPorPlaca(placa);
			if(vehiculo != null) {
				LOGGER.error(new VehiculoYaExisteException().getMessage());
				throw new VehiculoYaExisteException();
			}			
			vehiculo = factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculo);
			vehiculo = guardarVehiculo(vehiculo);		
			return vehiculo;		
		} catch (VehiculoBadRequestException e) {
			LOGGER.error(new VehiculoBadRequestException());
			throw new VehiculoBadRequestException();
		}	
	}
	
}
