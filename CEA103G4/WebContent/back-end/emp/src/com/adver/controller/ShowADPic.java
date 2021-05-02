package com.adver.controller;

import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.adver.model.*;

@WebServlet("/ShowADPic")
public class ShowADPic extends HttpServlet {

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/*");
		String adID = request.getParameter("ad_id");

		if (adID != null) { // 只請求封面圖
			AdService adService = new AdService();
			AdVO ad = adService.getOne(adID);
			if (ad != null) {
				OutputStream os = response.getOutputStream();
				os.write(ad.getAd_image());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
