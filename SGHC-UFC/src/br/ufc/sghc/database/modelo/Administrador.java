package br.ufc.sghc.database.modelo;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Administrador {

	@Id
	private String login;
	private String senha;

	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return super.toString();
	}
	
}
