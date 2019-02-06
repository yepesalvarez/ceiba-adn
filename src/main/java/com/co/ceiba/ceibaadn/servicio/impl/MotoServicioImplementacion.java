package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.repositorio.MotoRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

//@Service
public class MotoServicioImplementacion implements VehiculoServicio   {

	MotoRepositorio motoRepositorio;
	
	public MotoServicioImplementacion(MotoRepositorio motoRepositorio) {
		this.motoRepositorio = motoRepositorio;
	}
	
	@Override
	public List<Vehiculo> obtenerTodosVehiculos() {
		List<Vehiculo> motos = new ArrayList<>();
		Iterator<Moto> iterator = motoRepositorio.findAll().iterator();
		while (iterator.hasNext()) {
			motos.add(iterator.next());
		}
		return motos;
	}

	@Override
	public Vehiculo obtenerVehiculoPorPlaca(String placa) {
		return motoRepositorio.findByPlaca(placa.toLowerCase());
	}

	@Override
	public Vehiculo obtenerVehiculoPorId(Long idVehiculo) {
		return motoRepositorio.findById(idVehiculo).orElse(null);
	}

	@Override
	public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
		 return motoRepositorio.save((Moto) vehiculo);
	}

	@Override
	public void eliminarVehiculo(Vehiculo vehiculo) {
		motoRepositorio.delete((Moto) vehiculo);
	}

	@Override
	public void eliminarVehiculo(Long idVehiculo) {
		motoRepositorio.deleteById(idVehiculo);
	}
		
}
