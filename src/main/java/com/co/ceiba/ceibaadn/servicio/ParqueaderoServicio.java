package com.co.ceiba.ceibaadn.servicio;

import java.util.Set;

import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public interface ParqueaderoServicio {
	
	void guardarCambios(Parqueadero parqueadero);
	Set<Vehiculo> actualizarRangos();
	Set<Vehiculo> obtenerVehiculosParqueados();
	Set<Vehiculo> agregarVehiculo(Vehiculo vehiculo);
	void pagarParqueadero(Long idVehiculo);
	Set<Vehiculo> retirarVehiculo(Vehiculo vehiculo);

}
