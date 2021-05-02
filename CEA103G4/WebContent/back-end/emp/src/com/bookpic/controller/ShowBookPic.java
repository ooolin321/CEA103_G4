package com.bookpic.controller;

import java.io.ByteArrayInputStream;
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

@WebServlet("/ShowBookPic")
public class ShowBookPic extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/*");
		String bookID = request.getParameter("bookID");
		String bookPicName = request.getParameter("bookPicName");
		Optional<BookPicture> bookPic = null;

		if (bookID != null && bookPicName != null) {
			BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");
			bookPic = bookPicService.getByBookIDAndBookPicName(bookID, bookPicName);
		} else if (bookID != null && bookPicName == null) { // 只請求封面圖
			BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");
			bookPic = bookPicService.getFirstPicByBookID(bookID);
		}

		if (bookPic.isPresent()) {
			writeBytes(response, bookPic.get());
		}

	}

	private void writeBytes(HttpServletResponse response, BookPicture bookPicture) throws IOException {
		byte[] buf = new byte[4 * 1024];
		ByteArrayInputStream bin = new ByteArrayInputStream(bookPicture.getBookPic());
		OutputStream os = response.getOutputStream();
		int len;
		while ((len = bin.read(buf)) != -1) {
			os.write(buf, 0, len);
		}
		bin.close();
		os.flush();
		os.close();
	}

}
