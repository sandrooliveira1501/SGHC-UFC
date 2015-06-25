package br.ufc.sghc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import br.ufc.sghc.database.dao.AlunoDAO;
import br.ufc.sghc.database.dao.AlunoJPADAO;
import br.ufc.sghc.database.dao.AtividadeDAO;
import br.ufc.sghc.database.dao.AtividadeJPADAO;
import br.ufc.sghc.database.dao.CategoriaCursoDAO;
import br.ufc.sghc.database.dao.CategoriaCursoJPADAO;
import br.ufc.sghc.database.modelo.Aluno;
import br.ufc.sghc.database.modelo.Atividade;
import br.ufc.sghc.database.modelo.Categoria;

@RequestScoped
@ManagedBean
public class AtividadeBean implements Serializable {

	private static final long serialVersionUID = -8413893573030456356L;
	private Atividade atividade;

	public AtividadeBean() {
		this.atividade = new Atividade();
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public List<Categoria> getCategorias() {
		
		HttpSession session = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext().getSession(true);
		Aluno aluno = (Aluno) session.getAttribute("usuario");
		CategoriaCursoDAO categoriaDao = new CategoriaCursoJPADAO();
		List<Categoria> categorias = categoriaDao.getCategorias(aluno.getCurso());
		
		if(categorias == null)
			categorias = new ArrayList<Categoria>();
		
		return categorias;
	}

	public String adicionarAtividade() {

		AtividadeDAO dao = new AtividadeJPADAO();
		try {

			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			Aluno aluno = (Aluno) session.getAttribute("usuario");
			atividade.setAluno(aluno);
			dao.save(atividade);
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage("Erro ao tentar alterar atividade,tente novamente!",
							""));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage("Erro ao tentar alterar atividade,tente novamente!",
							""));
		}

		return "/app/home?faces-redirect=true";
	}

	public String verAtividade() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();
		Long id = Long.parseLong(request.getParameter("idAtividade"));

		AtividadeDAO dao = new AtividadeJPADAO();
		try {
			this.atividade = dao.find(id);
		} catch (PersistenceException ex) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"Aviso",
							new FacesMessage("Erro ao tentar selecionar atividade,tente novamente!",
									""));
			ex.printStackTrace();
			return "";
		}

		return "/app/ver-atividade";
	}

	public String atualizaAtividade() {

		AtividadeDAO dao = new AtividadeJPADAO();

		try {
			Aluno aluno = new Aluno();
			System.out.println(atividade);
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);

			if (session != null && session.getAttribute("usuario") != null) {
				aluno = (Aluno) session.getAttribute("usuario");
			}
			atividade.setAluno(aluno);
			dao.update(atividade);

		} catch (PersistenceException e) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"Aviso",
							new FacesMessage("Erro ao tentar alterar atividade,tente novamente!",
									""));
			e.printStackTrace();
		}

		return "/app/home?faces-redirect=true";
	}

}
