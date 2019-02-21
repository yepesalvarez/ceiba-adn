package com.co.ceiba.ceibaadn.testdatabuilder;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

public class CapacidadTestDataBuilder {
	
	static final int LIMITE_PARQUEADERO = 1;
	static final double VALOR_HORA_PARQUEADERO = 500;
	static final double VALOR_DIA_PARQUEADERO = 4000;
	
	TipoVehiculo tipoVehiculo;
	int limite;
	double valorHora;
	double valorDia;
	
	public CapacidadTestDataBuilder() {
		this.limite = LIMITE_PARQUEADERO;
		this.valorDia = VALOR_DIA_PARQUEADERO;
		this.valorHora = VALOR_HORA_PARQUEADERO;
		TipoVehiculoTestDataBuilder tipoVehiculoTestDataBuilder = new TipoVehiculoTestDataBuilder(); 
		this.tipoVehiculo = tipoVehiculoTestDataBuilder.build();
	}
	
	public CapacidadTestDataBuilder conLimite(int limite) {
		this.limite = limite;
		return this;
	}
		
	public CapacidadTestDataBuilder conValorDia(double valorDia) {
		this.valorDia = valorDia;
		return this;
	}
	
	public CapacidadTestDataBuilder conValorHora(double valorHora) {
		this.valorHora = valorHora;
		return this;
	}
	
	public CapacidadTestDataBuilder conTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
		return this;
	}
	
	public Capacidad build() {
		Capacidad capacidad = new Capacidad();
		capacidad.setLimite(this.limite);
		capacidad.setTipoVehiculo(this.tipoVehiculo);
		capacidad.setValorDia(this.valorDia);
		capacidad.setValorHora(this.valorHora);
		return capacidad;
	}

}
