package com.mem.controller;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mem.model.MemService;
import com.mem.model.MemVO;


@WebServlet("/mem/MemPic")
public class MemPic extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		res.setContentType("image/*");
		String mem_id = req.getParameter("mem_id");
		ServletOutputStream out = res.getOutputStream();
		
		try {
			MemService memSvc = new MemService();
			MemVO memVO = memSvc.getOneMem(mem_id);
			out.write(memVO.getMem_pic());
			out.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
