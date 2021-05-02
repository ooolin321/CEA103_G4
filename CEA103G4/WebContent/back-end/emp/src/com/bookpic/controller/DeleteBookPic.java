package com.bookpic.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.book.model.Book;
import com.book.model.BookService;
import com.bookpic.model.BookPicService;
import com.bookpic.model.BookPicture;

@WebServlet("/DeleteBookPic")
public class DeleteBookPic extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookID = request.getParameter("bookID");
		String bookPicName = request.getParameter("bookPicName");
		BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");
		BookService bookService = (BookService) getServletContext().getAttribute("bookService");
		Optional<BookPicture> bookPic = bookPicService.getByBookIDAndBookPicName(bookID, bookPicName);
		Optional<Book> book = bookService.getByBookID(bookID);

		if (bookPic.isPresent()) {
			bookPicService.deletePicByBookIDAndPicName(bookID, bookPicName);
			// 為了重導回去，必須重新查詢書籍資訊並Set
			request.setAttribute("book", book.get());
			request.setAttribute("bookPics", bookPicService.getByBookID(bookID));
		}

//		request.getRequestDispatcher("/UpdateBookPic?bookID=" + bookID).forward(request,
//				response);
		response.sendRedirect(request.getContextPath() + "/UpdateBookPic?bookID=" + bookID);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
