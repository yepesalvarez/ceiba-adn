package com.co.ceiba.ceibaadn.controlador.integracion;

import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.stream.StreamSupport;

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

import com.co.ceiba.ceibaadn.dominio.Vehiculo;
import com.co.ceiba.ceibaadn.dominio.dtos.VehiculoDto;
import com.co.ceiba.ceibaadn.dominio.excepciones.CobroNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.ParqueaderoRetiroVehiculoNoPosibleException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoBadRequestException;
import com.co.ceiba.ceibaadn.dominio.excepciones.VehiculoYaExisteException;
import com.co.ceiba.ceibaadn.dominio.util.FactoryVehiculo;
import com.co.ceiba.ceibaadn.repositorio.TipoVehiculoRepositorio;
import com.co.ceiba.ceibaadn.repositorio.VehiculoRepositorio;
import com.co.ceiba.ceibaadn.servicio.ParqueaderoServicio;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class ParqueaderoControladorRestTest {
	
	@Autowired
	private ParqueaderoServicio parqueaderoServicio;
	
	@Autowired
	private VehiculoRepositorio vehiculoRepositorio;
	
	@Autowired
	private FactoryVehiculo factoryVehiculo;
	
	@Autowired
	private TipoVehiculoRepositorio tipoVehiculoRepositorio;
	
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
	private static final String URL_INDEX = "http://localhost:8080/";
	
	@Test
    public void ingresarVehiculoParqueaderoTestE2EokCarro() throws Exception {
		
		vehiculoDto = new VehiculoDto(2L, PLACA_VALIDA_CUATRO, TIPO_VEHICULO_OK_CARRO);
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(vehiculoRepositorio.findByPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EokMoto() throws Exception {
		
		vehiculoDto = new VehiculoDto(9L, PLACA_VALIDA_TRES, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
		
		assertEquals(200, result.getResponse().getStatus());
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
		assertNull(vehiculoRepositorio.findByPlaca(vehiculoDto.getPlaca()));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2ETipoNoValido() throws Exception {
		
		vehiculoDto = new VehiculoDto(5L, PLACA_VALIDA, TIPO_VEHICULO_NO_VALIDO);
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
		assertNull(vehiculoRepositorio.findById(vehiculoDto.getId()).orElse(null));
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EDtoVacio() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EPlacaNula() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		vehiculoDto.setPlaca(null);
		vehiculoDto.setTipoVehiculo(TIPO_VEHICULO_OK_CARRO);
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2ETipoVehiculoNulo() throws Exception {
		
		vehiculoDto = new VehiculoDto();
		vehiculoDto.setPlaca(PLACA_VALIDA);
		vehiculoDto.setTipoVehiculo(null);
		
		MvcResult result = ingresarVehiculoTest(vehiculoDto);
	
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(VehiculoBadRequestException.class);
     		
	}
	
	@Test
    public void ingresarVehiculoTestE2EDtoNull() throws Exception {
			
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_INGRESAR_OBTENER_VEHICULO);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
	
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
	public void obtenerVehiculosEnParqueaderoAdicionandoOtrosTest() throws Exception {
		vehiculoDto = new VehiculoDto(6L, PLACA_VALIDA, TIPO_VEHICULO_OK_CARRO);
		ingresarVehiculoTest(vehiculoDto);
		vehiculoDto = new VehiculoDto(7L, PLACA_VALIDA_DOS, TIPO_VEHICULO_OK_MOTO, CILINDRAJE);
		ingresarVehiculoTest(vehiculoDto);
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_INGRESAR_OBTENER_VEHICULO);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertTrue(result.getResponse().getContentAsString().split("placa").length - 1L >= 2);
				
	}
	
	@Test
	public void obtenerVehiculosEnParqueaderoSinAdicionarMasTest() throws Exception {

		int vehiculos = (int) StreamSupport.stream(vehiculoRepositorio.findAll().spliterator(), false).count();
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_INGRESAR_OBTENER_VEHICULO);
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertEquals(vehiculos, result.getResponse().getContentAsString().split("placa").length - 1L);
				
	}
	
	@Test
	public void retirarVehiculoParqueaderoOk() throws Exception {
		String placa = "vvv666";
		vehiculoDto = new VehiculoDto(placa, "moto", 500);
		ingresarVehiculoTest(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
		pagarParqueaderoTest(vehiculo.getId());
		
		int vehiculosAntesBorrado = parqueaderoServicio.obtenerVehiculosParqueados().size();
		MvcResult result = retirarVehiculoTest(vehiculo.getId());
		int vehiculosDespuesBorrado = parqueaderoServicio.obtenerVehiculosParqueados().size();
		
		assertEquals(200, result.getResponse().getStatus());
		assertTrue(vehiculosAntesBorrado > vehiculosDespuesBorrado);
		
	}
	
	@Test
	public void retirarVehiculoParqueaderoNoPagadoTest() throws Exception {
		String placa = "mmm999";
		vehiculoDto = new VehiculoDto(placa, "moto", 500);
		ingresarVehiculoTest(vehiculoDto);
		Vehiculo vehiculo = vehiculoRepositorio.findByPlaca(placa);
		
		int vehiculosAntesBorrado = parqueaderoServicio.obtenerVehiculosParqueados().size();
		MvcResult result = retirarVehiculoTest(vehiculo.getId());
		int vehiculosDespuesBorrado = parqueaderoServicio.obtenerVehiculosParqueados().size();
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
		assertTrue(vehiculosAntesBorrado == vehiculosDespuesBorrado);
		
	}
	
	@Test
	public void retirarVehiculoParqueaderoIdNuloTest() throws Exception {

		MvcResult result = retirarVehiculoTest(null);	
		
		assertEquals(405, result.getResponse().getStatus());

	}
	
	@Test
	public void retirarVehiculoParqueaderoIdInexistenteTest() throws Exception {
				
		MvcResult result = retirarVehiculoTest(9999999999L);
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
	}
	
	@Test
	public void retirarVehiculoExistenteNoParqueadoTest() throws Exception {
		String placa = "bbb555";
		vehiculoDto = new VehiculoDto(placa, TIPO_VEHICULO_OK_MOTO, 150);
		Vehiculo vehiculo = factoryVehiculo.getVehiculo(vehiculoDto, tipoVehiculoRepositorio.findByNombre(TIPO_VEHICULO_OK_MOTO));
		vehiculo = vehiculoRepositorio.save(vehiculo);
		
		MvcResult result = retirarVehiculoTest(vehiculo.getId());
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(ParqueaderoRetiroVehiculoNoPosibleException.class);
	}
	
	@Test
	public void pagarParqueaderoVehiculoNoExiste() throws Exception {
		
		MvcResult result = pagarParqueaderoTest(999999999999L);
		
		assertEquals(400, result.getResponse().getStatus());
		assertThatExceptionOfType(CobroNoPosibleException.class);
		
	}
	
	@Test
	public void obtenerParqueaderoTestOk() throws Exception {
		RequestBuilder requestBuilder = MockMvcRequestBuilders.get(URL_INDEX);
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(parqueaderoServicio.getParqueadero().getVehiculos());
	}
	
	public MvcResult ingresarVehiculoTest(VehiculoDto vehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post(URL_INGRESAR_OBTENER_VEHICULO)
		.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehiculo).toString());
		return mockMvc.perform(requestBuilder).andReturn();
	}
	
	public MvcResult retirarVehiculoTest(Long idVehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.delete(URL_INGRESAR_OBTENER_VEHICULO + "/{idVehiculo}" , idVehiculo);
		return mockMvc.perform(requestBuilder).andReturn();
	}
	
	public MvcResult pagarParqueaderoTest(Long idVehiculo) throws Exception{
		RequestBuilder requestBuilder = MockMvcRequestBuilders.patch(URL_INGRESAR_OBTENER_VEHICULO).param("idVehiculo", idVehiculo.toString());			
				return mockMvc.perform(requestBuilder).andReturn();
	}

}
