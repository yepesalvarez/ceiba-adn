package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "carros")
public class Carro extends Vehiculo {
	
	public Carro(String placa, TipoVehiculo tipoVehiculo) {
		super(placa, tipoVehiculo);
	}
	
	public Carro(){}

}
