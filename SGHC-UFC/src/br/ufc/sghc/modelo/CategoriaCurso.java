package br.ufc.sghc.modelo;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class CategoriaCurso {

	@Id
	@GeneratedValue
	private long id;
	@ManyToOne()
	@JoinColumn(name = "id_curso", nullable = false)
	private Curso curso;
	@ManyToOne()
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

}
