package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.List;

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
	
	public static final Logger LOGGER = Logger.getLogger(VehiculoServicioImplementacion.class);

	@Override
	public List<Vehiculo> obtenerTodosVehiculos() {
		return (List<Vehiculo>) vehiculoRepositorio.findAll();
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
		
		String placa = vehiculoDto.getPlaca();
		String tipoVehiculoString = vehiculoDto.getTipoVehiculo();
		TipoVehiculo tipoVehiculo = tipoVehiculoServicio.obtenerPorNombre(tipoVehiculoString);
		if ((placa == null || placa.equals("") || placa.length() > 6) || (tipoVehiculoString == null 
				|| tipoVehiculoString.equals("")) || tipoVehiculo == null) {
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
		if(vehiculo == null) {
			LOGGER.error(new VehiculoBadRequestException().getMessage());
			throw new VehiculoBadRequestException();
		}
		
		return vehiculo;
	}

	@Override
	public void eliminarVehiculo(Vehiculo vehiculo) {
		vehiculoRepositorio.delete(vehiculo);
	}

	@Override
	public void eliminarVehiculo(Long idVehiculo) {
		vehiculoRepositorio.deleteById(idVehiculo);
		
	}

}
