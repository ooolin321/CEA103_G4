package com.promo.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;
import com.promo.model.Promo;
import com.promo.model.PromoService;

import tools.StrUtil;

@WebServlet("/PromoManagement")
public class PromoManagement extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		String whichPage = request.getParameter("whichPage");
		String showAll = request.getParameter("showAll");

		// 換頁操作(session中保有前次的查詢結果和本次請求第幾頁)
		if (session != null) {
			if (session.getAttribute("promotions") != null && (whichPage != null || showAll != null)) {
				request.setAttribute("whichPage", whichPage);
				request.setAttribute("showAll", showAll);
				request.setAttribute("promotions", session.getAttribute("promotions"));
			}
		}
		request.getRequestDispatcher("/back-end/jsp_PromoManagement/PromoManagement.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String term;

		// 使用搜尋
		if ("getAdvSearch".equals(request.getParameter("action"))) {
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			Map<String, String> map = new HashMap<String, String>();
			map.put("promoName", StrUtil.tryToTrim(request.getParameter("promoName")));
			map.put("promoStartTime", request.getParameter("promoStartTime"));
			map.put("promoEndTime", request.getParameter("promoEndTime"));
			List<Promo> promotionsTemp = promoService.getByAdvSearch(map);
			List<Promo> promotions = new ArrayList<Promo>();

			String isValid = request.getParameter("isValid");

			if (isValid != null) {
				if ("1".equals(isValid)) {
					promotionsTemp.forEach(promo -> {
						if (promo.isValid()) {
							promotions.add(promo);
						}
					});
					request.setAttribute("promotions", promotions);
				} else if ("0".equals(isValid)) {
					promotionsTemp.forEach(promo -> {
						if (!promo.isValid()) {
							promotions.add(promo);
						}
					});
					request.setAttribute("promotions", promotions);
				} else {
					request.setAttribute("promotions", promotionsTemp);
				}
			}
			request.getRequestDispatcher("/back-end/jsp_PromoManagement/PromoManagement.jsp").forward(request,
					response);
			// AJAX自動補字
		} else if ((term = request.getParameter("term")) != null) {
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			response.setContentType("application/json");
			List<String> promoNames = promoService.getPromoNameLike(term);
			String searchList = new Gson().toJson(promoNames);
			response.getWriter().write(searchList);
		}

	}

}
