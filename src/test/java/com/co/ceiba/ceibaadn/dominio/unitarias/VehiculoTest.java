package com.co.ceiba.ceibaadn.dominio.unitarias;

import static org.junit.Assert.*;

import org.junit.Test;

import com.co.ceiba.ceibaadn.dominio.Carro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;

public class VehiculoTest {

	Vehiculo vehiculo;

	@Test
	public void testEqualsObjectDiferenteTipo() {
		
		vehiculo = new Carro();
		vehiculo.setId(1L);
		Parqueadero parqueadero = new Parqueadero();
		vehiculo.setId(1L);
		
		boolean result = vehiculo.equals(parqueadero);
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsObjectMismaReferencia() {
		
		vehiculo = new Carro();
		vehiculo.setId(1L);
		
		Vehiculo otrovehiculo = vehiculo;
		
		boolean result = vehiculo.equals(otrovehiculo);
		
		assertTrue(result);
	}

	@Test
	public void testEqualsObjectObjetosDistintosMismoId() {
		
		vehiculo = new Moto();
		vehiculo.setId(1L);
		
		Vehiculo otrovehiculo = new Moto();
		otrovehiculo.setId(1L);
		
		boolean result = vehiculo.equals(otrovehiculo);
		
		assertTrue(result);
	}

}
