package com.co.ceiba.ceibaadn.testdatabuilder;

import java.time.LocalDateTime;

import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.enums.EstadoCobro;

public class CobroTestDataBuilder {
	
	static final int DIFERENCIA_HORAS_INICIO_PARQUEO = 2;
	
	private String estado;
	private LocalDateTime inicioParqueo;
	private LocalDateTime finParqueo;
	private double valorPagar;
	
	public CobroTestDataBuilder() {
		this.inicioParqueo = LocalDateTime.now().minusHours(DIFERENCIA_HORAS_INICIO_PARQUEO);
		this.estado = EstadoCobro.PENDIENTE.toString();
	}
	
	public CobroTestDataBuilder conInicioParqueo (LocalDateTime inicioParqueo) {
		this.inicioParqueo = inicioParqueo;
		return this;
	}
	
	public CobroTestDataBuilder conFinParqueo (LocalDateTime finParqueo) {
		this.finParqueo = finParqueo;
		return this;
	}
	
	public CobroTestDataBuilder conValorPagar (double valorPagar) {
		this.valorPagar = valorPagar;
		return this;
	}
	
	public CobroTestDataBuilder conEstado (String estado) {
		this.estado = estado;
		return this;
	}
	
	public Cobro build() {
		Cobro cobro = new Cobro();
		cobro.setEstado(estado);
		cobro.setInicioParqueo(inicioParqueo);
		cobro.setFinParqueo(finParqueo);
		cobro.setValorPagar(valorPagar);
		return cobro;
	}

}
