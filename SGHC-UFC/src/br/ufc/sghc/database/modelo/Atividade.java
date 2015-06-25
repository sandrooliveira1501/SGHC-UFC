package br.ufc.sghc.database.modelo;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Entity
public class Atividade implements Serializable {

	private static final long serialVersionUID = -4449185962540454025L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@ManyToOne()
	@JoinColumn(name = "id_aluno", nullable = false)
	private Aluno aluno;
	private String descricao;
	@ManyToOne()
	@JoinColumn(name = "id_categoria", nullable = false)
	private Categoria categoria;
	private Integer horas;
	private Date dataAtividade;
	private boolean computada;
	@Lob
	private byte[] certificado;
	private boolean possuiArquivo;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public Integer getHoras() {
		return horas;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public void setHoras(Integer horas) {
		this.horas = horas;
	}

	public Date getDataAtividade() {
		return dataAtividade;
	}

	public void setDataAtividade(Date dataAtividade) {
		this.dataAtividade = dataAtividade;
	}

	public boolean isComputada() {
		return computada;
	}

	public void setComputada(boolean computada) {
		this.computada = computada;
	}

	public byte[] getCertificado() {
		return certificado;
	}

	public void setCertificado(byte[] certificado) {
		this.possuiArquivo = true;
		this.certificado = certificado;
	}

	public boolean isPossuiArquivo() {
		return possuiArquivo;
	}

	public void setPossuiArquivo(boolean possuiArquivo) {
		this.possuiArquivo = possuiArquivo;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Atividade)) {
			return false;
		}

		Atividade atividade = (Atividade) obj;

		return atividade.getId() == this.getId();
	}

	@Override
	public String toString() {
		if (this.descricao.length() <= 30) {
			return this.descricao;
		} else {
			return this.descricao.substring(0, 30) + " ...";
		}
	}

}
