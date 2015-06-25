package br.ufc.sghc.database.dao;

import javax.persistence.Query;

import br.ufc.sghc.database.modelo.Curso;

public class CursoJPADAO extends GenericJPADAO<Curso> implements CursoDAO{

	public CursoJPADAO(){
		this.persistenceClass = Curso.class;
	}

	@Override
	public void delete(Curso curso) {
		deleteDependencies(curso);

		this.getEm().remove(curso);
		this.getEm().flush();
	}
	
	private void deleteDependencies(Curso curso) {

		String hql = "delete from CategoriaCurso cc where cc.curso = :curso";

		Query query = getEm().createQuery(hql);
		query.setParameter("curso", curso);
		query.executeUpdate();

	}
	
}
