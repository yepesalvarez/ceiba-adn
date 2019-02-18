package com.co.ceiba.ceibaadn.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@Service
public class VehiculoServicioImplementacion implements VehiculoServicio {
	
	@Autowired
	VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	TipoVehiculoServicio tipoVehiculoServicio;
	
	public VehiculoServicioImplementacion(VehiculoRepositorio vehiculoRepositorio,
			TipoVehiculoServicio tipoVehiculoServicio) {
		this.vehiculoRepositorio = vehiculoRepositorio;
		this.tipoVehiculoServicio = tipoVehiculoServicio;
	}

	@Override
	public Vehiculo obtenerVehiculoPorPlaca(String placa) {
		return vehiculoRepositorio.findByPlaca(placa);
	}

	@Override
	public Vehiculo obtenerVehiculoPorId(Long idVehiculo) {
		return vehiculoRepositorio.findById(idVehiculo).orElse(null);
	}

	@Override
	public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
		return vehiculoRepositorio.save(vehiculo);
	}
	
}
