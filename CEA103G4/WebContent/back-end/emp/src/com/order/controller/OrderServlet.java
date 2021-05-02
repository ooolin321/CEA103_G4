package com.order.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Vector;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.detail.model.DetailVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.order.model.OrderService;
import com.order.model.OrderVO;

public class OrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public OrderServlet() {
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		

		if ("PAY".equals(action)) {

			try {
				// order info
				String mem_id = req.getParameter("mem_Id");
				if (mem_id == null || mem_id.trim().length() == 0) {
				}
				String rec_name = req.getParameter("rec_Name").trim();
				if (rec_name == null || rec_name.trim().length() == 0) {
				}
				String rec_tel = null;
				try {
					rec_tel = new String(req.getParameter("rec_Tel"));
				} catch (NumberFormatException e) {
				}
				String rec_add = req.getParameter("rec_Add").trim();
				if (rec_add == null || rec_add.trim().length() == 0) {
				}
				Integer order_qty = null;
				try {
					order_qty = new Integer(req.getParameter("order_Qty").trim());
				} catch (NumberFormatException e) {
				}
				Integer order_total = null;
				try {
					order_total = new Integer(req.getParameter("order_Total").trim());
				} catch (NumberFormatException e) {
				}
				Integer order_pay = null;
				try {
					order_pay = new Integer(req.getParameter("order_Pay").trim());
				} catch (NumberFormatException e) {
				}
				Integer delivery = null;
				try {
					delivery = new Integer(req.getParameter("delivery").trim());
				} catch (NumberFormatException e) {
				}
				Integer get_bonus = null;
				try {
					get_bonus = new Integer(req.getParameter("get_Bonus").trim());
				} catch (NumberFormatException e) {
				}
				Integer use_bonus = null;
				try {
					use_bonus = new Integer(req.getParameter("use_Bonus").trim());
				} catch (NumberFormatException e) {
				}
				String mem_note = null;
				try {
					mem_note = new String(req.getParameter("mem_Note").trim());
				} catch (NumberFormatException e) {
				}

				// order detail info
				String[] book_id = req.getParameterValues("book_Id");
				String[] items_name = req.getParameterValues("book_Name");
				String[] comm_price = req.getParameterValues("price");
				String[] comm_qty = req.getParameterValues("comm_Qty");
				String[] comm_bonus = req.getParameterValues("book_BP");

				OrderVO odCartVO = new OrderVO();

				odCartVO.setMem_id(mem_id);
				odCartVO.setRec_name(rec_name);
				odCartVO.setRec_tel(rec_tel);
				odCartVO.setRec_add(rec_add);
				odCartVO.setOrder_qty(order_qty);
				odCartVO.setOrder_total(order_total);
				odCartVO.setOrder_pay(order_pay);
				odCartVO.setDelivery(delivery);
				odCartVO.setGet_bonus(get_bonus);
				odCartVO.setUse_bonus(use_bonus);
				odCartVO.setMem_note(mem_note);
				odCartVO.setOrder_status(1);

				List<DetailVO> cartlist = new Vector<DetailVO>();

				for (int i = 0; i < book_id.length; i++) {
					DetailVO dtCartVO = new DetailVO();
					dtCartVO.setBook_id(book_id[i]);
					dtCartVO.setItems_name(items_name[i]);
					dtCartVO.setComm_qty(new Integer(comm_qty[i]));
					dtCartVO.setComm_price(new Double(comm_price[i]));
					dtCartVO.setComm_bonus(new Integer(comm_bonus[i]));
//					System.out.println(book_id[i] + "、" + items_name[i] + "、" + comm_qty[i] + "、" + comm_price[i] + "、"
//							+ comm_bonus[i]);

					cartlist.add(dtCartVO);
				}

				// 扣除使用的紅利點數
				MemVO memVO = (MemVO) req.getSession().getAttribute("memVO");
				MemService memSvc = new MemService();
				memVO = memSvc.getOneMem(memVO.getMem_id()); //較不會有意外發生
				
				Double newBonus = memVO.getMem_bonus() - odCartVO.getUse_bonus() + odCartVO.getGet_bonus();

				OrderService odSvc = new OrderService();
				odSvc.createODDT(odCartVO, cartlist, newBonus);
				
				
				memVO = memSvc.getOneMem(memVO.getMem_id());
				req.getSession().setAttribute("memVO", memVO);

				HttpSession session = req.getSession();
				session.removeAttribute("shpingcart");
				session.removeAttribute("getTotal");
				
				String url = "/front-end/shopping/finished.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				System.out.println("▲Error： [訂單失敗!]" + e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/shopping/cart.jsp");
				failureView.forward(req, res);
			}

		}

//《Insert》		
		if ("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
				String mem_id = req.getParameter("mem_id");
				if (mem_id == null || mem_id.trim().length() == 0) {
					errorMsgs.add("會員ID: 請勿空白");
				}
				String rec_name = req.getParameter("rec_name").trim();
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("收件人請勿空白");
				}

				String rec_tel = null;
				try {
					rec_tel = new String(req.getParameter("rec_tel").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("收件人電話請填數字.");
				}
				String rec_add = req.getParameter("rec_add").trim();
				if (rec_add == null || rec_add.trim().length() == 0) {
					errorMsgs.add("收件人地址請勿空白.");
				}

				Integer order_qty = null;
				try {
					order_qty = new Integer(req.getParameter("order_qty").trim());
				} catch (NumberFormatException e) {
					order_qty = 1;
					errorMsgs.add("數量請填數字(1-45).");
				}

				Integer order_total = null;
				try {
					order_total = new Integer(req.getParameter("order_total").trim());
				} catch (NumberFormatException e) {
					order_total = 1;
					errorMsgs.add("金額請填數字(1-45).");
				}

				Integer order_pay = null;
				try {
					order_pay = new Integer(req.getParameter("order_pay").trim());
				} catch (NumberFormatException e) {
					order_pay = 1;
					errorMsgs.add("付款方式請填數字(1-信用卡 2-貨到付款).");
				}

				Integer delivery = null;
				try {
					delivery = new Integer(req.getParameter("delivery").trim());
				} catch (NumberFormatException e) {
					delivery = 1;
					errorMsgs.add("訂單狀態請填數字(1-訂單成立 2-已出貨).");
				}

				Integer get_bonus = null;
				try {
					get_bonus = new Integer(req.getParameter("get_bonus").trim());
				} catch (NumberFormatException e) {
					get_bonus = 1;
					errorMsgs.add("獲得紅利請填數字(1-10).");
				}

				Integer use_bonus = null;
				try {
					use_bonus = new Integer(req.getParameter("use_bonus").trim());
				} catch (NumberFormatException e) {
					use_bonus = 1;
					errorMsgs.add("紅利折抵請填數字(1-10).");
				}

				String mem_note = null;
				try {
					mem_note = new String(req.getParameter("mem_note").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("備註請填文字.");
				}

				OrderVO odVO = new OrderVO();
				odVO.setMem_id(mem_id);
				odVO.setRec_name(rec_name);
				odVO.setRec_tel(rec_tel);
				odVO.setRec_add(rec_add);
				odVO.setOrder_qty(order_qty);
				odVO.setOrder_total(order_total);
				odVO.setOrder_pay(order_pay);
				odVO.setDelivery(delivery);
				odVO.setGet_bonus(get_bonus);
				odVO.setUse_bonus(use_bonus);
				odVO.setMem_note(mem_note);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("odVO", odVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/create_order.jsp");
					failureView.forward(req, res);
					return;
				}
				OrderService odSvc = new OrderService();

				odVO = odSvc.createOd(mem_id, rec_name, rec_tel, rec_add, order_qty, order_total, order_pay, delivery,
						get_bonus, use_bonus, mem_note);
				String url = "/back-end/order/listAll_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				System.out.println("▲Error： " + e.getMessage());
				e.printStackTrace();
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/create_order.jsp");
				failureView.forward(req, res);
			}
		}

//《Select single》
		if ("selOneOd".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String str = req.getParameter("order_id");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入訂單編號");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/select_order.jsp");
					failureView.forward(req, res);
					return;
				}
				String order_id = null;
				try {
					order_id = str;
				} catch (Exception e) {
					errorMsgs.add("編號格式不正確");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/select_order.jsp");
					failureView.forward(req, res);
					return;
				}
				OrderService odSvc = new OrderService();
				OrderVO odVO = odSvc.getOneOd(order_id);
				if (odVO == null) {
					errorMsgs.add("查無資料");
				}
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/select_order.jsp");
					failureView.forward(req, res);
					return;
				}
				req.setAttribute("odVO", odVO);
				String url = "/back-end/order/listOne_order.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/order/select_order.jsp");
				failureView.forward(req, res);
			}
		}

//《Update》
		if ("getupdate".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String order_id = req.getParameter("order_id");
				System.out.println(order_id);

				OrderService odSvc = new OrderService();
				OrderVO odVO = odSvc.getOneOd(order_id);
				req.setAttribute("odVO", odVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/order/update_order.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
				failView.forward(req, res);
			}
		}

		if ("update".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String order_id = req.getParameter("order_id");
				String rec_name = req.getParameter("rec_name");
				String name_Reg = "^[\\u4e00-\\u9fa5]+$|^[a-zA-Z\\s]+$";
				if (rec_name == null || rec_name.trim().length() == 0) {
					errorMsgs.add("錯誤：收件人姓名空白");
				} else if (!rec_name.trim().matches(name_Reg)) {
					errorMsgs.add("錯誤：收件人姓名只能是中、英文字");
				}

				String rec_tel = req.getParameter("rec_tel");
				String tel_Reg = "^[09]{2}[0-9]{8}$";
				if (rec_tel == null || rec_tel.trim().length() == 0) {
					errorMsgs.add("錯誤：收件人電話空白");
				} else if (!rec_tel.trim().matches(tel_Reg)) {
					errorMsgs.add("錯誤：收件人電話有誤");
				}
				String rec_add = req.getParameter("rec_add");
				if (rec_add == null || rec_add.trim().length() == 0) {
					errorMsgs.add("錯誤：收件人地址空白");
				}

				Integer order_pay = new Integer(req.getParameter("order_pay").trim());
				Integer delivery = new Integer(req.getParameter("delivery").trim());
				Integer order_status = new Integer(req.getParameter("order_status").trim());

				String mem_note = req.getParameter("mem_note");

				OrderVO odVO = new OrderVO();
				odVO.setOrder_id(order_id);
				odVO.setRec_name(rec_name);
				odVO.setRec_tel(rec_tel);
				odVO.setRec_add(rec_add);
				odVO.setOrder_pay(order_pay);
				odVO.setDelivery(delivery);
				odVO.setOrder_status(order_status);
				odVO.setMem_note(mem_note);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("odVO", odVO);
					RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/update_order.jsp");
					failView.forward(req, res);
					return;
				}

				OrderService odSvc = new OrderService();
				odVO = odSvc.updateOd(order_id, rec_name, rec_tel, rec_add, order_pay, delivery, order_status,
						mem_note);

				req.setAttribute("odVO", odVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/update_order.jsp");
				failView.forward(req, res);
			}

		}
		
//《shipment》
		if ("shipment".equals(action)) {
			String jspname = req.getParameter("name");
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String order_id = req.getParameter("order_id");
				Integer order_status = 3;
				
				OrderVO odVO = new OrderVO();
				odVO.setOrder_id(order_id);
				odVO.setOrder_status(order_status);
			
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("odVO", odVO);
					RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
					failView.forward(req, res);
					return;
				}
				OrderService odSvc = new OrderService();
				odSvc.shipment(odVO);
				OrderVO detailVO = odSvc.getOneOd(order_id);
				if(jspname.equals("logistics")) {
					res.sendRedirect("/front-end/bookshop-eshop/index.jsp");
					
				}else {
				req.setAttribute("odVO", detailVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/order/listOne_order.jsp");
				successView.forward(req, res);
				}
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
				failView.forward(req, res);
			}

		}
		
		
		

//《Cancel》

		if ("getcancel".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String order_id = req.getParameter("order_id");
				System.out.println(order_id);

				OrderService odSvc = new OrderService();
				OrderVO odVO = odSvc.getOneOd(order_id);
				req.setAttribute("odVO", odVO);
				RequestDispatcher successView = req.getRequestDispatcher("/back-end/order/cancel_order.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
				failView.forward(req, res);
			}
		}

		if (action.equals("cancel")) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				String order_id = req.getParameter("order_id");

				OrderService odSvc = new OrderService();
				odSvc.cancel(order_id);

				RequestDispatcher successView = req.getRequestDispatcher("/back-end/order/listAll_order.jsp");
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("▲Error：[ 無法取得資料 ]" + e.getMessage());
				RequestDispatcher failView = req.getRequestDispatcher("/back-end/order/update_order.jsp");
				failView.forward(req, res);
			}

		}

	}

}
