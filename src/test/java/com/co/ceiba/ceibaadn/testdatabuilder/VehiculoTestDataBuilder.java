package com.co.ceiba.ceibaadn.testdatabuilder;

import com.co.ceiba.ceibaadn.dominio.Carro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public class VehiculoTestDataBuilder {
	
	static final int CILINDRAJE_SIN_RECARGO = 150;
	static final String PLACA_VALIDA = "bsd234";
	
	private String placa;
	private TipoVehiculo tipoVehiculo;
	
	public VehiculoTestDataBuilder() {
		this.placa = PLACA_VALIDA;
		TipoVehiculoTestDataBuilder testDataBuilder = new TipoVehiculoTestDataBuilder();
		this.tipoVehiculo = testDataBuilder.build();
	}
	
	public VehiculoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoTestDataBuilder conTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public Vehiculo buildMoto(int cilindraje) {
		Moto moto = new Moto();
		moto.setCilindraje(cilindraje);
		moto.setPlaca(placa);
		moto.setTipoVehiculo(tipoVehiculo);
		return moto;
	}
	
	public Vehiculo buildCarro() {
		Carro carro = new Carro();
		carro.setPlaca(placa);
		carro.setTipoVehiculo(tipoVehiculo);
		return carro;
	}
	
	
	
	

}
