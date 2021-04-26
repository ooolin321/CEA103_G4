package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;

import com.product.model.ProductDAO;
import com.product.model.ProductService;
import com.product.model.ProductVO;
import com.product_type.model.Product_TypeService;



@WebServlet("/ProductSearch")
public class ProductSearch extends HttpServlet {

	private static final long serialVersionUID = 1L;
	private static final String SUCESS_URL = "/front-end/productsell/shop.jsp";
	private static final String ERROR_URL = "/front-end/index.jsp";

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		doPost(req, res);
		
//		String action = req.getParameter("action");
//		List<String> errorMsgs = new ArrayList<String>();
//		req.setAttribute("errorMsgs", errorMsgs);
//		String url = "/front-end/productsell/shop.jsp";
//		
		
//		if("getJson".equals(action)) {
//		ProductService productSvc = new ProductService();
//		List<ProductVO> list = productSvc.getAllShopWithoutPhoto();
//		JSONObject jsonObj = new JSONObject();
//		
//		try {
//			jsonObj.put("results", list);
//
//		} catch (JSONException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		res.setContentType("text/html; charset=UTF-8");
//	
//		PrintWriter out = res.getWriter();
//		
//		out.println(jsonObj.toString());
//	}
		
		

	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		

		
		// 使用一般搜尋
		if (("s_catagories".equals(action)) || ("search".equals(action)))  {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			


			try {
				/*************************** 1.接收請求參數 ****************************************/
				HttpSession session = req.getSession();
				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
				
				// 以下的 if 區塊只對第一次執行時有效
				if (req.getParameter("whichPage") == null){
					Map<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
					session.setAttribute("map",map1);
					map = map1;
				} 

				/*************************** 2.開始查詢資料 ****************************************/
				ProductService productSvc = new ProductService();
				List<ProductVO> list  = productSvc.getAllShop(map);
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("products", list); // 資料庫取出的list物件,存入request
				

				RequestDispatcher successView = req.getRequestDispatcher(SUCESS_URL);
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) { //錯誤訊息有問題待修
				errorMsgs.add("目前無此商品,請重新查詢" + e.getMessage());
				req.getRequestDispatcher(ERROR_URL).forward(req, res);
				return;
			}	
		} 
		
//		ajax方法先不用
//		if (("search_ajax".equals(action)))  {
//
//			List<String> errorMsgs = new LinkedList<String>();
//			req.setAttribute("errorMsgs", errorMsgs);
//
//			try {
//				/*************************** 1.接收請求參數 ****************************************/
//				HttpSession session = req.getSession();
//				Map<String, String[]> map = (Map<String, String[]>)session.getAttribute("map");
//				
//				// 以下的 if 區塊只對第一次執行時有效
//				if (req.getParameter("whichPage") == null){
//					Map<String, String[]> map1 = new HashMap<String, String[]>(req.getParameterMap());
//					session.setAttribute("map",map1);
//					map = map1;
//				} 
//
//				/*************************** 2.開始查詢資料 ****************************************/
//				ProductService productSvc = new ProductService();
//				List<ProductVO> list  = productSvc.getAllShop(map);
//				/***************************3.查詢完成,準備轉交(Send the Success view)************/
//				
//				res.setContentType("text/html; charset=utf-8");
//				PrintWriter out = res.getWriter();
//				
//				JSONObject jsonObj = new JSONObject();
//				
//				try {
//					jsonObj.put("results", list);
//				} catch (JSONException e) {
//					e.printStackTrace();
//				}
//				
//				out.println(jsonObj.toString());
//				
//				/***************************其他可能的錯誤處理**********************************/
//			} catch (Exception e) {
//				errorMsgs.add(e.getMessage());
//				RequestDispatcher failureView = req
//						.getRequestDispatcher("/front-end/index.jsp");
//				failureView.forward(req, res);
//			}	
//		} 
}			
}

