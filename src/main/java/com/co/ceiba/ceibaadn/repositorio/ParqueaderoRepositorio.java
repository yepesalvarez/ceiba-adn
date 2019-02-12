package com.co.ceiba.ceibaadn.repositorio;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.co.ceiba.ceibaadn.dominio.Parqueadero;

@Repository
public interface ParqueaderoRepositorio extends CrudRepository<Parqueadero, Long> {

}
