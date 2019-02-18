package com.co.ceiba.ceibaadn.dominio.excepciones;

public class ParqueaderoInternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = -3565193523255286595L;
	
	private static final String MESSAGE = "Oops! algo salió mal, trabajamos para resolverlo";

	public ParqueaderoInternalServerErrorException() {
		super(MESSAGE);		
	}

}
