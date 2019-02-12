package com.co.ceiba.ceibaadn.controlador.excepciones;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT)
public class ParqueaderoIngresoNoPosibleException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1106693727748056911L;
	
	private static final String MESSAGE = "Ingreso no posible. Parqueadero lleno, el vehículo esta en el parqueadero o su placa no esta habilitada para este dia";

	public ParqueaderoIngresoNoPosibleException() {
		super(MESSAGE);		
	}

}
