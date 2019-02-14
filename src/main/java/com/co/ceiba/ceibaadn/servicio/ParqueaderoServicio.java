package com.co.ceiba.ceibaadn.servicio;

import java.util.Set;

import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;

public interface ParqueaderoServicio {
	
	void guardarCambios(Parqueadero parqueadero);
	Set<VehiculoDto> actualizarRangos();
	Set<VehiculoDto> obtenerVehiculosParqueados();
	Set<VehiculoDto> agregarVehiculo(VehiculoDto vehiculoDto);
	void pagarParqueadero(Long idVehiculo);
	Set<VehiculoDto> retirarVehiculo(Long idVehiculo);

}
