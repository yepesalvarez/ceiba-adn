package com.co.ceiba.ceibaadn.dominio.unitarias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;

public class CapacidadTest {
		
	Capacidad capacidad;

	@Test
	public void testEqualsObjectDiferenteTipo() {
		
		capacidad = new Capacidad();
		capacidad.setId(1L);
		Parqueadero parqueadero = new Parqueadero();
		capacidad.setId(1L);
		
		boolean result = capacidad.equals(parqueadero);
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsObjectMismaReferencia() {
		
		capacidad = new Capacidad();
		capacidad.setId(1L);
		
		Capacidad otraCapacidad = capacidad;
		
		boolean result = capacidad.equals(otraCapacidad);
		
		assertTrue(result);
	}

	@Test
	public void testEqualsObjectObjetosDistintosMismoId() {
		
		capacidad = new Capacidad();
		capacidad.setId(1L);
		
		Capacidad otraCapacidad = new Capacidad();
		otraCapacidad.setId(1L);
		
		boolean result = capacidad.equals(otraCapacidad);
		
		assertTrue(result);
	}
	
}
