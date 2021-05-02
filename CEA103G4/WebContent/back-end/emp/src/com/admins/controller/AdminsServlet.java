package com.admins.controller;

import java.io.*;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.http.Part;

import com.adminpermission.model.AdminPermissionService;
import com.adminpermission.model.AdminPermissionVO;
import com.admins.model.AdminsService;
import com.admins.model.AdminsVO;
import com.permissiondelimit.model.PermissionDelimitService;
import com.permissiondelimit.model.PermissionDelimitVO;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)
public class AdminsServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		doPost(req, res);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");

		if ("insert".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*********************** 1.接受請求參數 - 輸入格式的錯誤處理 *************************/
				/** 1管理員名稱 **/
				String admin_name = req.getParameter("admin_name");
				String admin_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
				if (admin_name == null || admin_name.trim().length() == 0) {
					errorMsgs.add("管理員名稱:不得空白");
				} else if (!admin_name.trim().matches(admin_nameReg)) {
					errorMsgs.add("管理員名稱:格式有誤，請輸入中文字");
				}

				/** 2管理員身分證號 **/
				String admin_id_no = req.getParameter("admin_id_no");
				String admin_id_noReg = "^[A-Z]+[1|2]+[0-9]{8}$";
				if (admin_id_no == null || admin_id_no.trim().length() == 0) {
					errorMsgs.add("管理員身分證:不得空白");
				} else if (!admin_id_no.trim().matches(admin_id_noReg)) {
					errorMsgs.add("管理員身分證:格式有誤，開頭為大寫英文字母");
				}

				/** 3管理員密碼 **/
				String admin_pswd = genAuthCode(8);

				/** 4管理員手機 **/
				String admin_mobile = req.getParameter("admin_mobile");
				String admin_mobileReg = "^09[0-9]{8}$";
				if (admin_mobile == null || admin_mobile.trim().length() == 0) {
					errorMsgs.add("管理員手機:不得空白");
				} else if (!admin_mobile.trim().matches(admin_mobileReg)) {
					errorMsgs.add("管理員手機:格式有誤，開頭為09");
				}

				/** 5管理員地址 **/
				String city = req.getParameter("city").trim();
				String town = req.getParameter("town").trim();
				String address = req.getParameter("address").trim();

				String[] addressArray = { city, town, address };
//				String admin_address = String.join(",",addressArray);//Java8				
				StringBuilder sb = new StringBuilder();
				if (addressArray.length > 0) {
					for (int i = 0; i < addressArray.length; i++) {
						sb.append(addressArray[i]);
					}
				}
				String admin_address = sb.toString();

				if (admin_address == null || admin_address.trim().length() == 0) {
					errorMsgs.add("管理員地址:不得空白");
				}

				/** 6管理員雇用日期 **/
				java.sql.Date admin_dutydate = null;
				try {
					admin_dutydate = java.sql.Date.valueOf(req.getParameter("admin_dutydate").trim());
				} catch (IllegalArgumentException e) {
					errorMsgs.add("管理員雇用日期:不得空白");
				}

				/** 7管理員就職狀態 **/
				Integer admin_jobstate = null;
				try {
					admin_jobstate = new Integer(req.getParameter("admin_jobstate").trim());
				} catch (NumberFormatException e) {
					errorMsgs.add("管理員在職狀態:不得空白");
				}

				/** 8管理員照片 **/
				byte[] admin_pic = null;
				Part part = req.getPart("admin_pic");
				if (part == null || part.getSize() == 0) {
					errorMsgs.add("請上傳一張圖片");
				}
				InputStream in = part.getInputStream();
				admin_pic = new byte[in.available()];
				in.read(admin_pic);
				in.close();

				/** 9管理員郵件 **/
				String admin_mail = req.getParameter("admin_mail").trim();
				if (admin_mail == null || admin_mail.trim().length() == 0) {
					errorMsgs.add("管理員郵件:不得空白");
				}

				AdminsVO adminsVO = new AdminsVO();
				adminsVO.setAdmin_name(admin_name);
				adminsVO.setAdmin_id_no(admin_id_no);
				adminsVO.setAdmin_pswd(admin_pswd);
				adminsVO.setAdmin_mobile(admin_mobile);
				adminsVO.setAdmin_address(admin_address);
				adminsVO.setAdmin_dutydate(admin_dutydate);
				adminsVO.setAdmin_jobstate(admin_jobstate);
				adminsVO.setAdmin_pic(admin_pic);
				adminsVO.setAdmin_mail(admin_mail);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminsVO", adminsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/addAdmins.jsp");
					failureView.forward(req, res);
					return;
				}
				/*************************** 2.開始新增資料 ***************************************/
				AdminsService adminsSvc = new AdminsService();
				AdminsVO adminsVO2 = adminsSvc.addAdmins(admin_name, admin_id_no, admin_pswd, admin_mobile,
						admin_address, admin_dutydate, admin_jobstate, admin_pic, admin_mail);
				// System.out.println("adminsVO2"+adminsVO2);
				String admin_id = adminsVO2.getAdmin_id();
//				System.out.println("admin_id"+admin_id);
				/** 10權限編號 **/
				String per_id[] = req.getParameterValues("per_id[]"); // 一個員工可以有很多權限編號
//				System.out.println("per_id"+per_id);
				AdminPermissionVO adminpermissionVO = new AdminPermissionVO(); // 使用關連表格的值物件來設定權限編號 (從前端選取的資料)
				for (int i = 0; i < per_id.length; i++) {
					adminpermissionVO.setAdmin_id(admin_id);
					adminpermissionVO.setPer_id(per_id[i]);
				}

				AdminPermissionService adminpermissionSvc = new AdminPermissionService();
				for (int i = 0; i < per_id.length; i++) {
					adminpermissionVO = adminpermissionSvc.addAdminPermission(admin_id, per_id[i]); // 設進資料庫
				}

				List<AdminPermissionVO> list = new AdminPermissionService().getOneAdminPermission(admin_id);
				// 顯示權限名字
				List<PermissionDelimitVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService()
							.getOnePermissionDelimit(list.get(i).getPer_id());// 將關聯表格的per_id改為權限定義的per_id
					list2.add(permissiondelimitVO);
					req.setAttribute("permissiondelimitVO", list2);
//					System.out.println(permissiondelimitVO);
//					System.out.println("list2"+list2);
				}
				/*************************** 3.新增完成，準備轉交(Send the Success view) ***********/
				String url = "/back-end/admins/listAllAdmins.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功轉交listAllAdmins.jsp
				successView.forward(req, res);
//				System.out.println("AdminsSevlet-insert OK!");

				String to = adminsVO.getAdmin_mail();

				String subject = "部客匣員工帳號密碼通知";
				String acnt = adminsVO2.getAdmin_id();
				String ch_name = adminsVO.getAdmin_name();
				String passRandom = adminsVO.getAdmin_pswd();
				String link = req.getServerName() + ":" + req.getServerPort() + req.getContextPath();
				String messageText = "Hello! " + ch_name + "\n" + "你的帳號:" + acnt + " " + " 首次登入密碼: " + passRandom + "\n"
						+ " (請前往 http://" + link + "/back-end/admins/update_pswd.jsp 更改密碼)";

				MailService mailService = new MailService();
				mailService.sendMail(to, subject, messageText);

				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add(e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/addAdmins.jsp");
				failureView.forward(req, res);
			}
		}

		if ("update".equals(action)) { // 修改員工資料:來自update_admins.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/

				String admin_id_no = req.getParameter("admin_id_no");
				java.sql.Date admin_dutydate = java.sql.Date.valueOf(req.getParameter("admin_dutydate").trim());
//				System.out.println("@update輸入1," + admin_id_no);
//				System.out.println("@update輸入2," + admin_dutydate);
				/** 管理員編號接受請求 **/
				String admin_id = req.getParameter("admin_id").trim();

				/** 管理員就職狀態 **/
				Integer admin_jobstate = null;
				admin_jobstate = new Integer(req.getParameter("admin_jobstate").trim());
//				System.out.println("@update輸入7," + admin_jobstate);

				/** 10權限編號 **/
				AdminPermissionService adminpermissionSvc2 = new AdminPermissionService();

				String[] per_id = req.getParameterValues("per_id[]");
				if (per_id == null || admin_jobstate == 0) {
					adminpermissionSvc2.deleteAdminPermission(admin_id);
				} else {
					adminpermissionSvc2.deleteAdminPermission(admin_id);

					AdminPermissionVO adminpermissionVO = new AdminPermissionVO();
					for (int i = 0; i < per_id.length; i++) {
						adminpermissionVO.setAdmin_id(admin_id);
						adminpermissionVO.setPer_id(per_id[i]);
					}

					AdminPermissionService adminpermissionSvc = new AdminPermissionService();
					for (int i = 0; i < per_id.length; i++) {
						adminpermissionVO = adminpermissionSvc.addAdminPermission(admin_id, per_id[i]); // 設進資料庫
					}
				}

				List<AdminPermissionVO> list = new AdminPermissionService().getOneAdminPermission(admin_id);

				// 顯示權限名字
				List<PermissionDelimitVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService()
							.getOnePermissionDelimit(list.get(i).getPer_id());// 將關聯表格的per_id改為權限定義的per_id
					list2.add(permissiondelimitVO);
					req.setAttribute("permissiondelimitVO", list2);
//					System.out.println(permissiondelimitVO);
//					System.out.println("6" + list2);
				}
				// 以上為未修改但要顯示的資料

				AdminsVO adminsVO = new AdminsVO();

//				System.out.println("@update輸入3," + admin_id);
				/** 管理員名稱 **/
				String admin_name = req.getParameter("admin_name");
				String admin_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
				if (admin_name == null || admin_name.trim().length() == 0) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員名稱:不得空白");
				} else if (!admin_name.trim().matches(admin_nameReg)) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員名稱:格式有誤，請輸入2~20個中文字");
				}
//				System.out.println("@update輸入4," + admin_name);

				/** 管理員手機 **/
				String admin_mobile = req.getParameter("admin_mobile");
				String admin_mobileReg = "^09[0-9]{8}$";
				if (admin_mobile == null || admin_mobile.trim().length() == 0) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員手機:不得空白");
				} else if (!admin_mobile.trim().matches(admin_mobileReg)) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員手機:格式有誤，手機號為09開頭");

				}
//				System.out.println("@update輸入5," + admin_mobile);

				/** 管理員地址 **/
				String admin_address = req.getParameter("admin_address").trim();
				if (admin_address == null || admin_address.trim().length() == 0) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員地址:不得空白");
				}

//				System.out.println("@update輸入6," + admin_address);

				/** 管理員照片 **/
				byte[] admin_pic = null;
				Part part = req.getPart("admin_pic");
				if (part == null || part.getSize() == 0) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					AdminsService adminsSvc2 = new AdminsService();
					AdminsVO adminsVO2 = adminsSvc2.getOneAdmin(admin_id);
					admin_pic = adminsVO2.getAdmin_pic();
				} else {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					InputStream in = part.getInputStream();
					admin_pic = new byte[in.available()];
					in.read(admin_pic);
					in.close();
				}
//				System.out.println("@update輸入8," + admin_pic);

				/** 管理員郵件 **/
				String admin_mail = req.getParameter("admin_mail").trim();
				if (admin_mail == null || admin_mail.trim().length() == 0) {
					req.setAttribute("adminsVO", adminsVO);
					req.setAttribute("permissiondelimitVO", list2);
					errorMsgs.add("管理員郵件:不得空白");
				}
//				System.out.println("@update輸入9," + admin_mail);

				adminsVO.setAdmin_id_no(admin_id_no);
				adminsVO.setAdmin_dutydate(admin_dutydate);
				adminsVO.setAdmin_jobstate(admin_jobstate);
				adminsVO.setAdmin_id(admin_id);
				adminsVO.setAdmin_name(admin_name);
				adminsVO.setAdmin_mobile(admin_mobile);
				adminsVO.setAdmin_address(admin_address);
				adminsVO.setAdmin_pic(admin_pic);
				adminsVO.setAdmin_mail(admin_mail);

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminsVO", adminsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_admins.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				/*************************** 2.開始修改資料 *****************************************/
				AdminsService adminsSvc = new AdminsService();
				adminsVO = adminsSvc.updateAdmins(admin_id, admin_name, admin_mobile, admin_address, admin_jobstate,
						admin_pic, admin_mail);
				adminsVO.setAdmin_id_no(admin_id_no);
				adminsVO.setAdmin_dutydate(admin_dutydate);
//				System.out.println("@AdminsServlet開始修改");
//				System.out.println("@AdminsServlet修改後,有取到Admin_id_no" + admin_id_no);
//				System.out.println("@AdminsServlet修改後,有取到Admin_dutydate" + admin_dutydate);

				/*************************** 3.修改完成，準備轉交(Send the Success view) *************/
				req.setAttribute("adminsVO", adminsVO); // 資料庫update成功後，正確的adminsVO,req存入
				String url = "/back-end/admins/listOneAdmins.jsp";//
				RequestDispatcher successView = req.getRequestDispatcher(url); // 修改成功轉交listOneAdmins.jsp
				successView.forward(req, res);
//				System.out.println("@AdminsSevlet-update OK!");

				/*************************** 其他可能的錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("修改資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_admins.jsp");
				failureView.forward(req, res);
			}
		}

		// 修改密碼

		if ("update_pswd".equals(action)) {
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
//			List<String> rightMsgs = new LinkedList<String>();
//			req.setAttribute("rightMsgs", rightMsgs);
			try {
				// admin_id參數
				String admin_id = req.getParameter("admin_id");
//				System.out.println("1,admin_id " + admin_id);

				String pswd = req.getParameter("pswd").trim();
				String pswd_again = req.getParameter("pswd_again").trim();
				String admin_pswd = null;
//				System.out.println("2,pswd " + pswd);
//				System.out.println("3,pswd_again" + pswd_again);

				if (pswd == null || pswd.trim().length() == 0 || pswd_again == null
						|| pswd_again.trim().length() == 0) {
					errorMsgs.add("密碼:不得空白");
				} else if (!pswd.trim().equals(pswd_again)) {
					errorMsgs.add("兩次輸入修改密碼不一樣");
				} else {
					admin_pswd = pswd_again;
					String update = "update";
					req.setAttribute("update", update);
				}
//				System.out.println("4,admin_pswd"+admin_pswd);

				AdminsVO adminsVO = new AdminsVO();
				adminsVO.setAdmin_pswd(admin_pswd);

				if (!errorMsgs.isEmpty()) {
					req.setAttribute("adminsVO", adminsVO);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_pswd.jsp");
					failureView.forward(req, res);
					return; // 程式中斷
				}

				// 開始修改
				AdminsService adminsSvc = new AdminsService();
				adminsVO = adminsSvc.updatePswd(admin_id, admin_pswd);
//				System.out.println("5,adminsVO.getAdmin_pswd()"+adminsVO.getAdmin_pswd());

				/*************************** 3.新增完成，準備轉交(Send the Success view) ***********/
				req.setAttribute("adminsVO", adminsVO);
//				System.out.println("6,adminsVO"+adminsVO);

				String url = "/back-end/login/login.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 新增成功轉交listAllAdmins.jsp
				successView.forward(req, res);
//				System.out.println("AdminsSevlet-insert OK!");
				req.getSession().invalidate();

			} catch (Exception e) {
				errorMsgs.add("修改密碼失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_pswd.jsp");
				failureView.forward(req, res);
			}
		}

		// 用admin_id來搜查管理員資料
		if ("getOne_For_Display".equals(action)) { // 來自listAllAdmins.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
				String admin_id = req.getParameter("admin_id").trim().toUpperCase();
				String admin_idReg = "^ADM[0-9]{4}$";
				if (admin_id == null || (admin_id.trim()).length() == 0) {
					errorMsgs.add("管理員編號:請勿空白");
				} else if (!admin_id.trim().matches(admin_idReg)) {
					errorMsgs.add("管理員編號:只能為ADM開頭+四個數字");
				}
				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 2.開始查詢資料 *****************************************/
				AdminsService adminsSvc = new AdminsService();
				AdminsVO adminsVO = adminsSvc.getOneAdmin(admin_id);

				if (adminsVO == null) {
					errorMsgs.add("查無資料");
				}
				AdminPermissionService adminpermissionSvc = new AdminPermissionService();
				List<AdminPermissionVO> list = adminpermissionSvc.getOneAdminPermission(admin_id);
//				System.out.println("display one auth" + list);
				// 顯示權限名字
				List<PermissionDelimitVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService()
							.getOnePermissionDelimit(list.get(i).getPer_id());// 將關聯表格的per_id改為權限定義的per_id
					list2.add(permissiondelimitVO);
					req.setAttribute("permissiondelimitVO", list2);

//					System.out.println(permissiondelimitVO);
//					System.out.println("display one auth" + list2);
				}

				// Send the use back to the form, if there were errors
				if (!errorMsgs.isEmpty()) {
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
					failureView.forward(req, res);
					return;// 程式中斷
				}

				/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
				req.setAttribute("adminsVO", adminsVO);
				String url = "/back-end/admins/listOneAdmins.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAdmins.jsp
				successView.forward(req, res);
//				System.out.println("AdminsSevlet-getOne_For_Display OK!");

				/*************************** 其他可能錯誤處理 *************************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
				failureView.forward(req, res);
			}
		}

		// 用admin_name來搜查管理員資料
				if ("getOne_For_Display_By_Name".equals(action)) { // 來自listAllAdmins.jsp的請求

					List<String> errorMsgs = new LinkedList<String>();
					req.setAttribute("errorMsgs", errorMsgs);

					try {
						/*************************** 1.接受請求參數 - 輸入格式的錯誤處理 **********************/
						String admin_name = req.getParameter("admin_name");
						System.out.println("admin_name"+admin_name);
						String admin_nameReg = "^[(\u4e00-\u9fa5)]{2,20}$";
						if (admin_name == null || admin_name.trim().length() == 0) {
							errorMsgs.add("管理員名稱:不得空白");
						} else if (!admin_name.trim().matches(admin_nameReg)) {
							errorMsgs.add("管理員名稱:格式有誤，請輸入中文字");
						}
						// Send the use back to the form, if there were errors
						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
							failureView.forward(req, res);
							return;// 程式中斷
						}

						/*************************** 2.開始查詢資料 *****************************************/
						AdminsService adminsSvc = new AdminsService();
						List<AdminsVO> admins = adminsSvc.getAll();
						if (admins == null) {
							errorMsgs.add("查無資料");
						}

						AdminPermissionService adminpermissionSvc = new AdminPermissionService();
						for (int j = 0; j < admins.size(); j++) {
							if (admin_name.equals(admins.get(j).getAdmin_name())) {
								AdminsVO adminsVO = adminsSvc.getOneAdmin(admins.get(j).getAdmin_id());
								req.setAttribute("adminsVO", adminsVO);
//								System.out.println("adminsVO"+adminsVO.getAdmin_id());

								List<AdminPermissionVO> list = adminpermissionSvc
										.getOneAdminPermission(adminsVO.getAdmin_id());
								// 顯示權限名字
								List<PermissionDelimitVO> list2 = new ArrayList<>();
								for (int i = 0; i < list.size(); i++) {
									PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService()
											.getOnePermissionDelimit(list.get(i).getPer_id());// 將關聯表格的per_id改為權限定義的per_id
									list2.add(permissiondelimitVO);
									req.setAttribute("permissiondelimitVO", list2);
//									System.out.println("permissiondelimitVO"+list2);
								}
							}
						}

						if (!errorMsgs.isEmpty()) {
							RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
							failureView.forward(req, res);
							return;// 程式中斷
						}

						/*************************** 3.查詢完成，準備轉交(Send the Success view) *************/
						String url = "/back-end/admins/listOneAdmins.jsp";
						RequestDispatcher successView = req.getRequestDispatcher(url); // 成功轉交 listOneAdmins.jsp
						successView.forward(req, res);
//								System.out.println("AdminsSevlet-getOne_For_Display OK!");

						/*************************** 其他可能錯誤處理 *************************************/
					} catch (Exception e) {
						errorMsgs.add("無法取得資料:" + e.getMessage());
						RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
						failureView.forward(req, res);
					}
				}

		// 從listAllAdmins.jsp的“修改”按鈕進入，由admin_id來修正員工資料(導到update_admins_input)
		if ("getOne_For_Update".equals(action)) { // 來自修改成功轉交listAllAdmins.jsp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接受請求參數 ****************************************/
				String admin_id = req.getParameter("admin_id");

				/*************************** 2.開始查詢資料 ****************************************/
				AdminsService adminsSvc = new AdminsService();
				AdminsVO adminsVO = adminsSvc.getOneAdmin(admin_id);

				AdminPermissionService adminpermissionSvc = new AdminPermissionService();
				List<AdminPermissionVO> list = adminpermissionSvc.getOneAdminPermission(admin_id);
//				System.out.println("display one auth" + list);
				// 顯示權限名字
				List<PermissionDelimitVO> list2 = new ArrayList<>();
				for (int i = 0; i < list.size(); i++) {
					PermissionDelimitVO permissiondelimitVO = new PermissionDelimitService()
							.getOnePermissionDelimit(list.get(i).getPer_id());// 將關聯表格的per_id改為權限定義的per_id
					list2.add(permissiondelimitVO);
					req.setAttribute("permissiondelimitVO", list2);

//					System.out.println(permissiondelimitVO);
//					System.out.println("display one auth" + list2);
				}

				/*************************** 3.查詢完成，準備轉交(Send the Success view) ************/
				req.setAttribute("adminsVO", adminsVO);
				String url = "/back-end/admins/update_admins.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 成功轉交 update_admins_input.jsp
				successView.forward(req, res);
//				System.out.println("@AdminsSevlet-成功轉交至update_admins.jsp");

				/*************************** 其他可能錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("無法取得要修改資料:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/listAllAdmins.jsp");
				failureView.forward(req, res);
			}
		}

		// 從listAllAdmins.jsp的“權限刪除”按鈕進入
		if ("getOne_For_Delete_Perid".equals(action)) { // 來自修改成功轉交listAllAdmins.jsp.jsp的請求

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ***************************************/
				String admin_id = req.getParameter("admin_id").trim();
//				System.out.println(admin_id);
				/*************************** 2.開始刪除資料 ***************************************/
				AdminPermissionService adminpermissionSvc = new AdminPermissionService();
				adminpermissionSvc.deleteAdminPermission(admin_id);
//				System.out.println("delete");
				/*************************** 3.刪除完成,準備轉交(Send the Success view) ***********/
				AdminsService adminsSvc = new AdminsService();
				AdminsVO adminsVO = adminsSvc.getOneAdmin(admin_id);

				req.setAttribute("adminsVO", adminsVO);
				String url = "/back-end/admins/update_admins.jsp";
				RequestDispatcher successView = req.getRequestDispatcher(url);// 刪除成功後,轉交回送出刪除的來源網頁
				successView.forward(req, res);
//				System.out.println("轉交成功");
				/*************************** 其他可能的錯誤處理 **********************************/
			} catch (Exception e) {
				errorMsgs.add("刪除資料失敗:" + e.getMessage());
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/admins/update_admins.jsp");
				failureView.forward(req, res);
			}
		}
		if ("logout".equals(action)) {
			req.getSession().invalidate();
			res.sendRedirect(req.getContextPath() + "/back-end/login/login.jsp");
//			System.out.println("logout");
		}

		if ("login".equals(action)) {

			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);

			try {
				/*************************** 1.接收請求參數 ****************************************/
				String account = new String(req.getParameter("admin_id"));
				String password = new String(req.getParameter("admin_pswd"));

				// header登入人名
				AdminsVO adminsVO = new AdminsService().getOneAdmin(account);
				String admin_name = adminsVO.getAdmin_name();
				String admin_id = adminsVO.getAdmin_id();// update_pswd改密碼用

				// 取得權限
				List<AdminPermissionVO> adminpermissionVO = new AdminPermissionService().getOneAdminPermission(account);
				List<PermissionDelimitVO> list = new ArrayList<>();
				PermissionDelimitService permissiondelimitSvc = new PermissionDelimitService();

				for (int i = 0; i < adminpermissionVO.size(); i++) {
					PermissionDelimitVO permissiondelimitVO = permissiondelimitSvc
							.getOnePermissionDelimit(adminpermissionVO.get(i).getPer_id());
					list.add(permissiondelimitVO);
//					System.out.println("permissiondelimitVO"+permissiondelimitVO.getPer_name());
				}

				// 【檢查該帳號 , 密碼是否有效】
				if (adminsVO.getAdmin_jobstate() == 0) {
					String quit = "quit";
					req.setAttribute("quit", quit);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login/login.jsp");
					failureView.forward(req, res);
				}

				else if (!allowUser(account, password)) { // 【帳號 , 密碼無效時】
					String error = "error";
					req.setAttribute("error", error);
					RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login/login.jsp");
					failureView.forward(req, res);

				} else {
					// 【帳號 , 密碼有效時, 才做以下工作】
					HttpSession session = req.getSession();
					session.setAttribute("account_s", account); // *工作1: 才在session內做已經登入過的標識
					session.setAttribute("admin_name_s", admin_name);
					session.setAttribute("admin_id_s", admin_id);
					session.setAttribute("adminsVO_s", adminsVO);
					session.setAttribute("adminpermissionVO_s", adminpermissionVO);
					session.setAttribute("permissiondelimitVO_s", list);
//			      System.out.println("account"+account);
//			      System.out.println("admin_name"+admin_name);
//			      System.out.println("adminpermissionVO"+adminpermissionVO);
//			      System.out.println("list"+list);

					try {
						String location = (String) session.getAttribute("location");
						if (location != null) {
							session.removeAttribute("location"); // *工作2: 看看有無來源網頁 (-->如有來源網頁:則重導至來源網頁)
							res.sendRedirect(location);
							return;
						}
					} catch (Exception ignored) {
					}
					res.sendRedirect(req.getContextPath() + "/back-end/login/loginSuccess.jsp"); // *工作3:
																									// (-->如無來源網頁:則重導至login_success.jsp)
				}
			} catch (Exception e) {
				String badguy = "badguy";
				req.setAttribute("badguy", badguy);
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/login/login.jsp");
				failureView.forward(req, res);
			}
		}
	}

	protected boolean allowUser(String account, String password) {

		AdminsVO adminsVO = new AdminsVO();
		AdminsService adminsSvc = new AdminsService();

		adminsVO = adminsSvc.getOneAdmin(account);

		if (adminsVO == null) {
			return false;
		}

		if ((adminsVO.getAdmin_id()).equals(account) && (adminsVO.getAdmin_pswd()).equals(password)
				&& adminsVO.getAdmin_jobstate() == 1) {
			return true;
		} else {
			return false;
		}

	}

	public static String genAuthCode(int n) {
		String data = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz1234567890";
		char[] ch = new char[n];
		for (int i = 0; i < n; i++) {
			Random random = new Random();
			int index = random.nextInt(data.length());
			ch[i] = data.charAt(index);
		}
		String result = String.valueOf(ch);
		return result;
	}

}
