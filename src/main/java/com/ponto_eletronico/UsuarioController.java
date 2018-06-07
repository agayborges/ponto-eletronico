package com.ponto_eletronico;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

	@Autowired
	private UsuarioRepository repository;
	
	@GetMapping
	public Iterable<Usuario> retrieveAllUsuarios() {
		return repository.findAll();
	}
	
	@PostMapping
	public void createUsuario(@RequestBody Usuario usuario) {
		repository.save(usuario);
	}
	
	@PutMapping
	public void updateUsuario(@RequestBody Usuario usuario) {
		repository.save(usuario);
	}
	
	@DeleteMapping
	public void deleteUsaurio(@RequestBody Usuario usuario) {
		repository.delete(usuario);
	}
	
	@GetMapping("/{pis}")
	public Optional<Usuario> retrieveUsuario(@PathVariable String pis) {
		return repository.findById(pis);
	}
}
