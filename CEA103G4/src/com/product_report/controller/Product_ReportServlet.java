package com.product_report.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.product_report.model.Product_ReportService;
import com.product_report.model.Product_ReportVO;

public class Product_ReportServlet {

	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doPost(req, res);
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
				
		
		if ("getOne_For_Update".equals(action)) { // 來自listAllProduct_Report.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				/***************************1.接收請求參數****************************************/
				Integer pro_report_no = new Integer(req.getParameter("pro_report_no"));
				
				/***************************2.開始查詢資料****************************************/
				Product_ReportService product_reportSvc = new Product_ReportService();
				Product_ReportVO product_reportVO = product_reportSvc.getOneProduct_Report(pro_report_no);
								
				/***************************3.查詢完成,準備轉交(Send the Success view)************/
				req.setAttribute("product_reportVO", product_reportVO);         // 資料庫取出的product_reportVO物件,存入req
				String url = "/back-end/product_report/update_product_report_input.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交update_product_report_input.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改的資料:" + e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_report/listAllProduct_Report.jsp");
				failureView.forward(req, res);
			}
		}
		
		
		if ("update".equals(action)) { // 來自update_product_type_input.jsp的請求
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				Integer pro_report_no = new Integer(req.getParameter("pro_report_no").trim());
				
				String pro_report_content = req.getParameter("pro_report_content");
				if (pro_report_content == null || pro_report_content.trim().length() == 0) {
					errorMsgs.add("商品檢舉內容: 請勿空白");
				} 
				//*需更改 之後要做點選按鈕 動態抓取商品的商品編號(會員不需自行填寫)
				Integer product_no = new Integer(req.getParameter("product_no").trim());;
				//*需更改 檢舉者帳號 動態抓取自動帶入
				String user_id = req.getParameter("user_id");
				//檢舉時間不用驗證
				Integer empno = null;
				try {
					empno = new Integer(req.getParameter("empno").trim());
				} catch (NumberFormatException e) {
					empno = 14001;
					errorMsgs.add("員工編號請勿空白");
				}
				
				Integer proreport_state = null;
				try {
					proreport_state = new Integer(req.getParameter("proreport_state").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("檢舉處理狀態請勿空白");
				}
			
				
				Product_ReportVO product_reportVO = new Product_ReportVO();
				product_reportVO.setPro_report_no(pro_report_no);
				product_reportVO.setPro_report_content(pro_report_content);
				product_reportVO.setProduct_no(product_no);
				product_reportVO.setUser_id(user_id);
				product_reportVO.setEmpno(empno);
				product_reportVO.setProreport_state(proreport_state);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("product_reportVO", product_reportVO); // 含有輸入格式錯誤的product_reportVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_type/update_product_type_input.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始修改資料*****************************************/
				Product_ReportService product_typeSvc = new Product_ReportService();
				product_reportVO = product_typeSvc.updateProduct_Type(pro_report_no, pdtype_name);
				
				/***************************3.修改完成,準備轉交(Send the Success view)*************/
				req.setAttribute("product_reportVO", product_reportVO); // 資料庫update成功後,正確的的product_reportVO物件,存入req
				String url = "/back-end/product_type/listOneProduct_Type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功後,轉交listOneProduct_Type.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_type/update_product_type_input.jsp");
				failureView.forward(req, res);
			}
		}

        if ("insert".equals(action)) { // 來自addProduct_Type.jsp的請求  
			
			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
		
			try {
				/***************************1.接收請求參數 - 輸入格式的錯誤處理**********************/
				String pdtype_name = req.getParameter("pdtype_name");

				String pdtype_nameReg = "^[(\u4e00-\u9fa5)(a-zA-Z0-9_)]{2,20}$";
				if (pdtype_name == null || pdtype_name.trim().length() == 0) {
					errorMsgs.add("商品名稱: 請勿空白");
				} else if(!pdtype_name.trim().matches(pdtype_nameReg)) { //以下練習正則(規)表示式(regular-expression)
					errorMsgs.add("商品名稱: 只能是中、英文字母、數字和_ , 且長度必需在2到20之間");
	            }
				
				Product_TypeVO product_reportVO = new Product_TypeVO();
				product_reportVO.setPdtype_name(pdtype_name);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("product_reportVO", product_reportVO); // 含有輸入格式錯誤的product_reportVO物件,也存入req
					RequestDispatcher failureView = req
							.getRequestDispatcher("/back-end/product_type/addProduct_Type.jsp");
					failureView.forward(req, res);
					return; //程式中斷
				}
				
				/***************************2.開始新增資料*****************************************/
				Product_ReportService product_typeSvc = new Product_ReportService();
				product_reportVO = product_typeSvc.addProduct_Type(pdtype_name);
				
				/***************************3.新增完成,準備轉交(Send the Success view)*************/
				String url = "/back-end/product_type/listAllProduct_Type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功後,轉交listAllProduct_Type.jsp
				successView.forward(req, res);

				/***************************其他可能的錯誤處理*************************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_type/addProduct_Type.jsp");
				failureView.forward(req, res);
			}
		}
				
		if ("delete".equals(action)) { // 來自listAllProduct_Type.jsp

			List<String> errorMsgs = new LinkedList<String>();
			// Store this set in the request scope, in case we need to
			// send the ErrorPage view.
			req.setAttribute("errorMsgs", errorMsgs);
	
			try {
				/***************************1.接收請求參數***************************************/
				Integer pro_report_no = new Integer(req.getParameter("pro_report_no"));
				
				/***************************2.開始刪除資料***************************************/
				Product_ReportService product_typeSvc = new Product_ReportService();
				product_typeSvc.deleteProduct_Type(pro_report_no);
				
				/***************************3.刪除完成,準備轉交(Send the Success view)***********/								
				String url = "/back-end/product_type/listAllProduct_Type.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
				
				/***************************其他可能的錯誤處理**********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:"+e.getMessage());
				RequestDispatcher failureView = req
						.getRequestDispatcher("/back-end/product_type/listAllProduct_Type.jsp");
				failureView.forward(req, res);
			}
		}
	}	
}
