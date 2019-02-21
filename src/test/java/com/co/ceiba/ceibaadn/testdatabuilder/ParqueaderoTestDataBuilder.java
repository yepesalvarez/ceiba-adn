package com.co.ceiba.ceibaadn.testdatabuilder;

import java.util.Set;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;

public class ParqueaderoTestDataBuilder {
	
	static final int MINIMO_HORAS_DIA = 9;
	static final int MAXIMO_HORAS_DIA = 24;
	
	private int minimoHorasDia;
	private int maximoHorasDia;
	private Set<Capacidad> capacidades;
	private Set<Cobro> cobros;
	
	public ParqueaderoTestDataBuilder() {
		this.minimoHorasDia = MINIMO_HORAS_DIA;
		this.maximoHorasDia = MAXIMO_HORAS_DIA;
	}
	
	public ParqueaderoTestDataBuilder conCapacidades(Set<Capacidad> capacidades) {
		this.capacidades = capacidades;
		return this;
	}

	public ParqueaderoTestDataBuilder conCobros(Set<Cobro> cobros) {
		this.cobros = cobros;
		return this;
	}
	
	public Parqueadero build() {
		Parqueadero parqueadero = new Parqueadero();
		parqueadero.setCapacidades(capacidades);
		parqueadero.setCobros(cobros);
		parqueadero.setMinimoHorasDia(minimoHorasDia);
		parqueadero.setMaximoHorasDia(maximoHorasDia);
		return parqueadero;
	}
	
	
}
