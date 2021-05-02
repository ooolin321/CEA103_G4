package com.category.controller;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;

@WebServlet("/DeleteCategory")
public class DeleteCategory extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryID = request.getParameter("categoryID");
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");

		if (categoryService.getByCategoryID(categoryID).isPresent()) {
			categoryService.deleteCategory(categoryID);
		}

		response.sendRedirect(request.getContextPath() + "/CategoryManagement");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
