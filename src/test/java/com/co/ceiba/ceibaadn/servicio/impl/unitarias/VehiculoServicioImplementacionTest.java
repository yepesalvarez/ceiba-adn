package com.co.ceiba.ceibaadn.servicio.impl.unitarias;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.impl.VehiculoServicioImplementacion;

public class VehiculoServicioImplementacionTest {

	@Mock
	TipoVehiculoServicio tipoVehiculoServicio;
	
	@Mock
	VehiculoRepositorio vehiculoRepositorio;
	
	@Mock
	FactoryVehiculo factoryVehiculo;
	
	VehiculoDto vehiculoDto;
	Vehiculo vehiculo;
	TipoVehiculo tipoVehiculo;
	
	@InjectMocks
	VehiculoServicioImplementacion vehiculoServicioImpl;
	
	static final String PLACA = "bsd234";
	static final String PLACA_INVALIDA = "bsd2345";
	static final String TIPO_VEHICULO_VALIDO = "moto";
	static final String TIPO_VEHICULO_NO_VALIDO = "tren";
	
	@Before
	public void setUp() {
		
		initMocks(this);
		
		vehiculoServicioImpl = spy(new VehiculoServicioImplementacion(vehiculoRepositorio, tipoVehiculoServicio, factoryVehiculo));
		
		vehiculoDto = new VehiculoDto(PLACA, TIPO_VEHICULO_VALIDO, 600);
		
		tipoVehiculo = new TipoVehiculo();
		tipoVehiculo.setId(2L);
		tipoVehiculo.setNombre("moto");
		
		vehiculo = new Moto();
		vehiculo.setPlaca(PLACA);
		vehiculo.setTipoVehiculo(tipoVehiculo);
		
		
	
	}

	@Test
	public void testGuardarVehiculoVehiculoDtoOk() {
		
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO)).thenReturn(tipoVehiculo);
		Mockito.when(vehiculoServicioImpl.obtenerVehiculoPorPlaca(PLACA)).thenReturn(null);
		Mockito.when(factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculo)).thenReturn(vehiculo);
		Mockito.when(vehiculoServicioImpl.guardarVehiculo(vehiculo)).thenReturn(vehiculo);
		
		Vehiculo vehiculoGuardado = vehiculoServicioImpl.guardarVehiculo(vehiculoDto);
		
		assertNotNull(vehiculoGuardado);
		assertEquals(PLACA, vehiculoGuardado.getPlaca());
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoPlacaInvalida() {
		
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO)).thenReturn(tipoVehiculo);
		Mockito.when(vehiculoServicioImpl.obtenerVehiculoPorPlaca(PLACA)).thenReturn(null);
		Mockito.when(factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculo)).thenReturn(vehiculo);
		Vehiculo vehiculoGuardado = null;
		
		vehiculoGuardado = vehiculoServicioImpl.guardarVehiculo(vehiculoDto);
		
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertNull(vehiculoGuardado);
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoPlacaYaExiste() {
		
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO)).thenReturn(tipoVehiculo);
		Mockito.when(vehiculoServicioImpl.obtenerVehiculoPorPlaca(PLACA)).thenReturn(vehiculo);	
		
		assertThatThrownBy(() -> vehiculoServicioImpl.guardarVehiculo(vehiculoDto)).isInstanceOf(VehiculoYaExisteException.class);
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoPlacaVacia() {
		
		vehiculoDto.setPlaca("");
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO)).thenReturn(tipoVehiculo);
		Mockito.when(vehiculoServicioImpl.obtenerVehiculoPorPlaca("")).thenReturn(null);	
		
		assertThatThrownBy(() -> vehiculoServicioImpl.guardarVehiculo(vehiculoDto)).isInstanceOf(VehiculoBadRequestException.class);
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoTipoVehiculoInexistente() {
		
		vehiculoDto.setTipoVehiculo(TIPO_VEHICULO_NO_VALIDO);
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_NO_VALIDO)).thenReturn(null);
				
		assertThatThrownBy(() -> vehiculoServicioImpl.guardarVehiculo(vehiculoDto)).isInstanceOf(VehiculoBadRequestException.class);
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoTipoVehiculoVacio() {
		
		vehiculoDto.setTipoVehiculo("");
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre("")).thenReturn(null);
				
		assertThatThrownBy(() -> vehiculoServicioImpl.guardarVehiculo(vehiculoDto)).isInstanceOf(VehiculoBadRequestException.class);
		
	}
	
	@Test
	public void testGuardarVehiculoVehiculoDtoTipoVehiculoNulo() {
		
		vehiculoDto.setTipoVehiculo(null);
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(null)).thenReturn(null);
				
		assertThatThrownBy(() -> vehiculoServicioImpl.guardarVehiculo(vehiculoDto)).isInstanceOf(VehiculoBadRequestException.class);
		
	}

}
