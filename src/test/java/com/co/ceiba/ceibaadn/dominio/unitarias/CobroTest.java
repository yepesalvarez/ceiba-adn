package com.co.ceiba.ceibaadn.dominio.unitarias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;

public class CobroTest {

	Cobro cobro;

	@Test
	public void testEqualsObjectDiferenteTipo() {
		
		cobro = new Cobro();
		cobro.setId(1L);
		Parqueadero parqueadero = new Parqueadero();
		cobro.setId(1L);
		
		boolean result = cobro.equals(parqueadero);
		
		assertFalse(result);
	}
	
	@Test
	public void testEqualsObjectMismaReferencia() {
		
		cobro = new Cobro();
		cobro.setId(1L);
		
		Cobro otrocobro = cobro;
		
		boolean result = cobro.equals(otrocobro);
		
		assertTrue(result);
	}

	@Test
	public void testEqualsObjectObjetosDistintosMismoId() {
		
		cobro = new Cobro();
		cobro.setId(1L);
		
		Cobro otrocobro = new Cobro();
		otrocobro.setId(1L);
		
		boolean result = cobro.equals(otrocobro);
		
		assertTrue(result);
	}

}
