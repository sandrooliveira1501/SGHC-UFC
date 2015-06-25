package br.ufc.sghc.database.dao;

import java.util.List;

import br.ufc.sghc.modelo.Categoria;
import br.ufc.sghc.modelo.CategoriaCurso;
import br.ufc.sghc.modelo.Curso;

public interface CategoriaCursoDAO extends GenericDAO<CategoriaCurso>{

	public List<Categoria> getCategorias(Curso curso);
	public List<Categoria> getCategoriasDisponiveis(Curso curso);
	public void save(List<Categoria> categorias, Curso curso);
	public void delete(Long idCategoria, Long idCurso);
	
}
