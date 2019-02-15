package com.co.ceiba.ceibaadn.servicio.impl;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import com.co.ceiba.ceibaadn.controlador.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.CobroRepositorio;
import com.co.ceiba.ceibaadn.servicio.CobroServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@Service
public class CobroServicioImplementacion implements CobroServicio {

	public CobroServicioImplementacion(CobroRepositorio cobroRepositorio, VehiculoServicio vehiculoServicio,
			FactoryVehiculo factoryVehiculo) {
		this.cobroRepositorio = cobroRepositorio;
		this.vehiculoServicio = vehiculoServicio;
		this.factoryVehiculo = factoryVehiculo;
	}

	public int getDiasCobrar() {
		return diasCobrar;
	}

	public void setDiasCobrar(int diasCobrar) {
		this.diasCobrar = diasCobrar;
	}

	public int getHorasCobrar() {
		return horasCobrar;
	}

	public void setHorasCobrar(int horasCobrar) {
		this.horasCobrar = horasCobrar;
	}

	@Autowired
	CobroRepositorio cobroRepositorio;
	
	@Autowired
	VehiculoServicio vehiculoServicio;
	
	@Autowired
	FactoryVehiculo factoryVehiculo;
	
	private int diasCobrar = 0;
	private int horasCobrar = 0;
	
	@Override
	public Cobro guardarCobro(Cobro cobro) {
		return cobroRepositorio.save(cobro);
	}

	@Override
	public Cobro obtenerCobroPorVehiculo(Vehiculo vehiculo) {
		return cobroRepositorio.findByVehiculo(vehiculo);
	}

	@Override
	public double calcularCobro(Long idVehiculo, String fechaFinParqueoString) {
		try {
			if (idVehiculo == null || fechaFinParqueoString == null) {
				throw new CobroNoPosibleException();
			}
			Vehiculo vehiculo = vehiculoServicio.obtenerVehiculoPorId(idVehiculo);
			if (vehiculo == null) {
				throw new CobroNoPosibleException();
			}
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
			LocalDateTime fechaFinParqueo = LocalDateTime.parse(fechaFinParqueoString, formatter);
			Cobro cobro = obtenerCobroPorVehiculo(vehiculo);
			cobro.setFinParqueo(fechaFinParqueo);
			LocalDateTime tempDateTime = LocalDateTime.from(cobro.getInicioParqueo());
			long segundosParqueo = tempDateTime.until(fechaFinParqueo, ChronoUnit.SECONDS);
			int horasParqueo = (int) segundosParqueo / 3600;
			if (segundosParqueo % 3600 != 0) {
				horasParqueo+=1;
			}
			calcularTiempo(horasParqueo);
			double netoPagar = 0;
			for (Capacidad capacidad : Parqueadero.getInstance().getCapacidades()) {
				if (vehiculo.getTipoVehiculo().equals(capacidad.getTipoVehiculo())) {
					netoPagar += capacidad.getValorDia() * diasCobrar;
					netoPagar += capacidad.getValorHora() * horasCobrar;
					netoPagar += factoryVehiculo.getRecargoVehiculo(vehiculo);
				}
			}
			cobro.setValorPagar(netoPagar);
			guardarCobro(cobro);
			return netoPagar;		
		} catch(DateTimeParseException | CobroNoPosibleException e) {
			throw new CobroNoPosibleException();
		}
	}
	
	public void calcularTiempo(int horasBase) {
		if (horasBase > Parqueadero.getInstance().getMinimoHorasDia()) {
			this.diasCobrar++;
			if(horasBase > Parqueadero.getInstance().getMaximoHorasDia()) {
				int horasRestantes = horasBase - 24;
				calcularTiempo(horasRestantes);
			}else {
				this.horasCobrar = 24 - horasBase;
			}
		}else {
			this.horasCobrar = horasBase;
		}
	}

}
