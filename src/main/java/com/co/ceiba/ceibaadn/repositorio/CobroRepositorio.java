package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

@Repository
public interface CobroRepositorio extends CrudRepository<Cobro, Long> {
	
	Cobro findByVehiculo(Vehiculo vehiculo);
	Iterable<Cobro> findByEstado(String estado);

}
