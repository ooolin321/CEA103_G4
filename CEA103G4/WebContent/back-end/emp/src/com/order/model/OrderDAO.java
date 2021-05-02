package com.order.model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.sql.Connection;

import com.detail.model.DetailDAO;
import com.detail.model.DetailVO;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.mem.model.MemDAO;

import tools.jdbcUtil_CompositeQuery_Order;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

public class OrderDAO implements OrderDAO_Interface {

	private static DataSource ds = null;
	static {
		try {
			Context ctx = new InitialContext();
			ds = (DataSource) ctx.lookup("java:comp/env/jdbc/bookshop");

		} catch (NamingException e) {
			e.printStackTrace();
		}
	}

	private static final String INS_ORD = "INSERT INTO ORDERS VALUES(('OD'||LPAD(ODID_SEQ.NEXTVAL,10,'0')),?,?,?,?,DEFAULT,?,?,?,?,?,?,DEFAULT,?)";
	private static final String UPD_ORD = "UPDATE ORDERS SET REC_NAME=?, REC_TEL=?, REC_ADD=?, ORDER_PAY=?, DELIVERY=?, ORDER_STATUS=?, MEM_NOTE=? WHERE ORDER_ID=?";
	private static final String DEL_ORD = "UPDATE ORDERS SET ORDER_QTY=0, ORDER_TOTAL=0, GET_BONUS=0, USE_BONUS=0, ORDER_STATUS=4 WHERE ORDER_ID=?";
	private static final String GO_ORD =  "UPDATE ORDERS SET ORDER_STATUS=? WHERE ORDER_ID=?";
	private static final String FIND_ID = "SELECT * FROM ORDERS WHERE ORDER_ID=?";
	private static final String GET_ALL = "SELECT * FROM ORDERS ORDER BY ORDER_ID";


	@Override
	public void doCreate(OrderVO odvo) {
		
		Connection con =null;
		PreparedStatement pstmt =null;
		try {
			
			con = ds.getConnection();
			pstmt = con.prepareStatement(INS_ORD);
			
			pstmt.setString(1,odvo.getMem_id());
			pstmt.setString(2,odvo.getRec_name());
			pstmt.setString(3,odvo.getRec_tel());
			pstmt.setString(4,odvo.getRec_add());
			pstmt.setInt(5,odvo.getOrder_qty());
			pstmt.setInt(6,odvo.getOrder_total());
			pstmt.setInt(7,odvo.getOrder_pay());
			pstmt.setInt(8,odvo.getDelivery());
			pstmt.setInt(9,odvo.getGet_bonus());
			pstmt.setInt(10,odvo.getUse_bonus());
			pstmt.setString(11, odvo.getMem_note());
			
			pstmt.executeUpdate();		

		}catch(SQLException sqle) {
			throw new RuntimeException("▲Error： [doCreate]" + sqle.getMessage());
		}finally {
			if(pstmt !=null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if(con !=null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		
	}
	
	@Override
	public void doCreateODDT(OrderVO odCartVO, List<DetailVO> cartlist, Double newBonus) {
		Connection con = null;
		PreparedStatement pstmt = null;
		
		
		try {
			
			//訂單ID
			String[] orid = {"order_id"};
			//擷取訂單ID(轉送明細)
			String orid_next =null;
			con = ds.getConnection();
			pstmt = con.prepareStatement(INS_ORD, orid);
			
			//關閉自動更新
			con.setAutoCommit(false);

			pstmt.setString(1, odCartVO.getMem_id());
			pstmt.setString(2, odCartVO.getRec_name());
			pstmt.setString(3, odCartVO.getRec_tel());
			pstmt.setString(4, odCartVO.getRec_add());
			pstmt.setInt(5, odCartVO.getOrder_qty());
			pstmt.setInt(6, odCartVO.getOrder_total());
			pstmt.setInt(7, odCartVO.getOrder_pay());
			pstmt.setInt(8, odCartVO.getDelivery());
			pstmt.setInt(9, odCartVO.getGet_bonus());
			pstmt.setInt(10, odCartVO.getUse_bonus());
			pstmt.setString(11, odCartVO.getMem_note());

			pstmt.executeUpdate();
			
			//訂單與明細對應的自增主鍵值
			ResultSet rs = pstmt.getGeneratedKeys();
			if (rs.next()) {
				orid_next =  rs.getString(1); 
				
				System.out.println("取出訂單ID： " + orid_next);
			}else {
				System.out.println("▲Error： [無法取得訂單ID]");
			}rs.close();
			
			//新增訂單明細
			DetailDAO dtDAO = new DetailDAO();
			for(DetailVO dtVO:cartlist) {
				dtVO.setOrder_id(orid_next);
				dtDAO.doCreate(dtVO, con);
			}
			
			//修改會員紅利點數
			MemDAO memDAO = new MemDAO();
			memDAO.updateBonus(odCartVO.getMem_id(), newBonus, con);
			
			con.commit();
			con.setAutoCommit(true);
			
			//新增出貨QRCode
			orderqr(orid_next);
			
			System.out.println("本次新增訂單："+orid_next+" 包含"+cartlist.size()+" 筆明細.");
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [doCreateOD]" + sqle.getMessage());
		} catch (IOException e) {
			e.printStackTrace();
		} catch (WriterException e) {
			e.printStackTrace();
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}


	@Override
	public void shipment(OrderVO odvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(GO_ORD);
			con.setAutoCommit(false);

			pstmt.setInt(1, odvo.getOrder_status());
			pstmt.setString(2, odvo.getOrder_id());

			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [shipment]" + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}

	@Override
	public void cancel(String order_id) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {
			con = ds.getConnection();
			pstmt = con.prepareStatement(DEL_ORD);
			con.setAutoCommit(false);
			pstmt.setString(1, order_id);
			pstmt.executeUpdate();
			con.commit();
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [cancel]" + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
	}
	
	@Override
	public void update(OrderVO odvo) {
		Connection con = null;
		PreparedStatement pstmt = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(UPD_ORD);
			con.setAutoCommit(false);

			pstmt.setString(1, odvo.getRec_name());
			pstmt.setString(2, odvo.getRec_tel());
			pstmt.setString(3, odvo.getRec_add());
			pstmt.setInt(4, odvo.getOrder_pay());
			pstmt.setInt(5, odvo.getDelivery());
			pstmt.setInt(6, odvo.getOrder_status());
			pstmt.setString(7, odvo.getMem_note());
			pstmt.setString(8, odvo.getOrder_id());

			pstmt.executeUpdate();

			con.commit();

		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [Update]" + sqle.getMessage());
		} finally {
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}

	}

	@Override
	public OrderVO findbyid(String order_id) {
		OrderVO odVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(FIND_ID);
			pstmt.setString(1, order_id);
			con.setAutoCommit(false);

			rs = pstmt.executeQuery();
			while (rs.next()) {
				odVO = new OrderVO();
				odVO.setOrder_id(rs.getString("order_id"));
				odVO.setMem_id(rs.getString("mem_id"));
				odVO.setRec_name(rs.getString("rec_name"));
				odVO.setRec_tel(rs.getString("rec_tel"));
				odVO.setRec_add(rs.getString("rec_add"));
				odVO.setOrder_date(rs.getTimestamp("order_date"));
				odVO.setOrder_qty(rs.getInt("order_qty"));
				odVO.setOrder_total(rs.getInt("order_total"));
				odVO.setOrder_pay(rs.getInt("order_pay"));
				odVO.setDelivery(rs.getInt("delivery"));
				odVO.setGet_bonus(rs.getInt("get_bonus"));
				odVO.setUse_bonus(rs.getInt("use_bonus"));
				odVO.setOrder_status(rs.getInt("order_status"));
				odVO.setMem_note(rs.getString("mem_note"));
			}

			con.commit();
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲Error： [Findbytime]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return odVO;
	}

	@Override
	public List<OrderVO> getAll() {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO odVO = null;
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {

			con = ds.getConnection();
			pstmt = con.prepareStatement(GET_ALL);
			rs = pstmt.executeQuery();
			con.setAutoCommit(false);

			while (rs.next()) {
				odVO = new OrderVO();
				odVO.setOrder_id(rs.getString("order_id"));
				odVO.setMem_id(rs.getString("mem_id"));
				odVO.setRec_name(rs.getString("rec_name"));
				odVO.setRec_tel(rs.getString("rec_tel"));
				odVO.setRec_add(rs.getString("rec_add"));
				odVO.setOrder_date(rs.getTimestamp("order_date"));
				odVO.setOrder_qty(rs.getInt("order_qty"));
				odVO.setOrder_total(rs.getInt("order_total"));
				odVO.setOrder_pay(rs.getInt("order_pay"));
				odVO.setDelivery(rs.getInt("delivery"));
				odVO.setGet_bonus(rs.getInt("get_bonus"));
				odVO.setUse_bonus(rs.getInt("use_bonus"));
				odVO.setOrder_status(rs.getInt("order_status"));
				odVO.setMem_note(rs.getString("mem_note"));
				list.add(odVO);
			}

			con.commit();
		} catch (SQLException sqle) {
			try {
				con.rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			throw new RuntimeException("▲▲Error： [GetAll]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
	public List<OrderVO> allSelect(Map<String, String[]> map) {
		List<OrderVO> list = new ArrayList<OrderVO>();
		OrderVO odVO = null;
		
		Connection con = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try {

			con = ds.getConnection();
			String finalSQL = "SELECT * FROM ORDERS "
			+ jdbcUtil_CompositeQuery_Order.get_WhereCondition(map)
			+ "ORDER BY ORDER_ID DESC";
			pstmt = con.prepareStatement(finalSQL);
			

			rs = pstmt.executeQuery();
			
			while (rs.next()) {
				odVO = new OrderVO();
				odVO.setOrder_id(rs.getString("order_id"));
				odVO.setMem_id(rs.getString("mem_id"));
				odVO.setRec_name(rs.getString("rec_name"));
				odVO.setRec_tel(rs.getString("rec_tel"));
				odVO.setRec_add(rs.getString("rec_add"));
				odVO.setOrder_date(rs.getTimestamp("order_date"));
				odVO.setOrder_qty(rs.getInt("order_qty"));
				odVO.setOrder_total(rs.getInt("order_total"));
				odVO.setOrder_pay(rs.getInt("order_pay"));
				odVO.setDelivery(rs.getInt("delivery"));
				odVO.setGet_bonus(rs.getInt("get_bonus"));
				odVO.setUse_bonus(rs.getInt("use_bonus"));
				odVO.setOrder_status(rs.getInt("order_status"));
				odVO.setMem_note(rs.getString("mem_note"));
				list.add(odVO);
			}

			
		} catch (SQLException sqle) {
			throw new RuntimeException("▲Error： [Findbytime]" + sqle.getMessage());
		} finally {
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (pstmt != null) {
				try {
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace(System.err);
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (Exception e) {
					e.printStackTrace(System.err);
				}
			}
		}
		return list;
	}
//	
	public void orderqr(String orid_next) throws IOException, WriterException {
//		File urlFile =new File("logistics.jsp");
//		String url = "http://35.234.43.11/EA103G4/front-end/logistics/logistics.jsp?order_id=";
		String url = "http://34.80.154.240/EA103G4/front-end/logistics/logistics.jsp?order_id=";
//		String url = "http://localhost/EA103G4/front-end/logistics/logistics.jsp?order_id=";
		String format = "jpg";
		String order_id = orid_next;
		String logisticsURL = url+order_id;
		
		int width = 300;
		int height = 300;
		
		// 設定編碼格式與容錯率
		Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
		hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
		hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
		// 設置QRCode的存放目錄、檔名與圖片格式
		String catPath = System.getProperty("catalina.base");
		catPath = catPath.substring(0, catPath.indexOf(".metadata")) + "EA103G4/WebContent/images/cliff/orderQR/";
//		System.out.println(catPath);
//		String filePath = "C:/EA103_WebApp/eclipse_workspace1/EA103G4/WebContent/images/cliff/orderQR/";
		String fileName = order_id + ".jpg";
		Path path = FileSystems.getDefault().getPath(catPath, fileName);
		// 開始產生QRCode
		BitMatrix matrix = new MultiFormatWriter().encode(logisticsURL, BarcodeFormat.QR_CODE, width, height, hints);
		// 把產生的QRCode存到指定的目錄
		MatrixToImageWriter.writeToPath(matrix, format, path);
//		System.out.println("path=" + path.toString());
		// 轉成Base64
		File file = new File(path.toString());
		InputStream input = new FileInputStream(file);
		String result = convert2Byte(input);
//		System.out.println("result=" + result);
		
	}
	private String convert2Byte(InputStream input) throws IOException {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		byte[] buff = new byte[100];
		int length = 0;
		while ((length = input.read(buff, 0, 100)) > 0) {
			baos.write(buff, 0, length);
		}
		byte[] in2b = baos.toByteArray();
		baos.flush();
		baos.close();
		input.close();
		return new String(Base64.getEncoder().encodeToString(in2b));

	}
	

}
