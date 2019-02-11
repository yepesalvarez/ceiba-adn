package com.co.ceiba.ceibaadn.dominio.dtos;

public class TipoVehiculoDto {
	
	private Long id;
	private String nombre;
	
	public TipoVehiculoDto(){}
	
	public TipoVehiculoDto(Long id, String nombre){
		this.id = id;
		this.nombre = nombre;
	}
	
	public TipoVehiculoDto(String nombre){
		this.nombre = nombre;
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

}
