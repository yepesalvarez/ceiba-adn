package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
public abstract class Vehiculo extends Entidad {

	@Column(name = "placa", unique = true)
	@Size(max = 6)
	private String placa;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "tipoVehiculo_fk", referencedColumnName = "id")
	private TipoVehiculo tipoVehiculo;
	
	@ManyToOne
	@JoinColumn(name = "parqueadero_fk", referencedColumnName = "id")
	Parqueadero parqueadero;
	
	public Vehiculo(String placa, TipoVehiculo tipoVehiculo) {
		this.placa = placa;
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public Vehiculo() {}
		
	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public TipoVehiculo getTipoVehiculo() {
		return tipoVehiculo;
	}

	public void setTipoVehiculo(TipoVehiculo tipoVehiculo) {
		this.tipoVehiculo = tipoVehiculo;
	}
	
	public Parqueadero getParqueadero() {
		return parqueadero;
	}

	public void setParqueadero(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
	}

	@Override
	   public boolean equals(Object obj)
	   {
	      if (!(obj instanceof Vehiculo))
	      {
	        return false;
	      }
	      if (this == obj)
	      {
	         return true;
	      }
	      final Vehiculo otroVehiculo = (Vehiculo) obj;
	      return new EqualsBuilder()
	         .append(this.getId(), otroVehiculo.getId())
	         .append(this.getPlaca(), otroVehiculo.getPlaca())
	         .isEquals();
	   }
	@Override
	   public int hashCode()
	   {
	      return new HashCodeBuilder()
	         .append(this.getId())
	         .append(this.getPlaca())
	         .toHashCode();
	   }

}
