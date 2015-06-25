package br.ufc.sghc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import br.ufc.sghc.database.dao.CategoriaCursoDAO;
import br.ufc.sghc.database.dao.CategoriaCursoJPADAO;
import br.ufc.sghc.database.dao.CursoDAO;
import br.ufc.sghc.database.dao.CursoJPADAO;
import br.ufc.sghc.modelo.Categoria;
import br.ufc.sghc.modelo.Curso;

@SessionScoped
@ManagedBean(name = "categoriaCursoBean")
public class CategoriaCursoBean {

	private Curso curso;
	private List<Categoria> categorias;

	public CategoriaCursoBean() {
		this.curso = new Curso();
	}

	public Curso getCurso() {
		return curso;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public List<Categoria> getCategorias() {
		return categorias;
	}

	public void setCategorias(List<Categoria> categorias) {
		this.categorias = categorias;
	}

	public List<Categoria> getCategoriasDisponiveis() {

		CategoriaCursoDAO categoriaDao = new CategoriaCursoJPADAO();

		return categoriaDao.getCategoriasDisponiveis(curso);

	}

	public String prepararCategorias() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idCurso = Long.parseLong(request.getParameter("idCurso"));

			CursoDAO cursoDao = new CursoJPADAO();
			this.curso = cursoDao.find(idCurso);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso inexistente!", ""));

		}

		return "adicionar_categorias";
	}

	public String adicionarCategorias() {
		
		try{
			
			CategoriaCursoDAO categoriaDao = new CategoriaCursoJPADAO();
			categoriaDao.save(categorias, curso);
			
		}catch(PersistenceException ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso ou Categorias inexistentes!", ""));
		}
		this.categorias = new ArrayList<Categoria>();
		
		return "home?faces-redirect=true";
	}

	public String visualizarCategorias(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idCurso = Long.parseLong(request.getParameter("idCurso"));

			CursoDAO cursoDao = new CursoJPADAO();
			CategoriaCursoDAO categoriaDao = new CategoriaCursoJPADAO();
			
			this.curso = cursoDao.find(idCurso);
			this.categorias = categoriaDao.getCategorias(curso);
			
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso inexistente!", ""));
		}
		
		return "visualizar_categorias";
	}
	
	public String removerCategoria(){
		
		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idCurso = Long.parseLong(request.getParameter("idCurso"));
			long idCategoria = Long.parseLong(request.getParameter("idCategoria"));

			CategoriaCursoDAO categoriaDao = new CategoriaCursoJPADAO();
			
			categoriaDao.delete(idCategoria, idCurso);
			this.categorias = categoriaDao.getCategorias(curso);

			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage("Categoria Removida do Curso!", ""));
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso ou Categoria inexistente!", ""));
		}catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso ou Categoria inexistente!", ""));
		}
		
		return "";
	}
	
}
