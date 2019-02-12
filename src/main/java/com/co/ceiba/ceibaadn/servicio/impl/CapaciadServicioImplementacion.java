package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.repositorio.CapacidadRepositorio;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;

@Service
public class CapaciadServicioImplementacion implements CapacidadServicio {
	
	@Autowired
	CapacidadRepositorio capacidadRepositorio;

	@Override
	public Capacidad obtenerCapacidadPorId(Long idCapacidad) {
		return capacidadRepositorio.findById(idCapacidad).orElse(null);
	}

	@Override
	public Capacidad obtenerCapacidadPorTipoVehiculo(TipoVehiculo tipoVehiculo) {
		return capacidadRepositorio.findByTipoVehiculo(tipoVehiculo);
	}

	@Override
	public List<Capacidad> obtenerTodasCapacidad() {
		return (List<Capacidad>) capacidadRepositorio.findAll();
	}

	@Override
	public Capacidad guardarCapacidad(Capacidad capacidad) {
		return capacidadRepositorio.save(capacidad);
	}

	@Override
	public void eliminarCapacidad(Capacidad capacidad) {
		 capacidadRepositorio.delete(capacidad);
	}

	@Override
	public void eliminarCapacidad(Long idCapacidad) {
		capacidadRepositorio.deleteById(idCapacidad);
	}

}
