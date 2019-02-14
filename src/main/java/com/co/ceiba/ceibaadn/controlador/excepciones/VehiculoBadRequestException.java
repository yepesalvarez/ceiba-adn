package com.co.ceiba.ceibaadn.controlador.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class VehiculoBadRequestException extends RuntimeException {

	private static final long serialVersionUID = 2608679225020530964L;
	
	private static final String MESSAGE = "Parámetros requeridos para la creación del vehículo incorrectos";

	public VehiculoBadRequestException() {
		super(MESSAGE);		
	}
}
