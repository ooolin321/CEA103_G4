package com.rev.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONArray;
import org.json.JSONObject;

import com.mem.model.MemService;
import com.rev.model.RevService;
import com.rev.model.RevVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

@WebServlet("/rev/rev.do")
public class RevServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
 
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		if ("insert".equals(action)) {
			
			RevVO revVO = new RevVO();
			
			String revContent = req.getParameter("revContent").trim();
		
			String memId = req.getParameter("memId");
			
			String bookId = req.getParameter("bookId");
	
			Integer rating = new Integer(req.getParameter("rating"));
			System.out.println(rating);
			RevService revSvc = new RevService();
			revVO = revSvc.addRev(revContent, memId, bookId, rating);	
			
			MemService memSvc = new MemService();
			revVO.setMem_name(memSvc.getOneMem(revVO.getMem_id()).getMem_name());
			
			DateFormat sdf = new SimpleDateFormat("MMMM d, yyyy, HH:mm");
			
			Long time = revVO.getRev_date().getTime();
			revVO.setTimeForJson(sdf.format(time));
			System.out.println(sdf.format(time));
			String obj = new JSONObject(revVO).toString();
			System.out.println(obj);
			PrintWriter out = res.getWriter();
			out.write(obj);
			out.flush();
			out.close();
			
			
//			String url = "/front-end/shopping/prddetail.jsp";
//			RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後轉交insertSuccess.jsp
//			successView.forward(req, res);
		}
		
		if("getByBookId".equals(action)) {
			
			RevService revSvc = new RevService();
			MemService memSvc = new MemService();
			String bookId = req.getParameter("bookId");
			System.out.println(bookId);
			List<RevVO> list = revSvc.getByBookId(bookId);
			
			for(RevVO revVO : list) 
				revVO.setMem_name(memSvc.getOneMem(revVO.getMem_id()).getMem_name());
			
			
			String listObj = new JSONArray(list).toString();
			System.out.println(listObj);
			PrintWriter out = res.getWriter();
			out.write(listObj);
			out.flush();
			out.close();
		}
	}

}
