package com.co.ceiba.ceibaadn.controlador;

import java.util.Set;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class ParqueaderoControladorRest {
	
	@Autowired
	Environment env;
	
	@Autowired
	ParqueaderoServicio parqueaderoServicio;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@GetMapping(value = "/")
	public Set<VehiculoDto> obtenerParqueadero() {
		return parqueaderoServicio.actualizarRangos();
	}
	
	@PostMapping(value = "/api/parqueadero")
	public Set<VehiculoDto> ingresarVehiculoParqueadero(@RequestBody VehiculoDto vehiculoDto) {
		parqueaderoServicio.actualizarRangos();
		return parqueaderoServicio.ingresarVehiculo(vehiculoDto);
	}
	
	@DeleteMapping(value = "/api/parqueadero/{idVehiculo}")
	public Set<VehiculoDto> retirarVehiculo(@PathVariable ("idVehiculo") Long idVehiculo){
		return parqueaderoServicio.retirarVehiculo(idVehiculo);
	}
	
	@GetMapping(value = "/api/parqueadero")
	public Set<VehiculoDto> obtenerVehiculosEnParqueadero(){
		parqueaderoServicio.actualizarRangos();
		return parqueaderoServicio.obtenerVehiculosParqueados();
	}
	
	@PatchMapping(value = "/api/parqueadero")
	public String pagarParqueadero(@RequestParam("idVehiculo") Long idVehiculo){
		parqueaderoServicio.pagarParqueadero(idVehiculo);
		return JSONObject.quote(env.getProperty("controller.status.ok"));
	}
	
}
