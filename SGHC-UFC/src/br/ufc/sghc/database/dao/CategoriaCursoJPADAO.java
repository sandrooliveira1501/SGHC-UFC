package br.ufc.sghc.database.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ufc.sghc.database.modelo.Categoria;
import br.ufc.sghc.database.modelo.CategoriaCurso;
import br.ufc.sghc.database.modelo.Curso;

public class CategoriaCursoJPADAO extends GenericJPADAO<CategoriaCurso>
		implements CategoriaCursoDAO {

	public CategoriaCursoJPADAO() {
		this.persistenceClass = CategoriaCurso.class;
	}

	@Override
	public List<Categoria> getCategorias(Curso curso) {

		String hql = "select categoria from Categoria categoria "
				+ "where categoria in (select cc.categoria from CategoriaCurso cc inner join"
				+ " cc.curso where cc.curso = :curso)";

		Query query = getEm().createQuery(hql);
		query.setParameter("curso", curso);
		try {

			List<Categoria> categorias = query.getResultList();
			return categorias;

		} catch (PersistenceException ex) {
			ex.printStackTrace();
			return new ArrayList<Categoria>();
		}
	}

	@Override
	public List<Categoria> getCategoriasDisponiveis(Curso curso) {

		String hql = "select categoria from Categoria categoria "
				+ "where categoria not in (select cc.categoria from CategoriaCurso cc inner join"
				+ " cc.curso where cc.curso = :curso)";

		Query query = getEm().createQuery(hql);
		query.setParameter("curso", curso);
		try {
			List<Categoria> categorias = query.getResultList();
			return categorias;

		} catch (PersistenceException ex) {
			ex.printStackTrace();
			return new ArrayList<Categoria>();
		}
	}

	@Override
	public void save(List<Categoria> categorias, Curso curso) {

		for (Categoria categoria : categorias) {

			CategoriaCurso categoriaCurso = new CategoriaCurso();
			categoriaCurso.setCurso(curso);
			categoriaCurso.setCategoria(categoria);

			this.save(categoriaCurso);

		}

	}

	@Override
	public void delete(Long idCategoria, Long idCurso) {

		String hql = "delete from CategoriaCurso cc where cc.categoria.id = :idCategoria and cc.curso.id = :idCurso";

		Query query = getEm().createQuery(hql);
		query.setParameter("idCategoria", idCategoria);
		query.setParameter("idCurso", idCurso);
		query.executeUpdate();

	}


}
