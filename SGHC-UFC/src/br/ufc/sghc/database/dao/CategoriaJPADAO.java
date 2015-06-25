package br.ufc.sghc.database.dao;

import javax.persistence.Query;

import br.ufc.sghc.modelo.Categoria;

public class CategoriaJPADAO extends GenericJPADAO<Categoria> implements
		CategoriaDAO {

	public CategoriaJPADAO() {
		this.persistenceClass = Categoria.class;
	}

	@Override
	public void delete(Categoria categoria) {
		deleteDependencies(categoria);
		this.getEm().remove(categoria);
		this.getEm().flush();
	}

	private void deleteDependencies(Categoria categoria) {
		String hql = "delete from CategoriaCurso cc where cc.categoria = :categoria";

		Query query = getEm().createQuery(hql);
		query.setParameter("categoria", categoria);
		query.executeUpdate();

	}

}