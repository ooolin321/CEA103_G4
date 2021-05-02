package com.categorie.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.categorie.model.*;

public class CategorieServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String lc_class_id = req.getParameter("lc_class_id");
				String lc_class_idReg = "^LC[0-9]{3}$";
				if (lc_class_id == null || (lc_class_id.trim()).length() == 0) {
					errorMsgs.add("categorie id : do not be blank!");
				} else if (!lc_class_id.trim().matches(lc_class_idReg)) {
					errorMsgs.add("categorie id : The format must be LC plus 3 numbers !");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/categorie/listAllCategorie.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始查詢資料 *****************************************/
				CategorieService categorieService = new CategorieService();
				CategorieVO categorieVO = categorieService.getOneCategorie(lc_class_id);

				if (categorieVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/categorie/listAllCategorie.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("categorieVO", categorieVO);
				String url = "/back-end/categorie/listOneCategorie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneCategorie.jsp
				successView.forward(req, res);
//				System.out.println("轉交成功");
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/listAllCategorie.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 ****************************************/
				String lc_class_id = new String(req.getParameter("lc_class_id"));

				/*************************** 2.開始查詢資料 ****************************************/
				CategorieService categorieService = new CategorieService();
				CategorieVO categorieVO = categorieService.getOneCategorie(lc_class_id);

				/*************************** 3.查詢完成，準備轉交(Send the Success view) ************/
				req.setAttribute("categorieVO", categorieVO);
				String url = "/back-end/categorie/update_categorie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交update_categorie.jsp
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/listAllCategorie.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String lc_class_id = new String(req.getParameter("lc_class_id").trim());

				String lc_class_name = req.getParameter("lc_class_name");
				String lc_class_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (lc_class_name == null || lc_class_name.trim().length() == 0) {
					errorMsgs.add("categorie name : do not be blank!");
				} else if (!lc_class_name.trim().matches(lc_class_nameReg)) {
					errorMsgs.add("categorie name : format is not correct!");
				}

				CategorieVO categorieVO = new CategorieVO();
				categorieVO.setLc_class_id(lc_class_id);
				categorieVO.setLc_class_name(lc_class_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("categorieVO", categorieVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/categorie/update_categorie.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始修改資料 *****************************************/
				CategorieService categorieService = new CategorieService();
				categorieVO = categorieService.updateCategorie(lc_class_id, lc_class_name);

				/*************************** 3.修改完成，準備轉交(Send the Success view) *************/
				req.setAttribute("categorieVO", categorieVO);
				String url = "/back-end/categorie/listOneCategorie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功轉交listOneCategorie.jsp
				successView.forward(req, res);
				System.out.println("轉交成功");
				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/update_categorie.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
				String lc_class_name = req.getParameter("lc_class_name");
				String lc_class_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (lc_class_name == null || lc_class_name.trim().length() == 0) {
					errorMsgs.add("categorie name : do not be blank!");
				} else if (!lc_class_name.trim().matches(lc_class_nameReg)) {
					errorMsgs.add("categorie name : format is not correct!");
				}

				CategorieVO categorieVO = new CategorieVO();
				categorieVO.setLc_class_name(lc_class_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("categorieVO", categorieVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/addCategorie.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				CategorieService categorieService = new CategorieService();
				categorieVO = categorieService.addCategorie(lc_class_name);
				System.out.println("here");

				/*************************** 3.新增完成，準備轉交(Send the Success view) ***********/
				String url = "/back-end/categorie/listAllCategorie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功轉交listAllCategorie.jsp
				successView.forward(req, res);
				System.out.println("her2e");

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/addCategorie.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
				String lc_class_id = new String(req.getParameter("lc_class_id"));

				/*************************** 2.開始刪除資料 ***************************************/
				CategorieService categorieService = new CategorieService();
				categorieService.deleteCategorie(lc_class_id);

				/*************************** 3.刪除完成，準備轉交(Send the Success view) ***********/
				String url = "/back-end/categorie/listAllCategorie.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

				/*************************** ��L�i�઺���~�B�z **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/categorie/listAllCategorie.jsp");
				failureView.forward(req, res);
			}
		}
	}

}
