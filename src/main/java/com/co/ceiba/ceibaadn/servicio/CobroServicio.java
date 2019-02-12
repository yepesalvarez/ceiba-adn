package com.co.ceiba.ceibaadn.servicio;

import java.util.List;

import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public interface CobroServicio {
	
	double calcularCobro(Long idVehiculo, String fechaFinParqueo);
	Cobro guardarCobro(Cobro cobro);
	Cobro obtenerCobroPorVehiculo(Vehiculo vehiculo);
	Cobro obtenerCobroPorId(Long idCobro);
	List<Cobro> obtenerCobroPorEstado(String estadoCobro);
	List<Cobro> obtenerTodosCobro();
	
}
