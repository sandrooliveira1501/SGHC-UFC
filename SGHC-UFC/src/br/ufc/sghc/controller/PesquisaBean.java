package br.ufc.sghc.controller;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

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
import br.ufc.sghc.modelo.Aluno;
import br.ufc.sghc.modelo.Atividade;
import br.ufc.sghc.modelo.Categoria;

@RequestScoped
@ManagedBean(name = "pesquisaBean")
public class PesquisaBean {

	private List<Atividade> atividades;
	private Categoria categoria;
	private Atividade atividade;
	private boolean search = false;

	public PesquisaBean() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		Aluno aluno = (Aluno) session.getAttribute("usuario");

		AtividadeDAO atividadeDao = new AtividadeJPADAO();

		this.atividades = atividadeDao.getAtividades(aluno);
	}

	public List<Atividade> getAtividades() {
		return this.atividades;
	}

	public List<Atividade> getAtividadesPesquisa() {
		return this.atividades;
	}

	public void setAtividades(List<Atividade> atividades) {
		this.atividades = atividades;
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public Atividade getAtividade() {
		return atividade;
	}

	public void setAtividade(Atividade atividade) {
		this.atividade = atividade;
	}

	public boolean isSearch() {
		return search;
	}

	public void setSearch(boolean search) {
		this.search = search;
	}

	public String removerAtividade() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(true);
		Aluno alunoSession = (Aluno) session.getAttribute("usuario");

		AlunoDAO alunoDao = new AlunoJPADAO();
		Aluno aluno = alunoDao.find(alunoSession.getMatricula());

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		Atividade atividade = new Atividade();

		atividade.setId(Long.parseLong(request.getParameter("idAtividade")));

		AtividadeDAO dao = new AtividadeJPADAO();
		
		try {
			atividade = dao.find(atividade.getId());
			dao.delete(atividade);
			this.atividades.remove(atividade);
			FacesContext.getCurrentInstance().addMessage("Aviso",
					new FacesMessage("Atividade removida com sucesso!", ""));

		} catch (PersistenceException e) {
			e.printStackTrace();
			FacesContext
					.getCurrentInstance()
					.addMessage(
							"Aviso",
							new FacesMessage(
									"Erro ao tentar excluir a atividade, tente novamente!",
									""));

		}

		return "?faces-redirect=true";
	}

	public String pesquisarPorCategoria() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		Aluno aluno = (Aluno) session.getAttribute("usuario");
		AtividadeDAO atividadeDao = new AtividadeJPADAO();
		this.atividades = atividadeDao.getAtividadesCategoria(aluno, categoria);
		this.search = true;

		return "pesquisa";
	}

	public int getTotalHoras() {

		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		int totalHoras = 0;

		Map<Categoria, Integer> horasCategorias = new LinkedHashMap<Categoria, Integer>();
		for (Atividade atividade : this.atividades) {
			Categoria categoria = atividade.getCategoria();
			Integer qtdHoras = horasCategorias.get(categoria);
			if (qtdHoras == null) {
				qtdHoras = 0;
			} else {
				totalHoras -= qtdHoras;
			}

			qtdHoras = qtdHoras + atividade.getHoras();
			if (qtdHoras > categoria.getMaximoHoras()) {
				qtdHoras = categoria.getMaximoHoras();
			}

			horasCategorias.put(categoria, qtdHoras);
			totalHoras += qtdHoras;

		}

		return totalHoras;
	}

	public double getPorcentagemHoras() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		Aluno aluno = (Aluno) session.getAttribute("usuario");

		if (search == false) {
			return (this.getTotalHoras() * 100.00)
					/ aluno.getCurso().getQuantidadeHoras();
		} else {
			return (this.getTotalHoras() * 100.00) / categoria.getMaximoHoras();
		}

	}

	public String limparPesquisa() {
		HttpSession session = (HttpSession) FacesContext.getCurrentInstance()
				.getExternalContext().getSession(false);

		Aluno aluno = (Aluno) session.getAttribute("usuario");

		AtividadeDAO atividadeDao = new AtividadeJPADAO();

		this.atividades = atividadeDao.getAtividades(aluno);

		this.search = false;

		return "?faces-redirect=true";
	}

}
