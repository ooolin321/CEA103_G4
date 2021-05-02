package com.promo.controller;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.category.model.CategoryService;
import com.promo.model.Promo;
import com.promo.model.PromoService;

import tools.StrUtil;

@WebServlet("/AddPromo")
public class AddPromo extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.getRequestDispatcher("/back-end/jsp_PromoManagement/AddPromo.jsp").forward(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String checkPromoName = StrUtil.tryToTrim(request.getParameter("checkPromoName"));
		String promoName = StrUtil.tryToTrim(request.getParameter("promoName"));

		// AJAX檢查
		if (checkPromoName != null && !"".equals(checkPromoName)) {
			response.setContentType("text/html;charset=UTF-8");
			String promoID = request.getParameter("promoID");
			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			Optional<Promo> checkPromo = promoService.getByPromoNameUnique(checkPromoName);

			// 更新，判斷promoName是否和其他的promoName衝突
			if (!"".equals(promoID) && promoID != null) {
				if (checkPromo.isPresent() && promoID.equals(checkPromo.get().getPromoID())) {
					// 檢查促銷時間是否矛盾
					checkPromoTime(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("名稱與其他促銷事件重複");
				}
			// 新增
			} else {
				// 檢查promoName是否已存在
				if (!checkPromo.isPresent()) {
					// 檢查促銷時間是否矛盾
					checkPromoTime(request, response);
				} else {
					response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
					response.getWriter().write("促銷事件名稱已存在資料庫，不得重複");
				}
			}

		// 送出新增
		} else if (promoName != null) {

			PromoService promoService = (PromoService) getServletContext().getAttribute("promoService");
			String promoStartTimeStr = request.getParameter("promoStartTime");
			String promoEndTimeStr = request.getParameter("promoEndTime");

			// 處理JS傳來的時間格式
			if (promoStartTimeStr != null && promoEndTimeStr != null
					&& !promoService.getByPromoNameUnique(promoName).isPresent()) {
				Timestamp promoStartTime = StrUtil.toTimestamp(promoStartTimeStr);
				Timestamp promoEndTime = StrUtil.toTimestamp(promoEndTimeStr);
				promoService.addPromo(promoName, promoStartTime, promoEndTime);
			}

			Optional<Promo> newPromo = promoService.getByPromoNameUnique(promoName);

			if (newPromo.isPresent()) {
				response.sendRedirect(
						request.getContextPath() + "/AddPromoDetails?promoID=" + newPromo.get().getPromoID());
			}
		// 非送出新增且AJAX檢查名稱trim後為null或空字串
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("促銷事件名稱不得為空");
		}
	}

	private void checkPromoTime(HttpServletRequest request, HttpServletResponse response) throws IOException {
		Timestamp promoStartTime = StrUtil.toTimestamp(request.getParameter("checkPromoStartTime"));
		Timestamp promoEndTime = StrUtil.toTimestamp(request.getParameter("checkPromoEndTime"));
		// 檢查促銷時間是否矛盾
		if (promoStartTime.before(promoEndTime)) {
			response.getWriter().write("OK");
		} else {
			response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			response.getWriter().write("促銷事件結束時間必須晚於開始時間");
		}
	}
}
