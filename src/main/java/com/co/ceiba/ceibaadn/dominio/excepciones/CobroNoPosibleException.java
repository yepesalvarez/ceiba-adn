package com.co.ceiba.ceibaadn.dominio.excepciones;

public class CobroNoPosibleException extends RuntimeException{

	private static final long serialVersionUID = -8735747766814649240L;

	private static final String MESSAGE = "El cobro no se generó/realizó porque el vehículo no se encuentra registrado o los datos entregados son inválidos";

	public CobroNoPosibleException() {
		super(MESSAGE);		
	}
	
}
