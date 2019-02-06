package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Moto;

@Repository
public interface MotoRepositorio extends CrudRepository<Moto, Long> {
	
	Moto findByPlaca(String placa);

}
