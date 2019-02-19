package com.co.ceiba.ceibaadn.servicio.impl.unitarias;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
import com.co.ceiba.ceibaadn.dominio.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.CobroRepositorio;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.impl.CobroServicioImplementacion;

public class CobroServicioImplementacionTest {

	@Mock
	CobroRepositorio cobroRepositorio;
	
	@Mock
	FactoryVehiculo factoryVehiculo;
	
	@Mock
	VehiculoServicio vehiculoServicio;
	
	@Mock
	ParqueaderoServicio parqueaderoServicio;
		
	@InjectMocks
	CobroServicioImplementacion cobroServicioImplementacion;
	
	Vehiculo vehiculoMoto;
	TipoVehiculo tipoVehiculoMoto;
	Cobro cobroVehiculoMoto;
	Set<Capacidad> capacidades;
	Parqueadero parqueadero;
	String fechaFinParqueo;
	
	
	static final String COBRO_NO_POSIBLE = "El cobro no se generó/realizó porque el vehículo no se encuentra registrado o los datos entregados son inválidos";
	static final String PLACA_VALIDA = "POO666";
	static final String TIPO_VEHICULO_MOTO = "moto";
	static final Long ID_TIPO_VEHICULO_MOTO = 2L;
	static final int CILINDRAJE = 600;
	static final Long ID_VEHICULO = 1L;
	static final Long ID_COBRO = 20L;
	static final String FORMATO_FECHA_VALIDO = "yyyy-MM-dd HH:mm";
	static final String KEY_RECARGO_MOTO = "vehiculos.moto.cilndraje.recargo";
	static final String KEY_PRECIO_HORA_MOTO = "vehiculos.moto.hora.precio";
	static final int MINIMO_HORAS_DIA_PARQUEO = 9;
	static final int MAXIMO_HORAS_DIA_PARQUEO = 24;
	static final double RECARGO_MOTO = 2000;
	static final double VALOR_HORA_MOTO = 500;
	static final double VALOR_DIA_MOTO = 4000;
		
	@Before
	public void setUp() {
		
		initMocks(this);

		cobroServicioImplementacion = spy(new CobroServicioImplementacion(cobroRepositorio, vehiculoServicio, factoryVehiculo, parqueaderoServicio));

		tipoVehiculoMoto = new TipoVehiculo(); 
		tipoVehiculoMoto.setNombre(TIPO_VEHICULO_MOTO);
		tipoVehiculoMoto.setId(ID_TIPO_VEHICULO_MOTO);
				
		vehiculoMoto = new Moto();
		vehiculoMoto.setId(ID_VEHICULO);
		vehiculoMoto.setPlaca(PLACA_VALIDA);
		vehiculoMoto.setTipoVehiculo(tipoVehiculoMoto);
		
		cobroVehiculoMoto = new Cobro();
		cobroVehiculoMoto.setEstado(EstadoCobro.PENDIENTE.toString());
		cobroVehiculoMoto.setId(ID_COBRO);
		cobroVehiculoMoto.setInicioParqueo(LocalDateTime.now().minusHours(2));
		cobroVehiculoMoto.setVehiculo(vehiculoMoto);
		
		Capacidad capacidad = new Capacidad();
		capacidad.setTipoVehiculo(tipoVehiculoMoto);
		capacidad.setValorDia(VALOR_DIA_MOTO);
		capacidad.setValorHora(VALOR_HORA_MOTO);
		capacidades = new HashSet<>();
		capacidades.add(capacidad);
		
		parqueadero = new Parqueadero();
		parqueadero.setMinimoHorasDia(MINIMO_HORAS_DIA_PARQUEO);
		parqueadero.setMaximoHorasDia(MINIMO_HORAS_DIA_PARQUEO);
		parqueadero.setCapacidades(capacidades);
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA_VALIDO);
		fechaFinParqueo = LocalDateTime.now().plusHours(2).format(formatter);
		
	}

	@Test
	public void testCalcularCobroOk() {
				
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(ID_VEHICULO)).thenReturn(vehiculoMoto);
		Mockito.when(cobroServicioImplementacion.obtenerCobroPorVehiculo(vehiculoMoto)).thenReturn(cobroVehiculoMoto);
		Mockito.when(parqueaderoServicio.getParqueadero()).thenReturn(parqueadero);
		Mockito.when(factoryVehiculo.getRecargoVehiculo(vehiculoMoto)).thenReturn(Double.valueOf(RECARGO_MOTO));
		
		double result = cobroServicioImplementacion.calcularCobro(ID_VEHICULO, fechaFinParqueo);
		
		assertTrue(4000 == result);
		
	}
	
	@Test
	public void testCalcularCobroIdVehiculoNulo() {
				
		assertThatThrownBy(() -> cobroServicioImplementacion.calcularCobro(null, fechaFinParqueo)).hasMessage(COBRO_NO_POSIBLE);
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testCalcularCobroIdVehiculoNoExistente() {
		
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(ID_VEHICULO)).thenReturn(null);
		
		assertThatThrownBy(() -> cobroServicioImplementacion.calcularCobro(ID_VEHICULO, fechaFinParqueo)).hasMessage(COBRO_NO_POSIBLE);
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testCalcularCobroFechaFinParqueoNula() {
		
		assertThatThrownBy(() -> cobroServicioImplementacion.calcularCobro(ID_VEHICULO, null)).hasMessage(COBRO_NO_POSIBLE);
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testCalcularCobroFechaFinParqueoEnBlanco() {
			
		assertThatThrownBy(() -> cobroServicioImplementacion.calcularCobro(ID_VEHICULO, "")).hasMessage(COBRO_NO_POSIBLE);
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}

}
