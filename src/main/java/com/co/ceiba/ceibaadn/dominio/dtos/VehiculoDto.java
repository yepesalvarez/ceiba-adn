package com.co.ceiba.ceibaadn.dominio.dtos;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class VehiculoDto {
	
	Long id;
	String placa;
	String tipoVehiculo;
	int cilindraje;
	String fechaIngreso;
	
	public VehiculoDto () {}
	
	public VehiculoDto (String placa, String tipoVehiculo) {
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public VehiculoDto (String placa, String tipoVehiculo, int cilindraje) {
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
		this.cilindraje = cilindraje;
	}
	
	public VehiculoDto (Long id, String placa, String tipoVehiculo) {
		this.id = id;
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public VehiculoDto (Long id, String placa, String tipoVehiculo, int cilindraje) {
		this.id = id;
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
		this.cilindraje = cilindraje;
	}
	
	public VehiculoDto (String placa, String tipoVehiculo, String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public VehiculoDto (String placa, String tipoVehiculo, int cilindraje, String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
		this.cilindraje = cilindraje;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public String getPlaca() {
		return placa;
	}
	
	public void setPlaca(String placa) {
		this.placa = placa;
	}
	
	public String getTipoVehiculo() {
		return tipoVehiculo;
	}
	
	public void setTipoVehiculo(String tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public String getFechaIngreso() {
		return fechaIngreso;
	}

	public void setFechaIngreso(LocalDateTime fechaIngreso) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
		this.fechaIngreso = fechaIngreso.format(formatter);
	}

	public void setFechaIngreso(String fechaIngreso) {
		this.fechaIngreso = fechaIngreso;
	}

	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

}
