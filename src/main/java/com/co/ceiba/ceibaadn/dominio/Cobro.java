package com.co.ceiba.ceibaadn.dominio;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

@Entity
@Table(name = "cobros")
public class Cobro extends Entidad {
	
	@Column(name = "estado")
	private String estado;
	
	@Column(name = "inicio_parqueo")
	private LocalDateTime inicioParqueo;
	
	@Column(name = "fin_parqueo")
	private LocalDateTime finParqueo;
	
	@Column(name = "valor_pagar")
	private double valorPagar;
	
	@ManyToOne
	@JoinColumn(name = "parqueadero_fk", referencedColumnName = "id")
	Parqueadero parqueadero;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "vehiculo_fk", referencedColumnName = "id")
	private Vehiculo vehiculo;

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}

	public LocalDateTime getInicioParqueo() {
		return inicioParqueo;
	}

	public void setInicioParqueo(LocalDateTime inicioParqueo) {
		this.inicioParqueo = inicioParqueo;
	}

	public LocalDateTime getFinParqueo() {
		return finParqueo;
	}

	public void setFinParqueo(LocalDateTime finParqueo) {
		this.finParqueo = finParqueo;
	}

	public double getValorPagar() {
		return valorPagar;
	}

	public void setValorPagar(double valorPagar) {
		this.valorPagar = valorPagar;
	}

	public void setParqueadero(Parqueadero parqueadero) {
		this.parqueadero = parqueadero;
	}

	public Vehiculo getVehiculo() {
		return vehiculo;
	}

	public void setVehiculo(Vehiculo vehiculo) {
		this.vehiculo = vehiculo;
	}
	
	@Override
	   public boolean equals(Object obj)
	   {
			if (!(obj instanceof Cobro))
			{
				return false;
			}
			if (this == obj)
			{
				return true;
			}
			final Cobro otroCobro = (Cobro) obj;

	      return new EqualsBuilder()
	         .append(this.getId(), otroCobro.getId())
	         .append(this.getVehiculo(), otroCobro.getVehiculo())
	         .append(this.getEstado(), otroCobro.getEstado())
	         .append(this.getInicioParqueo(), otroCobro.getInicioParqueo())
	         .append(this.getFinParqueo(), otroCobro.getFinParqueo())
	         .isEquals();
	   }
	
	@Override
	   public int hashCode()
	   {
	      return new HashCodeBuilder()
	         .append(this.getId())
	         .append(this.getVehiculo())
	         .append(this.getEstado())
	         .append(this.getInicioParqueo())
	         .append(this.getFinParqueo())
	         .toHashCode();
	   }

}
