package com.revreport.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.rev.model.RevService;
import com.revreport.model.RevReportService;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

@WebServlet("/rev/revreport.do")
public class RevReportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doGet(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		//新增書評檢舉
		if ("insert".equals(action)) {
			
			String revReportReason = req.getParameter("reason").trim();
			String memId = req.getParameter("memId");
			String revId = req.getParameter("revId");

			RevReportService revReportSvc = new RevReportService();
			revReportSvc.addRevReport(revReportReason, revId, memId);
			
		}
		
		//後台審核檢舉
		if("updateStatus".equals(action)) {
			String revRepId = req.getParameter("rev_rep_id");
			Integer revRepStatus = new Integer(req.getParameter("rev_rep_status"));
			
			RevReportService revReportSvc = new RevReportService();
			revReportSvc.updateStatus(revRepStatus, revRepId);
			
			RequestDispatcher successView = req.getRequestDispatcher("/back-end/forum/reviewBack.jsp");
			successView.forward(req, res);
		}
	}

}
