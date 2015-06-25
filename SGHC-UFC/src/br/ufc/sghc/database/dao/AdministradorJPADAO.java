package br.ufc.sghc.database.dao;

import javax.persistence.PersistenceException;
import javax.persistence.Query;

import br.ufc.sghc.exceptions.ErroLoginException;
import br.ufc.sghc.modelo.Administrador;

public class AdministradorJPADAO extends GenericJPADAO<Administrador> implements AdministradorDAO{

	 public AdministradorJPADAO() {
		persistenceClass = Administrador.class;
	}
	
	@Override
	public Administrador verificarAdministrador(Administrador administrador) {
		
		String sql = "select a from Administrador a where a.login = :login and a.senha = :senha";
		Query query = getEm().createQuery(sql);
		query.setParameter("login", administrador.getLogin());
		query.setParameter("senha", administrador.getSenha());
		try {

			return (Administrador) query.getSingleResult();

		} catch (PersistenceException ex) {
			ex.printStackTrace();
			throw new ErroLoginException("Login ou senha inválidos");
		}
	}

	
	
}
