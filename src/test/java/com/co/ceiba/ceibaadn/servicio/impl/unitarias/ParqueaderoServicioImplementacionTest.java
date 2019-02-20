package com.co.ceiba.ceibaadn.servicio.impl.unitarias;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.spy;
import static org.mockito.MockitoAnnotations.initMocks;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.mock.env.MockEnvironment;

import com.co.ceiba.ceibaadn.dominio.Capacidad;
import com.co.ceiba.ceibaadn.dominio.Cobro;
import com.co.ceiba.ceibaadn.dominio.Moto;
import com.co.ceiba.ceibaadn.dominio.Parqueadero;
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.enums.EstadoCobro;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoIngresoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.dominio.util.ModelToDto;
import com.co.ceiba.ceibaadn.repositorio.CobroRepositorio;
import com.co.ceiba.ceibaadn.repositorio.ParqueaderoRepositorio;
import com.co.ceiba.ceibaadn.servicio.CapacidadServicio;
import com.co.ceiba.ceibaadn.servicio.TipoVehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;
import com.co.ceiba.ceibaadn.servicio.impl.ParqueaderoServicioImplementacion;

public class ParqueaderoServicioImplementacionTest {
	
	@Mock
	ParqueaderoRepositorio parqueaderoRepositorio;
	
	@Mock
	VehiculoServicio vehiculoServicio;
	
	@Mock
	CobroRepositorio cobroRepositorio;
	
	@Mock
	FactoryVehiculo factoryVehiculo;
	
	@Mock
	CapacidadServicio capacidadServicio;
	
	@Mock
	ModelToDto modelToDto;
		
	@Mock
	MockEnvironment env;
	
	@Mock
	TipoVehiculoServicio tipoVehiculoServicio;
	
	ParqueaderoServicioImplementacion parqueaderoServicioImplementacion;
	
	VehiculoDto vehiculoDto;
	Vehiculo vehiculo;
	Moto moto;
	TipoVehiculo tipoVehiculoMoto;
	TipoVehiculo tipoVehiculoCarro;
	Set<VehiculoDto> vehiculosDto;
	Set<Vehiculo> vehiculos;
	Iterable<Parqueadero> parqueaderos;
	Parqueadero parqueadero;
	Set<Capacidad> capacidadesParqueadero;
	Capacidad capacidadTipoVehiculoMOTO;
	Capacidad capacidadTipoVehiculoCarro;
	Set<Cobro> cobrosParqueadero;
	Cobro cobro;
	
	static final String PLACA_VALIDA = "bsd234";
	static final String PLACA_VALIDA_OTRA = "fbv899";
	static final String PLACA_INVALIDA = "bsd2345";
	static final String PLACA_INVALIDA_PICO_PLACA = "abc123";
	static final String TIPO_VEHICULO_VALIDO_MOTO = "moto";
	static final String TIPO_VEHICULO_VALIDO_CARRO = "carro";
	static final String TIPO_VEHICULO_NO_VALIDO = "tren";
	static final int CILINDRAJE_SIN_RECARGO = 150;
	static final String KEY_HORAS_MINIMO_DIA_PARQUEO = "horas.minimo.dia"; 
	static final String HORAS_MINIMO_DIA_PARQUEO = "9";
	static final String KEY_HORAS_MAXIMO_DIA_PARQUEO = "horas.maximo.dia"; 
	static final String HORAS_MAXIMO_DIA_PARQUEO = "24";
	static final String LIMITE_MOTO_PARQUEADERO = "1";
	static final String LIMITE_CARRO_PARQUEADERO = "1";
	static final String LIMITE_MOTO_PARQUEADERO_LLENO = "0";
	static final String VALOR_HORA_MOTO_PARQUEADERO = "500";
	static final String VALOR_DIA_MOTO_PARQUEADERO = "4000";
	static final String VALOR_HORA_CARRO_PARQUEADERO = "1000";
	static final String VALOR_DIA_CARRO_PARQUEADERO = "8000";
	static final String GUARDADO_NO_POSIBLE = "Parámetros requeridos para la creación del vehículo incorrectos";
	static final String VEHICULO_REPETIDO = "El vehículo que intenta crear ya existe";
	static final String INGRESO_NO_POSIBLE = "Ingreso no posible. Parqueadero lleno, el vehículo esta en el parqueadero o su placa no esta habilitada para este dia";
	static final String RETIRO_NO_POSIBLE = "El vehiculo no esta autorizado para salir por pagos pendientes o ya había sido retirado del parqueadero";

	@Before
	public void setUp() {
		
		env = new MockEnvironment();
		env.setProperty(KEY_HORAS_MINIMO_DIA_PARQUEO, HORAS_MINIMO_DIA_PARQUEO);
		env.setProperty(KEY_HORAS_MAXIMO_DIA_PARQUEO, HORAS_MAXIMO_DIA_PARQUEO);
		
		initMocks(this);
				
		parqueaderoServicioImplementacion = spy(new	ParqueaderoServicioImplementacion(parqueaderoRepositorio,
				vehiculoServicio, cobroRepositorio,factoryVehiculo,
				capacidadServicio, modelToDto, tipoVehiculoServicio));
		
		tipoVehiculoCarro = new TipoVehiculo();
		tipoVehiculoCarro.setId(1L);
		tipoVehiculoCarro.setNombre(TIPO_VEHICULO_VALIDO_CARRO);
		
		tipoVehiculoMoto = new TipoVehiculo();
		tipoVehiculoMoto.setId(2L);
		tipoVehiculoMoto.setNombre(TIPO_VEHICULO_VALIDO_MOTO);
		
		capacidadesParqueadero = new HashSet<>();
		
		capacidadTipoVehiculoCarro = new Capacidad();
		capacidadTipoVehiculoCarro.setId(1L);
		capacidadTipoVehiculoCarro.setLimite(Integer.parseInt(LIMITE_CARRO_PARQUEADERO));
		capacidadTipoVehiculoCarro.setTipoVehiculo(tipoVehiculoCarro);
		capacidadTipoVehiculoCarro.setValorHora(Double.parseDouble(VALOR_HORA_CARRO_PARQUEADERO));
		capacidadTipoVehiculoCarro.setValorDia(Double.parseDouble(VALOR_DIA_CARRO_PARQUEADERO));
		capacidadesParqueadero.add(capacidadTipoVehiculoCarro);
		
		capacidadTipoVehiculoMOTO = new Capacidad();
		capacidadTipoVehiculoMOTO.setId(2L);
		capacidadTipoVehiculoMOTO.setLimite(Integer.parseInt(LIMITE_MOTO_PARQUEADERO));
		capacidadTipoVehiculoMOTO.setTipoVehiculo(tipoVehiculoMoto);
		capacidadTipoVehiculoMOTO.setValorHora(Double.parseDouble(VALOR_HORA_MOTO_PARQUEADERO));
		capacidadTipoVehiculoMOTO.setValorDia(Double.parseDouble(VALOR_DIA_MOTO_PARQUEADERO));
		capacidadesParqueadero.add(capacidadTipoVehiculoMOTO);
		
		vehiculoDto = new VehiculoDto(PLACA_VALIDA, TIPO_VEHICULO_VALIDO_MOTO, CILINDRAJE_SIN_RECARGO);	
		vehiculoDto.setId(10L);
		vehiculoDto.setFechaIngreso(LocalDateTime.now());
		
		vehiculosDto = new HashSet<>();
		vehiculosDto.add(vehiculoDto);
		
		moto = new Moto();
		moto.setCilindraje(CILINDRAJE_SIN_RECARGO);
		vehiculo = moto;
		vehiculo.setPlaca(PLACA_VALIDA);
		vehiculo.setTipoVehiculo(tipoVehiculoMoto);
		vehiculo.setId(10L);
		
		vehiculos = new HashSet<>();
		vehiculos.add(vehiculo);
		
		cobrosParqueadero = new HashSet<>();
		cobro = new Cobro();
		cobro.setId(10L);
		cobro.setEstado(EstadoCobro.PENDIENTE.toString());
		cobro.setInicioParqueo(LocalDateTime.now());
		cobro.setVehiculo(vehiculo);
		cobrosParqueadero.add(cobro);
		
		
		parqueadero = new Parqueadero();
		parqueadero.setId(1L);
		parqueadero.setVehiculos(new HashSet<>());
		List<Parqueadero> listParqueaderos = new ArrayList<>();
		listParqueaderos.add(parqueadero);
		parqueaderos = listParqueaderos;
		parqueadero.setCobros(cobrosParqueadero);
		parqueadero.setCapacidades(capacidadesParqueadero);
		
	}

	@Test
	public void testIngresarVehiculoOk() {
		
		actualizarRangosMock();
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO_MOTO)).thenReturn(tipoVehiculoMoto);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca())).thenReturn(null);
		Mockito.when(vehiculoServicio.guardarVehiculo(modelToDto.vehiculoDtoToVehiculo(vehiculoDto))).thenReturn(vehiculo);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		Mockito.when(modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos())).thenReturn(vehiculosDto);	
		
		
		Set<VehiculoDto> resultado = parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto);
		
		assertEquals(vehiculosDto, resultado);
		
	}
	
	@Test
	public void testIngresarVehiculoParqueaderoLleno() {
		
		capacidadesParqueadero.stream().filter(capacidad -> capacidad.getTipoVehiculo().equals(tipoVehiculoMoto))
		.findFirst().ifPresent(capacidadLimitar -> capacidadLimitar.setLimite(Integer.parseInt(LIMITE_MOTO_PARQUEADERO_LLENO)));	
		actualizarRangosMock();
		Mockito.when(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca())).thenReturn(null);
		Mockito.when(vehiculoServicio.guardarVehiculo(modelToDto.vehiculoDtoToVehiculo(vehiculoDto))).thenReturn(vehiculo);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO_MOTO)).thenReturn(tipoVehiculoMoto);
		Mockito.when(modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos())).thenReturn(vehiculosDto);	
			
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(INGRESO_NO_POSIBLE);
		assertThatExceptionOfType(ParqueaderoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testVerificarVehiculoEnDiaNoHabilitado() {
		if (!LocalDateTime.now().getDayOfWeek().name().equals("SUNDAY") && !LocalDateTime.now().getDayOfWeek().name().equals("MONDAY")) {
			
			assertThatThrownBy(() -> parqueaderoServicioImplementacion.verificarVehiculoEnDiaNoHabilitado(PLACA_INVALIDA_PICO_PLACA))
			.hasMessage(INGRESO_NO_POSIBLE);
			assertThatExceptionOfType(ParqueaderoIngresoNoPosibleException.class);
			
		} else {
			testIngresarVehiculoOk();
		}
	}
	
	@Test
	public void testIngresarVehiculoPlacaYaExiste() {
		
		actualizarRangosMock();
		vehiculo.setParqueadero(parqueadero);
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO_MOTO)).thenReturn(tipoVehiculoMoto);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca())).thenReturn(vehiculo);
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(VEHICULO_REPETIDO);
		assertThatExceptionOfType(VehiculoYaExisteException.class);
	}
	
	@Test
	public void testIngresarVehiculoPlacaYaExisteNoParqueado() {
		
		actualizarRangosMock();
		vehiculo.setParqueadero(null);
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_VALIDO_MOTO)).thenReturn(tipoVehiculoMoto);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorPlaca(PLACA_VALIDA)).thenReturn(vehiculo);
		Mockito.when(cobroRepositorio.save(cobro)).thenReturn(cobro);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		Mockito.when(modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos())).thenReturn(vehiculosDto);
		
		Set<VehiculoDto> resultado = parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto);
		
		assertEquals(vehiculosDto, resultado);
		
	}
	
	@Test
	public void testIngresarVehiculoPlacaInvalida() {
		
		vehiculoDto.setPlaca(PLACA_INVALIDA);
		actualizarRangosMock();
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testIngresarVehiculoPlacaEnBlanco() {
		
		vehiculoDto.setPlaca("");
		actualizarRangosMock();
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testIngresarVehiculoPlacaNula() {
		
		vehiculoDto.setPlaca(null);
		actualizarRangosMock();
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testIngresarTipoVehiculoNulo() {
		
		vehiculoDto.setTipoVehiculo(null);
		actualizarRangosMock();
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testIngresarTipoVehiculoEnBlanco() {
		
		vehiculoDto.setTipoVehiculo("");
		actualizarRangosMock();
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testIngresarTipoVehiculoNoValido() {
		
		vehiculoDto.setTipoVehiculo(TIPO_VEHICULO_NO_VALIDO);
		actualizarRangosMock();
		Mockito.when(tipoVehiculoServicio.obtenerPorNombre(TIPO_VEHICULO_NO_VALIDO)).thenReturn(null);
		
		assertThatThrownBy(() -> parqueaderoServicioImplementacion.ingresarVehiculo(vehiculoDto)).hasMessage(GUARDADO_NO_POSIBLE);
		assertThatExceptionOfType(VehiculoIngresoNoPosibleException.class);
		
	}
	
	@Test
	public void testRetirarVehiculoOk() {
		actualizarRangosMock();
		vehiculo.setParqueadero(parqueadero);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId())).thenReturn(vehiculo);
		cobro.setEstado(EstadoCobro.PAGADO.toString());
		Mockito.when(cobroRepositorio.findByVehiculo(vehiculo)).thenReturn(cobro);
		Mockito.when(vehiculoServicio.guardarVehiculo((vehiculo))).thenReturn(vehiculo);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		vehiculosDto.remove(vehiculoDto);
		Mockito.when(modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos())).thenReturn(vehiculosDto);

		Set<VehiculoDto> resultado = parqueaderoServicioImplementacion.retirarVehiculo(vehiculoDto.getId());
		
		assertEquals(vehiculosDto, resultado);

	}
	
	@Test
	public void testRetirarVehiculoSinPagar() {
		actualizarRangosMock();
		vehiculo.setParqueadero(parqueadero);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId())).thenReturn(vehiculo);
		Mockito.when(cobroRepositorio.findByVehiculo(vehiculo)).thenReturn(cobro);
		Mockito.when(vehiculoServicio.guardarVehiculo((vehiculo))).thenReturn(vehiculo);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		vehiculosDto.remove(vehiculoDto);
		Mockito.when(modelToDto.vehiculosToVehiculosDto(parqueadero.getVehiculos())).thenReturn(vehiculosDto);

		assertThatThrownBy(() -> parqueaderoServicioImplementacion.retirarVehiculo(vehiculoDto.getId()))
		.hasMessage(RETIRO_NO_POSIBLE);
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
		
	}
	
	@Test
	public void testRetirarVehiculoNoExistente() {
		actualizarRangosMock();
		vehiculo.setParqueadero(parqueadero);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId())).thenReturn(null);

		assertThatThrownBy(() -> parqueaderoServicioImplementacion.retirarVehiculo(vehiculoDto.getId()))
		.hasMessage(RETIRO_NO_POSIBLE);
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
		
	}
	
	@Test
	public void testRetirarVehiculoExistenteNoEnParqueadero() {
		actualizarRangosMock();
		vehiculo.setParqueadero(null);
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId())).thenReturn(vehiculo);

		assertThatThrownBy(() -> parqueaderoServicioImplementacion.retirarVehiculo(vehiculoDto.getId()))
		.hasMessage(RETIRO_NO_POSIBLE);
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
		
	}

	@Test
	public void testPagarParqueaderoOk() {
		actualizarRangosMock();
		Mockito.when(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId())).thenReturn(vehiculo);
		Mockito.when(cobroRepositorio.findByVehiculo(vehiculo)).thenReturn(cobro);
		cobro.setEstado(EstadoCobro.PAGADO.toString());
		Mockito.when(cobroRepositorio.save(cobro)).thenReturn(cobro);
		parqueadero.getCobros().add(cobro);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		
		parqueaderoServicioImplementacion.pagarParqueadero(vehiculoDto.getId());
		
		assertTrue(parqueaderoServicioImplementacion.getParqueadero().getCobros().contains(cobro));
		assertNotNull(cobroRepositorio.findByVehiculo(vehiculo));
		assertTrue(cobroRepositorio.findByVehiculo(vehiculo).getEstado().equals(EstadoCobro.PAGADO.toString()));
				
	}
	
	public void actualizarRangosMock() {
		
		Mockito.when((parqueaderoRepositorio.findAll())).thenReturn(parqueaderos);
		Mockito.when(env.getProperty(KEY_HORAS_MINIMO_DIA_PARQUEO)).thenReturn(HORAS_MINIMO_DIA_PARQUEO);
		Mockito.when(env.getProperty(KEY_HORAS_MAXIMO_DIA_PARQUEO)).thenReturn(HORAS_MAXIMO_DIA_PARQUEO);
		Mockito.when(factoryVehiculo.getCapacidadesParqueadero()).thenReturn(capacidadesParqueadero);
		Mockito.when(capacidadServicio.guardarCapacidad(capacidadTipoVehiculoMOTO)).thenReturn(capacidadTipoVehiculoMOTO);
		Mockito.when(parqueaderoServicioImplementacion.guardarParqueadero(parqueadero)).thenReturn(parqueadero);
		
	}

}
