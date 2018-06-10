package com.ponto_eletronico;

import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class AbstractControllerTest {

	protected String path = null;
	
	@Autowired
	protected ObjectMapper objectMapper;
	
	@Autowired
	protected UsuarioRepository usuarioRepository;
	protected static String pis = "83169153803";
	protected static String senha = "321";
	protected String basicString = pis+":"+senha;
	protected static Usuario usuario = new Usuario(pis,senha);;
	
	@Before
	public void config() {
		usuario = usuarioRepository.save(usuario);
		path = getPath();
	}

	protected abstract String getPath();
}
