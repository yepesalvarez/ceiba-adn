package com.co.ceiba.ceibaadn.controlador.excepciones;

import org.jboss.logging.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.co.ceiba.ceibaadn.dominio.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoInternalServerErrorException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoYaExisteException;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {
	 
	public static final Logger LOGGER_EXCEPTION = Logger.getLogger(ApiExceptionHandler.class);
	
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler ({
		VehiculoIngresoNoPosibleException.class, VehiculoYaExisteException.class, ParqueaderoIngresoNoPosibleException.class,
		ParqueaderoRetiroVehiculoNoPosibleException.class, CobroNoPosibleException.class
	})
	@ResponseBody
	public ErrorMessage badRequest(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception);
		LOGGER_EXCEPTION.error(errorMessage);
		return errorMessage;
	}
	
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	@ExceptionHandler ({
		ParqueaderoInternalServerErrorException.class
	})
	@ResponseBody
	public ErrorMessage internalServerError(Exception exception) {
		ErrorMessage errorMessage = new ErrorMessage(exception);
		LOGGER_EXCEPTION.error(errorMessage);
		return errorMessage;
	}

}
