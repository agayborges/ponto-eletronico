package com.ponto_eletronico.auth;

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
		Usuario usuario = repository.findByPis(username);
        if (usuario == null) {
            throw new UsernameNotFoundException(username);
        }
        return new PeUserDetails(usuario);
	}

}
