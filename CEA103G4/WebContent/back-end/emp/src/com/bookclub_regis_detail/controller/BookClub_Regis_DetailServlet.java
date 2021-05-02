package com.bookclub_regis_detail.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.bookclub.model.BookClubJNDIDAO;
import com.bookclub.model.BookClubService;
import com.bookclub.model.BookClubVO;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailJNDIDAO;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailService;
import com.bookclub_regis_detail.model.BookClub_Regis_DetailVO;
import com.mem.model.MemService;
import com.mem.model.MemVO;
import com.question.model.QuestionService;
import com.question.model.QuestionVO;
import com.questionnair_answer.model.Questionnair_AnswerService;
import com.questionnair_answer.model.Questionnair_AnswerVO;
import com.util.MailService;
import com.util.ReadPic;

public class BookClub_Regis_DetailServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
//		=========取得請求參數
		String action = req.getParameter("action");

//		===============================================拿到全部資料========================================================
		if ("getAll".equals(action)) {
			BookClub_Regis_DetailJNDIDAO dao = new BookClub_Regis_DetailJNDIDAO();
			List<BookClub_Regis_DetailVO> list = dao.getAll();
		}
//		===============================================拿到全部資料========================================================
//		===============================================刪除==============================================================
		if ("delete".equals(action)) {// 來自刪除按鈕
			try {
				/********* 1.接收請求參數 ******/
				String brd_id = req.getParameter("brd_id");
				/********* 2.取得資料 ******/
				BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();
				bookClub_Regis_DetailSvc.delete(brd_id);
				/****************** 3.新增完成,準備轉交(Send the Success view) **********/
				String url = "/back-end/bookclub_regis_detail/listAllBookClub_Regis_Detail.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);
				successView.forward(req, res);

			} catch (Exception e) {
				System.out.println("刪除出問題啦");
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/bookclub_regis_detail/listAllBookClub_Regis_Detail.jsp");
				failureView.forward(req, res);
			}
		}

		/*********************** 新增 *************************/
		if ("insert".equals(action)) {
			Map<String, String> situation = new LinkedHashMap<String, String>();
			req.setAttribute("situation", situation);
				String requestURL = req.getParameter("requestURL");
				/*********************** 問卷回答 ************************/
				String[] qa_data = req.getParameterValues("qa_data");

				/*********************** 會員編號 *************************/
				String mem_id = req.getParameter("mem_id");

				/*********************** 讀書會編號 *************************/
				String bc_id = req.getParameter("bc_id").trim();

				// 資料放入blogVO
				BookClub_Regis_DetailVO bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
				Questionnair_AnswerVO questionnair_AnswerVO = new Questionnair_AnswerVO();
				BookClubVO bookClubVO = new BookClubVO();
				BookClubService bookClubSvc = new BookClubService();
				bookClubVO = bookClubSvc.getOneBookClub(bc_id);
				req.setAttribute("listOneBookClub", bookClubVO);
				
//				如上現已滿，則不能加入，轉交回瀏覽
				if(bookClubVO.getBc_comfirm_peo() >= bookClubVO.getBc_peo_upper_limit()) {
					situation.put("isFull", "人數已滿");
					RequestDispatcher rd = req.getRequestDispatcher(requestURL);
					rd.forward(req, res);
					return;
				}
				
				
				
				/*************************** 2.開始新增資料 ***************************************/
			try {
				
				BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();
				Questionnair_AnswerService questionnair_AnswerSvc = new Questionnair_AnswerService();
				
				// 讀書會報名明細資料
				bookClub_Regis_DetailVO = bookClub_Regis_DetailSvc.addBookClub_Regis_Detail(bc_id, mem_id);
				// 問卷回答資料
				String brd_id = bookClub_Regis_DetailVO.getBrd_id();// 取得剛剛新增之讀書會報名明細編號(自增主鍵)
				String[] bcq_id = req.getParameterValues("bcq_id");
				
				if (qa_data != null) {
					for (int i = 0; i < qa_data.length; i++) {
						questionnair_AnswerVO = questionnair_AnswerSvc.addQuestionnair_Answer(brd_id, bcq_id[i],qa_data[i]);
					}
				}
				// 更新報名人數
				Integer bc_comfirm_peo = bookClubVO.getBc_comfirm_peo() + 1;
				bookClubSvc.updateComfirmPeo(bc_comfirm_peo, bc_id);
				/******************
				 * 3.新增完成,準備轉交(Send the Success view)
				 ****************************/
				situation.put("success", "通過");
				RequestDispatcher successView = req.getRequestDispatcher(requestURL);
				successView.forward(req, res);
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				//重複報名
				situation.put("registered", "registered");
				RequestDispatcher failureView = req.getRequestDispatcher(requestURL);
				failureView.forward(req, res);
				
			}
		}
//		===============================================刪除========================================================
//		====================================================審核區塊開始========================================================
		if ("verify".equals(action)) {
			String bc_id = req.getParameter("bc_id");
			String verify = req.getParameter("verify");
			String mem_id = req.getParameter("mem_id");
			
			BookClubService bookClubSvc = new BookClubService();
			BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();

			if ("Pass".equals(verify)) {
				bookClub_Regis_DetailSvc.update_Status(bc_id, mem_id, new Integer(2));
				BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);
				
				req.setAttribute("listOneBookClub", bookClubVO);
				req.setAttribute("verifySuccess", "verifySuccess");
				

				RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/listOneBookClub.jsp");
				rd.forward(req, res);
				return;
			}

			if ("fail".equals(verify)) {
				bookClub_Regis_DetailSvc.update_Status(bc_id, mem_id, new Integer(3));
				BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);

				Integer bc_comfirm_peo = bookClubVO.getBc_comfirm_peo() - 1;
				bookClubSvc.updateComfirmPeo(bc_comfirm_peo, bc_id);

				req.setAttribute("listOneBookClub", bookClubVO);
				req.setAttribute("verifySuccess", "");

				RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/listOneBookClub.jsp");
				rd.forward(req, res);
				return;
			}
		}
//		====================================================審核區塊結束========================================================
//		====================================================退出區塊開始========================================================
		if("quit".equals(action)) {
			String bc_id = req.getParameter("bc_id");
			String mem_id = req.getParameter("mem_id");
			System.out.println(mem_id);
			
			
			Map<String, String> situation = new HashMap<String, String>();
			req.setAttribute("situation", situation);
			
			BookClub_Regis_DetailService bookClub_Regis_DetailSvc = new BookClub_Regis_DetailService();
			BookClubService bookClubSvc = new BookClubService();
			BookClubVO bookClubVO = bookClubSvc.getOneBookClub(bc_id);
		
//			退出此行程
			bookClub_Regis_DetailSvc.update_Status(bc_id, mem_id, new Integer(4));
			situation.put("quit", "success");
//			此行程目前人數減一
			Integer bc_comfirm_peo = bookClubVO.getBc_comfirm_peo() - 1;
			bookClubSvc.updateComfirmPeo(bc_comfirm_peo, bc_id);
			
			
			RequestDispatcher rd = req.getRequestDispatcher("/front-end/bookclub/review_BookClub.jsp");
			rd.forward(req, res);
			return;
		}
//		====================================================退出區塊結束========================================================	

	}

}
