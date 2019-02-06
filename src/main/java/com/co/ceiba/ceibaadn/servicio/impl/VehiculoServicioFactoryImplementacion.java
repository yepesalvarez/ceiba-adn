package com.co.ceiba.ceibaadn.servicio.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.co.ceiba.ceibaadn.repositorio.CarroRepositorio;
import com.co.ceiba.ceibaadn.repositorio.MotoRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicioFactory;

@Service
public class VehiculoServicioFactoryImplementacion implements VehiculoServicioFactory {
	
	public static final String CARRO = "carro";
	public static final String MOTO = "moto";
	
	@Autowired
	private CarroRepositorio carroRepositorio;
	
	@Autowired
	private MotoRepositorio motoRepositorio;
	
	@Override
	public VehiculoServicio obtenerVehiculoServicio(String tipoVehiculo) {
		if(tipoVehiculo.equalsIgnoreCase(CARRO)) {
			return new CarroServicioImplementacion(carroRepositorio);
		}
		if(tipoVehiculo.equalsIgnoreCase(MOTO)) {
			return new MotoServicioImplementacion(motoRepositorio);
		}
		return null;
	}
	
}
