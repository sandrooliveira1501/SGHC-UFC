package br.ufc.sghc.controller;

import java.util.ArrayList;
import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import br.ufc.sghc.database.dao.CategoriaCursoDAO;
import br.ufc.sghc.database.dao.CategoriaCursoJPADAO;
import br.ufc.sghc.database.dao.CategoriaDAO;
import br.ufc.sghc.database.dao.CategoriaJPADAO;
import br.ufc.sghc.database.dao.CursoDAO;
import br.ufc.sghc.database.dao.CursoJPADAO;
import br.ufc.sghc.database.modelo.Categoria;

@ManagedBean(name = "categoriaBean")
public class CategoriaBean {

	private Categoria categoria;

	public CategoriaBean() {
		this.categoria = new Categoria();
	}

	public Categoria getCategoria() {
		return categoria;
	}

	public void setCategoria(Categoria categoria) {
		this.categoria = categoria;
	}

	public List<Categoria> getCategorias() {

		CategoriaDAO categoriaDao = new CategoriaJPADAO();

		List<Categoria> categorias = categoriaDao.find();
		if (categorias == null)
			categorias = new ArrayList<Categoria>();

		return categorias;
	}

	public String adicionarCategoria() {

		CategoriaDAO categoriaDao = new CategoriaJPADAO();
		try {

			categoriaDao.save(this.categoria);
			FacesContext.getCurrentInstance().addMessage("Sucesso",
					new FacesMessage("Categoria adicionado com sucesso!", ""));
		} catch (PersistenceException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage("Tente adicionar a categoria novamente!",
							""));
		}

		return "?faces-redirect=true";
	}

	public String removerCategoria() {

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idCategoria = Long.parseLong(request.getParameter("idCategoria"));

			CategoriaDAO categoriaDao = new CategoriaJPADAO();
			Categoria categoria = categoriaDao.find(idCategoria);

			categoriaDao.delete(categoria);

		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Categoria inexistente!",
							""));

		} catch (PersistenceException e) {
			e.printStackTrace();
			new CategoriaJPADAO().rollback();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN, "Já existem atividades nesta categoria!",
							""));
		}

		return "?faces-redirect=true";
	}

	public String visualizarCategoria(){

		HttpServletRequest request = (HttpServletRequest) FacesContext
				.getCurrentInstance().getExternalContext().getRequest();

		try {
			long idcategoria = Long.parseLong(request.getParameter("idCategoria"));

			CategoriaDAO categoriaDao = new CategoriaJPADAO();
			this.categoria = categoriaDao.find(idcategoria);
		} catch (NumberFormatException ex) {
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Categoria inexistente!", ""));
			return "";
		}

		return "visualizar_categoria";
	}
	
	public String atualizarCategoria(){
		
		try{
			
			CategoriaDAO categoriaDao = new CategoriaJPADAO();
			categoriaDao.update(this.categoria);
			

			FacesContext.getCurrentInstance().addMessage(
					"Sucesso",
					new FacesMessage(
							"Categoria atualizado", ""));
		}catch(PersistenceException ex){
			ex.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(
					"Aviso",
					new FacesMessage(FacesMessage.SEVERITY_WARN,
							"Erro ao atualizar dados da categoria", ""));
			return "";
		}
		
		return "categorias?faces-redirect=true";
	}
	
}
