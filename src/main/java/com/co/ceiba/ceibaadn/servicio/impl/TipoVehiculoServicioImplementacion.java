package com.co.ceiba.ceibaadn.servicio.impl;

import org.jboss.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.repositorio.TipoVehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;

@Service
public class TipoVehiculoServicioImplementacion implements TipoVehiculoServicio {
	
	public TipoVehiculoServicioImplementacion(TipoVehiculoRepositorio tipoVehiculoRepositorio) {
		this.tipoVehiculoRepositorio = tipoVehiculoRepositorio;
	}

	public static final Logger LOGGER = Logger.getLogger(TipoVehiculoServicioImplementacion.class);

	@Autowired
	TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
	@Override
	public TipoVehiculo obtenerPorNombre(String nombreTipoVehiculo) {
			return tipoVehiculoRepositorio.findByNombre(nombreTipoVehiculo.toLowerCase());
	}
	
	@Override
	public TipoVehiculo guardarTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return tipoVehiculoRepositorio.save(tipoVehiculo);
	}

}
