package com.co.ceiba.ceibaadn.dominio;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parqueadero")
public class Parqueadero extends Entidad {

	@OneToMany
	@JoinColumn(name = "parqueadero_fk")
	private Set<Vehiculo> vehiculos;
	
	@OneToMany(mappedBy = "parqueadero",
			fetch = FetchType.LAZY)
	private Set<Capacidad> capacidades;
	
	@OneToMany(mappedBy = "parqueadero",
			fetch = FetchType.LAZY)
	private Set<Cobro> cobros;
	
	@Column(name = "minimo_horas_dia")
	private int minimoHorasDia;
	
	@Column(name = "maximo_horas_dia")
	private int maximoHorasDia;
	    	
	public void addCapacidad(Capacidad capacidad) {
		this.capacidades.add(capacidad);
		capacidad.setParqueadero(this);
	}
	
	public void removeCapacidad(Capacidad capacidad) {
		this.capacidades.remove(capacidad);
		capacidad.setParqueadero(null);
	}
	
	public void addCobro(Cobro cobro) {
		this.cobros.add(cobro);
		cobro.setParqueadero(this);
	}
	
	public void removeCobro(Cobro cobro) {
		this.cobros.remove(cobro);
		cobro.setParqueadero(null);
	}
	
	public void addVehiculo(Vehiculo vehiculo) {
		this.vehiculos.add(vehiculo);
		vehiculo.setParqueadero(this);
	}
	
	public void removeVehiculo(Vehiculo vehiculo) {
		this.vehiculos.remove(vehiculo);
		vehiculo.setParqueadero(null);
	}
	
	public Set<Vehiculo> getVehiculos() {
		return vehiculos;
	}
	
	public void setVehiculos(Set<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
	
	public int getMinimoHorasDia() {
		return minimoHorasDia;
	}
	
	public void setMinimoHorasDia(int minimoHorasDia) {
		this.minimoHorasDia = minimoHorasDia;
	}
	
	public int getMaximoHorasDia() {
		return maximoHorasDia;
	}
	
	public void setMaximoHorasDia(int maximoHorasDia) {
		this.maximoHorasDia = maximoHorasDia;
	}

	public Set<Capacidad> getCapacidades() {
		return capacidades;
	}

	public void setCapacidades(Set<Capacidad> capacidades) {
		this.capacidades = capacidades;
	}

	public Set<Cobro> getCobros() {
		return cobros;
	}

	public void setCobros(Set<Cobro> cobros) {
		this.cobros = cobros;
	}

}
