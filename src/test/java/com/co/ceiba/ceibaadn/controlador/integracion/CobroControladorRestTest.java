package com.co.ceiba.ceibaadn.controlador.integracion;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.context.junit4.SpringRunner;

import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;


@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class CobroControladorRestTest {

	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	private ParqueaderoServicio parqueaderoServicio;
		
	@Autowired
	private Environment env;
	
	private static final String URL_COBRO = "http://localhost:8080/api/cobro";
	private static final String FORMATO_FECHA = "yyyy-MM-dd HH:mm";
	private static final String TIPO_VEHICULO_CARRO = "carro";
	private static final String TIPO_VEHICULO_MOTO = "moto";
	private static final String PARAMETRO_ID_VEHICULO = "idVehiculo";
	private static final String PARAMETRO_FECHA_FIN_PARQUEO = "finParqueo";
	private static final String KEY_PRECIO_HORA_CARRO = "vehiculos.carro.hora.precio";
	private static final String KEY_PRECIO_HORA_MOTO = "vehiculos.moto.hora.precio";
	private static final String KEY_PRECIO_DIA_CARRO = "vehiculos.carro.dia.precio";
	private static final String KEY_LIMITE_CILINDRAJE = "vehiculos.moto.cilindraje,maximo";
	private static final String KEY_RECARGO_CILINDRAJE = "vehiculos.moto.cilndraje.recargo";

	@Test
	public void testGenerarCobroBasicoHoras() throws Exception {
		
		String placa = "ghj654";
		VehiculoDto vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_CARRO);
		parqueaderoServicio.ingresarVehiculo(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, vehiculo.getId().toString())
				.param(PARAMETRO_FECHA_FIN_PARQUEO, LocalDateTime.now().plusHours(2).format(formatter));			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertTrue(Double.parseDouble(env.getProperty(KEY_PRECIO_HORA_CARRO)) * 2
				<= Double.parseDouble(result.getResponse().getContentAsString()));
		
	}
	
	@Test
	public void testGenerarCobroHorasConRecargo() throws Exception {	
		
		String placa = "sdf321";
		VehiculoDto vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_MOTO, Integer.parseInt(env.getProperty(KEY_LIMITE_CILINDRAJE)) + 200);
		parqueaderoServicio.ingresarVehiculo(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, vehiculo.getId().toString())
				.param(PARAMETRO_FECHA_FIN_PARQUEO, LocalDateTime.now().plusHours(12).format(formatter));			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertTrue(Double.parseDouble(env.getProperty(KEY_PRECIO_HORA_MOTO)) * 2 
				+ Double.parseDouble(env.getProperty(KEY_RECARGO_CILINDRAJE)) 
						<=  Double.parseDouble(result.getResponse().getContentAsString()));
	}
	
	@Test
	public void testGenerarCobroMayorUnDia() throws Exception {
		
		String placa = "iop789";
		VehiculoDto vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_CARRO);
		parqueaderoServicio.ingresarVehiculo(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, vehiculo.getId().toString())
				.param(PARAMETRO_FECHA_FIN_PARQUEO, LocalDateTime.now().plusDays(2).plusHours(2).format(formatter));			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(String.valueOf(Double.parseDouble(env.getProperty(KEY_PRECIO_HORA_CARRO)) * 2
				+ Double.parseDouble(env.getProperty(KEY_PRECIO_DIA_CARRO)) * 2)
				, result.getResponse().getContentAsString());
	}
	
	@Test
	public void testGenerarCobroVehiculoNoExistente() throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, String.valueOf(9999999999L))
				.param(PARAMETRO_FECHA_FIN_PARQUEO, LocalDateTime.now().plusHours(2).format(formatter));			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testGenerarCobroFechaInvalida() throws Exception {
		
		String placa = "KEL601";
		VehiculoDto vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_CARRO);
		parqueaderoServicio.ingresarVehiculo(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);		
		String fechaInvalida = "02/12/2019 05:40 PM";
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, vehiculo.getId().toString())
				.param(PARAMETRO_FECHA_FIN_PARQUEO, fechaInvalida);			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testGenerarCobroFechaFinParqueoVacia() throws Exception {
		
		String placa = "KEL602";
		VehiculoDto vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_CARRO);
		parqueaderoServicio.ingresarVehiculo(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);		
		String fechaInvalida = "";
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, vehiculo.getId().toString())
				.param(PARAMETRO_FECHA_FIN_PARQUEO, fechaInvalida);			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void testGenerarCobroIdVehiculoVacio() throws Exception {
		
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(FORMATO_FECHA);
		 
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_COBRO).param(PARAMETRO_ID_VEHICULO, "")
				.param(PARAMETRO_FECHA_FIN_PARQUEO, LocalDateTime.now().plusHours(2).format(formatter));			
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	


}
