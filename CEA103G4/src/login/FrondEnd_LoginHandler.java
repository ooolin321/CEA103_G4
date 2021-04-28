package login;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;
import com.user.model.*;


import javax.servlet.annotation.WebServlet;

@WebServlet("/FrondEnd_LoginHandler")
public class FrondEnd_LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		String action = req.getParameter("action");
		PrintWriter out = res.getWriter();
		
		Map<String,String> errorMsgs = new LinkedHashMap<String,String>();
		req.setAttribute("errorMsgs", errorMsgs);
		
		if ("signIn".equals(action))
		// 【取得使用者 帳號(account) 密碼(password)】
		try {
			String str = req.getParameter("account");
			String str2 = req.getParameter("password");
			if (str == null || (str.trim().length() == 0)) {
				errorMsgs.put("user_id","請輸入會員帳號");
			}if(str2 == null || (str2.trim().length() == 0)) {
				errorMsgs.put("user_pwd","請輸入會員密碼");
			}
			
			// 錯誤發生時將內容發送回表單
			if (!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/userLogin.jsp");
				failureView.forward(req, res);
				return;
			} // 程式中斷，回傳當前頁面
			
			//會員帳號密碼已是String不需此步驟
//			Integer empno = null;
//			try {
//				empno = new Integer(str);
//			} catch (Exception e) {
//				errorMsgs.put("empno","員工帳號格式不正確");
//			}
//			empno = new Integer(req.getParameter("account").trim());
			
			String user_id = str;
			
			String user_pwd = str2;
			
			UserService userSvc = new UserService();
			UserVO userVO = userSvc.selectUser(user_id, user_pwd);
			
			
			if(userVO != null) {
				HttpSession session = req.getSession();
				session.setAttribute("account", userVO); // *工作1: 才在session內做已經登入過的標識
				try {
					String location = (String) session.getAttribute("location");
																  // account == null才有
					if (location != null) {
						session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
						res.sendRedirect(location);
						return;
					}
				} catch (Exception ignored) {
				}
				res.sendRedirect(req.getContextPath() + "/front-end/userIndex.jsp"); // *工作3:
				// (-->如無來源網頁:則重導至userIndex.jsp)
			}if(!errorMsgs.isEmpty()) {
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/userLogin.jsp");
				failureView.forward(req, res);
				return;
			}
			}catch (Exception e) {
				
			}out.println("<HTML><HEAD><TITLE>Access Denied</TITLE></HEAD>");
				out.println("<BODY>你的帳號 , 密碼無效!<BR>");
				out.println("請按此重新登入 <A HREF=" + req.getContextPath() + "/front-end/userLogin.jsp>重新登入</A>");
				out.println("</BODY></HTML>");
	}
}