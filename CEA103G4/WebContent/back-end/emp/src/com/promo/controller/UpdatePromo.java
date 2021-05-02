package com.promo.controller;

import java.io.IOException;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.promo.model.PromoService;
import tools.StrUtil;

@WebServlet("/UpdatePromo")
public class UpdatePromo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String promoID = request.getParameter("promoID");
		PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
		request.setAttribute("promo", promoService.getByPromoID(promoID).get());
		request.getRequestDispatcher("/back-end/jsp_PromoManagement/UpdatePromo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String promoID = request.getParameter("promoID");
		String promoName = StrUtil.tryToTrim(request.getParameter("promoName"));

		Timestamp promoStartTime = StrUtil.toTimestamp(request.getParameter("promoStartTime"));
		Timestamp promoEndTime = StrUtil.toTimestamp(request.getParameter("promoEndTime"));
		PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
		promoService.updatePromo(promoID, promoName, promoStartTime, promoEndTime);
		response.sendRedirect(request.getContextPath() + "/ShowPromoDetails?promoID=" + promoID);
	}


}
