package com.co.ceiba.ceibaadn.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RestController
@RequestMapping(value = "/api")
public class VehiculoControladorRest {
	
	@Autowired
	Environment env;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@PostMapping(value = "/vehiculo")
	public ResponseEntity<String> guardarVehiculo(@RequestBody VehiculoDto vehiculoDto){		
		try {
			vehiculoServicio.guardarVehiculo(vehiculoDto);
			return new ResponseEntity<>(env.getProperty("controller.status.ok"), HttpStatus.OK);
		} catch (VehiculoBadRequestException | VehiculoYaExisteException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		
	}
	
}
