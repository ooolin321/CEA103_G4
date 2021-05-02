package filters;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.servlet.*;
import javax.servlet.http.*;

import com.adminpermission.model.AdminPermissionVO;
import com.permissiondelimit.model.PermissionDelimitService;
import com.permissiondelimit.model.PermissionDelimitVO;

public class BackEndLoginFilter implements Filter {

	private FilterConfig config;

	public void init(FilterConfig config) {
		this.config = config;
	}

	public void destroy() {
		config = null;
	}

	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws ServletException, IOException {
		
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		
		HttpSession session = req.getSession();
		
		//判斷是否登入
		Object account = session.getAttribute("account_s");
		
		if (account == null) {
			session.setAttribute("location", req.getRequestURI());
			res.sendRedirect(req.getContextPath() + "/back-end/login/login.jsp");
			return;
		}else {
			chain.doFilter(request, response);
			} 
	}
}