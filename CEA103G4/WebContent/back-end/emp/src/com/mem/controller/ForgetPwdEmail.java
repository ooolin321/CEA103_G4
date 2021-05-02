package com.mem.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import com.mem.model.MemService;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@WebServlet(urlPatterns = { "/mem/ForgetPwdEmail.do" }, initParams = {
		@WebInitParam(name = "host", value = "smtp.gmail.com"), @WebInitParam(name = "port", value = "587"),
		@WebInitParam(name = "username", value = "bookshop.ea103@gmail.com"),
		@WebInitParam(name = "password", value = "1j4dk4vu86") })
public class ForgetPwdEmail extends HttpServlet {
	private String host;
	private int port;
	private String username;
	private String password;
	private Properties props;

	@Override
	public void init() throws ServletException {
		host = getServletConfig().getInitParameter("host");
		port = Integer.parseInt(getServletConfig().getInitParameter("port"));
		username = getServletConfig().getInitParameter("username");
		password = getServletConfig().getInitParameter("password");

		props = new Properties();
		props.put("mail.smtp.host", host);
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", port);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");

		String to = req.getParameter("to");
		
		if(to == null || to.trim().length() == 0) {
			String error = "請輸入您的E-Mail";
			req.setAttribute("error", error);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/forgetPwd.jsp");
			failureView.forward(req, res);
			return;
		}
		
		MemService memSvc = new MemService();
		if (!memSvc.checkEmail(to)) {
			String error = "無此E-Mail,請重新輸入";
			req.setAttribute("error", error);
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/forgetPwd.jsp");
			failureView.forward(req, res);
			return;
		}

		String from = req.getParameter("from");
		String subject = req.getParameter("subject");
		
		String genAuthCode = new Mail().genAuthCode(); //Mail.java裡的方法 取得8位亂碼
		String text = "請將以下驗證碼複製後，貼至修改密碼頁面: " + genAuthCode;

		try {
			req.getSession().setAttribute("mem_id", memSvc.getMemIdbyMail(to));
			req.getSession().setAttribute("genAuthCode", genAuthCode);
			String url = "/front-end/member/updateForForgetPwd.jsp";
			req.getRequestDispatcher(url).forward(req, res);
			
			Message message = createMessage(from, to, subject, text);
			Transport.send(message);
//            res.getWriter().println("郵件傳送成功");
//			System.out.println("郵件傳送成功");	
		} catch (MessagingException e) {
			throw new RuntimeException(e);
		}
	}

	private Message createMessage(String from, String to, String subject, String text) throws MessagingException {
		Session session = Session.getInstance(props, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});

		Message message = new MimeMessage(session);
		message.setFrom(new InternetAddress(from));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
		message.setSubject(subject);
		message.setSentDate(new Date());
		message.setText(text);

		return message;
	}
}