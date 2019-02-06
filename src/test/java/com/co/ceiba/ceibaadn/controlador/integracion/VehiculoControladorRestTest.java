package com.co.ceiba.ceibaadn.controlador.integracion;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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
	
	@Before
	public void setUp() {
		 tipoVehiculo = new TipoVehiculo();
		 tipoVehiculo.setId(1L);
	     tipoVehiculo.setNombre("carro");
	     tipoVehiculoRepositorio.save(tipoVehiculo);
	     
	     vehiculoDto = new VehiculoDto(1L, "ZAP777", "carro");
		
	}
	
	@Test
    public void guardarVehiculoTestE2E() throws Exception {
		
		RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/api/vehiculo")
		.contentType(MediaType.APPLICATION_JSON_UTF8).content(new JSONObject(vehiculoDto).toString());
		
		MvcResult result = mockMvc.perform(requestBuilder).andReturn();
		assertEquals(result.getResponse().getContentAsString(), 
				env.getProperty("controller.status.ok"));
		assertEquals(200, result.getResponse().getStatus());
		assertNotNull(vehiculoServicio.obtenerVehiculoPorPlaca(vehiculoDto.getPlaca()));
     		
	}


}
