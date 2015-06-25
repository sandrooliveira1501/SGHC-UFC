package br.ufc.sghc.database.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaQuery;

import br.ufc.sghc.util.JPAUtil;

public abstract class GenericJPADAO <E> implements GenericDAO<E>{

	private EntityManager em;
	protected Class<E> persistenceClass; 
	
	public GenericJPADAO(){
		em = JPAUtil.createEntityManager();
	}
	
	/*
	 * Implementação dos métodos da interface GenericDao
	 */
	
	@Override
	public void save(E entity) {
		em.persist(entity);
	}

	@Override
	public void update(E entity) {
		em.merge(entity);
	}

	@Override
	public void delete(E entity) {
		em.remove(entity);	
	}

	@Override
	public E find(Object id) {
		return em.find(persistenceClass, id);
	}

	@Override
	public List<E> find() {
		CriteriaQuery<E> cq = em.getCriteriaBuilder().createQuery(persistenceClass);
		cq.from(persistenceClass);
		return em.createQuery(cq).getResultList();
	}

	@Override
	public void begin() {
		em.getTransaction().begin();
	}

	@Override
	public void commit() {
		em.getTransaction().commit();
	}

	@Override
	public void rollback() {
		em.getTransaction().rollback();
	}

	@Override
	public void close() {
		JPAUtil.close();
	}

	@Override
	public boolean transactionActive() {
		return em.getTransaction().isActive();
	}

	public EntityManager getEm() {
		return em;
	}

	
	
}
