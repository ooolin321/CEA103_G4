package com.Far.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.Fa.model.*;
import com.Far.model.*;
import com.Fm.model.*;

@WebServlet("/farServlet")
public class FarServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		
		//轉交到新增文章檢舉頁面
		if("getOne_Fa_Report".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//取得 文章PK
				String faId = new String(req.getParameter("faId"));
				
				FaVO faVO = new FaVO();
				faVO.setFaId(faId);
				//轉交到新增檢舉頁面
				req.setAttribute("faVO", faVO);
				String url = "/front-end/forum/addFarPage.jsp";
				RequestDispatcher successView = req
						.getRequestDispatcher(url);
				successView.forward(req, res);
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/forumIndex.jsp");
				failureView.forward(req, res);
			}
		}
		
		//新增文章檢舉
		if("insert".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				String faId = req.getParameter("faId");
				String memId = req.getParameter("memId");
				//後面改session取 memId 登入的會員去做檢舉
				String farContent = req.getParameter("farContent");
				if(farContent == null || farContent.trim().length() == 0) {
					errorMsgs.add("檢舉內容請勿空白!");
				}
				FarVO farVO = new FarVO();
				farVO.setFaId(faId);
				farVO.setMemId(memId);
				farVO.setFarContent(farContent);
				
				FaVO faVO = new FaVO();
				
				if(!errorMsgs.isEmpty()) {
					req.setAttribute("farVO", farVO);
					RequestDispatcher failureView = req
							.getRequestDispatcher("/front-end/forum/addFarPage.jsp");
					//錯誤處理轉交到 新增檢舉頁面
					failureView.forward(req, res);
					return; //中斷程式
				}
				
				FarService farSvc = new FarService();
				farVO = farSvc.addFar(faId, memId, farContent);
				
				FaService faSvc = new FaService();
				faVO = faSvc.getOneFa(faId);
				
				FmService fmSvc = new FmService();
				List<FmVO> list = fmSvc.getOneFAFm(faId);
				
				req.setAttribute("faVO",faVO);
				req.setAttribute("list", list);
				
				String url = "/front-end/forum/forumPage.jsp"; // 轉交到原本觀看的文章頁面
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			} catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/front-end/forum/addFarPage.jsp");
				failureView.forward(req, res);
			}		
			
		}
		
		//審核是否通過檢舉
		if("judge".equals(action)) {
			
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			try {
				String farId = req.getParameter("farId");
				String farStatus = req.getParameter("farStatus");
				String faId = req.getParameter("faId");
				String adminId = req.getParameter("adminId");
				//取得文章下的留言
				FmService fmSvc = new FmService();
				List<FmVO> list = fmSvc.getOneFAFm(faId);
				//更改檢舉文章狀態
				FaVO faVO = new FaVO();
				faVO.setFaId(faId);
				//更改文章檢舉狀態
				FarService farSvc = new FarService();
				farSvc.judgeFar(Integer.parseInt(farStatus),adminId,farId);
				
				//如果檢舉文章狀態為 1(通過) 更改文章狀態為 1(下架) 文章下留言狀態 改為1(下架)
				FaService faSvc = new FaService();
				if(Integer.parseInt(farStatus) == 1) {
					faSvc.deleteFa(faId);
					for(FmVO fmVO : list) {
						fmSvc.logout_Fm(fmVO.getFmId());
					}
				}
				
				String url = "/back-end/forum/forumBack_forums.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);
				
			}catch(Exception e){
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/forum/forumBack_forums.jsp");
				failureView.forward(req, res);
			}	
			
			
		}
		
		
	}

}
