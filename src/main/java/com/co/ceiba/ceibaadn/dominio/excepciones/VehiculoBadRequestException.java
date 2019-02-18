package com.co.ceiba.ceibaadn.dominio.excepciones;

public class VehiculoBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 2608679225020530964L;
	
	private static final String MESSAGE = "Par�metros requeridos para la creaci�n del veh�culo incorrectos";

	public VehiculoBadRequestException() {
		super(MESSAGE);		
	}
}
