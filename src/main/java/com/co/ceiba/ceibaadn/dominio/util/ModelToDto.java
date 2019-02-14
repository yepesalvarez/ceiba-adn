package com.co.ceiba.ceibaadn.dominio.util;

import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.CobroServicio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;

@Component
public class ModelToDto {
	
	@Autowired
	ModelMapper modelMapper;
	
	@Autowired
	CobroServicio cobroServicio;
	
	@Autowired
	TipoVehiculoServicio tipoVehiculoServicio;
	
	@Autowired
	FactoryVehiculo factoryVehiculo;
	
	public VehiculoDto vehiculoToVehiculoDto(Vehiculo vehiculo) {
		VehiculoDto vehiculoDto = modelMapper.map(vehiculo, VehiculoDto.class);
		vehiculoDto.setTipoVehiculo(vehiculo.getTipoVehiculo().getNombre());
		vehiculoDto.setFechaIngreso(cobroServicio.obtenerCobroPorVehiculo(vehiculo).getInicioParqueo());
		return vehiculoDto;
	}
	
	public Set<VehiculoDto> vehiculosToVehiculosDto(Set<Vehiculo> vehiculos) {
		return vehiculos.stream()
				.map(this::vehiculoToVehiculoDto)
				.collect(Collectors.toSet());
	}
	
	public Vehiculo vehiculoDtoToVehiculo(VehiculoDto vehiculoDto) {
		TipoVehiculo tipoVehiculo = tipoVehiculoServicio.obtenerPorNombre(vehiculoDto.getTipoVehiculo());
		return factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculo);
	}

}
