package br.ufc.sghc.database.dao;

import br.ufc.sghc.modelo.Aluno;
public interface AlunoDAO extends GenericDAO<Aluno>{

	public Aluno verificaAluno(Aluno aluno);
	public Aluno findByEmail(String email);
}
