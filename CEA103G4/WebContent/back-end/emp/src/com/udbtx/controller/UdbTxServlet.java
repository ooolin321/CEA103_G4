package com.udbtx.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.udbtx.model.UdbTxService;
import com.udbtx.model.UdbTxVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class UdbTxServlet extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

//		if ("insert".equals(action)) {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
//				/** book_id **/
//				String book_id = req.getParameter("book_id");
//				System.out.println(book_id);
//
//				/** seller_mem_id **/
//				String seller_mem_id = req.getParameter("seller_mem_id ");
//				System.out.println(seller_mem_id);
//
//				/** buyer_mem_id **/
//				String buyer_mem_id = req.getParameter("buyer_mem_id ");
//				System.out.println(buyer_mem_id);
//
//				/** book_state **/
//				String book_state = req.getParameter("book_state");
//				String book_stateReg = "^[(\u4e00-\u9fa5)]{0,20}$";
//				if (!book_state.trim().matches(book_stateReg)) {
//					errorMsgs.add("BOOK_STATE:最多20字");
//				}
//				System.out.println(book_state);
//
//				/** book_state_pic **/
//				byte[] book_state_pic = null;
//				Part part = req.getPart("book_state_pic");
//				if (part == null || part.getSize() == 0) {
//					errorMsgs.add("請上傳一張圖片");
//				}
//				InputStream in = part.getInputStream();
//				book_state_pic = new byte[in.available()];
//				System.out.println(book_state_pic);
//				in.read(book_state_pic);
//				in.close();
//
//				System.out.println(part);
//
//				/** UDB_SALE_PRICE **/
//				Integer udb_sale_price = null;
//				try {
//					udb_sale_price = new Integer(req.getParameter("udb_sale_price").trim());
//					if (udb_sale_price <= 0) {
//						errorMsgs.add("售價必須大於0");
//					}
//				} catch (NumberFormatException e) {
//					udb_sale_price = 0;
//					errorMsgs.add("售價必須不於0");
//				}
//
//				System.out.println(udb_sale_price);
//
//				/** release_date **/
//				java.sql.Timestamp release_date = null;
//				release_date = java.sql.Timestamp.valueOf(req.getParameter("release_date").trim());
//
//				System.out.println(release_date);
//
//				/** UDB_ORDER_STATE_NO **/
//				Integer udb_order_state_no = new Integer(req.getParameter("udb_order_state_no").trim());
//				System.out.println(udb_order_state_no);
//
//				/** udb_order_date **/
//				java.sql.Timestamp udb_order_date = null;
//				udb_order_date = java.sql.Timestamp.valueOf(req.getParameter("udb_order_date").trim());
//
//				System.out.println(udb_order_date);
//
//				/** prod_state **/
//				Integer prod_state = new Integer(req.getParameter("prod_state").trim());
//				System.out.println(prod_state);
//
//				/** payment_state **/
//				Integer payment_state = new Integer(req.getParameter("payment_state").trim());
//				System.out.println(payment_state);
//
//				UsedbookTransactionVO usedbooktransactionVO = new UsedbookTransactionVO();
//				usedbooktransactionVO.setBook_id(book_id);
//				usedbooktransactionVO.setSeller_mem_id(seller_mem_id);
//				usedbooktransactionVO.setBuyer_mem_id(buyer_mem_id);
//				usedbooktransactionVO.setBook_state(book_state);
//				usedbooktransactionVO.setBook_state_pic(book_state_pic);
//				usedbooktransactionVO.setUdb_sale_price(udb_sale_price);
//				usedbooktransactionVO.setRelease_date(release_date);
//				usedbooktransactionVO.setUdb_order_state_no(udb_order_state_no);
//				usedbooktransactionVO.setUdb_order_date(udb_order_date);
//				usedbooktransactionVO.setProd_state(prod_state);
//				usedbooktransactionVO.setPayment_state(payment_state);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("usedbooktransactionVO", usedbooktransactionVO);
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/usedbooktransaction/addUsedbookTransaction.jsp");
//					failureView.forward(req, res);
//					return;
//				}
//
//				/*************************** 2.開始新增資料 ***************************************/
//				UsedbookTransactionService usedbooktransactionSvc = new UsedbookTransactionService();
//				usedbooktransactionSvc.addUsedbookTransaction(book_id, seller_mem_id, buyer_mem_id, book_state,
//						book_state_pic, udb_sale_price, release_date, udb_order_state_no, udb_order_date, prod_state,
//						payment_state);
//
//				/*************************** 3.新增完成，準備轉交(Send the Success view) ***********/
//				String url = "/back-end/usedbooktransaction/addUsedbookTransaction.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功轉交listAllUsedbookTransaction.jsp
//				successView.forward(req, res);
//				System.out.println("OKOKOKOKO");
//
//				/*************************** 其他可能的錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/usedbooktransaction/addUsedbookTransaction.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//		if ("update".equals(action)) { // 來自update_emp_input.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
//				String po_no = req.getParameter("po_no").trim();
//
//				/** book_state **/
//				String book_state = req.getParameter("book_state");
//				String book_stateReg = "^[(\u4e00-\u9fa5)]{0,20}$";
//				if (!book_state.trim().matches(book_stateReg)) {
//					errorMsgs.add("BOOK_STATE:最多20字");
//				}
//				System.out.println(book_state);
//
//				/** book_state_pic **/
//				byte[] book_state_pic = null;
//				Part part = req.getPart("book_state_pic");
//				if (part == null || part.getSize() == 0) {
//					errorMsgs.add("請上傳一張圖片");
//				}
//				InputStream in = part.getInputStream();
//				book_state_pic = new byte[in.available()];
//				System.out.println(book_state_pic);
//				in.read(book_state_pic);
//				in.close();
//
//				System.out.println(part);
//
//				/** UDB_SALE_PRICE **/
//				Integer udb_sale_price = null;
//				try {
//					udb_sale_price = new Integer(req.getParameter("udb_sale_price").trim());
//					if (udb_sale_price <= 0) {
//						errorMsgs.add("售價必須大於0");
//					}
//				} catch (NumberFormatException e) {
//					udb_sale_price = 0;
//					errorMsgs.add("售價必須不於0");
//				}
//
//				System.out.println(udb_sale_price);
//
//				/** UDB_ORDER_STATE_NO **/
//				Integer udb_order_state_no = new Integer(req.getParameter("udb_order_state_no").trim());
//				System.out.println(udb_order_state_no);
//
//				/** prod_state **/
//				Integer prod_state = new Integer(req.getParameter("prod_state").trim());
//				System.out.println(prod_state);
//
//				/** payment_state **/
//				Integer payment_state = new Integer(req.getParameter("payment_state").trim());
//				System.out.println(payment_state);
//
//				UsedbookTransactionVO usedbooktransactionVO = new UsedbookTransactionVO();
//
//				usedbooktransactionVO.setPo_no(po_no);
//				usedbooktransactionVO.setBook_state(book_state);
//				usedbooktransactionVO.setBook_state_pic(book_state_pic);
//				usedbooktransactionVO.setUdb_sale_price(udb_sale_price);
//				usedbooktransactionVO.setUdb_order_state_no(udb_order_state_no);
//				usedbooktransactionVO.setProd_state(prod_state);
//				usedbooktransactionVO.setPayment_state(payment_state);
//
//				// Send the use back to the form, if there were errors
//				if (!errorMsgs.isEmpty()) {
//					req.setAttribute("usedbooktransactionVO", usedbooktransactionVO);
//					RequestDispatcher failureView = req
//							.getRequestDispatcher("/back-end/usedbooktransaction/update_usedbooktransaction_input.jsp");
//					failureView.forward(req, res);
//					return; // 程式中斷
//				}
//
//				/*************************** 2.開始修改資料 *****************************************/
//				UsedbookTransactionService usedbooktransactionSvc = new UsedbookTransactionService();
//				usedbooktransactionVO = usedbooktransactionSvc.updateUsedbookTransaction(po_no, book_state,
//						book_state_pic, udb_sale_price, udb_order_state_no, prod_state, payment_state);
//
//				/*************************** 3.修改完成，準備轉交(Send the Success view) *************/
//				req.setAttribute("usedbooktransactionVO", usedbooktransactionVO); // 資料庫update成功後，正確的usedbooktransactionVO,req存入
//				String url = "/back-end/usedbooktransaction/listOneUsedbookTransaction.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功轉交listOneUsedbookTransaction.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能的錯誤處理 *************************************/
//			} catch (Exception e) {
//				errorMsgs.add("修改資料失敗:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/usedbooktransaction/update_usedbooktransaction_input.jsp");
//				failureView.forward(req, res);
//			}
//		}

		if ("delete".equals(action)) { // 來自listAllUsedbookTransaction.jsp

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 ***************************************/
				String po_no = req.getParameter("po_no").trim();

				/*************************** 2.開始刪除資料 ***************************************/
				UdbTxService udbtxSvc = new UdbTxService();
				udbtxSvc.deleteUdbTx(po_no);

				/*************************** 3.刪除完成，準備轉交(Send the Success view) ***********/
				String url = "/back-end/udbtx/listAllUdbTx.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後，轉交回送出刪除的來源網頁
				successView.forward(req, res);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/udbtx/listAllUdbTx.jsp");
				failureView.forward(req, res);
			}
		}
		if ("getOne_For_Display".equals(action)) { // 來自select_page.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String po_no= req.getParameter("po_no").trim();
				String po_noReg = "^PO[0-9]{4}$";
				if (po_no == null || (po_no.trim()).length() == 0) {
					errorMsgs.add("PO_no請勿空白");
				} else if (!po_no.trim().matches(po_noReg)) {
					errorMsgs.add("PO_no只能為PO開頭+四個數字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/udbtx/listAllUdbTx.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				UdbTxService udbtxSvc = new UdbTxService();
				UdbTxVO udbtxVO = udbtxSvc.getOnePoNo(po_no);
				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("udbtxVO", udbtxVO);
				String url = "/back-end/udbtx/listOneUdbTx.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneUsedbookTransaction.jsp
				successView.forward(req, res);

				/*************************** 其他可能錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/udbtx/listAllUdbTx.jsp");
				failureView.forward(req, res);
			}
		}

//		if ("getOne_For_Update".equals(action)) { // 來自修改成功轉交listAllUsedbookTransaction.jsp.jsp的請求
//
//			List<String> errorMsgs = new LinkedList<String>();
//			// Store this set in the request scope, in case we need to
//			// send the ErrorPage view.
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接受請求參數 ****************************************/
//				String po_no = req.getParameter("po_no");
//
//				/*************************** 2.開始查詢資料 ****************************************/
//				UsedbookTransactionService usedbooktransactionSvc = new UsedbookTransactionService();
//				UsedbookTransactionVO usedbooktransactionVO = usedbooktransactionSvc
//						.getOneUsedbookTransaction(po_no);
//
//				/*************************** 3.查詢完成，準備轉交(Send the Success view) ************/
//				req.setAttribute("usedbooktransactionVO", usedbooktransactionVO);
//				String url = "/back-end/usedbooktransaction/update_usedbooktransaction_input.jsp";
//				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交
//																				// update_usedbooktransaction_input.jsp
//				successView.forward(req, res);
//
//				/*************************** 其他可能錯誤處理 **********************************/
//			} catch (Exception e) {
//				errorMsgs.add("無法取得要修改資料:" + e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/back-end/usedbooktransaction/listAllUsedbookTransaction.jsp");
//				failureView.forward(req, res);
//			}
//		}
//
//	}

//	private byte[] getPictureByteArray(Part part) throws IOException {
//		InputStream is = part.getInputStream();
//		ByteArrayOutputStream baos = new ByteArrayOutputStream();
//		byte[] buffer = new byte[8192];
//		int i;
//		while ((i = is.read(buffer)) != -1) {
//			baos.write(buffer, 0, i);
//			baos.flush();
//		}
//		baos.close();
//		is.close();
//
//		return baos.toByteArray();
	}
}
