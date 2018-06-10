package com.ponto_eletronico;

import java.util.Optional;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

public interface UsuarioService {

	public Iterable<Usuario> retrieveAllUsuarios();
	
	public void saveUsuario(@RequestBody Usuario usuario);
	
	public void deleteUsaurio(@RequestBody Usuario usuario);
	
	public Optional<Usuario> retrieveUsuario(@PathVariable String pis);
}
