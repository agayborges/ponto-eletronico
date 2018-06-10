package com.ponto_eletronico;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	private UsuarioRepository repository;
	
	public Iterable<Usuario> retrieveAllUsuarios() {
		return repository.findAll();
	}
	
	public void saveUsuario(@RequestBody Usuario usuario) {
		repository.save(usuario);
	}
	
	public void deleteUsaurio(@RequestBody Usuario usuario) {
		repository.delete(usuario);
	}
	
	public Optional<Usuario> retrieveUsuario(@PathVariable String pis) {
		return repository.findById(pis);
	}
}
