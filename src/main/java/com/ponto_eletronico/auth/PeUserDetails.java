package com.ponto_eletronico.auth;

import java.util.Collection;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.ponto_eletronico.Usuario;

public class PeUserDetails implements UserDetails {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4918105006808244568L;
	
	private Usuario usuario;

	public PeUserDetails(Usuario usuario) {
		this.usuario = usuario;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getPassword() {
		String password = null;
		if (usuario != null) {
			password = usuario.getSenha();
		}
		return password;
	}

	@Override
	public String getUsername() {
		String username = null;
		if (usuario != null) {
			username = usuario.getPis();
		}
		return username;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return false;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
}
