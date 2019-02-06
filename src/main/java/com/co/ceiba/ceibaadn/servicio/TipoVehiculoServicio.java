package com.co.ceiba.ceibaadn.servicio;

import java.util.List;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

public interface TipoVehiculoServicio {
	
	List<TipoVehiculo> obtenerTodosTipoVehiculo();
	TipoVehiculo obtenerPorNombre(String nombreTipoVehiculo);
	TipoVehiculo obtenerPorId(Long idTipoVehiculo);
	TipoVehiculo guardarTipoVehiculo(TipoVehiculo tipoVehiculo);
	void eliminarTipoVehiculo(TipoVehiculo tipoVehiculo);
	void eliminarTipoVehiculo(Long idTipoVehiculo);
	
}
