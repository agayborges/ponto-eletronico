package com.ponto_eletronico;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.Base64Utils;

import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class UsuarioControllerTest {

	private static String path = "/usuarios";
	
	@Autowired
    private MockMvc mockMvc;
	
	@Autowired
	private ObjectMapper objectMapper;
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	private String pis = "83169153803";
	private String senha = "321";
	private String basicString = pis+":"+senha;
	
	@Before
	public void config() {
		Usuario usuario = new Usuario(pis,senha);
		usuarioRepository.save(usuario);
	}
	
	@Test
	public void testSave() throws Exception {
		Usuario usuario = new Usuario("19457513978","123");
		mockMvc.perform(post(path)
				.header(HttpHeaders.AUTHORIZATION,
	                    "Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(usuario))).andExpect(status().isOk());
		
		Usuario usuario2 = new Usuario();
		mockMvc.perform(post(path)
				.header(HttpHeaders.AUTHORIZATION,
						"Basic " + Base64Utils.encodeToString(basicString.getBytes()))
				.contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsBytes(usuario2))).andExpect(status().is(400));
	}

}
