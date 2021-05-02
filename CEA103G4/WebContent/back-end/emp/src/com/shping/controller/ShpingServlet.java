package com.shping.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.book.model.Book;
import com.book.model.BookService;
import com.bookpic.model.BookPicService;
import com.bookpic.model.BookPicture;
import com.category.model.CategoryService;
import com.shping.model.Cart;

@WebServlet("/Shopping.html")
public class ShpingServlet extends HttpServlet {
	private static final long serialVersionUID = 06L;

	public ShpingServlet() {
		super();
	}

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		String detailURL = "/front-end/shopping/prddetail.jsp";
		String indexURL = "/front-end/shopping/bookindex.jsp";

		String book_id = req.getParameter("book_id");
		BookService bkSvc = (BookService) getServletContext().getAttribute("bookService");
		BookPicService bkpicSvc = (BookPicService) getServletContext().getAttribute("bookPicService");
		Optional<Book> prddetail = bkSvc.getByBookID(book_id);
		List<BookPicture> bookPiclist = bkpicSvc.getByBookID(book_id);
		if (prddetail.isPresent()) {
			Book book = prddetail.get();
			if (book.getIsSold() == 1) {
				req.setAttribute("prddetail", book);
				req.setAttribute("bookPiclist", bookPiclist);
				req.setAttribute("bookID", book_id);

				// 利用cookie取得/更新近期瀏覽書籍(30本)
				List<Book> recentViewedBooks = bkSvc.getByBookIDList(UpdateRecentViewedBooksCookie(book_id, req, res));
				// 利用近期瀏覽書籍(30本)計算出推薦書籍
				Set<Book> recommBooks = bkSvc.getRecommendedBooks(recentViewedBooks, book_id);
				// 瀏覽次數+1
				bkSvc.UpdateRedisViewCount(book_id);

				CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
				req.setAttribute("recentViewedBooks", recentViewedBooks);
				req.setAttribute("recommBooks", new ArrayList<Book>(recommBooks));
				req.setAttribute("catLevelMap", categoryService.getCatLevelMap(book.getCategoryID()));

				req.getRequestDispatcher(detailURL).forward(req, res);
			} else {
				req.getRequestDispatcher("/front-end/shopping/notSold.jsp").forward(req, res);
			}
		} else {
			req.getRequestDispatcher(indexURL).forward(req, res);
		}

	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		HttpSession session = req.getSession();

		String indexURL = "/front-end/shopping/bookindex.jsp";
		String detailURL = "http://localhost:8081/EA103G4/Shopping.html?book_id=B00000020741";
		String cartURL = "/front-end/shopping/cart.jsp";
		String delURL = "http://localhost:8081/BookShop1014(2202)/front-end/shopping/cart.jsp";
		String payURL = "/front-end/shopping/pay.jsp";
		String whichPage = req.getParameter("whichPage");
		String showAll = req.getParameter("showAll");
		
		
		@SuppressWarnings("unchecked")
		List<Cart> cartlist = (Vector<Cart>) session.getAttribute("shpingcart");
		String action = req.getParameter("action");
		ShpingServlet shping = new ShpingServlet();
		JSONArray careFormJSON = new JSONArray();

		 

		if (!action.equals("BOOKDETAIL")) {
			// 加入購物車
			if (action.equals("ADD") || action.equals("DETAILADD")) {
				Cart cart1 = getPrd(req);

				if (cartlist == null) {
					cartlist = new Vector<Cart>();
					cartlist.add(cart1);
				} else {
					if (cartlist.contains(cart1)) {
						Cart cart2 = cartlist.get(cartlist.indexOf(cart1));
						cart2.setComm_Qty(cart2.getComm_Qty() + cart1.getComm_Qty());
					} else {
						cartlist.add(cart1);
					}
				}
				listToJSON(cartlist);

				}

				String[] getTotal = shping.gettotal(cartlist);
				session.setAttribute("getTotal", getTotal);
				session.setAttribute("shpingcart", cartlist);

				PrintWriter out = res.getWriter();
				out.write(careFormJSON.toString());
				out.flush();
				out.close();

			}
			// 刪除商品
			if (action.contentEquals("DEL")) {
				String del = req.getParameter("del");
				int delindex = Integer.parseInt(del);
				cartlist.remove(delindex);

				String[] getTotal = shping.gettotal(cartlist);
				session.setAttribute("getTotal", getTotal);
				session.setAttribute("shpingcart", cartlist);
				
				PrintWriter out = res.getWriter();
				out.write(careFormJSON.toString());
				out.flush();
				out.close();

				
			}
	}
	private JSONArray listToJSON(List<Cart> cartlist) {
		JSONArray careFormJSON = new JSONArray();
	
		for (Cart cart : cartlist) {
			JSONObject obj = new JSONObject();
			try {
				obj.put("mem_id", cart.getMem_Id());
				obj.put("isbn", cart.getIsbn());
				obj.put("book_id", cart.getBook_Id());
				obj.put("book_name", cart.getBook_Name());
				obj.put("publisher_id", cart.getPublisher_Id());
				obj.put("price", cart.getPrice());
				obj.put("book_bp", cart.getBook_BP());
				obj.put("comm_qty", cart.getComm_Qty());
				careFormJSON.put(obj);
			} catch (JSONException e) {
				throw new RuntimeException("▲Error： [加入JSON失敗!]" + e.getMessage());
			}
		}
		return careFormJSON;
		
	}
	

	private Cart getPrd(HttpServletRequest req) throws ServletException, IOException {
		String mem_Id = req.getParameter("mem_Id");

		String isbn = req.getParameter("isbn");
		String book_Id = req.getParameter("book_Id");
		String book_Name = req.getParameter("book_Name");
		String publisher_Id = req.getParameter("publisher_Id");
		String comm_Qty = req.getParameter("comm_Qty");
		String price = req.getParameter("price");
		String book_BP = req.getParameter("book_BP");

		Cart cart = new Cart();
		try {

			cart.setMem_Id(mem_Id);
			cart.setIsbn(isbn);
			cart.setBook_Id(book_Id);
			cart.setBook_Name(book_Name);
			cart.setPublisher_Id(publisher_Id);
			cart.setPrice(new Double(price));
			cart.setBook_BP(new Double(book_BP));
			cart.setComm_Qty(new Integer(comm_Qty));
		} catch (Exception e) {
			throw new RuntimeException("▲Error： [加入購物車失敗!]" + e.getMessage());
		}
		return cart;
	}

	private String[] gettotal(List<Cart> cartlist) {

		int total = 0;
		int bonus = 0;
		int qty = 0;

		for (int i = 0; i < cartlist.size(); i++) {
			Cart cart = cartlist.get(i);

			double book_BP = cart.getBook_BP();
			double price = cart.getPrice();
			int comm_Qty = cart.getComm_Qty();
			total += (price * comm_Qty);
			bonus += (book_BP * comm_Qty);
			qty += comm_Qty;
		}

		String[] totalArray = new String[3];
		String get_Total = String.valueOf(total);
		String get_Bonus = String.valueOf(bonus);
		String get_Qty = String.valueOf(qty);
		totalArray[0] = get_Total;
		totalArray[1] = get_Bonus;
		totalArray[2] = get_Qty;

		return totalArray;

	}

	private List<String> UpdateRecentViewedBooksCookie(String bookID, HttpServletRequest request,
			HttpServletResponse response) {
		Cookie recentViewedBooksCookie = null;
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if ("recentViewedBookIDs".equals(cookie.getName())) {
					recentViewedBooksCookie = cookie;
				}
			}
		}

		String recentViewedBookIDs = UpdateRecentViewedBookIDs(bookID, recentViewedBooksCookie);
		List<String> bookIDs = Arrays.asList(recentViewedBookIDs.split("_"));

		if (recentViewedBooksCookie != null) {
			recentViewedBooksCookie.setValue(recentViewedBookIDs);
		} else {
			recentViewedBooksCookie = new Cookie("recentViewedBookIDs", recentViewedBookIDs);
		}
		recentViewedBooksCookie.setMaxAge(60 * 60 * 24 * 365); // 365天效期cookie
		response.addCookie(recentViewedBooksCookie);

		return bookIDs;
	}

	private String UpdateRecentViewedBookIDs(String bookID, Cookie cookie) {
		LinkedList<String> bookIDList = new LinkedList<String>();
		StringBuffer recentViewedBookIDs = new StringBuffer();

		if (cookie != null) {
			String bookIDs = cookie.getValue();
			if (bookIDs != null) {
				bookIDList = new LinkedList<String>(Arrays.asList(bookIDs.split("_")));
			}

			// 只保留30個近期瀏覽記錄
			if (bookIDList.size() < 30) {
				// 去除重複
				if (bookIDList.contains(bookID)) {
					bookIDList.remove(bookID);
				}
			} else {
				// 去除重複
				if (bookIDList.contains(bookID)) {
					bookIDList.remove(bookID);
				} else {
					bookIDList.removeLast();
				}
			}
		}

		bookIDList.addFirst(bookID);
		bookIDList.forEach(viewedBookID -> {
			recentViewedBookIDs.append(viewedBookID).append("_");
		});

		return recentViewedBookIDs.toString();
	}

}
