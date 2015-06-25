package br.ufc.sghc.database.dao;

import java.util.List;

import br.ufc.sghc.database.modelo.Categoria;
import br.ufc.sghc.database.modelo.CategoriaCurso;
import br.ufc.sghc.database.modelo.Curso;

public interface CategoriaCursoDAO extends GenericDAO<CategoriaCurso>{

	public List<Categoria> getCategorias(Curso curso);
	public List<Categoria> getCategoriasDisponiveis(Curso curso);
	public void save(List<Categoria> categorias, Curso curso);
	public void delete(Long idCategoria, Long idCurso);
	
}
