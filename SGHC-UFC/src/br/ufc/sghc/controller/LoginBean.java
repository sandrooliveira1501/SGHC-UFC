package br.ufc.sghc.controller;

import java.io.Serializable;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.ufc.sghc.database.dao.AlunoDAO;
import br.ufc.sghc.database.dao.AlunoJPADAO;
import br.ufc.sghc.exceptions.ErroLoginException;
import br.ufc.sghc.modelo.Aluno;
import br.ufc.sghc.util.CriptoSenha;

@ViewScoped
@ManagedBean(name = "loginBean")
public class LoginBean implements Serializable{

	private static final long serialVersionUID = -8985504728496996570L;
	private Aluno aluno;

	public LoginBean() {
		this.aluno = new Aluno();
	}

	public Aluno getAluno() {
		return aluno;
	}

	public void setAluno(Aluno aluno) {
		this.aluno = aluno;
	}

	public String realizarLogin() {

		AlunoDAO dao = new AlunoJPADAO();
		try {
			
			aluno.setSenha(CriptoSenha.md5(aluno.getSenha()));	
			aluno = dao.verificaAluno(aluno);

			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("usuario", aluno);
			session.setAttribute("apenasSubmetidas", false);
			return "/app/home.xhtml?faces-redirect=true";
		} catch (ErroLoginException ex) {
			String mensagem = "Usuário ou senha inválidos";
			FacesContext.getCurrentInstance().addMessage("Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,mensagem,
mensagem));
			return "?faces-redirect=true";
		} 

	}

	public String realizarLogout(){
		
		HttpSession session = (HttpSession)FacesContext.getCurrentInstance().getExternalContext().getSession(false);
		
		if(session != null){
			session.invalidate();
		}
		
		return "/login?faces-redirect=true";
	}
	
}
