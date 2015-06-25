package br.ufc.sghc.database.dao;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ufc.sghc.exceptions.ErroLoginException;
import br.ufc.sghc.modelo.Aluno;

public class AlunoJPADAO extends GenericJPADAO<Aluno> implements AlunoDAO {

	public AlunoJPADAO() {
		persistenceClass = Aluno.class;
	}

	/**
	 * Implementação do método verificaAluno
	 * Busca no banco o aluno comparando matricula e senha, se existir retorna o Aluno e caso contrário dispara a exceção ErroLoginException 
	 */
	@Override
	public Aluno verificaAluno(Aluno aluno) {
		
		String sql = "select a from Aluno a where a.matricula = :matricula and a.senha = :senha";
		Query query = getEm().createQuery(sql);
		query.setParameter("matricula", aluno.getMatricula());
		query.setParameter("senha", aluno.getSenha());
		try {

			Aluno a = (Aluno) query.getSingleResult();
			return a;

		} catch (PersistenceException ex) {
			ex.printStackTrace();
			throw new ErroLoginException("Login ou senha inválidos");
		}

	}
	
	@Override
	public Aluno findByEmail(String email) {
		String hql = "select a from Aluno a where a.email = :email";
		
		Query query = this.getEm().createQuery(hql);
		query.setParameter("email", email);
		
		return (Aluno) query.getSingleResult();
	}
	
	@Override
	public void save(Aluno aluno) {
		this.getEm().persist(aluno);
		this.getEm().flush();
	}

}
