package com.co.ceiba.ceibaadn.controlador;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;
//import com.co.ceiba.ceibaadn.servicio.VehiculoServicioFactory;

@RestController
@RequestMapping(value = "/api")
public class VehiculoControladorRest extends AbstractControlador {

	@Autowired
	private FactoryVehiculo factoryVehiculo;
	
	@Autowired
	private TipoVehiculoServicio tipoVehiculoServicio;
	
//	@Autowired
//	private VehiculoServicioFactory vehiculoServicioFactory;
	
	@Autowired
	private VehiculoServicio vehiculoServicio;
		
	@PostMapping(value = "/vehiculo")
	public ResponseEntity<String> guardarVehiculo(@RequestBody VehiculoDto vehiculoDto){		
		
		String placa = vehiculoDto.getPlaca();
		String tipoVehiculoString = vehiculoDto.getTipoVehiculo();
		TipoVehiculo tipoVehiculo = tipoVehiculoServicio.obtenerPorNombre(tipoVehiculoString);
		if ((placa == null || placa.equals("") || placa.length() > 6) || (tipoVehiculoString == null 
				|| tipoVehiculoString.equals("")) || tipoVehiculo == null) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		
		Vehiculo vehiculo;
		
		//VehiculoServicio vehiculoServicio = vehiculoServicioFactory.obtenerVehiculoServicio(tipoVehiculoString);
		vehiculo = vehiculoServicio.obtenerVehiculoPorPlaca(placa);
		if(vehiculo != null) {
			return new ResponseEntity<>(getStatusConflict(), HttpStatus.CONFLICT);
		}
		
		vehiculo = factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculo);
		if (vehiculo == null) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		
		vehiculo = vehiculoServicio.guardarVehiculo(vehiculo);
		if(vehiculo == null) {
			return new ResponseEntity<>(getStatusBadRequest(), HttpStatus.BAD_REQUEST);
		}
		
		return new ResponseEntity<>(getStatusOK(), HttpStatus.OK);
		
	}
	
}
