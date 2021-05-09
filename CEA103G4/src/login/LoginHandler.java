package login;

import java.io.*;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.*;
import javax.servlet.http.*;

import com.auth.model.AuthService;
import com.auth.model.AuthVO;
import com.emp.model.EmpService;
import com.emp.model.EmpVO;
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
			// 【取得使用者 帳號(empAccount) 密碼(password)】
			try {
				String str = req.getParameter("empAccount");
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

				empno = new Integer(req.getParameter("empAccount").trim());

				String empPwd = req.getParameter("password");
				
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
					failureView.forward(req, res);
					return;
				}

				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.selectEmp(empno, empPwd);//查詢資料庫是否有此員工
								
				if (empVO == null) {
					errorMsgs.put("empno", "帳號密碼不正確，請重新輸入");
					String url = "/back-end/backendLogin.jsp";
					RequestDispatcher failureView = req.getRequestDispatcher(url);
					failureView.forward(req, res);
				} 
				else  {
					int state = empVO.getState(); //查詢員工在職狀態
					if (state == 0) {			  //離職的話導回login
						String quit = "quit";
						req.setAttribute("quit", quit);
						errorMsgs.put("empno", "此員工已離職");
						RequestDispatcher failureView = req.getRequestDispatcher("back-end/backendLogin.jsp");
						failureView.forward(req, res);
					}				
					
					//取得員工帳號的權限
					AuthService authSvc = new AuthService();
					List<AuthVO> authList = authSvc.getAuthNOs(empno);
//					for(AuthVO auth:list) {
//						System.out.println(auth.getFunno()+",");
//					}
					
					HttpSession session = req.getSession();
					session.setAttribute("empAccount", empVO); // *工作1: 才在session內做已經登入過的標識
					session.setAttribute("authList", authList);					

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
			}
		
		
		
		if ("signOut".equals(action)) {
			HttpSession session = req.getSession();
			if (!session.isNew()) {
				// 使用者登出
				session.invalidate();

				String url = "/back-end/backendLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 登出後轉至首頁
				successView.forward(req, res);
			}
		}
		
		if ("forgotPassword".equals(action)) {
			try {
				String email = req.getParameter("email");
				String emailReg = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
				if (email == null || email.trim().length() == 0) {
					errorMsgs.put("email", "email請勿空白");
				} else if (!email.trim().matches(emailReg)) {
					errorMsgs.put("email", "email格式不正確");
				}

				EmpVO empVO = new EmpVO();
				empVO.setEmail(email);

				EmpService empSvc = new EmpService();
				empVO = empSvc.selectEmail(email);
				if (empVO == null) {
System.out.println("loginServlet 146 = "+empVO.getEmail());
					errorMsgs.put("email", "沒有Email資料，請重新輸入");
					String url = "/back-end/backendLogin.jsp";
					RequestDispatcher successView = req.getRequestDispatcher(url);
					successView.forward(req, res);
				}

				String link = req.getServerName() + ":" + req.getServerPort() + req.getContextPath();

				empVO.setLink(link);
				empVO = empSvc.forgotEmail(email, link);

System.out.println("154 = " + empVO.getEname());

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
					failureView.forward(req, res);
					return;
				}

				String url = "/back-end/backendLogin.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

			} catch (Exception e) {
				errorMsgs.put("Exception", e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/backendLogin.jsp");
				failureView.forward(req, res);
			}
		}

	}
}
