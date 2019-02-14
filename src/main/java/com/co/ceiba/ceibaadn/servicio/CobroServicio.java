package com.co.ceiba.ceibaadn.servicio;

import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public interface CobroServicio {
	
	double calcularCobro(Long idVehiculo, String fechaFinParqueo);
	Cobro guardarCobro(Cobro cobro);
	Cobro obtenerCobroPorVehiculo(Vehiculo vehiculo);
	
}
