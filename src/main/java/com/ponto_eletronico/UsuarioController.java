package com.ponto_eletronico;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioService service;
	
	@GetMapping
	public Iterable<Usuario> retrieveAllUsuarios() {
		return service.retrieveAllUsuarios();
	}
	
	@ExceptionHandler(PeException.class)
	@PostMapping
	public void saveUsuario(@RequestBody Usuario usuario) {
		service.saveUsuario(usuario);
	}
	
	@DeleteMapping
	public void deleteUsaurio(@RequestBody Usuario usuario) {
		service.deleteUsaurio(usuario);
	}
	
	@GetMapping("/{pis}")
	public Optional<Usuario> retrieveUsuario(@PathVariable String pis) {
		return service.retrieveUsuario(pis);
	}
}
