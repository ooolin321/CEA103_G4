package com.mem.filters;

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

import com.bookclub.model.BookClubVO;
import com.mem.model.MemVO;

public class ComefromPage implements Filter {

	private FilterConfig config;

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		HttpServletRequest req = (HttpServletRequest) request;
//		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		String indexUrl = "/front-end/front-index.jsp";
		
		BookClubVO b = (BookClubVO) req.getAttribute("listOneBookClub");
		String bc_id = null;
		if(b != null) {
			bc_id = b.getBc_id();
		}else {
			bc_id = (String) session.getAttribute("bc_id");
		}
		
		if(session.getAttribute("MemVO") == null){
			if(req.getRequestURI().equals(req.getContextPath() + "/front-end/member/signUp.jsp")
			|| req.getRequestURI().equals(req.getContextPath() + "/front-end/member/updateForForgetPwd.jsp")
			|| req.getRequestURI().equals(req.getContextPath() + "/front-end/member/confirmationCode.jsp")) {
				
				session.setAttribute("comefromPage", indexUrl);
			} else {
				session.setAttribute("comefromPage", req.getServletPath());
				session.setAttribute("bc_id", bc_id);
			}
		}
		
		chain.doFilter(request, response);
	}

	public void init(FilterConfig config) throws ServletException {
		this.config = config;
	}
}
