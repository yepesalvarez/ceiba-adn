package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;

@Repository
public interface VehiculoRepositorio extends CrudRepository<Vehiculo, Long> {

	Vehiculo findByPlaca(String placa);
	
}
