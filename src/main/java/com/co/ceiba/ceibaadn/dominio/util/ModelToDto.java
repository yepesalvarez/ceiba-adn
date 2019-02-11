package com.co.ceiba.ceibaadn.dominio.util;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;

@Component
public class ModelToDto {
	
	@Autowired
	ModelMapper modelMapper;
	
	public VehiculoDto vehiculoToVehiculoDto(Vehiculo vehiculo) {
		VehiculoDto vehiculoDto = modelMapper.map(vehiculo, VehiculoDto.class);
		vehiculoDto.setTipoVehiculo(vehiculo.getTipoVehiculo().getNombre());
		return vehiculoDto;
	}

}
