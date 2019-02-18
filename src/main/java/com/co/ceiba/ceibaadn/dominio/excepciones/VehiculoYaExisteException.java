package com.co.ceiba.ceibaadn.dominio.excepciones;

public class VehiculoYaExisteException extends RuntimeException {

	private static final long serialVersionUID = 3723079203457854510L;

	private static final String MESSAGE = "El veh�culo que intenta crear ya existe";

	public VehiculoYaExisteException() {
		super(MESSAGE);		
	}
	
}
