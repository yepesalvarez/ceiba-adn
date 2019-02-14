package com.co.ceiba.ceibaadn.controlador.excepciones;

public class ParqueaderoRetiroVehiculoNoPosibleException extends RuntimeException {
	
	private static final long serialVersionUID = 4683903556392531321L;
	private static final String MESSAGE = "El vehiculo no esta autorizado para salir por pagos pendientes o ya había sido retirado del parqueadero";

	public ParqueaderoRetiroVehiculoNoPosibleException() {
		super(MESSAGE);		
	}

}
