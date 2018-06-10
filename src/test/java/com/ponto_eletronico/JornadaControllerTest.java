package com.ponto_eletronico;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.OffsetDateTime;

import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class JornadaControllerTest extends AbstractControllerTest {

	@Autowired
    private MockMvc mockMvc;

	@Override
	protected String getPath() {
		return "/jornadas";
	}
	
	@Autowired
	private JornadaRepository jornadaRepository;
	static OffsetDateTime now = OffsetDateTime.now();
	private static Jornada jornada = new Jornada(usuario, now.minusDays(10).minusMinutes(23));
	private static Batida batida;
	private static boolean initialized = false;
	
	@Before
	public void configLocal() {
		if (!initialized) {
			jornada = jornadaRepository.save(jornada);
			batida = jornada.getBatidas().get(0);
			initialized = true;
		}
	}
	
	@Test
	public void testFind() throws Exception {
		mockMvc.perform(get(path +"/" + jornada.getId())
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mockMvc.perform(get(path).header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.param("mode", "MES")
				.param("ano", "2018")
				.param("mes", "6")
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		mockMvc.perform(get(path)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.param("mode", "DIA")
				.param("data", "2018/06/10")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk());
		
		mockMvc.perform(get(path)
				.header(HttpHeaders.AUTHORIZATION, "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.param("mode", "PERIODO")
				.param("data", "2018/05/10")
				.param("dataFinal", "2018/11/10")
				.contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andExpect(jsonPath("$[0].id", is(jornada.getId().intValue())));
	}
	
	@Test
	public void testRealizarBatida() throws Exception {
		mockMvc.perform(post(path +"/usuarios/" + pis)
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk());
		
		Hora hora = new Hora(now.getHour(), now.getMinute());
		mockMvc.perform(post(path + "/" + jornada.getId())
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(hora))
				).andExpect(status().isOk());
	}
	
	@Test
	public void testRemove() throws Exception {

		mockMvc.perform(delete(path + "/batidas/" + batida.getId())
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk());
	
		mockMvc.perform(delete(path + "/" + jornada.getId())
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)
				).andExpect(status().isOk());
	}
}
