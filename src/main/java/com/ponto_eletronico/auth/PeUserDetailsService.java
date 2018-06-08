package com.ponto_eletronico.auth;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ponto_eletronico.Usuario;
import com.ponto_eletronico.UsuarioRepository;

@Service
public class PeUserDetailsService implements UserDetailsService {

	@Autowired
    private UsuarioRepository repository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		Optional<Usuario> usuario = repository.findById(username);
        if (!usuario.isPresent()) {
            throw new UsernameNotFoundException(username);
        }
        return new PeUserDetails(usuario.get());
	}

}
