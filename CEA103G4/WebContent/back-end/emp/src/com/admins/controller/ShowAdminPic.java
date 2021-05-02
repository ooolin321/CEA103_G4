package com.admins.controller;


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

@WebServlet("/ShowAdminPic")
public class ShowAdminPic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/*");
		String admin_id = request.getParameter("admin_id");
		
		if (admin_id != null) {
			AdminsService adminsService = new AdminsService();
			Optional<AdminsVO> adminsVO = adminsService.getAdminPicByAdminId(admin_id);
			
			if (adminsVO.isPresent()) {
				OutputStream os = response.getOutputStream();
				os.write(adminsVO.get().getAdmin_pic());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
