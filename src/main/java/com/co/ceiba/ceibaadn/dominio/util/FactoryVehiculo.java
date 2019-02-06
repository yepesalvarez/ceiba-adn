package com.co.ceiba.ceibaadn.dominio.util;
import org.springframework.stereotype.Component;

import com.co.ceiba.ceibaadn.dominio.Carro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;

@Component
public class FactoryVehiculo {
		
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

}
