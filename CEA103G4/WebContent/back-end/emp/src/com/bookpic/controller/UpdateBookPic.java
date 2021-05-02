package com.bookpic.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookpic.model.BookPicService;
import com.bookpic.model.BookPicture;

@WebServlet("/UpdateBookPic")
@MultipartConfig
public class UpdateBookPic extends HttpServlet {
	private final Pattern fileNameRegex = Pattern.compile("filename=\"(.*)\"");

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookID = request.getParameter("bookID");
		// 重讀資料庫裡的圖片
		BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");
		List<BookPicture> bookPics = bookPicService.getByBookID(bookID);

		if (bookPics.size() == 1) {
			// 查到B00000000001的尚無圖片則不給顯示、編輯
			if ("B00000000001".equals(bookPics.get(0).getBookID())) {
				bookPics = null;
			}
		}
		request.setAttribute("bookPics", bookPics);
		request.setAttribute("bookID", bookID);
		request.getRequestDispatcher("/back-end/jsp_BookManagement/UpdateBookPic.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String bookID = request.getParameter("bookID");
		// 讀取上傳的圖檔
		List<Part> parts = (List<Part>) request.getParts();
		BookPicService bookPicService = (BookPicService) getServletContext().getAttribute("bookPicService");
		for (Part part : parts) {
			if ("image/jpeg".equals(part.getContentType())) {
				String filename = getSubmittedFileName(part);
				byte[] bytes = getBytes(part);
				bookPicService.addBookPic(bookID, filename, bytes);
			}
		}
		// 重新導回自己
		response.sendRedirect(request.getContextPath() + "/UpdateBookPic?bookID=" + bookID);
	}

	private byte[] getBytes(Part part) throws IOException {
		try (InputStream in = part.getInputStream(); ByteArrayOutputStream out = new ByteArrayOutputStream()) {
			byte[] buffer = new byte[1024];
			int length = -1;
			while ((length = in.read(buffer)) != -1) {
				out.write(buffer, 0, length);
			}
			return out.toByteArray();
		}
	}

	private String getSubmittedFileName(Part part) {
		String header = part.getHeader("Content-Disposition"); // upload request的part的header參照CH03-20
		Matcher matcher = fileNameRegex.matcher(header);
		matcher.find();

		String filename = matcher.group(1);
		if (filename.contains("\\")) {
			return filename.substring(filename.lastIndexOf("\\") + 1);
		}
		return filename;
	}

}
