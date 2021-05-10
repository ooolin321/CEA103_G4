package com.product.controller;

import java.util.*;
import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import com.product.model.*;

@WebServlet("/ShoppingServlet")
public class ShoppingServlet extends HttpServlet {
	
	private static final long serialVersionUID = 1L;

	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();
		
		@SuppressWarnings("unchecked")
		List<ProductVO> buylist = (Vector<ProductVO>) session.getAttribute("shoppingcart");
		String action = req.getParameter("action");
		ProductVO product= new ProductVO();
		if (!action.equals("CHECKOUT")) {

			// 刪除購物車中的商品
			if (action.equals("DELETE")) {
				String del = req.getParameter("del");
				int d = Integer.parseInt(del);
				buylist.remove(d);
				
				session.setAttribute("shoppingcart", buylist);
				
				String url = "/front-end/productsell/shoppingCart.jsp";
				RequestDispatcher rd = req.getRequestDispatcher(url);
				rd.forward(req, res);
				return;
			}
			// 新增商品至購物車中
			else if (action.equals("ADD")) {
				
				// 取得後來新增的商品
				product = getProduct(req);

				if (buylist == null) {
					buylist = new Vector<ProductVO>();
					buylist.add(product);
					
				} else {
					if (buylist.contains(product)) {
						ProductVO innerProductVO = buylist.get(buylist.indexOf(product));
						innerProductVO.setProduct_quantity(innerProductVO.getProduct_quantity() + product.getProduct_quantity());
					} else {
						buylist.add(product);
					}
				}
			}
			
			session.setAttribute("shoppingcart", buylist);
			String url = "/front-end/productsell/product.jsp";
			req.setAttribute("productVO", product);
			RequestDispatcher rd = req.getRequestDispatcher(url);
			rd.forward(req, res);
		}

//		// 結帳，計算購物車商品價錢總數
//		else if (action.equals("CHECKOUT")) {
//			double total = 0;
//			for (int i = 0; i < buylist.size(); i++) {
//				ProductVO order = buylist.get(i);
//				Double price = order.getPrice();
//				Integer quantity = order.getQuantity();
//				total += (price * quantity);
//			}
//
//			String amount = String.valueOf(total);
//			req.setAttribute("amount", amount);
//			String url = "/Checkout.jsp";
//			RequestDispatcher rd = req.getRequestDispatcher(url);
//			rd.forward(req, res);
//		}
	}

	private ProductVO getProduct(HttpServletRequest req) {
		
		String product_no = req.getParameter("product_no");
		String product_name = req.getParameter("product_name");
		String product_price = req.getParameter("product_price");
		String proqty = req.getParameter("proqty");
		String product_remaining = req.getParameter("product_remaining");
		String product_state = req.getParameter("product_state");
		
		ProductVO productVO = new ProductVO();

		productVO.setProduct_no(new Integer(product_no));
		productVO.setProduct_name(product_name);
		productVO.setProduct_price(new Integer(product_price));
		productVO.setProduct_quantity(new Integer(proqty));
		productVO.setProduct_remaining(new Integer(product_remaining));
		productVO.setProduct_state(new Integer(product_state));
		return productVO;
	}
	
}