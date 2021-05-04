package login;

import java.io.*;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.auth.model.AuthService;
import com.auth.model.AuthVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
import com.fun.model.FunService;
import com.fun.model.FunVO;

import javax.servlet.annotation.WebServlet;

@WebServlet("/loginhandler")
public class LoginHandler extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("text/html; charset=utf-8");
		String action = req.getParameter("action");

		Map<String, String> errorMsgs = new LinkedHashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);

		if ("signIn".equals(action))
			// 【取得使用者 帳號(account) 密碼(password)】
			try {
				String str = req.getParameter("account");
				String str2 = req.getParameter("password");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.put("empno", "請輸入員工帳號");
				}
				if (str2 == null || (str2.trim().length() == 0)) {
					errorMsgs.put("empPwd", "請輸入密碼");
				}
				// 錯誤發生時將內容發送回表單
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
					failureView.forward(req, res);
					return;
				} // 程式中斷，回傳當傳頁面

				Integer empno = null;

				try {
					empno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.put("empno", "員工帳號格式不正確");
				}

				empno = new Integer(req.getParameter("account").trim());

				String empPwd = req.getParameter("password");
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
					failureView.forward(req, res);
					return;
				}

				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.selectEmp(empno, empPwd);
//				EmpVO empVO2 = (EmpVO) empSvc.getAll();
//				
//				List <AuthVO> authVO = (List<AuthVO>) new AuthService().getOneAuth(empno);
//				List<FunVO> list = new ArrayList<>();
//				FunService funSvc = new FunService();
//				for (int i = 0; i < authVO.size(); i++) {
//					FunVO funVO = funSvc
//							.getOneFun(authVO.get(i).getAuth_no());
//					list.add(funVO);
//				}
				if (empVO == null) {
					errorMsgs.put("empno", "帳號密碼不正確，請重新輸入");
					String url = "/back-end/backendLogin.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				} else if (empVO != null) {
					HttpSession session = req.getSession();
					session.setAttribute("account", empVO); // *工作1: 才在session內做已經登入過的標識
//					session.setAttribute("empVO", empVO2);
//					session.setAttribute("authVO", authVO);
					
					try {
						String location = (String) session.getAttribute("location");
						if (location != null) {
							session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
							res.sendRedirect(location);
							return;
						}
					} catch (Exception ignored) {

					}
					res.sendRedirect(req.getContextPath() + "/back-end/backendIndex.jsp"); // *工作3:
					// (-->如無來源網頁:則重導至login_success.jsp)
					if (!errorMsgs.isEmpty()) {
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
						failureView.forward(req, res);
						return;
					}
				}
			} catch (Exception e) {
				String badguys = "badguys";
				req.setAttribute("badguys", badguys);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
				failureView.forward(req, res);
			}if ("signOut".equals(action)) {
				HttpSession session = req.getSession();
				if(!session.isNew()) {
					//使用者登出
			        session.invalidate();
			        
					String url = "/back-end/backendLogin.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url); //登出後轉至首頁
					successView.forward(req, res);
				}
				if("forgotPassword".equals("action")) {
					try {
						String email = req.getParameter("forgotPassword");
						String emailReg = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
						if (email == null || email.trim().length() == 0) {
							errorMsgs.put("email","email請勿空白");
						} else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
							errorMsgs.put("email","email格式不正確");
						}
						
					} catch (Exception e) {
					
					}
				}
			}
	}
}
