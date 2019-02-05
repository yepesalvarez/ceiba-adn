package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity(name = "vehiculo")
@Table(name = "vehiculos")
public abstract class Vehiculo extends Entidad {
	
	@Column(name = "placa", unique = true)
	@NotNull
	@Size(max = 6)
	private String placa;
	
	@ManyToOne
	@JoinColumn(name = "tipoVehiculo_fk", referencedColumnName = "id")
	private TipoVehiculo tipoVehiculo;
	
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

	@Override
	   public String toString()
	   {
	      return new ToStringBuilder(this)
	         .append("Id: ", this.getId())
	         .append("Tipo Vehiculo: ", this.getTipoVehiculo())
	         .append("Placa: ", this.getPlaca())
	         .toString();
	   }
}