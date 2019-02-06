package com.co.ceiba.ceibaadn.servicio.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

//import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.dominio.Carro;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.repositorio.CarroRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

//@Service
public class CarroServicioImplementacion implements VehiculoServicio {

	CarroRepositorio carroRepositorio;
	
	public CarroServicioImplementacion(CarroRepositorio carroRepositorio){
		this.carroRepositorio = carroRepositorio;
	}
	
	@Override
	public List<Vehiculo> obtenerTodosVehiculos() {
		//return (List<Vehiculo>) carroRepositorio.findAll();
		List<Vehiculo> carros = new ArrayList<>();
		Iterator<Carro> iterator = carroRepositorio.findAll().iterator();
		while (iterator.hasNext()) {
			carros.add(iterator.next());
		}
		return carros;
	}

	@Override
	public Vehiculo obtenerVehiculoPorPlaca(String placa) {
		return carroRepositorio.findByPlaca(placa.toLowerCase());
	}

	@Override
	public Vehiculo obtenerVehiculoPorId(Long idVehiculo) {
		return carroRepositorio.findById(idVehiculo).orElse(null);
	}

	@Override
	public Vehiculo guardarVehiculo(Vehiculo vehiculo) {
		 return carroRepositorio.save((Carro) vehiculo);
	}

	@Override
	public void eliminarVehiculo(Vehiculo vehiculo) {
		carroRepositorio.delete((Carro) vehiculo);
	}

	@Override
	public void eliminarVehiculo(Long idVehiculo) {
		carroRepositorio.deleteById(idVehiculo);
	}
	
}
