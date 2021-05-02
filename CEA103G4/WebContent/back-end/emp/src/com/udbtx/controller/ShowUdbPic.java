package com.udbtx.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.admins.model.AdminsService;
import com.admins.model.AdminsVO;
import com.udbtx.model.UdbTxService;
import com.udbtx.model.UdbTxVO;

@WebServlet("/ShowUdbPic")
public class ShowUdbPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/*");
		String po_no = request.getParameter("po_no");
		
		if (po_no != null) {
			UdbTxService udbtxService = new UdbTxService();
			Optional<UdbTxVO> udbtxVO = udbtxService.getOnePoNoPic(po_no);
			
			if (udbtxVO.isPresent()) {
				OutputStream os = response.getOutputStream();
				os.write(udbtxVO.get().getBook_state_pic());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
