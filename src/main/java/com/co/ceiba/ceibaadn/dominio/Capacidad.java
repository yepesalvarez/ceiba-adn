package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "capacidades")
public class Capacidad extends Entidad{
	
	@ManyToOne
	@JoinColumn(name = "parqueadero_fk", referencedColumnName = "id") 
	private Parqueadero parqueadero;
		
	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipo_vehiculo_fk", referencedColumnName = "id")
	private TipoVehiculo tipoVehiculo;
	
	@Column(name = "limite")
	private int limite;
	
	@Column(name = "valor_hora")
	private double valorHora;
	
	@Column(name = "valor_dia")
	private double valorDia;

	public Parqueadero getParqueadero() {
		return parqueadero;
	}

	public void setParqueadero(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}

	public int getLimite() {
		return limite;
	}

	public void setLimite(int limite) {
		this.limite = limite;
	}

	public double getValorHora() {
		return valorHora;
	}

	public void setValorHora(double valorHora) {
		this.valorHora = valorHora;
	}

	public double getValorDia() {
		return valorDia;
	}

	public void setValorDia(double valorDia) {
		this.valorDia = valorDia;
	}
	
	@Override
	   public boolean equals(Object obj)
	   {
	      if (!(obj instanceof Capacidad))
	      {
	        return false;
	      }
	      if (this == obj)
	      {
	         return true;
	      }
	      final Capacidad otraCapacidad = (Capacidad) obj;

	      return new EqualsBuilder()
	         .append(this.getId(), otraCapacidad.getId())
	         .append(this.getParqueadero(), otraCapacidad.getParqueadero())
	         .append(this.getTipoVehiculo(), otraCapacidad.getTipoVehiculo())
	         .isEquals();
	   }
	
	@Override
	   public int hashCode()
	   {
	      return new HashCodeBuilder()
	         .append(this.getId())
	         .append(this.getParqueadero())
	         .append(this.getTipoVehiculo())
	         .toHashCode();
	   }

	@Override
	   public String toString()
	   {
	      return new ToStringBuilder(this)
	         .append("Id: ", this.getId())
	         .append("Tipo Vehiculo: ", this.getTipoVehiculo())
	         .append("Parqueadero: ", this.getParqueadero())
	         .append("Limite: ", this.getLimite())
	         .append("Valor hora: ", this.getValorHora())
	         .append("Valor dia: ", this.getValorDia())
	         .toString();
	   }

}
