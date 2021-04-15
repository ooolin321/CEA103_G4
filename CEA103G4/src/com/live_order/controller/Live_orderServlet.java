package com.live_order.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.live_order.model.Live_orderService;
import com.live_order.model.Live_orderVO;

public class Live_orderServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				String str = req.getParameter("live_order_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入直播訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/live_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				Integer live_order_no = null;
				try {
					live_order_no = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("直播編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/live_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				Live_orderService live_orderSvc = new Live_orderService();
				Live_orderVO live_orderVO = live_orderSvc.getOneLive_order(live_order_no);
				if (live_orderVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/live_order/select_page.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成,準備轉交(Send the Success view) *************/
				req.setAttribute("live_orderVO", live_orderVO); // 資料庫取出的live_orderVO物件,存入req
				String url = "/live_order/listOneLive_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/live_order/select_page.jsp");
				failureView.forward(req, res);
			}
		}

		if ("getOne_For_Update".equals(action)) { // 來自listAllEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				Integer live_order_no = new Integer(req.getParameter("live_order_no"));

				/*************************** 2.開始查詢資料 ****************************************/
				Live_orderService live_orderSvc = new Live_orderService();
				Live_orderVO live_orderVO = live_orderSvc.getOneLive_order(live_order_no);

				/*************************** 3.查詢完成,準備轉交(Send the Success view) ************/
				req.setAttribute("live_orderVO", live_orderVO); // 資料庫取出的live_orderVO物件,存入req
				String url = "/live_order/update_live_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_emp_input.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/live_order/listAllLive_order.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 - 輸入格式的錯誤處理 **********************/
				java.sql.Date order_date = null;
				try {
					order_date = java.sql.Date.valueOf(req.getParameter("order_date").trim());
				} catch (IllegalArgumentException e) {
					order_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer order_state = new Integer(req.getParameter("order_state").trim());
				Integer order_shipping = new Integer(req.getParameter("order_shipping").trim());
				Integer order_price = new Integer(req.getParameter("order_price").trim());
				Integer pay_method = new Integer(req.getParameter("pay_method").trim());
				java.sql.Date pay_deadline = null;
				try {
					pay_deadline = java.sql.Date.valueOf(req.getParameter("pay_deadline").trim());
				} catch (IllegalArgumentException e) {
					pay_deadline = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String rec_name = req.getParameter("rec_name");

				String rec_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("收件人姓名: 請勿空白");
				} else if (!rec_name.trim().matches(rec_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String rec_addr = req.getParameter("rec_addr");

				Integer rec_phone = new Integer(req.getParameter("rec_phone").trim());
				Integer rec_cellphone = new Integer(req.getParameter("rec_cellphone").trim());

				Integer logistics = new Integer(req.getParameter("logistics").trim());

				Integer logistics_state = new Integer(req.getParameter("logistics_state").trim());
				Integer discount = new Integer(req.getParameter("discount").trim());
				Integer live_no = new Integer(req.getParameter("live_no").trim());
				String user_id = req.getParameter("user_id");
				String seller_id = req.getParameter("seller_id");

				Integer srating = new Integer(req.getParameter("srating").trim());
				String srating_content = req.getParameter("srating_content");
				Integer point = new Integer(req.getParameter("point").trim());
				Integer live_order_no = new Integer(req.getParameter("live_order_no").trim());

				Live_orderVO live_orderVO = new Live_orderVO();
				live_orderVO.setOrder_date(order_date);
				live_orderVO.setOrder_state(order_state);
				live_orderVO.setOrder_shipping(order_shipping);
				live_orderVO.setOrder_price(order_price);
				live_orderVO.setPay_method(pay_method);
				live_orderVO.setPay_deadline(pay_deadline);
				live_orderVO.setRec_name(rec_name);
				live_orderVO.setRec_addr(rec_addr);
				live_orderVO.setRec_phone(rec_phone);
				live_orderVO.setRec_cellphone(rec_cellphone);
				live_orderVO.setLogistics(logistics);
				live_orderVO.setLogistics_state(logistics_state);
				live_orderVO.setDiscount(discount);
				live_orderVO.setLive_no(live_no);
				live_orderVO.setUser_id(user_id);
				live_orderVO.setSeller_id(seller_id);
				live_orderVO.setSrating(srating);
				live_orderVO.setSrating_content(srating_content);
				live_orderVO.setPoint(point);
				live_orderVO.setLive_order_no(live_order_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("live_orderVO", live_orderVO); // 含有輸入格式錯誤的live_orderVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/live_order/update_live_order_input.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				Live_orderService live_orderSvc = new Live_orderService();
				live_orderVO = live_orderSvc.updateLive_order(order_date, order_state, order_shipping, order_price,
						pay_method, pay_deadline, rec_name, rec_addr, rec_phone, rec_cellphone, logistics,
						logistics_state, discount, live_no, user_id, seller_id, srating, srating_content, point,
						live_order_no);

				/*************************** 3.修改完成,準備轉交(Send the Success view) *************/
				req.setAttribute("live_orderVO", live_orderVO); // 資料庫update成功後,正確的的live_orderVO物件,存入req
				String url = "/live_order/listOneLive_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/live_order/update_live_order_input.jsp");
				failureView.forward(req, res);
			}
		}

		if ("insert".equals(action)) { // 來自addEmp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				java.sql.Date order_date = null;
				try {
					order_date = java.sql.Date.valueOf(req.getParameter("order_date").trim());
				} catch (IllegalArgumentException e) {
					order_date = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				Integer order_state = new Integer(req.getParameter("order_state").trim());
				Integer order_shipping = new Integer(req.getParameter("order_shipping").trim());
				Integer order_price = new Integer(req.getParameter("order_price").trim());
				Integer pay_method = new Integer(req.getParameter("pay_method").trim());
				java.sql.Date pay_deadline = null;
				try {
					pay_deadline = java.sql.Date.valueOf(req.getParameter("pay_deadline").trim());
				} catch (IllegalArgumentException e) {
					pay_deadline = new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}

				String rec_name = req.getParameter("rec_name");

				String rec_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("收件人姓名: 請勿空白");
				} else if (!rec_name.trim().matches(rec_nameReg)) { // 以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
				}

				String rec_addr = req.getParameter("rec_addr");

				Integer rec_phone = new Integer(req.getParameter("rec_phone").trim());
				Integer rec_cellphone = new Integer(req.getParameter("rec_cellphone").trim());

				Integer logistics = new Integer(req.getParameter("logistics").trim());

				Integer logistics_state = new Integer(req.getParameter("logistics_state").trim());
				Integer discount = new Integer(req.getParameter("discount").trim());
				Integer live_no = new Integer(req.getParameter("live_no").trim());
				String user_id = req.getParameter("user_id");
				String seller_id = req.getParameter("seller_id");

				Integer srating = new Integer(req.getParameter("srating").trim());
				String srating_content = req.getParameter("srating_content");
				Integer point = new Integer(req.getParameter("point").trim());
				
				
				Live_orderVO live_orderVO = new Live_orderVO();
				live_orderVO.setOrder_date(order_date);
				live_orderVO.setOrder_state(order_state);
				live_orderVO.setOrder_shipping(order_shipping);
				live_orderVO.setOrder_price(order_price);
				live_orderVO.setPay_method(pay_method);
				live_orderVO.setPay_deadline(pay_deadline);
				live_orderVO.setRec_name(rec_name);
				live_orderVO.setRec_addr(rec_addr);
				live_orderVO.setRec_phone(rec_phone);
				live_orderVO.setRec_cellphone(rec_cellphone);
				live_orderVO.setLogistics(logistics);
				live_orderVO.setLogistics_state(logistics_state);
				live_orderVO.setDiscount(discount);
				live_orderVO.setLive_no(live_no);
				live_orderVO.setUser_id(user_id);
				live_orderVO.setSeller_id(seller_id);
				live_orderVO.setSrating(srating);
				live_orderVO.setSrating_content(srating_content);
				live_orderVO.setPoint(point);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("live_orderVO", live_orderVO); // 含有輸入格式錯誤的live_orderVO物件,也存入req
					RequestDispatcher failureView = req.getRequestDispatcher("/live_order/addLive_order.jsp");
					failureView.forward(req, res);
					return;
				}

				/*************************** 2.開始新增資料 ***************************************/
				Live_orderService live_orderSvc = new Live_orderService();
				live_orderVO = live_orderSvc.addLive_order(order_date, order_state, order_shipping, order_price,
						pay_method, pay_deadline, rec_name, rec_addr, rec_phone, rec_cellphone, logistics,
						logistics_state, discount, live_no, user_id, seller_id, srating, srating_content, point);

				/*************************** 3.新增完成,準備轉交(Send the Success view) ***********/
				String url = "/live_order/listAllLive_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交listAllEmp.jsp
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/live_order/addLive_order.jsp");
				failureView.forward(req, res);
			}
		}

		if ("delete".equals(action)) { // 來自listAllEmp.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				Integer live_order_no = new Integer(req.getParameter("live_order_no"));

				/*************************** 2.開始刪除資料 ***************************************/
				Live_orderService live_orderSvc = new Live_orderService();
				live_orderSvc.deleteLive_order(live_order_no);

				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				String url = "/live_order/listAllLive_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/live_order/listAllLive_order.jsp");
				failureView.forward(req, res);
			}
		}
	}
}