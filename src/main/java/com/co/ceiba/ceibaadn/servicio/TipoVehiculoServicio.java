package com.co.ceiba.ceibaadn.servicio;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

public interface TipoVehiculoServicio {
	
	TipoVehiculo obtenerPorNombre(String nombreTipoVehiculo);
	TipoVehiculo guardarTipoVehiculo(TipoVehiculo tipoVehiculo);
	
}
