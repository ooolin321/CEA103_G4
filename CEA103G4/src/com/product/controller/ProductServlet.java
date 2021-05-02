package com.product.controller;
import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.product.model.*;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class ProductServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		//商品區點選商品格取得get資料,查詢一筆資料畫面(跳轉至商品頁)
	
		String productURL = "/front-end/productsell/product.jsp";
		Integer product_no = new Integer(req.getParameter("product_no"));
		ProductService productSvc = new ProductService();
		ProductVO productVO = productSvc.getOneProduct(product_no);
		req.setAttribute("productVO", productVO);
		req.getRequestDispatcher(productURL).forward(req, res);

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
							.getRequestDispatcher("/back-end/product/select_page.jsp");
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
							.getRequestDispatcher("/back-end/product/select_page.jsp");
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
							.getRequestDispatcher("/back-end/product/select_page.jsp");
					failureView.forward(req, res);
					return;//程式中斷
				}
				
				/***************************3.查詢完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫取出的productVO物件,存入req
				String url = "/back-end/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/select_page.jsp");
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
				String url = "/back-end/product/update_product_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_product_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
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
				ProductVO productVO = new ProductVO();
				
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
				
				Integer product_remaining = null;
				try {
					product_remaining = new Integer(req.getParameter("product_remaining").trim());
				} catch (NumberFormatException e) {
					product_remaining = 1;
					errorMsgs.add("商品剩餘數量請填數字");
				}
				
				Integer product_state = null;
				try {
					product_state = new Integer(req.getParameter("product_state").trim());
				} catch (NumberFormatException e) {
					product_state = 0;
					errorMsgs.add("商品狀態請填數字");
				}
				
				byte[] product_photo = null;
					Part part = req.getPart("product_photo");
					if (part == null || part.getSize() == 0) {
						req.setAttribute("productVO", productVO);
						ProductService productSvc2 = new ProductService();
						ProductVO productVO2 = productSvc2.getOneProduct(product_no);
						product_photo = productVO2.getProduct_photo();
				} else {
					req.setAttribute("productVO", productVO);
					InputStream in = part.getInputStream();
					product_photo = new byte[in.available()];
					in.read(product_photo);
					in.close();
				}
				
				String user_id = req.getParameter("user_id").trim();
				if (user_id == null || user_id.trim().length() == 0) {
					errorMsgs.add("會員帳號請勿空白");
				}
				
				Integer pdtype_no = null;
				try {
					pdtype_no = new Integer(req.getParameter("pdtype_no").trim());
				} catch (NumberFormatException e) {
					pdtype_no = 1;
					errorMsgs.add("商品類別請填數字");
				}
				
//				Integer start_price = null;
//				try {
//					start_price = new Integer(req.getParameter("start_price").trim());
//				} catch (NumberFormatException e) {
//					start_price = 1;
//					errorMsgs.add("起標價請填數字");
//				}
//				
//				Integer live_no = null;
//				try {
//					live_no = new Integer(req.getParameter("live_no").trim());
//				} catch (NumberFormatException e) {
//					live_no = 1;
//					errorMsgs.add("直播編號請填寫");
//				}

				

			
				
				productVO.setProduct_no(product_no);
				productVO.setProduct_name(product_name);
				productVO.setProduct_info(product_info);
				productVO.setProduct_price(product_price);
				productVO.setProduct_quantity(product_quantity);
				productVO.setProduct_remaining(product_remaining);
				productVO.setProduct_state(product_state);
				productVO.setProduct_photo(product_photo);
				productVO.setUser_id(user_id);
				productVO.setPdtype_no(pdtype_no);
//				productVO.setStart_price(start_price);
//				productVO.setLive_no(live_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product/update_product_input.jsp");
					failureView.forward(req, res);
					return; 
				}
				
				/***************************2.開始修改資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.updateProduct(product_no, product_name, product_info, product_price, product_quantity, product_remaining, product_state,
						product_photo, user_id, pdtype_no);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("productVO", productVO); // 資料庫update成功後,正確的的productVO物件,存入req
				String url = "/back-end/product/listOneProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneEmp.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/update_product_input.jsp");
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
				
				String product_name = req.getParameter("product_name");
				String product_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (product_name == null || product_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!product_name.trim().matches(product_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				String product_info = req.getParameter("product_info");
				if (product_info == null || product_info.trim().length() == 0) {
					errorMsgs.add("商品說明請勿空白");
				}
				while (product_info.indexOf("\n") != -1) {
					product_info = product_info.replace("\n","<br>");
				}
				while (product_info.indexOf(" ") != -1) {
					product_info = product_info.replace(" ","&nbsp;");
				}

				Integer product_price = null;
				try {
					product_price = new Integer(req.getParameter("product_price").trim());
				} catch (NumberFormatException e) {
					product_price = 0;
					errorMsgs.add("商品價格請填數字");
				}
				
//				Integer product_quantity = null;
//				try {
//					product_quantity = new Integer(req.getParameter("product_quantity").trim());
//				} catch (NumberFormatException e) {
//					product_quantity = 1;
//					errorMsgs.add("商品數量請填數字");
//				}
				
				Integer product_remaining = null;
				try {
					product_remaining = new Integer(req.getParameter("product_remaining").trim());
				} catch (NumberFormatException e) {
					product_remaining = 1;
					errorMsgs.add("商品數量請填數字");
				}

				
				Integer product_state = new Integer(req.getParameter("product_state").trim());
				
				byte[] product_photo = null;
				Part part = req.getPart("product_photo");
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳一張圖片");
				} else {
					InputStream in = part.getInputStream();
					product_photo = new byte[in.available()];
					in.read(product_photo);
					in.close();
				}

				
				String user_id = req.getParameter("user_id").trim();

				
				Integer pdtype_no = null;
				try {
					pdtype_no = new Integer(req.getParameter("pdtype_no").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("商品類別請勿空白");
				}				
				

				ProductVO productVO = new ProductVO();
	
				productVO.setProduct_name(product_name);
				productVO.setProduct_info(product_info);
				productVO.setProduct_price(product_price);
//				productVO.setProduct_quantity(product_quantity);
				productVO.setProduct_remaining(product_remaining);
				productVO.setProduct_state(product_state);
				productVO.setProduct_photo(product_photo);
				productVO.setUser_id(user_id);
				productVO.setPdtype_no(pdtype_no);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("productVO", productVO); // 含有輸入格式錯誤的productVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/productManagement/productAdd.jsp");
					failureView.forward(req, res);
					return; 
				}
				System.out.println(product_name);
				System.out.println(product_info);
				System.out.println(product_price);
				System.out.println(product_remaining);
				System.out.println(product_state);
				System.out.println(user_id);
				System.out.println(pdtype_no);
				
				/***************************2.開始新增資料*****************************************/
				ProductService productSvc = new ProductService();
				productVO = productSvc.addProduct(product_name, product_info, product_price,product_remaining, product_state,
						product_photo, user_id, pdtype_no);
				
				/***************************3.新增完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listAllProduct.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/productManagement/productAdd.jsp");
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
				String url = "/back-end/product/listAllProduct.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product/listAllProduct.jsp");
				failureView.forward(req, res);
			}
		}   
		
//		if("getJson".equals(action)) {
//			ProductService productSvc = new ProductService();
//			List<ProductVO> list = productSvc.getAllWithoutPhoto();
//			JSONObject jsonObj = new JSONObject();
//			
//			try {
//				jsonObj.put("results", list);
//
//			} catch (JSONException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			res.setContentType("text/html; charset=UTF-8");
//		
//			PrintWriter out = res.getWriter();
//			
//			out.println(jsonObj.toString());
//		}
	}
}
