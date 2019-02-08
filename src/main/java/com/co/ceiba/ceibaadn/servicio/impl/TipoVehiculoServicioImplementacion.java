package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.repositorio.TipoVehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;

@Service
public class TipoVehiculoServicioImplementacion implements TipoVehiculoServicio {
	
	public static final Logger LOGGER = Logger.getLogger(TipoVehiculoServicioImplementacion.class);

	@Autowired
	TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
	@Override
	public List<TipoVehiculo> obtenerTodosTipoVehiculo() {
		return (List<TipoVehiculo>) tipoVehiculoRepositorio.findAll();
	}

	@Override
	public TipoVehiculo obtenerPorNombre(String nombreTipoVehiculo) {
		try {
			return tipoVehiculoRepositorio.findByNombre(nombreTipoVehiculo.toLowerCase());
		}catch (Exception e) {
			LOGGER.error(new VehiculoBadRequestException().getMessage(), e);
			throw new VehiculoBadRequestException();
		}
	}
	
	@Override
	public TipoVehiculo obtenerPorId(Long idTipoVehiculo) {
		try {
			return tipoVehiculoRepositorio.findById(idTipoVehiculo).orElse(null);
		}catch (Exception e) {
			LOGGER.error(new VehiculoBadRequestException().getMessage(), e);
			throw new VehiculoBadRequestException();
		}
	}

	@Override
	public TipoVehiculo guardarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		if(obtenerPorNombre(tipoVehiculo.getNombre())!=null) {
			LOGGER.error(new VehiculoYaExisteException());
			return null;
		}
		return tipoVehiculoRepositorio.save(tipoVehiculo);
	}

	@Override
	public void eliminarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		tipoVehiculoRepositorio.delete(tipoVehiculo);
	}

	@Override
	public void eliminarTipoVehiculo(Long idTipoVehiculo) {
		tipoVehiculoRepositorio.deleteById(idTipoVehiculo);
	}

}
