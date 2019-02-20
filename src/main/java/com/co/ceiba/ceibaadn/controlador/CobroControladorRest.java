package com.co.ceiba.ceibaadn.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.servicio.CobroServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@CrossOrigin(origins = { "http://localhost:4200" })
@RestController
public class CobroControladorRest {
	
	@Autowired
	CobroServicio cobroServicio;

	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@PostMapping(value = "/api/cobro")
	public double generarCobro(@RequestParam("idVehiculo") Long idVehiculo, @RequestParam("finParqueo") String finParqueo){
			return cobroServicio.calcularCobro(idVehiculo, finParqueo);
	}
}
