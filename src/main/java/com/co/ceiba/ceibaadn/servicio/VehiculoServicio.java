package com.co.ceiba.ceibaadn.servicio;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;

public interface VehiculoServicio {

	Vehiculo guardarVehiculo(VehiculoDto vehiculoDto) throws VehiculoBadRequestException;
	
	Vehiculo obtenerVehiculoPorPlaca(String placa);
	Vehiculo obtenerVehiculoPorId(Long idVehiculo);
	Vehiculo guardarVehiculo(Vehiculo vehiculo);
	
}
