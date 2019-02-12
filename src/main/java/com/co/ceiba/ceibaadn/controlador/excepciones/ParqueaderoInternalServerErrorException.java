package com.co.ceiba.ceibaadn.controlador.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
public class ParqueaderoInternalServerErrorException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3565193523255286595L;
	
	private static final String MESSAGE = "Oops! algo salió mal, trabajamos para resolverlo";

	public ParqueaderoInternalServerErrorException() {
		super(MESSAGE);		
	}

}
