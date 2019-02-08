package com.co.ceiba.ceibaadn.controlador.integracion;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import org.json.JSONObject;
import org.junit.Before;
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
import com.co.ceiba.ceibaadn.dominio.TipoVehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.repositorio.TipoVehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.VehiculoServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class VehiculoControladorRestTest {
	
	@Autowired
	private TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
	@Autowired
	private VehiculoServicio vehiculoServicio;
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	Environment env;
	
	private TipoVehiculo tipoVehiculo;
	private VehiculoDto vehiculoDto;
	
	private static final String PLACA_VALIDA = "ABC123";
	private static final String PLACA_NO_VALIDA = "ABC1234";
	private static final String TIPO_VEHICULO_OK = "carro";
	private static final String URL_GUARDAR_VEHICULO = "http://localhost:8080/api/vehiculo";
		
	@Before
	public void setUp() {
		 tipoVehiculo = new TipoVehiculo();
		 tipoVehiculo.setId(1L);
	     tipoVehiculo.setNombre(TIPO_VEHICULO_OK);
	     tipoVehiculoRepositorio.save(tipoVehiculo);		
	}
	
	@Test
    public void guardarVehiculoTestE2Eok() throws Exception {
		
		vehiculoDto = new VehiculoDto(2L, PLACA_VALIDA, TIPO_VEHICULO_OK);
		MvcResult result = crearVehiculo(vehiculoDto);
		
		assertEquals(result.getResponse().getContentAsString(), 
				env.getProperty("controller.status.ok"));
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EplacaRepetida() throws Exception {
		
		vehiculoDto = new VehiculoDto(3L, PLACA_VALIDA, TIPO_VEHICULO_OK);
		crearVehiculo(vehiculoDto);
		MvcResult result = crearVehiculo(vehiculoDto);
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoYaExisteException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoYaExisteException().getMessage());
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EplacaInvalida() throws Exception {
		
		vehiculoDto = new VehiculoDto(4L, PLACA_NO_VALIDA, TIPO_VEHICULO_OK);
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoBadRequestException().getMessage());
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2ETipoNoValido() throws Exception {
		
		vehiculoDto = new VehiculoDto(5L, PLACA_VALIDA, "tren");
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoBadRequestException().getMessage());
		assertNull(vehiculoServicio.obtenerVehiculoPorId(vehiculoDto.getId()));
     		
	}
	
	@Test
    public void guardarVehiculoTestE2EDtoNull() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoBadRequestException().getMessage());
     		
	}
	
	@Test
    public void guardarVehiculoTestCampoBlancoTipoVehiculo() throws Exception {
		
		vehiculoDto = new VehiculoDto(PLACA_VALIDA, "");
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoBadRequestException().getMessage());
     		
	}
	
	@Test
    public void guardarVehiculoTestCampoBlancoPlaca() throws Exception {
		
		vehiculoDto = new VehiculoDto("", TIPO_VEHICULO_OK);
		MvcResult result = crearVehiculo(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertEquals(result.getResponse().getContentAsString(), new VehiculoBadRequestException().getMessage());
     		
	}
	
	public MvcResult crearVehiculo(VehiculoDto vehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_GUARDAR_VEHICULO)
		.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehiculo).toString());
		return mockMvc.perform(requestBuilder).andReturn();
	}


}
