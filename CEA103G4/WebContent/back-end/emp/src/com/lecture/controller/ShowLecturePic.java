package com.lecture.controller;


import java.io.IOException;
import java.io.OutputStream;
import java.util.Optional;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.lecture.model.*;

@WebServlet("/ShowLecturePic")
public class ShowLecturePic extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.setContentType("image/*");
		String lc_id = request.getParameter("lc_id");
		
		if (lc_id != null) {
			LectureService lectureService = new LectureService();
			Optional<LectureVO> lectureVO = lectureService.getLecturePicByLcId(lc_id);
			
			if (lectureVO.isPresent()) {
				OutputStream os = response.getOutputStream();
				os.write(lectureVO.get().getLc_pic());
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}