package br.ufc.sghc.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.faces.context.Flash;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpSession;

import br.ufc.sghc.database.dao.AlunoDAO;
import br.ufc.sghc.database.dao.AlunoJPADAO;
import br.ufc.sghc.database.dao.CursoDAO;
import br.ufc.sghc.database.dao.CursoJPADAO;
import br.ufc.sghc.database.modelo.Aluno;
import br.ufc.sghc.database.modelo.Atividade;
import br.ufc.sghc.database.modelo.Curso;
import br.ufc.sghc.exceptions.ErroLoginException;
import br.ufc.sghc.util.CriptoSenha;

@ManagedBean(name = "alunoBean")
public class AlunoBean implements Serializable {

	private static final long serialVersionUID = -4088840626925102735L;
	private Aluno aluno;
	private String novaSenha;
	private String senhaAntiga;

	public AlunoBean() {
		if (this.aluno == null) {
			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext()
					.getSession(false);

			if (session == null || session.getAttribute("usuario") == null) {
				aluno = new Aluno();
			} else {
				Aluno alunoSession = (Aluno) session.getAttribute("usuario");
				AlunoDAO alunoDao = new AlunoJPADAO();
				this.aluno = alunoDao.find(alunoSession.getMatricula());
			}
		}
	}

	public Aluno getAluno() {

		return aluno;

	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public List<Curso> getCursos() {
		
		CursoDAO cursoDao= new CursoJPADAO();
		
		List<Curso> cursos = cursoDao.find();
		if(cursos == null) cursos = new ArrayList<Curso>();
		
		return cursos;
	}

	public String getNovaSenha() {
		return novaSenha;
	}

	public void setNovaSenha(String novaSenha) {
		this.novaSenha = novaSenha;
	}

	public String getSenhaAntiga() {
		return senhaAntiga;
	}

	public void setSenhaAntiga(String senhaAntiga) {
		this.senhaAntiga = senhaAntiga;
	}

	public String adicionaAluno() {
		AlunoDAO alunoDao = new AlunoJPADAO();
		try {

			aluno.setSenha(CriptoSenha.md5(aluno.getSenha()));
			alunoDao.save(aluno);
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_INFO,
							"Seu cadastro foi concluído com sucesso!",
							""));

		} catch (PersistenceException ex) {
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"Aviso",
							new FacesMessage(
									FacesMessage.SEVERITY_WARN,
									"Erro ao tentar cadastrar aluno, tente novamente!",
									""));
		}

		return "";
	}

	public String atualizaPerfil() {

		Flash flash = FacesContext.getCurrentInstance().getExternalContext()
				.getFlash();
		flash.setKeepMessages(true);
		AlunoDAO alunoDao = new AlunoJPADAO();

		try {
			alunoDao.update(this.aluno);
			FacesContext.getCurrentInstance().addMessage(null,
					new FacesMessage("Perfil Atualizado", ""));

		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage("Erro ao atualizar seus dados!",
							"Confira se todos os campos estão corretos"));

		}

		return "/app/home?faces-redirect=true";
	}

	public String atualizaSenha() {

		AlunoDAO alunoDao = new AlunoJPADAO();
		HttpSession session = (HttpSession) FacesContext
				.getCurrentInstance().getExternalContext()
				.getSession(false);
		
		Aluno alunoSession = (Aluno) session.getAttribute("usuario");
		try {
			alunoSession.setSenha(CriptoSenha.md5(senhaAntiga));
			this.aluno = alunoDao.verificaAluno(alunoSession);
			this.aluno.setSenha(CriptoSenha.md5(novaSenha));
			alunoDao.update(this.aluno);
			session.setAttribute("usuario", this.aluno);
			

			FacesContext.getCurrentInstance().addMessage(
					"Senha alterada",
					new FacesMessage("Senha alterada",
							""));
			
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Confira se todos os campos estão corretos!",
							"Erro ao atualizar dados"));

		} catch (ErroLoginException ex2) {
			ex2.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_ERROR,"Senha incorreta!",
							"Erro ao atualizar dados"));

		}

		this.senhaAntiga = "";
		this.novaSenha = "";
		

		return "?faces-redirect=true";
	}

}
