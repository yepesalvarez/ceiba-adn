package com.co.ceiba.ceibaadn.controlador;

import java.util.Set;
import java.util.stream.Collectors;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.controlador.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoInternalServerErrorException;
import com.co.ceiba.ceibaadn.controlador.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.util.ModelToDto;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RestController
public class ParqueaderoControladorRest {
	
	@Autowired
	Environment env;
	
	@Autowired
	ParqueaderoServicio parqueaderoServicio;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@Autowired
	ModelToDto modelToDto;

	@GetMapping(value = "/", produces = "application/json")
	public ResponseEntity<Set<Vehiculo>> obtenerParqueadero() {
		try {
			return new ResponseEntity<>(parqueaderoServicio.actualizarRangos(), HttpStatus.OK);
		}catch(ParqueaderoInternalServerErrorException e) {
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}
	
	@PostMapping(value = "/api/parqueadero", produces = "application/json")
	public ResponseEntity<Set<VehiculoDto>> ingresarVehiculoParqueadero(@RequestBody VehiculoDto vehiculoDto) {
		try {
			parqueaderoServicio.actualizarRangos();
			return new ResponseEntity<>(parqueaderoServicio.agregarVehiculo(
					vehiculoServicio.guardarVehiculo(vehiculoDto))
					.stream().map(vehiculo -> modelToDto.vehiculoToVehiculoDto(vehiculo))
					.collect(Collectors.toSet())
					, HttpStatus.OK);
		} catch (VehiculoBadRequestException | VehiculoYaExisteException | ParqueaderoIngresoNoPosibleException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@DeleteMapping(value = "/api/parqueadero/{idVehiculo}", produces = "application/json")
	public ResponseEntity<Set<VehiculoDto>> retirarVehiculo(@PathVariable ("idVehiculo") Long idVehiculo){
		try {
			return new ResponseEntity<>(parqueaderoServicio.retirarVehiculo(
					vehiculoServicio.obtenerVehiculoPorId(idVehiculo))
					.stream().map(vehiculo -> modelToDto.vehiculoToVehiculoDto(vehiculo))
					.collect(Collectors.toSet())
					, HttpStatus.OK);
		}catch(ParqueaderoRetiroVehiculoNoPosibleException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
	
	@GetMapping(value = "/api/parqueadero", produces = "application/json")
	public ResponseEntity<Set<VehiculoDto>> obtenerVehiculosEnParqueadero(){
		parqueaderoServicio.actualizarRangos();
		return new ResponseEntity<>(parqueaderoServicio.obtenerVehiculosParqueados()
				.stream().map(vehiculo -> modelToDto.vehiculoToVehiculoDto(vehiculo))
				.collect(Collectors.toSet())
				, HttpStatus.OK);
	}
	
	@PatchMapping(value = "/api/parqueadero", produces = "application/json")
	public ResponseEntity<String> pagarParqueadero(@RequestParam("idVehiculo") Long idVehiculo){
		try {
			parqueaderoServicio.pagarParqueadero(idVehiculo);
			return new ResponseEntity<>(JSONObject.quote(env.getProperty("controller.status.ok")), HttpStatus.OK);
		}catch(CobroNoPosibleException e){
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
	}
	
}
