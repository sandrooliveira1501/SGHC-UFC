package br.ufc.sghc.controller;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.hibernate.exception.ConstraintViolationException;

import br.ufc.sghc.database.dao.CursoDAO;
import br.ufc.sghc.database.dao.CursoJPADAO;
import br.ufc.sghc.database.modelo.Curso;

@ManagedBean(name = "cursoBean")
public class CursoBean {

	private Curso curso;

	public CursoBean() {
		this.curso = new Curso();
	}

	public Curso getCurso() {
		return curso;
	}

	public List<Curso> getCursos() {

		CursoDAO cursoDao = new CursoJPADAO();
		List<Curso> cursos = cursoDao.find();
		if (cursos == null) {
			return new ArrayList<Curso>();
		}

		return cursos;
	}

	public void setCurso(Curso curso) {
		this.curso = curso;
	}

	public String adicionarCurso() {

		CursoDAO cursoDao = new CursoJPADAO();
		try {

			cursoDao.save(this.curso);
			FacesContext.getCurrentInstance().addMessage("Sucesso",
					new FacesMessage("Curso adicionado com sucesso!", ""));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage("Aviso",
					new FacesMessage("Tente adicionar o curso novamente!", ""));
		}

		return "?faces-redirect=true";
	}

	public String removerCurso() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idCurso = Long.parseLong(request.getParameter("idCurso"));

			CursoDAO cursoDao = new CursoJPADAO();
			Curso curso = cursoDao.find(idCurso);

			cursoDao.delete(curso);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Curso inexistente!", ""));

		} catch (PersistenceException e) {
			e.printStackTrace();
			new CursoJPADAO().rollback();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Alunos já estão vinculados a este curso!", ""));
		}

		return "?faces-redirect=true";
	}

	public String visualizarCurso() {

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
			return "";
		}

		return "visualizar_curso";
	}
	
	public String atualizarCurso(){
		
		try{
			
			CursoDAO cursoDao = new CursoJPADAO();
			cursoDao.update(this.curso);
			

			FacesContext.getCurrentInstance().addMessage(
					"Sucesso",
					new FacesMessage(
							"Curso atualizado", ""));
		}catch(PersistenceException ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Erro ao atualizar dados do Curso", ""));
			return "";
		}
		
		return "home?faces-redirect=true";
	}

}
