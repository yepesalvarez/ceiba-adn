package com.co.ceiba.ceibaadn.dominio;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@Entity
@Table(name = "tipos_vehiculo")
public class TipoVehiculo extends Entidad{
	
	@Column(name = "nombre", unique = true)
	@Size(max = 200)
	private String nombre;
	
	@OneToMany(mappedBy = "tipoVehiculo",
			cascade = CascadeType.ALL,
			fetch = FetchType.LAZY)
	private Set<Vehiculo> vehiculos; 
	
	public void addVehiculo(Vehiculo vehiculo) {
		vehiculos.add(vehiculo);
		vehiculo.setTipoVehiculo(this);
	}
	
	public void removeVehiculo(Vehiculo vehiculo) {
		vehiculos.remove(vehiculo);
		vehiculo.setTipoVehiculo(null);
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public Set<Vehiculo> getVehiculos() {
		return vehiculos;
	}

	public void setVehiculos(Set<Vehiculo> vehiculos) {
		this.vehiculos = vehiculos;
	}
	
	@Override
	   public boolean equals(Object obj)
	   {
	      if (!(obj instanceof TipoVehiculo))
	      {
	        return false;
	      }
	      if (this == obj)
	      {
	         return true;
	      }
	      final TipoVehiculo otroVehiculo = (TipoVehiculo) obj;

	      return new EqualsBuilder()
	         .append(this.getId(), otroVehiculo.getId())
	         .append(this.getNombre(), otroVehiculo.getNombre())
	         .isEquals();
	   }
	
	@Override
	   public int hashCode()
	   {
	      return new HashCodeBuilder()
	         .append(this.getId())
	         .append(this.getNombre())
	         .toHashCode();
	   }
	
	@Override
	   public String toString()
	   {
	      return new ToStringBuilder(this)
	         .append("Id: ", this.getId())
	         .append("Nombre: ", this.getNombre())
	         .toString();
	   }

}
