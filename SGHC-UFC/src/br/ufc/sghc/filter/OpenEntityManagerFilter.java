package br.ufc.sghc.filter;

import java.io.IOException;

import javax.persistence.EntityManager;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

import br.ufc.sghc.util.JPAUtil;

@WebFilter("/*")
public class OpenEntityManagerFilter implements Filter {

	public OpenEntityManagerFilter() {
	}

	public void destroy() {
	}

	/**
	 *
	 */
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		String uri = ((HttpServletRequest) request).getRequestURI();
		/**
		 * Verificação se a página requisitada é um JavaScript,imagem os css,
		 *  se for não é feita a abertura de EntityManager
		 */
		if (uri.contains("javax.faces.resource")) {
			chain.doFilter(request, response);
		} else {
			System.err.println(uri);
			try {
				EntityManager em = JPAUtil.createEntityManager();
				JPAUtil.beginTransaction();
				chain.doFilter(request, response);
				if(em.getTransaction().isActive()){
					JPAUtil.commit();					
				}
			} catch (Throwable e) {
				JPAUtil.rollback();
				e.printStackTrace();
			} finally {
				JPAUtil.close();
			}
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
