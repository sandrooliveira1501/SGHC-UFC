package br.ufc.sghc.database.dao;

import java.util.List;

import br.ufc.sghc.modelo.Aluno;
import br.ufc.sghc.modelo.Atividade;
import br.ufc.sghc.modelo.Categoria;

public interface AtividadeDAO extends GenericDAO<Atividade>{
	
	public void adicionarArquivo(Atividade atividade);	
	public List<Atividade> getAtividadesComArquivo(Aluno aluno);
	public List<Atividade> getAtividadesCategoria(Aluno aluno, Categoria categoria);
	public List<Atividade> getAtividades(Aluno aluno);
	
}
