package com.promodetail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.model.Book;
import com.book.model.BookService;
import com.promo.model.Promo;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetail;
import com.promodetail.model.PromoDetailService;

@WebServlet("/ShowPromoDetails")
public class ShowPromoDetails extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String promoID = request.getParameter("promoID");

		if ("RemoveAllFromPromo".equals(request.getParameter("action"))) {
			PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
					.getAttribute("promoDetailService");
			promoDetailService.deletePDByPromoID(promoID);
		}

		// 從管理促銷事件頁面首次拜訪，而非從換頁操作
		if (promoID != null) {
			PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
					.getAttribute("promoDetailService");
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<PromoDetail> promoDetails = promoDetailService.getByPromoID(promoID);
			List<Book> promoBooks = bookService.getByPromoID(promoID, false);

			// 將get表單傳來的隱藏參數promoID轉發給下一個頁面，
			request.setAttribute("promo", promoService.getByPromoID(promoID).get());
			request.setAttribute("promoDetails", promoDetails);
			request.setAttribute("promoBooks", promoBooks);

		} else {
			HttpSession session = request.getSession();
			String whichPage = request.getParameter("whichPage");
			String showAll = request.getParameter("showAll");

			// 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
			if (session != null) {
				if (session.getAttribute("books") != null && (whichPage != null || showAll != null)) {
					request.setAttribute("whichPage", whichPage);
					request.setAttribute("showAll", showAll);
					request.setAttribute("promo", session.getAttribute("promo"));
					request.setAttribute("promoDetails", session.getAttribute("promoDetails"));
					request.setAttribute("promoBooks", session.getAttribute("books"));
				}
			}
		}

		request.getRequestDispatcher("/back-end/jsp_PromoManagement/ShowPromoDetails.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String promoID = request.getParameter("promoID");

		// 將選中的書移出此促銷事件
		if ("removeFromPromo".equals(action)) {
			String bookList = request.getParameter("bookList");
			if (bookList != null) {
				List<String> bookIDs = Arrays.asList(bookList.split(","));
				PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
						.getAttribute("promoDetailService");
				promoDetailService.deleteByPromoIDAndBookIDList(promoID, bookIDs);
			}
		}

		// 統一更新此促銷事件全部的折扣、紅利%數
		if ("UpdatePromoDetails".equals(action)) {
			PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
					.getAttribute("promoDetailService");

			String discountStr = request.getParameter("discount");
			String bpPercentStr = request.getParameter("bpPercent");
			int discount = Integer.parseInt(discountStr);
			int bpPercent = Integer.parseInt(bpPercentStr);

			List<PromoDetail> promoDetails = promoDetailService.getByPromoID(promoID);
			promoDetails.forEach(pd -> {
				pd.setBpPercent(bpPercent);
				pd.setDiscount(discount);
			});

			promoDetailService.updatePromoDetailBatch(promoDetails);
			response.sendRedirect(request.getContextPath() + "/ShowPromoDetails?promoID=" + promoID);
		}
	}
}
