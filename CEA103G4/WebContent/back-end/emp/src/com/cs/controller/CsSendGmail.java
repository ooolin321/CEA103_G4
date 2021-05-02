package com.cs.controller;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.cs.model.CsService;
import com.cs.model.CsVO;

public class CsSendGmail extends HttpServlet {
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		
		req.setCharacterEncoding("UTF-8");
		String action = req.getParameter("action");
		if("sendmail".equals(action)) {
			//接收成功,錯誤訊息
			
			List<String> messages = new LinkedList<String>();
			req.setAttribute("messages", messages);
			List<String> errorMsgs = new LinkedList<String>();
			req.setAttribute("errorMsgs", errorMsgs);
			
			try {
				//接收請求參數
				String cs_ID = req.getParameter("cs_ID");
				Integer cs_isSend =new Integer(req.getParameter("cs_isSend"));
				String cs_Email = req.getParameter("cs_Email");
				String cs_Subject = req.getParameter("cs_Subject");
				String cs_Message = req.getParameter("cs_Message");
				// 設定使用SSL連線至 Gmail smtp Server
				Properties props = new Properties();
				props.put("mail.smtp.host", "smtp.gmail.com");
				props.put("mail.smtp.socketFactory.port", "465");
				props.put("mail.smtp.socketFactory.class","javax.net.ssl.SSLSocketFactory");
				props.put("mail.smtp.auth", "true");
				props.put("mail.smtp.port", "465");

		       // ●設定 gmail 的帳號 & 密碼 (將藉由你的Gmail來傳送Email)
		       // ●須將myGmail的【安全性較低的應用程式存取權】打開
			     final String myGmail = "ea103qq30120@gmail.com";
			     final String myGmail_password = "QAZwsxedc123";
				   Session session = Session.getInstance(props, new Authenticator() {
					   protected PasswordAuthentication getPasswordAuthentication() {
						   return new PasswordAuthentication(myGmail, myGmail_password);
					   }
				   });

				   Message message = new MimeMessage(session);
				   message.setFrom(new InternetAddress(myGmail));
				   message.setRecipients(Message.RecipientType.TO,InternetAddress.parse(cs_Email));
				  
				   //設定信中的主旨  
				   message.setSubject(cs_Subject);
				   //設定信中的內容 
				   message.setText(cs_Message);
				   //寄出信件
				   Transport.send(message);
				   
				   //回覆狀態為0:未回覆 更改為1:已回覆
				   if(cs_isSend==0) {
					   cs_isSend= 1;
				   }
				   CsVO csVO = new CsVO();
				   csVO.setCs_ID(cs_ID);
				   csVO.setCs_isSend(cs_isSend);
				   
				   //修改資料
				   CsService csSvc = new CsService();
				   csVO = csSvc.updateCs(cs_isSend, cs_ID);

				   
				   
		     }catch (MessagingException e){
		    	errorMsgs.add("傳送失敗,請檢查網路是否正常");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/cs/csindex.jsp");
				failureView.forward(req, res);
		     } catch (Exception e) {
		    	errorMsgs.add("修改資料失敗");
				RequestDispatcher failureView = req.getRequestDispatcher("/back-end/cs/csindex.jsp");
				failureView.forward(req, res);
		     }
			
		}
		
	}
}
