package com.co.ceiba.ceibaadn.controlador.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class VehiculoYaExisteException extends RuntimeException {

	private static final long serialVersionUID = 3723079203457854510L;

	private static final String MESSAGE = "El vehículo que intenta crear ya existe";

	public VehiculoYaExisteException() {
		super(MESSAGE);		
	}
	
}
