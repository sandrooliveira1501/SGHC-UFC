package br.ufc.sghc.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JPAUtil {

	private static final EntityManagerFactory emf = Persistence
			.createEntityManagerFactory("dev");
	private static ThreadLocal<EntityManager> ems = new ThreadLocal<EntityManager>();

	public static EntityManager createEntityManager() {
		System.err.println("Criando EntityManager\n\n");
		EntityManager em = ems.get();
		if (em == null) {
			em = emf.createEntityManager();
			ems.set(em);
		}

		return em;
	}

	/**
	 * Fecha a conexão da EntityManager e tira ela da ThreadLocal
	 */
	public static void close() {
		System.out.println("Close EntityManager");
		EntityManager em = ems.get();

		if (em != null) {

			if (em.getTransaction().isActive()) {
				em.getTransaction().commit();
			}

			em.close();
			ems.set(null);

		}
	}

	/**
	 * Inicia transação
	 */
	public static void beginTransaction() {
		createEntityManager().getTransaction().begin();
	}

	/**
	 * Faz commit na transação se ela estiver ativa
	 */
	public static void commit() {
		EntityTransaction tx = createEntityManager().getTransaction();
		if (tx.isActive()) {
			tx.commit();
		}
	}

	/**
	 * Faz rollback na transação se ela estiver ativa
	 */
	public static void rollback() {
		EntityTransaction tx = createEntityManager().getTransaction();
		if (tx.isActive()) {
			tx.rollback();
		}
	}

}
