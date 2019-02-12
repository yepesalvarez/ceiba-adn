package com.co.ceiba.ceibaadn.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.controlador.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.servicio.CobroServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RestController
public class CobroControladorRest {
	
	@Autowired
	CobroServicio cobroServicio;

	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@GetMapping(value = "/api/cobro")
	public ResponseEntity<Double> generarCobro(@RequestParam("idVehiculo") Long idVehiculo, @RequestParam("finParqueo") String finParqueo){
		try {
			return new ResponseEntity<>(cobroServicio.calcularCobro(idVehiculo, finParqueo), HttpStatus.OK);
		}catch(CobroNoPosibleException e) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
	}
}
