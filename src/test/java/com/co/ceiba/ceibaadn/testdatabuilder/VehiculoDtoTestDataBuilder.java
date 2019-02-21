package com.co.ceiba.ceibaadn.testdatabuilder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;

public class VehiculoDtoTestDataBuilder {
	
	static final String PLACA_VALIDA = "bsd234";
	static final String PLACA_INVALIDA = "bsd2345";
	static final String TIPO_VEHICULO_VALIDO_MOTO = "moto";
	static final String TIPO_VEHICULO_NO_VALIDO = "tren";
	static final int CILINDRAJE_SIN_RECARGO = 150;
	static final int HORAS_DIFERENCIA_INGRESO = 2;
	private static final String FORMATO_FECHA = "yyyy-MM-dd HH:mm";
		
	private String placa;
	private String tipoVehiculo;
	private int cilindraje;
	private String fechaIngreso;
	
	public VehiculoDtoTestDataBuilder() {
		this.placa = PLACA_VALIDA;
		this.tipoVehiculo = TIPO_VEHICULO_VALIDO_MOTO;
		this.cilindraje = CILINDRAJE_SIN_RECARGO;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		this.fechaIngreso = LocalDateTime.now().minusHours(HORAS_DIFERENCIA_INGRESO).format(formatter);
	}
	
	public VehiculoDtoTestDataBuilder conCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
		return this;
	}
	
	public VehiculoDtoTestDataBuilder conPlaca(String placa) {
		this.placa = placa;
		return this;
	}
	
	public VehiculoDtoTestDataBuilder conPlacaInvalida() {
		this.placa = PLACA_INVALIDA;
		return this;
	}
		
	public VehiculoDtoTestDataBuilder conTipoVehiculoInvalido() {
		this.tipoVehiculo = TIPO_VEHICULO_NO_VALIDO;
		return this;
	}
	
	public VehiculoDtoTestDataBuilder conFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		return this;
	}
	
	public VehiculoDto build() {
		return new VehiculoDto(this.placa, this.tipoVehiculo, this.cilindraje, this.fechaIngreso);
	}
	
	

}
