package com.product.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

import com.product.model.ProductService;
import com.product.model.ProductVO;



@WebServlet("/ProductSearch")
public class ProductSearch extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
	
		String action = req.getParameter("action");
		List<String> errorMsgs = new ArrayList<String>();
		req.setAttribute("errorMsgs", errorMsgs);
		String url = "/front-end/productsell/shop.jsp";
		
		
		if("getJson".equals(action)) {
		ProductService productSvc = new ProductService();
		List<ProductVO> list = productSvc.getAllShopWithoutPhoto();
		JSONObject jsonObj = new JSONObject();
		
		try {
			jsonObj.put("results", list);

		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		res.setContentType("text/html; charset=UTF-8");
	
		PrintWriter out = res.getWriter();
		
		out.println(jsonObj.toString());
	}
		
		// 使用一般搜尋
		if ("search".equals(action)) {
			String product_name = req.getParameter("product_name");

			if (product_name == null || product_name.trim().length() == 0) {
				errorMsgs.add("請輸入搜尋關鍵字");
				req.getRequestDispatcher(url).forward(req, res);
				return;
			}

			ProductService productSvc = (ProductService) getServletContext().getAttribute("productSvc");
			List<String> product_names = productSvc.findProductsBySearch(product_name);
			req.setAttribute("product_names", product_names);
			req.getRequestDispatcher(url).forward(req, res);

}
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
}
}
