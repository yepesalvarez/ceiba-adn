package com.co.ceiba.ceibaadn.dominio.dtos;

public class VehiculoDto {
	
	Long id;
	String placa;
	String tipoVehiculo;
	int cilindraje;
	
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
	
	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}

}
