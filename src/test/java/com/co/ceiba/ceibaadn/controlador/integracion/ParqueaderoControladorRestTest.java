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
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParqueaderoControladorRestTest {
	
	@Autowired
	private VehiculoServicio vehiculoServicio;
	
	@Autowired
	private VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	Environment env;
	
	private VehiculoDto vehiculoDto;
	
	private static final String PLACA_VALIDA = "QBC123";
	private static final String PLACA_VALIDA_DOS = "qwe456";
	private static final String PLACA_VALIDA_TRES = "TSD777";
	private static final String PLACA_VALIDA_CUATRO = "ZZZ777";
	private static final String PLACA_NO_VALIDA = "ABC1234";
	private static final String TIPO_VEHICULO_OK_CARRO = "carro";
	private static final String TIPO_VEHICULO_OK_MOTO = "moto";
	private static final String TIPO_VEHICULO_NO_VALIDO = "tren";
	private static final int CILINDRAJE = 200; 

	private static final String URL_INGRESAR_OBTENER_VEHICULO = "http://localhost:8080/api/parqueadero";	
	
	@Test
    public void ingresarVehiculoParqueaderoTestE2EokCarro() throws Exception {
		
		vehiculoDto = new VehiculoDto(2L, PLACA_VALIDA_CUATRO, TIPO_VEHICULO_OK_CARRO);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
		//asegurarse que el carro si se retorna en el listado
		assertNotNull(vehiculoRepositorio.findByPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EokMoto() throws Exception {
		
		vehiculoDto = new VehiculoDto(9L, PLACA_VALIDA_TRES, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
		//asegurarse que en el listado de retorno esté esta moto
		assertNotNull(vehiculoRepositorio.findByPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EplacaRepetida() throws Exception {
		
		vehiculoDto = new VehiculoDto(3L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		ingresarVehiculoTest(vehiculoDto);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoYaExisteException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EplacaInvalida() throws Exception {
		
		vehiculoDto = new VehiculoDto(4L, PLACA_NO_VALIDA, TIPO_VEHICULO_OK_CARRO);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2ETipoNoValido() throws Exception {
		
		vehiculoDto = new VehiculoDto(5L, PLACA_VALIDA, TIPO_VEHICULO_NO_VALIDO);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EDtoNull() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestCampoBlancoTipoVehiculo() throws Exception {
		
		vehiculoDto = new VehiculoDto(PLACA_VALIDA, "");
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestCampoPlacaVacio() throws Exception {
		
		vehiculoDto = new VehiculoDto("", TIPO_VEHICULO_OK_CARRO);
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
	public void obtenerVehiculosEnParqueaderoTestOk() throws Exception {
		vehiculoDto = new VehiculoDto(6L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		ingresarVehiculoTest(vehiculoDto);
		vehiculoDto = new VehiculoDto(7L, PLACA_VALIDA_DOS, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		ingresarVehiculoTest(vehiculoDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_INGRESAR_OBTENER_VEHICULO);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(200, result.getResponse().getStatus());
		//Deben de existir mínimo dos placas
		assertTrue(result.getResponse().getContentAsString().split("placa").length - 1 >= 2);
				
	}
	
	public MvcResult ingresarVehiculoTest(VehiculoDto vehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_INGRESAR_OBTENER_VEHICULO)
		.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehiculo).toString());
		return mockMvc.perform(requestBuilder).andReturn();
	}

}
