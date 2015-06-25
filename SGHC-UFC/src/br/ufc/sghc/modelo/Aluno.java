package br.ufc.sghc.modelo;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class Aluno implements Serializable {

	private static final long serialVersionUID = -3651091686738187013L;
	@Id
	private Long matricula;
	private String nome;
	private String email;
	@ManyToOne()
	@JoinColumn(name = "id_curso", nullable = false)
	private Curso curso;
	private String senha;

	public Long getMatricula() {
		return matricula;
	}

	public void setMatricula(Long matricula) {
		this.matricula = matricula;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}

	@Override
	public String toString() {
		return this.matricula + " " + this.nome;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Aluno)) {
			return false;
		}

		Aluno aluno = (Aluno) obj;

		return aluno.getMatricula() == this.getMatricula();
	}

}
