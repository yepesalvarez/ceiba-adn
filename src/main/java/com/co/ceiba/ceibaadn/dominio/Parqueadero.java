package com.co.ceiba.ceibaadn.dominio;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "parqueadero")
public class Parqueadero extends Entidad {

	@OneToMany(cascade = CascadeType.DETACH)
	@JoinColumn(name = "parqueadero_fk")
	private Set<Vehiculo> vehiculos;
	
	@OneToMany(mappedBy = "parqueadero",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private Set<Capacidad> capacidades;
	
	@OneToMany(mappedBy = "parqueadero",
			cascade = CascadeType.ALL, 
			fetch = FetchType.LAZY)
	private Set<Cobro> cobros;
	
	@Column(name = "minimo_horas_dia")
	private int minimoHorasDia;
	
	@Column(name = "maximo_horas_dia")
	private int maximoHorasDia;
	
    private static final Parqueadero INSTANCIA_SINGLETON_PARQUEADERO = new Parqueadero();
    private Parqueadero() {}
    
    public static Parqueadero getInstance() {
      return INSTANCIA_SINGLETON_PARQUEADERO;
    }
	
	public void addCapacidad(Capacidad capacidad) {
		this.capacidades.add(capacidad);
		capacidad.setParqueadero(this);
	}
	
	public void removeCapacidad(Capacidad capacidad) {
		this.capacidades.remove(capacidad);
		capacidad.setParqueadero(null);
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
