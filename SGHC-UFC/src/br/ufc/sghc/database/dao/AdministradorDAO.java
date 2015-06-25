package br.ufc.sghc.database.dao;

import br.ufc.sghc.database.modelo.Administrador;

public interface AdministradorDAO extends GenericDAO<Administrador>{

	public Administrador verificarAdministrador(Administrador administrador);
	
}
