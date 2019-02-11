package com.co.ceiba.ceibaadn.controlador.integracion;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import org.json.JSONObject;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.controlador.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehiculoControladorRestTest {
	
	@Autowired
	private VehiculoServicio vehiculoServicio;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	Environment env;
	
	private VehiculoDto vehiculoDto;
	
	private static final String PLACA_VALIDA = "ABC123";
	private static final String PLACA_VALIDA_DOS = "qwe456";
	private static final String PLACA_VALIDA_TRES = "ASD777";
	private static final String PLACA_NO_VALIDA = "ABC1234";
	private static final String TIPO_VEHICULO_OK_CARRO = "carro";
	private static final String TIPO_VEHICULO_OK_MOTO = "moto";
	private static final String TIPO_VEHICULO_NO_VALIDO = "tren";
	private static final int CILINDRAJE = 200; 
	private static final String URL_GUARDAR_OBTENER_VEHICULO = "http://localhost:8080/api/vehiculo";
	private static final String BAD_REQUEST = JSONObject.quote(new VehiculoBadRequestException().getMessage());
	private static final String YA_EXISTE_VEHICULO = JSONObject.quote(new VehiculoYaExisteException().getMessage());
	
	
	
	@Test
    public void guardarVehiculoTestE2EokCarro() throws Exception {
		
		vehiculoDto = new VehiculoDto(2L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		MvcResult result = crearVehiculo(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(result.getResponse().getContentAsString(), 
				JSONObject.quote(env.getProperty("controller.status.ok")));
		assertNotNull(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EokMoto() throws Exception {
		
		vehiculoDto = new VehiculoDto(9L, PLACA_VALIDA_TRES, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		MvcResult result = crearVehiculo(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(result.getResponse().getContentAsString(), 
				JSONObject.quote(env.getProperty("controller.status.ok")));
		assertNotNull(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EplacaRepetida() throws Exception {
		
		vehiculoDto = new VehiculoDto(3L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		crearVehiculo(vehiculoDto);
		MvcResult result = crearVehiculo(vehiculoDto);
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoYaExisteException.class);
		assertEquals(result.getResponse().getContentAsString(), YA_EXISTE_VEHICULO);
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EplacaInvalida() throws Exception {
		
		vehiculoDto = new VehiculoDto(4L, PLACA_NO_VALIDA, TIPO_VEHICULO_OK_CARRO);
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), BAD_REQUEST);
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2ETipoNoValido() throws Exception {
		
		vehiculoDto = new VehiculoDto(5L, PLACA_VALIDA, TIPO_VEHICULO_NO_VALIDO);
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), BAD_REQUEST);
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EDtoNull() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), BAD_REQUEST);
     		
	}
	
	@Test
    public void guardarVehiculoTestCampoBlancoTipoVehiculo() throws Exception {
		
		vehiculoDto = new VehiculoDto(PLACA_VALIDA, "");
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), BAD_REQUEST);
     		
	}
	
	@Test
    public void guardarVehiculoTestCampoBlancoPlaca() throws Exception {
		
		vehiculoDto = new VehiculoDto("", TIPO_VEHICULO_OK_CARRO);
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), BAD_REQUEST);
     		
	}
	
	@Test
	public void obtenerTodosVehiculoTestOk() throws Exception {
		vehiculoDto = new VehiculoDto(6L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		crearVehiculo(vehiculoDto);
		vehiculoDto = new VehiculoDto(7L, PLACA_VALIDA_DOS, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		crearVehiculo(vehiculoDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_GUARDAR_OBTENER_VEHICULO);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		//Deben de existir mínimo dos placas
		assertTrue(result.getResponse().getContentAsString().split("placa").length - 1 >= 2);
				
	}
	
	public MvcResult crearVehiculo(VehiculoDto vehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_GUARDAR_OBTENER_VEHICULO)
		.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehiculo).toString());
		return mockMvc.perform(requestBuilder).andReturn();
	}

}
