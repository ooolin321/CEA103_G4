package com.order.controller;

import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.order.model.OrderService;
import com.order.model.OrderVO;

public class OrderServlet extends HttpServlet{

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("order_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order/select_page.jsp");
					failureView.forward(req, res);
					return;//{程式中斷
				}
				
				Integer order_no = null;
				try {
					order_no = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("訂單編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order/select_page.jsp");
					failureView.forward(req, res);
					return;//{程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				OrderService orderSvc = new OrderService();
				OrderVO orderVO = orderSvc.getOneOrder(order_no);
				if (orderVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderVO", orderVO); // 資料庫取出的empVO物件,存入req
				String url = "/front-end/order/listOneOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneOrder.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/order/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllOrder.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數 ****************************************/
				Integer order_no = new Integer(req.getParameter("order_no"));
				
				/***************************2.開始查詢資料****************************************/
				OrderService orderSvc = new OrderService();
				OrderVO orderVO = orderSvc.getOneOrder(order_no);
								
				/***************************3查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("orderVO", orderVO);         // 資料庫取出的orderVO物件,存入req
				String url = "/front-end/order/update_order_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_order_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/order/listAllOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		if ("update".equals(action)) { // 來自update_order_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				java.sql.Date order_date = null;
				try {
					order_date = java.sql.Date.valueOf(req.getParameter("order_date").trim());
				} catch (IllegalArgumentException e) {
					order_date=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				Integer order_state = null;
				try {
					order_state = new Integer(req.getParameter("order_state").trim());
				} catch (NumberFormatException e) {
					order_state = 0;
					errorMsgs.add("狀態請填數字.");
				}

				Integer order_shipping = null;
				try {
					order_shipping = new Integer(req.getParameter("order_shipping").trim());
				} catch (NumberFormatException e) {
					order_shipping = 0;
					errorMsgs.add("運費請填數字.");
				}
				
				Integer order_price = null;
				try {
					order_price = new Integer(req.getParameter("order_price").trim());
				} catch (NumberFormatException e) {
					order_price = 0;
					errorMsgs.add("訂單金額請填數字.");
				}
				
				Integer pay_method = null;
				try {
					pay_method = new Integer(req.getParameter("pay_method").trim());
				} catch (NumberFormatException e) {
					pay_method = 0;
					errorMsgs.add("付款方式請填數字.");
				}
				
				java.sql.Date pay_deadline = null;
				try {
					pay_deadline = java.sql.Date.valueOf(req.getParameter("pay_deadline").trim());
				} catch (IllegalArgumentException e) {
					pay_deadline=new java.sql.Date(System.currentTimeMillis());
					errorMsgs.add("請輸入日期!");
				}
				
				String rec_name = req.getParameter("rec_name");
				String rec_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("收件人姓名: 請勿空白");
				} else if(!rec_name.trim().matches(rec_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String rec_addr = req.getParameter("rec_addr");
				String rec_addrReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (rec_addr == null || rec_addr.trim().length() == 0) {
					errorMsgs.add("收件人地址: 請勿空白");
				} else if(!rec_addr.trim().matches(rec_addrReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人地址: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String rec_phone = req.getParameter("rec_phone");
				
				String rec_cellphone = req.getParameter("rec_cellphone");
				String rec_cellphoneReg = "^09[0-9]{8}$";
				if (rec_cellphone == null || rec_cellphone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!rec_cellphone.trim().matches(rec_cellphoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("手機號碼: 只能是數字 , 且長度是10");
	            }
				
				Integer logistics = null;
				try {
					logistics = new Integer(req.getParameter("logistics").trim());
				} catch (NumberFormatException e) {
					logistics = 0;
					errorMsgs.add("物流方式請填數字.");
				}
				
				Integer logisticsstate = null;
				try {
					logisticsstate = new Integer(req.getParameter("logisticsstate").trim());
				} catch (NumberFormatException e) {
					logisticsstate = 0;
					errorMsgs.add("物流狀態請填數字.");
				}
				
				Integer discount = null;
				try {
					discount = new Integer(req.getParameter("discount").trim());
				} catch (NumberFormatException e) {
					discount = 0;
					errorMsgs.add("點數折抵請填數字.");
				}
				
				String user_id = new String(req.getParameter("user_id").trim());
				
				String seller_id = new String(req.getParameter("seller_id").trim());
				
				Integer srating = null;
				try {
					srating = new Integer(req.getParameter("srating").trim());
				} catch (NumberFormatException e) {
					srating = 0;
					errorMsgs.add("評價分數請填數字.");
				}
			
				String srating_content = req.getParameter("srating_content");
				String srating_contentReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (srating_content == null || srating_content.trim().length() == 0) {
					errorMsgs.add("評價內容: 請勿空白");
				} else if(!srating_content.trim().matches(srating_contentReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("評價內容: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
			
				Integer point = null;
				try {
					point = new Integer(req.getParameter("point").trim());
				} catch (NumberFormatException e) {
					point = 0;
					errorMsgs.add("點數請填數字.");
				}
				Integer order_no = new Integer(req.getParameter("order_no"));
				

				OrderVO orderVO = new OrderVO();
				orderVO.setOrder_date(order_date);
				orderVO.setOrder_state(order_state);
				orderVO.setOrder_shipping(order_shipping);
				orderVO.setOrder_price(order_price);
				orderVO.setPay_method(pay_method);
				orderVO.setPay_deadline(pay_deadline);
				orderVO.setRec_name(rec_name);
				orderVO.setRec_addr(rec_addr);
				orderVO.setRec_phone(rec_phone);
				orderVO.setRec_cellphone(rec_cellphone);
				orderVO.setLogistics(logistics);
				orderVO.setLogisticsstate(logisticsstate);
				orderVO.setDiscount(discount);
				orderVO.setUser_id(user_id);
				orderVO.setSeller_id(seller_id);
				orderVO.setSrating(srating);
				orderVO.setSrating_content(srating_content);
				orderVO.setPoint(point);
				orderVO.setOrder_no(order_no);
				
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderVO", orderVO); // 含有輸入格式錯誤的orderVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order/update_order_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				OrderService orderSvc = new OrderService();
				orderVO = orderSvc.updateOrder(order_date, order_state, order_shipping, order_price, pay_method, pay_deadline, rec_name, rec_addr, rec_phone, rec_cellphone, logistics, logisticsstate, discount, user_id, seller_id, srating, srating_content, point, order_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("orderVO", orderVO); //資料庫update成功後,正確的的empVO物件,存入req
				String url = "/front-end/order/listOneOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); //修改成功後,轉交listOneOrder.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/order/update_order_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addOrder.jsp的請求 
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				/***********************1.接收請求參數 - 輸入格式的錯誤處理*************************/
				Integer order_state = null;
				try {
					order_state = new Integer(req.getParameter("order_state").trim());
				} catch (NumberFormatException e) {
					order_state = 0;
					errorMsgs.add("訂單狀態請填數字.");
				}
				
				Integer order_shipping = null;
				try {
					order_shipping = new Integer(req.getParameter("order_shipping").trim());
				} catch (NumberFormatException e) {
					order_shipping = 0;
					errorMsgs.add("訂單運費請填數字.");
				}
				
				Integer order_price = null;
				try {
					order_price = new Integer(req.getParameter("order_price").trim());
				} catch (NumberFormatException e) {
					order_price = 0;
					errorMsgs.add("訂單金額請填數字.");
				}
				
				Integer pay_method = null;
				try {
					pay_method = new Integer(req.getParameter("pay_method").trim());
				} catch (NumberFormatException e) {
					pay_method = 0;
					errorMsgs.add("付款方式請填數字.");
				}
				
				String rec_name = req.getParameter("rec_name");
				String rec_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("收件人姓名: 請勿空白");
				} else if(!rec_name.trim().matches(rec_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人姓名: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String rec_addr = req.getParameter("rec_addr");
				String rec_addrReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,30}$";
				if (rec_addr == null || rec_addr.trim().length() == 0) {
					errorMsgs.add("收件人地址: 請勿空白");
				} else if(!rec_addr.trim().matches(rec_addrReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("收件人地址: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }
				
				String rec_phone = req.getParameter("rec_phone");
				
				String rec_cellphone = req.getParameter("rec_cellphone");
				String rec_cellphoneReg = "^09[0-9]{8}$";
				if (rec_cellphone == null || rec_cellphone.trim().length() == 0) {
					errorMsgs.add("手機號碼: 請勿空白");
				} else if(!rec_cellphone.trim().matches(rec_cellphoneReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("手機號碼: 只能是數字 , 且長度必需是10");
	            }
				
				Integer logistics = null;
				try {
					logistics = new Integer(req.getParameter("logistics").trim());
				} catch (NumberFormatException e) {
					logistics = 0;
					errorMsgs.add("物流方式請填數字.");
				}

				Integer logisticsstate = null;
				try {
					logisticsstate = new Integer(req.getParameter("logisticsstate").trim());
				} catch (NumberFormatException e) {
					logisticsstate = 0;
					errorMsgs.add("物流狀態請填數字.");
				}
				
				Integer discount = null;
				try {
					discount = new Integer(req.getParameter("discount").trim());
				} catch (NumberFormatException e) {
					discount = 0;
					errorMsgs.add("折扣點數請填數字.");
				}
				
				String user_id = new String(req.getParameter("user_id").trim());
				
				String seller_id = new String(req.getParameter("seller_id").trim());
				
				Integer srating = null;
				try {
					srating = new Integer(req.getParameter("srating").trim());
				} catch (NumberFormatException e) {
					srating = 0;
					errorMsgs.add("評價分數請填數字.");
				}

				String srating_content = req.getParameter("srating_content");
				String srating_contentReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,10}$";
				if (srating_content == null || srating_content.trim().length() == 0) {
					errorMsgs.add("評價內容: 請勿空白");
				} else if(!srating_content.trim().matches(srating_contentReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("評價內容: 只能是中、英文字母、數字和_ , 且長度必需在2到10之間");
	            }

				Integer point = null;
				try {
					point = new Integer(req.getParameter("point").trim());
				} catch (NumberFormatException e) {
					point = 0;
					errorMsgs.add("請填數字.");
				}

				OrderVO orderVO = new OrderVO();
				orderVO.setOrder_state(order_state);
				orderVO.setOrder_shipping(order_shipping);
				orderVO.setOrder_price(order_price);
				orderVO.setPay_method(pay_method);
				orderVO.setRec_name(rec_name);
				orderVO.setRec_addr(rec_addr);
				orderVO.setRec_phone(rec_phone);
				orderVO.setRec_cellphone(rec_cellphone);
				orderVO.setLogistics(logistics);
				orderVO.setLogisticsstate(logisticsstate);
				orderVO.setDiscount(discount);
				orderVO.setUser_id(user_id);
				orderVO.setSeller_id(seller_id);
				orderVO.setSrating(srating);
				orderVO.setSrating_content(srating_content);
				orderVO.setPoint(point);
		

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("orderVO", orderVO); // 資料庫取出的orderVO物件,存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/order/addOrder.jsp");
					failureView.forward(req, res);
					return;
				}
				
				/***************************2.開始修改資料***************************************/
				OrderService orderSvc = new OrderService();
				orderVO = orderSvc.addOrder(order_state, order_shipping, order_price, pay_method, rec_name, rec_addr, rec_phone, rec_cellphone, logistics, logisticsstate, discount, user_id, seller_id, srating, srating_content, point);
				
				/***************************3.修改完成,準備轉交(Send the Success view)***********/
				String url = "/front-end/order/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneOrder.jsp
				successView.forward(req, res);				
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/order/addOrder.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { //來自listAllOrder.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer order_no = new Integer(req.getParameter("order_no"));
				
				/***************************2.開始刪除資料***************************************/
				OrderService orderSvc = new OrderService();
				orderSvc.deleteOrder(order_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/front-end/order/listAllOrder.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/order/listAllOrder.jsp");
				failureView.forward(req, res);
			}
		}
	}
}

