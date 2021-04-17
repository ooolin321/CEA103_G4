<%@ page language="java" contentType="text/html; charset=UTF-8"
    import="java.sql.*,org.json.*" %>

<% 
	Class.forName("com.mysql.cj.jdbc.Driver");
	String connUrl = "jdbc:mysql://database-1.canq3t4lp2za.ap-northeast-1.rds.amazonaws.com:3306/CEA103_G4?serverTimezone=Asia/Taipei";
	Connection conn = DriverManager.getConnection(connUrl, "admin", "12345678");
	
	String qryStmt = "SELECT product_no, product_name, product_info, product_price,product_quantity,product_remaining,product_state,user_id,pdtype_no,start_price,live_no FROM PRODUCT";
	PreparedStatement stmt = conn.prepareStatement(qryStmt);
	ResultSet rs = stmt.executeQuery();
	
	
    JSONArray jsonArray = new JSONArray();
    
    while (rs.next()) {
 
        int columns = rs.getMetaData().getColumnCount();
        JSONObject obj = new JSONObject();
 
        for (int i = 0; i < columns; i++)
            obj.put(rs.getMetaData().getColumnLabel(i + 1).toLowerCase(), rs.getObject(i + 1));
 
        jsonArray.put(obj);
    }
    out.println(jsonArray);

			conn.close();

					
%>
<!-- 照片會出現超多位數碼,未取。 -->
<!-- jsonformatter: https://jsonformatter.curiousconcept.com/# -->

