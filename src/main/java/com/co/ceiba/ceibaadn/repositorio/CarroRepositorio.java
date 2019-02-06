package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Carro;

@Repository
public interface CarroRepositorio extends CrudRepository<Carro, Long> {

	Carro findByPlaca(String placa);
	
}
