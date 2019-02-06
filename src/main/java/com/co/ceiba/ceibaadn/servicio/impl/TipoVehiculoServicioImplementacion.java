package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.List;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.repositorio.TipoVehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.excepciones.CeibaAdnServicioExcepcion;

@Service
public class TipoVehiculoServicioImplementacion implements TipoVehiculoServicio {
	
	public static final String TIPO_VEHICULO_DUPLICADO = "El tipo de vehiculo ya existe"; 

	@Autowired
	TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
	public static final Logger LOGGER = Logger.getLogger(TipoVehiculoServicioImplementacion.class);
	
	@Override
	public List<TipoVehiculo> obtenerTodosTipoVehiculo() {
		return (List<TipoVehiculo>) tipoVehiculoRepositorio.findAll();
	}

	@Override
	public TipoVehiculo obtenerPorNombre(String nombreTipoVehiculo) {
		return tipoVehiculoRepositorio.findByNombre(nombreTipoVehiculo.toLowerCase());
	}

	@Override
	public TipoVehiculo obtenerPorId(Long idTipoVehiculo) {
		return tipoVehiculoRepositorio.findById(idTipoVehiculo).orElse(null);
	}

	@Override
	public TipoVehiculo guardarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		if(obtenerPorNombre(tipoVehiculo.getNombre())!=null) {
			LOGGER.error(new CeibaAdnServicioExcepcion(TIPO_VEHICULO_DUPLICADO));
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
