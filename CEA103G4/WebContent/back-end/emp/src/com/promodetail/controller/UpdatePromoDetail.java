package com.promodetail.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.model.BookService;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetailService;

@WebServlet("/UpdatePromoDetail")
public class UpdatePromoDetail extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String promoID = request.getParameter("promoID");
		String bookID = request.getParameter("bookID");

		PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
				.getAttribute("promoDetailService");
		PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");

		request.setAttribute("promo", promoService.getByPromoID(promoID).get());
		request.setAttribute("promoDetail", promoDetailService.getByPromoIDAndBookID(promoID, bookID).get());
		request.setAttribute("book", bookService.getByBookID(bookID).get());

		request.getRequestDispatcher("/back-end/jsp_PromoManagement/UpdatePromoDetail.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String promoID = request.getParameter("promoID");
		String bookID = request.getParameter("bookID");
		String discountStr = request.getParameter("discount");
		String bpPercentStr = request.getParameter("bpPercent");
		int discount = Integer.parseInt(discountStr);
		int bpPercent = Integer.parseInt(bpPercentStr);

		PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
				.getAttribute("promoDetailService");
		promoDetailService.updatePromoDetail(promoID, bookID, discount, bpPercent);
		response.sendRedirect(request.getContextPath() + "/ShowPromoDetails?promoID=" + promoID);
	}

}
