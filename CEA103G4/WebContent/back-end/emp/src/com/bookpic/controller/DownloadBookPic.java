package com.bookpic.controller;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookpic.model.BookPicService;
import com.bookpic.model.BookPicture;

@WebServlet("/DownloadBookPic")
public class DownloadBookPic extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookID = request.getParameter("bookID");
		String bookPicName = request.getParameter("bookPicName");
		BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");

		Optional<BookPicture> bookPicture = bookPicService.getByBookIDAndBookPicName(bookID, bookPicName);
		if (bookPicture.isPresent()) {
			response.setContentType("application/octet-stream");
			response.setHeader("Content-disposition", "attachment; filename=\"" + bookPicName + "\"");
			OutputStream out = response.getOutputStream();
			out.write(bookPicture.get().getBookPic());
		}
	}
}
