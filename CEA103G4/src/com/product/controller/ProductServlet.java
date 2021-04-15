package com.product.controller;
import java.io.*;
import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import com.product.model.*;


public class ProductServlet extends HttpServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("getOne_For_Display".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
		try {
			/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String str = req.getParameter("product_no");
				if (str == null || (str.trim()).length() == 0) {
					errorMsgs.add("請輸入商品編號");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				Integer product_no = null;
				try {
					product_no = new Integer(str);
				} catch (Exception e) {
					errorMsgs.add("商品編號格式不正確");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************2.開始查詢資料*****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(product_no);
				if (productVO == null) {
					errorMsgs.add("查無資料");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/select_page.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllProduct.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer product_no = new Integer(req.getParameter("product_no"));
				
				/***************************2.開始查詢資料****************************************/
				ProductService productSvc = new ProductService();
				ProductVO productVO = productSvc.getOneProduct(product_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("productVO", productVO);         // 資料庫取出的productVO物件,存入req
				String url = "/product/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_product_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer product_no = new Integer(req.getParameter("product_no").trim());
				
				String product_name = req.getParameter("product_name");
				String product_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!product_name.trim().matches(product_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				String product_info = req.getParameter("product_info").trim();
				if (product_info == null || product_info.trim().length() == 0) {
					errorMsgs.add("商品說明請勿空白");
				}
				
				Integer product_price = null;
				try {
					product_price = new Integer(req.getParameter("product_price").trim());
				} catch (NumberFormatException e) {
					product_price = 0;
					errorMsgs.add("商品價格請填數字");
				}
				
				Integer product_quantity = null;
				try {
					product_quantity = new Integer(req.getParameter("product_quantity").trim());
				} catch (NumberFormatException e) {
					product_quantity = 1;
					errorMsgs.add("商品數量請填數字");
				}
				
				//商品剩餘數量/商品狀態 / 商品數量 / 商品類別編號/起標價 /直播編號 暫空
				

				ProductVO productVO = new ProductVO();
				productVO.setProduct_no(product_no);
				productVO.setProduct_name(product_name);
				productVO.setProduct_info(product_info);
				productVO.setProduct_price(product_price);
				productVO.setProduct_quantity(product_quantity);
//				productVO.setProduct_remaining(product_remaining);
//				productVO.setProduct_state(product_state);
//				productVO.setProduct_photo(product_photo);
//				productVO.setUser_id(user_id);
//				productVO.setPdtype_no(pdtype_no);
//				productVO.setStart_price(start_price);
//				productVO.setLive_no(live_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateEmp(product_no, product_name, product_info, product_price, product_quantity, product_remaining, product_state,
						product_photo, user_id, pdtype_no, start_price, live_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/update_product_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addProduct.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer product_no = new Integer(req.getParameter("product_no").trim());
				
				String product_name = req.getParameter("product_name");
				String product_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!product_name.trim().matches(product_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				String product_info = req.getParameter("product_info").trim();
				if (product_info == null || product_info.trim().length() == 0) {
					errorMsgs.add("商品說明請勿空白");
				}
				
				Integer product_price = null;
				try {
					product_price = new Integer(req.getParameter("product_price").trim());
				} catch (NumberFormatException e) {
					product_price = 0;
					errorMsgs.add("商品價格請填數字");
				}
				
				Integer product_quantity = null;
				try {
					product_quantity = new Integer(req.getParameter("product_quantity").trim());
				} catch (NumberFormatException e) {
					product_quantity = 1;
					errorMsgs.add("商品數量請填數字");
				}
				
				//商品剩餘數量/商品狀態 / 商品數量 / 商品類別編號/起標價 /直播編號 暫空
				

				ProductVO productVO = new ProductVO();
				productVO.setProduct_no(product_no);
				productVO.setProduct_name(product_name);
				productVO.setProduct_info(product_info);
				productVO.setProduct_price(product_price);
				productVO.setProduct_quantity(product_quantity);
//				productVO.setProduct_remaining(product_remaining);
//				productVO.setProduct_state(product_state);
//				productVO.setProduct_photo(product_photo);
//				productVO.setUser_id(user_id);
//				productVO.setPdtype_no(pdtype_no);
//				productVO.setStart_price(start_price);
//				productVO.setLive_no(live_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				/***************************2.開始新增資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(product_name, product_info, product_price, product_quantity, product_remaining, product_state,
						product_photo, user_id, pdtype_no, start_price, live_no);
				
				/***************************3.新增完成,準備轉交(Send the Success view)*************/
				String url = "/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/addProduct.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("delete".equals(action)) { // 來自listAllProduct.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer product_no = new Integer(req.getParameter("product_no"));
				
				/***************************2.開始刪除資料***************************************/
				ProductService productSvc = new ProductService();
				productSvc.deleteProduct(product_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}
	}
}