package com.util;

import com.bookclub.model.*;
import com.mem.model.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletResponse;
import javax.servlet.http.Part;

public class ReadPic {
//	show出圖片到前台
	public static void outputImg(ServletContext context, ServletResponse res, byte[] img) throws IOException {
		ServletOutputStream out = null;
		try {
			out = res.getOutputStream(); // Servlet 講義 p140 建立二位元輸出資料流
			out.write(img);
			out.flush();
			out.close();
		} catch (Exception e) {
			InputStream in = context.getResourceAsStream("/images/not_found_picture.png"); // Servlet 講義 p77 方法18
																							// 設定如果沒有圖片
			byte[] b = new byte[in.available()];
			in.read(b);
			out.write(b);
			in.close();
		}
	}

	public static byte[] getPartPicture(Part part) throws IOException {
		InputStream in = part.getInputStream(); // Servlet 講義 p80頁 part轉InputStream
		byte[] b = new byte[in.available()];
		ByteArrayOutputStream baos = new ByteArrayOutputStream(); // 緩衝
		in.read(b);
		baos.write(b);
		baos.flush();
		baos.close();
		in.close();
		return baos.toByteArray(); // 用ByteArrayOutputStream 的 toByteArray 獲取byte[]
	}

//	驗證前台傳來的圖片格式
	public static List<String> getMimeType() {
		List<String> mime = new ArrayList<String>();
		mime.add("image/jpg");
		mime.add("image/png");
		mime.add("image/jpeg");
		mime.add("image/bmp");
		mime.add("image/gif");
		mime.add("image/webp");
		return mime;
	}

	public static String getFileNameFromPart(Part part) {
		String header = part.getHeader("content-disposition");
		String filename = new File(header.substring(header.lastIndexOf("=") + 2, header.length() - 1)).getName();
		if (filename.length() == 0) {
			return null;
		}
		return filename;
	}

//	跑資料庫假圖片
	public static void setImg(String table, String col) throws IOException {
		final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		final String USER = "BOOKSHOP";
		final String PASSWORD = "123456";
		final String UPDATE = "UPDATE " + table + " " + "SET " + col + " = ?";
//		File file = new File("C:\\Users\\asus\\Desktop\\pictrue\\bookclub1.PNG");
		File file = new File("C:\\Users\\user\\Desktop\\test_front\\images\\bookclub\\bookclub1.PNG");
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[in.available()];
		in.read(b);
		out.write(b);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setBytes(1, out.toByteArray());

			pstmt.executeUpdate();
			System.out.println("圖片假資料更新完畢");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				out.close();
				in.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

//	跑資料庫假圖片
	public static void setImg2(String table,String col,String picName, String bc_id) throws IOException {
		final String DRIVER = "oracle.jdbc.driver.OracleDriver";
		final String URL = "jdbc:oracle:thin:@localhost:1521:xe";
		final String USER = "BOOKSHOP";
		final String PASSWORD = "123456";
		final String UPDATE = "UPDATE " + table + " " + "SET " + col + " = ? WHERE BC_ID = " + bc_id;
		System.out.println(UPDATE);
		File file = new File("C:\\EA103_WebApp\\eclipse_WTP_workspace1\\EA103G4\\WebContent\\images\\bookclub\\" + picName);
//		File file = new File("C:\\EA103_WebApp\\eclipse_WTP_workspace1\\BookShop_BookClub1012\\WebContent\\images\\member\\" + picName);
		FileInputStream in = new FileInputStream(file);
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		byte[] b = new byte[in.available()];
		in.read(b);
		out.write(b);

		try {
			Class.forName(DRIVER);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			con = DriverManager.getConnection(URL, USER, PASSWORD);
			pstmt = con.prepareStatement(UPDATE);
			pstmt.setBytes(1, out.toByteArray());

			pstmt.executeUpdate();
			System.out.println("圖片假資料更新完畢");
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			try {
				pstmt.close();
				con.close();
				out.close();
				in.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) throws IOException {

		BookClubService2 Svc = new BookClubService2();
		List<BookClubVO> list = Svc.getAll();
		for (int i = 0; i < list.size(); i++) {
			// 1.資料庫表格名稱 2.資料庫欄位 3.檔案位置 4.Where 
			setImg2("BOOKCLUB","BC_COVER_PIC", "bookclub" + (i+1) + ".PNG", "'" + "BC000" + (i+1) + "'");
		}
	}
}
