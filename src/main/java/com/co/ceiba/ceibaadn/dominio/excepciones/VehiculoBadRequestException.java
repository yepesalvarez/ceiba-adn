package com.co.ceiba.ceibaadn.dominio.excepciones;

public class VehiculoBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 2608679225020530964L;
	
	private static final String MESSAGE = "Parámetros requeridos para la creación del vehículo incorrectos";

	public VehiculoBadRequestException() {
		super(MESSAGE);		
	}
}
