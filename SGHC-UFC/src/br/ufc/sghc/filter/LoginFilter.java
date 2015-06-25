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

@WebFilter("/app/*")
public class LoginFilter implements Filter {

    public LoginFilter() {
    }

	public void destroy() {
		
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse resp = (HttpServletResponse) response;
		String path = req.getContextPath();
		HttpSession session = req.getSession(false);
		
		if(session != null && session.getAttribute("usuario") != null){			
			chain.doFilter(request, response);
		}else{
			resp.sendRedirect(path + "/login.xhtml");
		}
		
	
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}

}
