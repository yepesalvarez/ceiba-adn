package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Entity;

@Entity
public class Carro extends Vehiculo {
	
	public Carro(String placa, TipoVehiculo tipoVehiculo) {
		super(placa, tipoVehiculo);
	}
	
	public Carro(){}

}
