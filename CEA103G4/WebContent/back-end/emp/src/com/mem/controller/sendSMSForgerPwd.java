package com.mem.controller;

import java.io.*;
import java.util.*;
import java.net.*;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.mem.model.MemService;
import com.mem.model.MemVO;

import redis.clients.jedis.Jedis;


@WebServlet("/mem/sendSMSForgerPwd.do")
public class sendSMSForgerPwd extends HttpServlet {
	
	private HttpURLConnection sms_gw = null;
       
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		doPost(req, res);
	}

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");
		res.setContentType("text/html;charset=UTF-8");
		
		String memId = req.getParameter("mem_id");
		System.out.println(memId);
		
		String code = getCode();
		
		MemService memSvc = new MemService();
		MemVO memVO = memSvc.getOneMem(memId);
		
		req.getSession().setAttribute("mem_id", memId);
		
		Jedis jedis = new Jedis("localhost", 6379);
		jedis.auth("123456");
		jedis.set(memId, code);
		jedis.expire(memId, 300); //過期時間五分鐘
		jedis.close();
		
		SendSMS(memVO.getMem_tel(), code);
		
		String url = "/front-end/member/updateForForgetPwd.jsp";
		req.getRequestDispatcher(url).forward(req, res);
	}
	
	private void SendSMS(String phone, String code){
		try{			
			// 設定變數
			StringBuffer MSGData = new StringBuffer();
			
			// 設定參數
			String username = "jweitw199583";	// 帳號
			String password = "weiwei199583";		// 密碼
			String mobile = phone;	// 電話
			String message = "請將以下驗證碼輸入至修改密碼頁面,完成修改密碼程序  :" + code;	// 簡訊內容	

			MSGData.append("username="+username);
			MSGData.append("&password="+password);
			MSGData.append("&mobile="+mobile);
			MSGData.append("&message=");
			MSGData.append(UrlEncode(message.toString().getBytes("big5")));
			SendToGW(MSGData.toString());
		}
		catch(Exception e){ 
			System.out.println("程式錯誤!"); 
		} 
	}

	// 傳送至 TwSMS API server
    private boolean SendToGW(String post)
    {
        try {
            URL url = new URL("http://api.twsms.com/json/sms_send.php");
            sms_gw = (HttpURLConnection)url.openConnection();
            sms_gw.setDoInput(true);
            sms_gw.setDoOutput(true);
            sms_gw.setUseCaches(false);
            sms_gw.setRequestMethod("POST");
            
            DataOutputStream dos = new DataOutputStream(sms_gw.getOutputStream());
            dos.writeBytes(post);
            dos.flush();
            dos.close();
            BufferedReader br = new BufferedReader(new InputStreamReader(sms_gw.getInputStream()));
            String strResponse = "";
            String readLine;
            while ((readLine = br.readLine()) != null) {
                strResponse += readLine;
            }
            br.close();
			System.out.println("回傳碼:" + strResponse);
            return true;
        }
        catch (Exception e) {
            System.out.println("無法連接 TwSMS API Server!"); 
            return false;
        }
    }

	// UrlEncode Function
    private String UrlEncode(byte[] src){
        byte[] ASCIIMAP =
            {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 97, 98, 99, 100, 101, 102};
        int pivot = 0;
        byte[] data = new byte[src.length * 3];
        for(int i = 0; i < src.length; i++){
            if(src[i] == 0){
                data[pivot++] = 37;
                data[pivot++] = 48;
                data[pivot++] = 48;
            }

            else if(src[i] < 0){
                data[pivot++] = 37;
                data[pivot++] = ASCIIMAP[(src[i] >> 4) & 0x0f];
                data[pivot++] = ASCIIMAP[src[i] & 0x0f];
            }
            else{
                char cc = (char)src[i];

                if(Character.isLetterOrDigit(cc)){
                    data[pivot++] = src[i];
                }
                else if(cc == ' '){
                    data[pivot++] = 43;
                }
                else if(cc == '.' || cc == '-' || cc == '*' || cc == '_'){
                    data[pivot++] = src[i];
                }
                else{
                    data[pivot++] = 37;
                    data[pivot++] = ASCIIMAP[(src[i] >> 4) & 0x0f];
                    data[pivot++] = ASCIIMAP[src[i] & 0x0f];
                }
            }
        }
        if(pivot > 0) return new String(data, 0, pivot);
        return "";
    }
    
    public String getCode() {
    	String code = "";
		for(int i=0; i<4; i++) {
			Integer ranNum = (int) (Math.random() * 10);
			code += ranNum.toString();
		}
    	return code;
    }

}
