package com.co.ceiba.ceibaadn.dominio.util;
import java.util.HashSet;
import java.util.Set;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Carro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;

@Component
public class FactoryVehiculo {
	
	@Autowired
	CapacidadServicio capacidadServicio;
	
	@Autowired
	TipoVehiculoServicio tipoVehiculoServicio;
	
	@Autowired
	Environment env;
	
	@Autowired
	ModelMapper modelMapper;
	
	private FactoryVehiculo() {}
	
	public static final String CARRO = "carro";
	public static final String MOTO = "moto";
	
	public Vehiculo getVehiculo(VehiculoDto vehiculo, TipoVehiculo tipoVehiculo) {
		
		if (vehiculo.getTipoVehiculo().equalsIgnoreCase(CARRO)) {
			return new Carro(vehiculo.getPlaca(), tipoVehiculo);
		}
		if (vehiculo.getTipoVehiculo().equalsIgnoreCase(MOTO)) {
			return new Moto(vehiculo.getPlaca(), tipoVehiculo, vehiculo.getCilindraje());
		}
		return null;
	}
	
	public Set<Capacidad> getCapacidadesParqueadero() {
		
		Capacidad capacidadTipoVehiculo;
		
		Set<Capacidad> capacidadesParqueadero = new HashSet<>();
		
		capacidadTipoVehiculo = new Capacidad();
		capacidadTipoVehiculo.setLimite(Integer.parseInt(env.getProperty("vehiculos.carro.limite")));
		capacidadTipoVehiculo.setTipoVehiculo(tipoVehiculoServicio.obtenerPorNombre(CARRO));
		capacidadTipoVehiculo.setValorHora(Double.parseDouble(env.getProperty("vehiculos.carro.hora.precio")));
		capacidadTipoVehiculo.setValorDia(Double.parseDouble(env.getProperty("vehiculos.carro.dia.precio")));
		capacidadesParqueadero.add(capacidadServicio.guardarCapacidad(capacidadTipoVehiculo));
		
		capacidadTipoVehiculo = new Capacidad();
		capacidadTipoVehiculo.setLimite(Integer.parseInt(env.getProperty("vehiculos.carro.limite")));
		capacidadTipoVehiculo.setTipoVehiculo(tipoVehiculoServicio.obtenerPorNombre(MOTO));
		capacidadTipoVehiculo.setValorHora(Double.parseDouble(env.getProperty("vehiculos.carro.hora.precio")));
		capacidadTipoVehiculo.setValorDia(Double.parseDouble(env.getProperty("vehiculos.carro.dia.precio")));
		capacidadServicio.guardarCapacidad(capacidadTipoVehiculo);
		capacidadesParqueadero.add(capacidadServicio.guardarCapacidad(capacidadTipoVehiculo));
		
		return capacidadesParqueadero;
		
	}
	
	public double getRecargoVehiculo(Vehiculo vehiculo) {
		
		if(modelMapper.map(vehiculo, VehiculoDto.class).getCilindraje() > Double.parseDouble(env.getProperty("vehiculos.moto.cilindraje,maximo"))) {
				return Double.parseDouble(env.getProperty("vehiculos.moto.cilndraje.recargo"));
		}
		
		return 0;
		
	}
	
	
}
