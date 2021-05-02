package com.category.controller;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.bookpic.model.BookPicService;
import com.category.model.Category;
import com.category.model.CategoryService;

import tools.StrUtil;

@WebServlet("/UpdateCategory")
public class UpdateCategory extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryID = request.getParameter("categoryID");
		// 重讀資料庫裡的類別資料
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");

		request.setAttribute("category", categoryService.getByCategoryID(categoryID).get());
		request.getRequestDispatcher("/back-end/jsp_BookManagement/UpdateCategory.jsp").forward(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryID = request.getParameter("categoryID");
		String categoryName = StrUtil.tryToTrim(request.getParameter("categoryName"));
		String parentCategoryID = null;
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
		Optional<Category> parentCategory = categoryService.getParentCategoryByChildCategoryName(categoryName);
		if (parentCategory.isPresent()) {
			parentCategoryID = parentCategory.get().getCategoryID();
		}
		categoryService.updateCategory(categoryID, categoryName, parentCategoryID);
		// 重新導回類別管理主頁
		response.sendRedirect(request.getContextPath() + "/CategoryManagement");

	}

}
