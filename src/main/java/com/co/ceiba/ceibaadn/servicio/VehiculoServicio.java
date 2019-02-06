package com.co.ceiba.ceibaadn.servicio;

import java.util.List;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public interface VehiculoServicio {

	List<Vehiculo> obtenerTodosVehiculos();
	Vehiculo obtenerVehiculoPorPlaca(String placa);
	Vehiculo obtenerVehiculoPorId(Long idVehiculo);
	Vehiculo guardarVehiculo(Vehiculo vehiculo);
	void eliminarVehiculo(Vehiculo vehiculo);
	void eliminarVehiculo(Long idVehiculo);
	
}
