package com.co.ceiba.ceibaadn.controlador;

import java.util.List;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.util.ModelToDto;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RestController
@RequestMapping(value = "/api")
public class VehiculoControladorRest {
	
	@Autowired
	Environment env;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	ModelToDto modelToDto;
	
	@PostMapping(value = "/vehiculo", produces = "application/json")
	public ResponseEntity<String> guardarVehiculo(@RequestBody VehiculoDto vehiculoDto){		
		try {
			vehiculoServicio.guardarVehiculo(vehiculoDto);
			return new ResponseEntity<>(JSONObject.quote(env.getProperty("controller.status.ok")), HttpStatus.OK);
		} catch (VehiculoBadRequestException | VehiculoYaExisteException e) {
			return new ResponseEntity<>(JSONObject.quote(e.getMessage()), HttpStatus.BAD_REQUEST);
		}
		
	}
	
	@GetMapping(value = "/vehiculo", produces = "application/json")
	public ResponseEntity<List<VehiculoDto>> obtenerTodosVehiculo(){
		return new ResponseEntity<>(vehiculoServicio.obtenerTodosVehiculos().stream()
					.map(vehiculo -> modelToDto.vehiculoToVehiculoDto(vehiculo)).collect(Collectors.toList()), HttpStatus.OK);
	}
	
}
