package br.ufc.sghc.database.dao;

import java.util.List;

import javax.persistence.Query;

import br.ufc.sghc.database.modelo.Aluno;
import br.ufc.sghc.database.modelo.Atividade;
import br.ufc.sghc.database.modelo.Categoria;

public class AtividadeJPADAO extends GenericJPADAO<Atividade> implements
		AtividadeDAO {

	public AtividadeJPADAO() {
		persistenceClass = Atividade.class;
	}

	@Override
	public void adicionarArquivo(Atividade atividade) {
		byte[] arquivo = atividade.getCertificado();
		atividade = find(atividade.getId());
		atividade.setCertificado(arquivo);
		update(atividade);
	}

	public List<Atividade> getAtividadesComArquivo(Aluno aluno) {
		String sql = "select a from Atividade a where a.possuiArquivo = true and a.aluno = :aluno";
		Query query = getEm().createQuery(sql);
		query.setParameter("aluno", aluno);
		List<Atividade> list = query.getResultList();
		return list;
	}
	
	@Override
	public List<Atividade> getAtividadesCategoria(Aluno aluno,
			Categoria categoria) {
		String hql = "select a from Atividade a where a.categoria = :categoria and a.aluno = :aluno";
		Query query = getEm().createQuery(hql);
		query.setParameter("aluno", aluno);
		query.setParameter("categoria", categoria);
		List<Atividade> list = query.getResultList();
		return list;
	}

	@Override
	public List<Atividade> getAtividades(Aluno aluno) {
		String hql = "select a from Atividade a where a.aluno = :aluno";
		Query query = getEm().createQuery(hql);
		query.setParameter("aluno", aluno);
		List<Atividade> list = query.getResultList();
		return list;
	}
}
