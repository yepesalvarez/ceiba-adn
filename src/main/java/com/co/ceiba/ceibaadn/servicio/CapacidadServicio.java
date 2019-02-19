package com.co.ceiba.ceibaadn.servicio;

import com.co.ceiba.ceibaadn.dominio.Capacidad;

public interface CapacidadServicio {
	
	int obtenerMinimoHorasDiaParqueo();
	int obtenerMaximoHorasDiaParqueo();
	Capacidad guardarCapacidad(Capacidad capacidad);
	
}
