package com.ponto_eletronico;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Usuario {

	@Id
	private String pis;
	private String senha;

	public Usuario() {
		// TODO Auto-generated constructor stub
	}
	
	public Usuario(String pis) {
		this.pis = pis;
	}

	public Usuario(String pis, String senha) {
		this.pis = pis;
		this.senha = senha;
	}

	public String getPis() {
		return pis;
	}

	public void setPis(String pis) {
		this.pis = pis;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

}
