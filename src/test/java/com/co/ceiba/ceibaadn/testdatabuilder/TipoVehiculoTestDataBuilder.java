package com.co.ceiba.ceibaadn.testdatabuilder;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

public class TipoVehiculoTestDataBuilder {
	
	static final String TIPO_VEHICULO = "moto";
	
	private String nombre;
	
	public TipoVehiculoTestDataBuilder() {
		this.nombre = TIPO_VEHICULO;
	}
	
	public TipoVehiculoTestDataBuilder conNombre(String nombre) {
		this.nombre = nombre;
		return this;
	}
	
	public TipoVehiculo build() {
		TipoVehiculo tipoVehiculo = new TipoVehiculo();
		tipoVehiculo.setNombre(this.nombre);
		return tipoVehiculo;
	}
	
	
	

}
