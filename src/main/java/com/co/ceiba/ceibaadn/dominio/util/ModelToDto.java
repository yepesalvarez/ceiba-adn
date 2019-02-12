package com.co.ceiba.ceibaadn.dominio.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.CobroServicio;

@Component
public class ModelToDto {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CobroServicio cobroServicio;
	
	public VehiculoDto vehiculoToVehiculoDto(Vehiculo vehiculo) {
		VehiculoDto vehiculoDto = modelMapper.map(vehiculo, VehiculoDto.class);
		vehiculoDto.setTipoVehiculo(vehiculo.getTipoVehiculo().getNombre());
		vehiculoDto.setFechaIngreso(cobroServicio.obtenerCobroPorVehiculo(vehiculo).getInicioParqueo());
		return vehiculoDto;
	}

}
