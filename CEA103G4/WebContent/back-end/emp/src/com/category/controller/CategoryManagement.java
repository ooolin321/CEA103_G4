package com.category.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.Category;
import com.category.model.CategoryService;
import com.google.gson.Gson;

import tools.StrUtil;

@WebServlet("/CategoryManagement")
public class CategoryManagement extends HttpServlet {
	private static final String CATEGORY_REX = "([^\\s,]+,[^\\s,]+,[^\\s,]+)|([^\\s,]+,[^\\s,]+)|([^\\s,]+)";

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
		List<Category> categories = categoryService.getAll();
		List<String> categoryIDs = new ArrayList<String>();
		categories.forEach(cat -> categoryIDs.add(cat.getCategoryID()));

		request.setAttribute("categories", categories);
		request.setAttribute("categoryCountMap", categoryService.getBookNumByCategoryIDs(categoryIDs));
		request.getRequestDispatcher("/back-end/jsp_BookManagement/CategoryManagement.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String categoryName = StrUtil.tryToTrim(request.getParameter("categoryName"));
		String checkCategoryName = request.getParameter("checkCategoryName");

		// 新增類別
		if (categoryName != null) {
			CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
			categoryService.addCategory(categoryName);
			// 重新導回自己
			response.sendRedirect(request.getContextPath() + "/CategoryManagement");
		} // AJAX檢查
		else if (checkCategoryName != null && !"".equals(checkCategoryName)) {
			// 去除空白
			checkCategoryName = checkCategoryName.replaceAll("\\s+", "");
			response.setContentType("text/html;charset=UTF-8");

			if (checkCategoryName.matches(CATEGORY_REX)) {
				CategoryService categoryService = (CategoryService) getServletContext().getAttribute("categoryService");
				// 檢查categoryName是否已存在
				if (!categoryService.getByCategoryName(checkCategoryName).isPresent()) {
					response.getWriter().write("OK");
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("類別名稱已存在資料庫，不得重複");
				}
			} else {
				response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
				response.getWriter().write("類別名稱與層級有誤，層級最多三層，且分隔逗號之間需有非空白文字");
			}
		}

	}

}
