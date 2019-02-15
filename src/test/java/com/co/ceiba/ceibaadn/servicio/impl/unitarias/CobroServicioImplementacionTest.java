package com.co.ceiba.ceibaadn.servicio.impl.unitarias;

import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.enums.EstadoCobro;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.CobroRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.impl.CobroServicioImplementacion;

public class CobroServicioImplementacionTest {

//	@Mock
//	CobroRepositorio cobroRepositorio;
//	
//	@Mock
//	FactoryVehiculo factoryVehiculo;
//	
//	@Mock
//	VehiculoServicio vehiculoServicio;
//	
//	@InjectMocks
//	CobroServicioImplementacion cobroServicioImplementacion;
//	
//	Vehiculo vehiculoMoto;
//	TipoVehiculo tipoVehiculoMoto;
//	Cobro cobroVehiculoMoto;
//	Set<Capacidad> capacidades;
//	
//	static final String PLACA_VALIDA = "POO666";
//	static final String TIPO_VEHICULO_MOTO = "moto";
//	static final Long ID_TIPO_VEHICULO_MOTO = 2L;
//	static final int CILINDRAJE = 600;
//	static final Long ID_VEHICULO = 1L;
//	static final Long ID_COBRO = 20L;
//	static final String FORMATO_FECHA_VALIDO = "yyyy-MM-dd HH:mm";
//	static final String KEY_RECARGO_MOTO = "vehiculos.moto.cilndraje.recargo";
//	static final String KEY_PRECIO_HORA_MOTO = "vehiculos.moto.hora.precio";
//		
//	@Before
//	public void setUp() {
//		
//		initMocks(this);
//
//		cobroServicioImplementacion = spy(new CobroServicioImplementacion(cobroRepositorio, vehiculoServicio, factoryVehiculo));
//
//		tipoVehiculoMoto = new TipoVehiculo(); 
//		tipoVehiculoMoto.setNombre(TIPO_VEHICULO_MOTO);
//		tipoVehiculoMoto.setId(ID_TIPO_VEHICULO_MOTO);
//				
//		vehiculoMoto = new Moto();
//		vehiculoMoto.setId(ID_VEHICULO);
//		vehiculoMoto.setPlaca(PLACA_VALIDA);
//		vehiculoMoto.setTipoVehiculo(tipoVehiculoMoto);
//		
//		cobroVehiculoMoto = new Cobro();
//		cobroVehiculoMoto.setEstado(EstadoCobro.PENDIENTE.toString());
//		cobroVehiculoMoto.setId(ID_COBRO);
//		cobroVehiculoMoto.setInicioParqueo(LocalDateTime.now());
//		cobroVehiculoMoto.setVehiculo(vehiculoMoto);
//		
//		Capacidad capacidad = new Capacidad();
//		capacidad.setTipoVehiculo(tipoVehiculoMoto);
//		capacidades = new HashSet<>();
//		capacidades.add(capacidad);
//		
//	}
//
//	@Test
//	public void testCalcularCobro() {
//		
//		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA_VALIDO);
//		String fechaFinParqueo = LocalDateTime.now().plusHours(2).format(formatter);
//		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(ID_VEHICULO)).thenReturn(vehiculoMoto);
//		Mockito.when(cobroServicioImplementacion.obtenerCobroPorVehiculo(vehiculoMoto)).thenReturn(cobroVehiculoMoto);
//		Mockito.when(factoryVehiculo.getRecargoVehiculo(vehiculoMoto)).thenReturn(Double.valueOf(2000));
//		assertTrue(Double.valueOf(4000)	== cobroServicioImplementacion.calcularCobro(ID_VEHICULO, fechaFinParqueo));
//		
//	}

}
