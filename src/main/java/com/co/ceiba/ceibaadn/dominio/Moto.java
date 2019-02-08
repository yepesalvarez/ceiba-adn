package com.co.ceiba.ceibaadn.dominio;

import javax.persistence.Column;
import javax.persistence.Entity;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
public class Moto extends Vehiculo {

	@Column(name = "cilindraje")
	private int cilindraje;
	
	public Moto(String placa, TipoVehiculo tipoVehiculo, int cilindraje) {
		super(placa, tipoVehiculo);
		this.cilindraje = cilindraje;
	}
	
	public Moto() {}
	
	public int getCilindraje() {
		return cilindraje;
	}

	public void setCilindraje(int cilindraje) {
		this.cilindraje = cilindraje;
	}
	
	@Override
	   public boolean equals(Object obj)
	   {
	      if (!(obj instanceof Moto))
	      {
	        return false;
	      }
	      if (this == obj)
	      {
	         return true;
	      }
	      final Moto otraMoto = (Moto) obj;

	      return new EqualsBuilder()
	         .append(this.getId(), otraMoto.getId())
	         .append(this.getPlaca(), otraMoto.getPlaca())
	         .append(this.getCilindraje(), otraMoto.getCilindraje())
	         .isEquals();
	   }
	
	@Override
	   public int hashCode()
	   {
	      return new HashCodeBuilder()
	         .append(this.getId())
	         .append(this.getPlaca())
	         .append(this.getCilindraje())
	         .toHashCode();
	   }

	@Override
	   public String toString()
	   {
	      return new ToStringBuilder(this)
	         .append("Id: ", this.getId())
	         .append("Tipo Vehiculo: ", this.getTipoVehiculo())
	         .append("Placa: ", this.getPlaca())
	         .append("Cilindraje: ", this.getCilindraje())
	         .toString();
	   }
}
