package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

@Repository
public interface TipoVehiculoRepositorio extends CrudRepository<TipoVehiculo, Long> {
	
	TipoVehiculo findByNombre(String nombre);
	
}
