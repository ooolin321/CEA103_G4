package com.mem.controller;

import java.io.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.annotation.*;
import javax.servlet.http.*;

import org.json.JSONObject;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import redis.clients.jedis.Jedis;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

@MultipartConfig(fileSizeThreshold = 1024 * 1024, maxFileSize = 5 * 1024 * 1024, maxRequestSize = 5 * 5 * 1024 * 1024)

@WebServlet(urlPatterns = { "/mem/mail.do" }, initParams = { @WebInitParam(name = "host", value = "smtp.gmail.com"),
		@WebInitParam(name = "port", value = "587"),
		@WebInitParam(name = "username", value = "bookshop.ea103@gmail.com"),
		@WebInitParam(name = "password", value = "1j4dk4vu86") })
public class Mail extends HttpServlet {
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
		Map<String, String> errorMsgs = new HashMap<String, String>();
		req.setAttribute("errorMsgs", errorMsgs);

		// 來自signUp.jsp的請求
		try {
			/*********************** 1.接收請求參數 - 輸入格式的錯誤處理 *************************/
			MemService memSvc = new MemService(); // 需檢查欄位所以先宣告

			String mem_account = req.getParameter("mem_account").trim();
			// 正規表示法here

			if (mem_account == null || mem_account.trim().length() == 0)
				errorMsgs.put("account", "帳號欄請勿空白");
			else if (memSvc.checkAcc(mem_account))
				errorMsgs.put("account", "此帳號有人使用，請重新輸入");
			// else if 正規表示法條件

			String mem_password = req.getParameter("mem_password").trim();

			String confirPassword = req.getParameter("confirPassword").trim();

			if (mem_password == null || mem_password.trim().length() == 0)
				errorMsgs.put("password", "密碼欄請勿空白");
			else if (!(mem_password.equals(confirPassword)))
				errorMsgs.put("password", "密碼不一致");

			String mem_name = req.getParameter("mem_name").trim();
			if (mem_name == null || mem_name.trim().length() == 0)
				errorMsgs.put("name", "姓名欄請勿空白");

			String mem_email = req.getParameter("mem_email").trim();
			if (mem_email == null || mem_email.trim().length() == 0)
				errorMsgs.put("email", "EMAIL欄請勿空白");
			else if (memSvc.checkEmail(mem_email))
				errorMsgs.put("email", "此EMAIL已有人註冊，請重新輸入");

			String mem_nickname = req.getParameter("mem_nickname").trim();

			Integer mem_sex = null;
			String sex = req.getParameter("mem_sex").trim();

			if (sex == null || sex.trim().length() == 0)
				errorMsgs.put("sex", "性別欄請勿空白");
			else if (sex.equals("男"))
				mem_sex = new Integer("1");
			else if (sex.equals("女"))
				mem_sex = new Integer("0");
			else
				errorMsgs.put("sex", "請輸入正確性別?");

			java.sql.Date mem_birth = null;
			try {
				mem_birth = java.sql.Date.valueOf(req.getParameter("mem_birth").trim());
			} catch (IllegalArgumentException e) {
				mem_birth = new java.sql.Date(System.currentTimeMillis());
				errorMsgs.put("birth", "請輸入日期");
			}
			
			String city = req.getParameter("city").trim();//縣市
			String town = req.getParameter("town").trim();//地區
			String zipcode = req.getParameter("zipcode").trim();//郵遞區號
			String mem_addr = req.getParameter("mem_addr").trim();//地址
			if (mem_addr == null || mem_addr.trim().length() == 0)
				errorMsgs.put("addr", "地址欄請勿空白");

			String addr = city + town + zipcode + mem_addr;
			
			String mem_tel = req.getParameter("mem_tel").trim();
			if (mem_tel == null || mem_tel.trim().length() == 0)
				errorMsgs.put("tel", "電話欄請勿空白");

			Part part = req.getPart("mem_pic");
			byte[] mem_pic = null;
			InputStream in = part.getInputStream();
			if (in.available() != 0) {
				mem_pic = new byte[in.available()];
				in.read(mem_pic);
				in.close();
			} else {
				mem_pic = getPictureByteArray(getServletContext().getRealPath("/BookShopLogo/3.png"));
			}

			MemVO memVO = new MemVO();
			memVO.setMem_account(mem_account);
			memVO.setMem_password(mem_password);
			memVO.setMem_name(mem_name);
			memVO.setMem_email(mem_email);
			memVO.setMem_nickname(mem_nickname);
			memVO.setMem_sex(mem_sex);
			memVO.setMem_birth(mem_birth);
			memVO.setMem_addr(addr);
			memVO.setMem_tel(mem_tel);
			memVO.setMem_pic(mem_pic);

			// Send the use back to the form, if there were errors
			if (!errorMsgs.isEmpty()) {
				req.setAttribute("memVO", memVO);
				RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
				failureView.forward(req, res);
				return;
			}

			/* 確認無錯誤後，接收Email請求參數 */
			String subject = req.getParameter("subject");
			String genAuthCode = new Mail().genAuthCode();
			String text = "請將以下驗證碼輸入至註冊驗證頁面: " + genAuthCode;
			
			/*打包完成,準備轉交 */
			//驗證碼存入 redis
			Jedis jedis = new Jedis("localhost", 6379);
			jedis.auth("123456");
			jedis.set(memVO.getMem_email(), genAuthCode);
			jedis.expire(memVO.getMem_email(), 300); //過期時間五分鐘
			jedis.close();
			
			HttpSession session = req.getSession();
			session.setAttribute("memVO", memVO); // 將memVO存在session中，尚未存進資料庫
			
			String url = "/front-end/member/confirmationCode.jsp";
			RequestDispatcher successView = req.getRequestDispatcher(url); // 成功後轉交confirmationCode.jsp
			successView.forward(req, res);

			/* 寄出 */
			try {
				Message message = createMessage(username, memVO.getMem_email(), subject, text);
				Transport.send(message);
			} catch (MessagingException e) {
				throw new RuntimeException(e);
			}

		} catch (Exception e) {
			System.out.println(e.getMessage());
			RequestDispatcher failureView = req.getRequestDispatcher("/front-end/member/signUp.jsp");
			failureView.forward(req, res);
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

	public String genAuthCode() {
		String genAuthCode = "";
		for (int i = 1; i <= 8; i++) {
			int ranNum = (int) (Math.random() * 123);
			if (ranNum >= 0 && ranNum <= 9)
				genAuthCode += ranNum;
			else if (ranNum >= 65 && ranNum <= 90 || ranNum >= 97 && ranNum <= 122)
				genAuthCode += (char) (ranNum);
			else
				genAuthCode += (ranNum / 10);
		}
		return genAuthCode;
	}

	public static byte[] getPictureByteArray(String path) throws IOException {
		File file = new File(path);
		FileInputStream fis = new FileInputStream(file);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buffer = new byte[8192];
		int i;
		while ((i = fis.read(buffer)) != -1) {
			baos.write(buffer, 0, i);
		}
		baos.close();
		fis.close();

		return baos.toByteArray();
	}
}
