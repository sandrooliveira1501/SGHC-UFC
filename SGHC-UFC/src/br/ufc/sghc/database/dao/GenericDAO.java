package br.ufc.sghc.database.dao;

import java.util.List;

public interface GenericDAO <E>{

	public void save(E entity);
	public void update(E entity);
	public void delete(E entity);
	public E find(Object id);
	public List<E> find();
	public void begin();
	public void commit();
	public void rollback();
	public void close();
	public boolean transactionActive();
	
	
}
