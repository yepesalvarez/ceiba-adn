package com.co.ceiba.ceibaadn.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.repositorio.CapacidadRepositorio;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;

@Service
public class CapaciadServicioImplementacion implements CapacidadServicio {
	
	@Autowired
	CapacidadRepositorio capacidadRepositorio;

	@Override
	public Capacidad guardarCapacidad(Capacidad capacidad) {
		return capacidadRepositorio.save(capacidad);
	}

}
