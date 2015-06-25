package br.ufc.sghc.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter("/admin/*")
public class LoginAdministradorFilter implements Filter {

    public LoginAdministradorFilter() {
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getContextPath();
		String pathPage = req.getRequestURI();
		HttpSession session = req.getSession(false);
		
		if(pathPage.endsWith("admin/login.xhtml")){
			chain.doFilter(request, response);
		}else if(session != null && session.getAttribute("administrador") != null){			
			chain.doFilter(request, response);
		}else{
			resp.sendRedirect(path + "/admin/login.xhtml");
		}
		
	
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
