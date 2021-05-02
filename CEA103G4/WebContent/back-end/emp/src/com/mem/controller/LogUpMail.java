package com.mem.controller;

import java.util.Properties;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMessage.RecipientType;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.annotation.WebServlet;

@WebServlet(
	    urlPatterns={"/men/LogUpMail"},
	    initParams={
	        @WebInitParam(name = "host", value = "smtp.gmail.com"),
	        @WebInitParam(name = "port", value = "587"),
	        @WebInitParam(name = "username", value = "bookshop.ea103@gmail.com"),
	        @WebInitParam(name = "password", value = "1j4dk4vu86")
	    }
	)

public class LogUpMail {

    public static void sendMail(String email, String emailMsg) throws AddressException, MessagingException {
        // 1.建立一個程式與郵件伺服器會話物件 Session
        // 建立引數配置, 用於連線郵件伺服器的引數配置
        Properties props = new Properties(); // 引數配置 
        props.setProperty("mail.transport.protocol", "SMTP");// 使用的協議（JavaMail規範要求）
        props.setProperty("mail.host", "smtp.gmail.com");// // 發件人的郵箱的 SMTP 伺服器地址
        props.setProperty("mail.smtp.auth", "true");//請求認證，引數名稱與具體實現有關 指定驗證為true

        // 建立驗證器
        Authenticator auth = new Authenticator() {
            @Override
            public PasswordAuthentication getPasswordAuthentication() {
                // 網易郵箱Yang_li_g 使用者名稱 hao123授權碼,改為自己的賬號和密碼
                return new PasswordAuthentication("Yang_li_g", "hao123");
            }
        };

        Session session = Session.getInstance(props, auth);

        // 2.建立一個Message，它相當於是郵件內容
        Message message = new MimeMessage(session);
        //這裡也要改和上面對應，注意字尾和上面設定的一樣不然會報錯
        message.setFrom(new InternetAddress("Yang_li_g@163.com")); // 設定傳送者

        message.setRecipient(RecipientType.TO, new InternetAddress(email)); // 設定傳送方式與接收者

        message.setSubject("使用者啟用");
        // message.setText("這是一封啟用郵件，請<a href='#'>點選</a>");

        message.setContent(emailMsg, "text/html;charset=utf-8");

        // 3.建立 Transport用於將郵件傳送

        Transport.send(message);
    }
}

