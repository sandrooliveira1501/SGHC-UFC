package br.ufc.sghc.controller;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;

import br.ufc.sghc.database.dao.AdministradorDAO;
import br.ufc.sghc.database.dao.AdministradorJPADAO;
import br.ufc.sghc.database.modelo.Administrador;
import br.ufc.sghc.exceptions.ErroLoginException;
import br.ufc.sghc.util.CriptoSenha;

@ViewScoped
@ManagedBean(name = "loginAdministradorBean")
public class LoginAdministradorBean {

	private Administrador administrador;

	public LoginAdministradorBean() {
		this.administrador = new Administrador();
	}

	public Administrador getAdministrador() {
		return administrador;
	}

	public void setAdministrador(Administrador administrador) {
		this.administrador = administrador;
	}

	public String realizarLogin() {

		AdministradorDAO administradorDao = new AdministradorJPADAO();
		try {

			administrador.setSenha(CriptoSenha.md5(administrador.getSenha()));
			administrador = administradorDao.verificarAdministrador(administrador);

			HttpSession session = (HttpSession) FacesContext
					.getCurrentInstance().getExternalContext().getSession(true);
			session.setAttribute("administrador", administrador);
			System.out.println("teste");
			return "/admin/home.xhtml?faces-redirect=true";
		} catch (ErroLoginException ex) {
			String mensagem = "Usuário ou senha inválidos";
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN, mensagem,
							mensagem));
			return "?faces-redirect=true";
		}

	}

	public String realizarLogout() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		if (session != null) {
			session.invalidate();
		}

		return "/login?faces-redirect=true";
	}

}
