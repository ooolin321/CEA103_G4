package com.promodetail.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.book.model.Book;
import com.book.model.BookService;
import com.category.model.Category;
import com.category.model.CategoryService;
import com.promo.model.PromoService;
import com.promodetail.model.PromoDetail;
import com.promodetail.model.PromoDetailService;

@WebServlet("/AddPromoDetails")
public class AddPromoDetails extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String promoID = request.getParameter("promoID");
		String whichPage = request.getParameter("whichPage");
		String showAll = request.getParameter("showAll");
		HttpSession session = request.getSession();
		String url = "/back-end/jsp_PromoManagement/AddPromoDetails.jsp";
		
		// 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
		if (session.getAttribute("books") != null && (whichPage != null || showAll != null)) {
			request.setAttribute("books", session.getAttribute("books"));
			request.setAttribute("promo", session.getAttribute("promo"));
			request.setAttribute("promoID", promoID);
			request.setAttribute("whichPage", whichPage);
			request.setAttribute("showAll", showAll);
			request.setAttribute("categories", session.getAttribute("categories"));
		} else {
			// 首次拜訪，設定categories供搜尋使用
			CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
			List<Category> categories = categoryService.getAll();
			request.setAttribute("categories", categories);

			// 設定promo資訊
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			request.setAttribute("promo", promoService.getByPromoID(promoID).get());
		}
		
		// 全站加入促銷
		if ("AddAllToPromo".equals(request.getParameter("action"))) {
			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			PromoDetailService promoDetailService = (PromoDetailService) getServletContext().getAttribute("promoDetailService");
			List<PromoDetail> promoDetails = new ArrayList<PromoDetail>();
			bookService.getAll().forEach(book ->{
				PromoDetail promoDetail = new PromoDetail(promoID, book.getBookID(), 0, 0);
				promoDetails.add(promoDetail);
			});;
			promoDetailService.addPromoDetailBatch(promoDetails);
			url = "/ShowPromoDetails?promoID=" + promoID;
		}

		request.getRequestDispatcher(url).forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String action = request.getParameter("action");
		String promoID = request.getParameter("promoID");

		// 新增操作，完成後導向UpdatePromoDetails
		if ("AddToPromo".equals(action)) {
			String bookList = request.getParameter("bookList");
			if (bookList != null) {
				List<String> bookIDs = Arrays.asList(bookList.split(","));
				List<PromoDetail> promoDetails = new ArrayList<PromoDetail>();

				PromoDetailService promoDetailService = (PromoDetailService) getServletContext()
						.getAttribute("promoDetailService");
				bookIDs.forEach(bookID -> {
					if (bookID.length() == 12) { // 檢查是否為正常的書名
						// 預設discount和bpPercent皆為0
						PromoDetail promoDetail = new PromoDetail(promoID, bookID, 0, 0);
						promoDetails.add(promoDetail);
					}
				});
				// 全部設定完之後批量新增，由ajax重導到ShowPromoDetails
				promoDetailService.addPromoDetailBatch(promoDetails);
			}
		}

		// 使用搜尋
		if ("getAdvSearch".equals(action)) {
			// 頁面必要訊息
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
			List<Category> categories = categoryService.getAll();
			request.setAttribute("categories", categories);
			request.setAttribute("promoID", promoID);
			request.setAttribute("promo", promoService.getByPromoID(promoID).get());

			BookService bookService = (BookService) getServletContext().getAttribute("bookService");
			List<Book> books = bookService.advSearchByRequest(request, categoryService, false);

			request.setAttribute("books", books);
			request.getRequestDispatcher("/back-end/jsp_PromoManagement/AddPromoDetails.jsp").forward(request,
					response);
		}
	}

}
