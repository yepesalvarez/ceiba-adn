package com.co.ceiba.ceibaadn.servicio;

import java.util.List;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

public interface CapacidadServicio {
	
	Capacidad obtenerCapacidadPorId(Long idCapacidad);
	Capacidad obtenerCapacidadPorTipoVehiculo(TipoVehiculo tipoVehiculo);
	List<Capacidad> obtenerTodasCapacidad();
	Capacidad guardarCapacidad(Capacidad capacidad);
	void eliminarCapacidad(Capacidad capacidad);
	void eliminarCapacidad(Long idCapacidad);

}
