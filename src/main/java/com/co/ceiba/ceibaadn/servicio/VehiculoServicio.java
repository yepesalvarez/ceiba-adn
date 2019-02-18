package com.co.ceiba.ceibaadn.servicio;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public interface VehiculoServicio {
	
	Vehiculo obtenerVehiculoPorPlaca(String placa);
	Vehiculo obtenerVehiculoPorId(Long idVehiculo);
	Vehiculo guardarVehiculo(Vehiculo vehiculo);
	
}
