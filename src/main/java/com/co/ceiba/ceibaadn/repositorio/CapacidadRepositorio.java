package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;

@Repository
public interface CapacidadRepositorio extends CrudRepository<Capacidad, Long> {

	Capacidad findByTipoVehiculo(TipoVehiculo tipovehiculo);
	
}
