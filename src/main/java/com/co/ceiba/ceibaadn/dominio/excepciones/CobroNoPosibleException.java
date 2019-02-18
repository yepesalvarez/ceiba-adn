package com.co.ceiba.ceibaadn.dominio.excepciones;

public class CobroNoPosibleException extends RuntimeException{

	private static final long serialVersionUID = -8735747766814649240L;

	private static final String MESSAGE = "El cobro no se gener�/realiz� porque el veh�culo no se encuentra registrado o los datos entregados son inv�lidos";

	public CobroNoPosibleException() {
		super(MESSAGE);		
	}
	
}
