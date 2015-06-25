package br.ufc.sghc.database.dao;

import br.ufc.sghc.modelo.Administrador;

public interface AdministradorDAO extends GenericDAO<Administrador>{

	public Administrador verificarAdministrador(Administrador administrador);
	
}
