package com.emp.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.emp.model.*;


public class EmpServlet extends HttpServlet {
	
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res); 
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {// 來自selectEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.(將請求存起來，例外發生時可以查看)
			req.setAttribute("errorMsgs", errorMsgs);

			/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
			try {
				String str = req.getParameter("empno");
				if (str == null || (str.trim().length() == 0)) {
					errorMsgs.add("請輸入員工編號");
				} // 錯誤發生時將內容發送回表單
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/selectEmp.jsp");
					failureView.forward(req, res);
					return;
				} // 程式中斷，回傳當傳頁面

				Integer empno = null;
				try {
					empno = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("員工編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/selectEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始查詢資料 *****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);
				if (empVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/selectEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // 資料庫取出的empVO物件,存入req
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/selectEmp.jsp");
				failureView.forward(req, res);
			}
		}
		// ================================================================================================
		if ("getOne_For_Update".equals(action)) {// 來自listAllEmp.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.開始查詢資料 ****************************************/
				EmpService empSvc = new EmpService();
				EmpVO empVO = empSvc.getOneEmp(empno);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("empVO", empVO);
				String url = "/back-end/emp/update_emp_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);

			}
		}
		// =================================================================================================
		
		if ("update".equals(action)) {// 來自update_emp_input.jsp的請求
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				Integer empno = new Integer(req.getParameter("empno").trim());

				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if (!ename.trim().matches(enameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String job = req.getParameter("job").trim();
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				Integer gender = new Integer(req.getParameter("gender"));
				

				String id = req.getParameter("id");
				String idReg = "^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$";
				if (id == null || id.trim().length() == 0) {
					errorMsgs.add("身份證字號請勿空白");
				} else if (!id.trim().matches(idReg)) {
					errorMsgs.add("身份證字號不正確");
				}

				java.sql.Date dob = null;
				try {
					dob = java.sql.Date.valueOf(req.getParameter("dob").trim());
				} catch (IllegalArgumentException e) {
					dob = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String addr = req.getParameter("addr");
				if (addr == null || addr.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}
				
				String email = req.getParameter("email");
				String emailReg = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("email請勿空白");
				}else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("email格式不正確");
				}

				Double sal = null;
				try {
					sal = new Double(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0.0;
					errorMsgs.add("薪水請填數字.");
				}

				Integer state = new Integer(req.getParameter("state"));

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}


				String empPwd = req.getParameter("emp_pwd");
				if (empPwd == null || empPwd.trim().length() == 0) {
					errorMsgs.add("身份證字號請勿空白");
				}
					

				EmpVO empVO = new EmpVO();
				empVO.setEmpno(empno);
				empVO.setEname(ename);
				empVO.setJob(job);
				empVO.setId(id);
				empVO.setGender(gender);
				empVO.setDob(dob);
				empVO.setAddr(addr);
				empVO.setEmail(email);
				empVO.setSal(sal);
				empVO.setState(state);
				empVO.setHiredate(hiredate);
				empVO.setEmp_pwd(empPwd);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
					failureView.forward(req, res);
					return;
				}	
				/*************************** 2.開始修改資料 *****************************************/
				EmpService empSvc = new EmpService();			
				empVO = empSvc.updateEmp(empno, ename, job, id, gender, dob, addr, email, sal, state, hiredate, empPwd);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("empVO", empVO); // 資料庫update成功後,正確的的empVO物件,存入req
				String url = "/back-end/emp/listOneEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/update_emp_input.jsp");
				failureView.forward(req, res);
			}
		}
		// ====================================================================================================
		if (("insert").equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String ename = req.getParameter("ename");
				String enameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (ename == null || ename.trim().length() == 0) {
					errorMsgs.add("員工姓名: 請勿空白");
				} else if (!ename.trim().matches(enameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("員工姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String job = req.getParameter("job");
				if (job == null || job.trim().length() == 0) {
					errorMsgs.add("職位請勿空白");
				}

				Integer gender = new Integer(req.getParameter("gender").trim());

				String id = req.getParameter("id");
				String idReg = "^[a-zA-Z]{1}[1-2]{1}[0-9]{8}$";
				if (id == null || id.trim().length() == 0) {
					errorMsgs.add("身份證字號請勿空白");
				} else if (!id.trim().matches(idReg)) {
					errorMsgs.add("身份證字號不正確");
				}

				java.sql.Date dob = null;
				try {
					dob = java.sql.Date.valueOf(req.getParameter("dob").trim());
				} catch (IllegalArgumentException e) {
					dob = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String addr = req.getParameter("addr");
				if (addr == null || addr.trim().length() == 0) {
					errorMsgs.add("地址請勿空白");
				}
				
				String email = req.getParameter("email");
				String emailReg = "^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$";
				if (email == null || email.trim().length() == 0) {
					errorMsgs.add("email請勿空白");
				}else if (!email.trim().matches(emailReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("email格式不正確");
				}
				
				Double sal = null;
				try {
					sal = new Double(req.getParameter("sal").trim());
				} catch (NumberFormatException e) {
					sal = 0.0;
					errorMsgs.add("薪水請填數字.");
				}

				Integer state = new Integer(req.getParameter("state").trim());

				java.sql.Date hiredate = null;
				try {
					hiredate = java.sql.Date.valueOf(req.getParameter("hiredate").trim());
				} catch (IllegalArgumentException e) {
					hiredate = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				

				EmpVO empVO = new EmpVO();
				
				empVO.setEname(ename);
				empVO.setJob(job);
				empVO.setId(id);
				empVO.setGender(gender);
				empVO.setDob(dob);
				empVO.setAddr(addr);
				empVO.setEmail(email);
				empVO.setSal(sal);
				empVO.setState(state);
				empVO.setHiredate(hiredate);
				String empPwd = empVO.getGenAuthCode();

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("empVO", empVO); // 含有輸入格式錯誤的empVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 *****************************************/
				EmpService empSvc = new EmpService();
				empVO = empSvc.addEmp(ename, job, id, gender, dob, addr,email, sal, state, hiredate, empPwd);
				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/addEmp.jsp");
				failureView.forward(req, res);
			}
		}
		//===========================================================================================
		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer empno = new Integer(req.getParameter("empno"));

				/*************************** 2.開始刪除資料 ***************************************/
				EmpService empSvc = new EmpService();
				empSvc.deleteEmp(empno);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/back-end/emp/listAllEmp.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/emp/listAllEmp.jsp");
				failureView.forward(req, res);
			}
		}
	}
}
