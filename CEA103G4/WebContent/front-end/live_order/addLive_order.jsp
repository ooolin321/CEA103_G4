<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live_order.model.*"%>

<%
	Live_orderVO live_orderVO = (Live_orderVO) request.getAttribute("live_orderVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>直播訂單資料新增 - addLive_order.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}
</style>

<style>
table {
	width: 450px;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
}

table, th, td {
	border: 0px solid #CCCCFF;
}

th, td {
	padding: 1px;
}
</style>

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>直播訂單新增 - addLive_order.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/live_order/select_page.jsp"><img src="${pageContext.request.contextPath}/images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>直播訂單新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/live_order/live_order.do" name="form1">
		<table>
<!-- 			<tr> -->
<!-- 				<td>直播訂單日期:</td> -->
<!-- 				<td><input name="order_date" id="f_date1" type="DATE"></td> -->
<!-- 			</tr> -->

			<tr>
				<td>直播訂單狀態:</td>
				<td><select name="order_state">
						<option value="0" ${(live_orderVO.order_state==0)? 'selected':'' }>收到訂單</option>
						<option value="1" ${(live_orderVO.order_state==1)? 'selected':'' }>完成訂單</option>
				</select></td>
			</tr>

			<tr>
				<td>運費:</td>
				<td><input type="TEXT" name="order_shipping" size="45" value="<%= (live_orderVO==null)? "30" : live_orderVO.getOrder_shipping()%>"/></td>
				
			</tr>

			<tr>
				<td>直播訂單商品價格:</td>
				<td><input type="TEXT" name="order_price" size="45" value="<%= (live_orderVO==null)? "0" : live_orderVO.getOrder_price()%>" /></td>
			</tr>

			<tr>
				<td>付款方式:</td>
				<td><select name="pay_method">
						<option value="0" ${(live_orderVO.pay_method==0)? 'selected':'' }>錢包</option>
						<option value="1" ${(live_orderVO.pay_method==1)? 'selected':'' }>信用卡</option>
						<option value="2" ${(live_orderVO.pay_method==2)? 'selected':'' }>轉帳</option>
				</select>
			</tr>

<!-- 			<tr> -->
<!-- 				<td>直播訂單付款到期日期:</td> -->
<!-- 				<td><input name="pay_deadline" id="f_date1" type="DATE"></td> -->
<!-- 			</tr> -->

			<tr>
				<td>收件人姓名:</td>
				<td><input type="TEXT" name="rec_name" size="45" value="<%= (live_orderVO==null)? "測試人員" : live_orderVO.getRec_name()%>" /></td>
			</tr>

			<tr>
				<td>收件人地址:</td>
				<td>
				<div id="twzipcode"></div>
				
				
				<input name="rec_addr" type="text" size="45" value="<%= (live_orderVO==null)? "XX路X段XXX巷XX號" : live_orderVO.getRec_addr()%>">
				</td>
				
<!-- 				<td><input type="TEXT" name="rec_addr" size="45" -->
<%-- 					value="<%=(live_orderVO == null) ? "台灣" : live_orderVO.getRec_addr()%>" /></td> --%>
			</tr>

			<tr>
				<td>收件人電話:</td>
				<td><input type="TEXT" name="rec_phone" size="45" value="<%= (live_orderVO==null)? "031234567" : live_orderVO.getRec_phone()%>"  /></td>
			</tr>

			<tr>
				<td>收件人手機:</td>
				<td><input type="TEXT" name="rec_cellphone" size="45" value="<%= (live_orderVO==null)? "0912345678" : live_orderVO.getRec_cellphone()%>"/></td>
			</tr>

			<tr>
				<td>物流方式:</td>
				<td><select name="logistics">
						<option value="0" ${(live_orderVO.logistics==0)? 'selected':'' }>宅配</option>
						<option value="1" ${(live_orderVO.logistics==1)? 'selected':'' }>超商</option>
				</select></td>
			</tr>

			<tr>
				<td>物流狀態:</td>
				<td><select name="logistics_state">
						<option value="0" ${(live_orderVO.logistics_state==0)? 'selected':'' }>未出貨</option>
						<option value="1" ${(live_orderVO.logistics_state==1)? 'selected':'' }>已出貨</option>
						<option value="2" ${(live_orderVO.logistics_state==2)? 'selected':'' }>買家已取貨</option>
				</select></td>
			</tr>


			<tr>
				<td>點數折扣:</td>
				<td><input type="TEXT" name="discount" size="45" value="<%= (live_orderVO==null)? "0" : live_orderVO.getDiscount()%>" /></td>
			</tr>

			<tr>
				<td>直播編號:</td>
				<td><input type="TEXT" name="live_no" size="45" value="<%= (live_orderVO==null)? "8001" : live_orderVO.getLive_no()%>"/></td>
			</tr>

			<tr>
				<td>買家帳號:</td>
				<td><input type="TEXT" name="user_id" size="45" value="<%= (live_orderVO==null)? "abcd01" : live_orderVO.getUser_id()%>"/></td>
			</tr>

			<tr>
				<td>賣家帳號:</td>
				<td><input type="TEXT" name="seller_id" size="45" value="<%= (live_orderVO==null)? "abcd02" : live_orderVO.getSeller_id()%>" /></td>
			</tr>

			<tr>
				<td>評價分數:</td>
				<td><select name="srating">
						<option value="5" ${(live_orderVO.srating==5)? 'selected':''}>5</option>
						<option value="4" ${(live_orderVO.srating==4)? 'selected':''}>4</option>
						<option value="3" ${(live_orderVO.srating==3)? 'selected':''}>3</option>
						<option value="2" ${(live_orderVO.srating==2)? 'selected':''}>2</option>
						<option value="1" ${(live_orderVO.srating==1)? 'selected':''}>1</option>
				</select></td>

			</tr>

			<tr>
				<td>評價內容:</td>
				<td><input type="TEXT" name="srating_content" size="45" value="<%= (live_orderVO==null)? "讚" : live_orderVO.getSrating_content()%>" /></td>
			</tr>

			<tr>
				<td>回饋點數:</td>
				<td><input type="TEXT" name="point" size="45" value="<%= (live_orderVO==null)? "0" : live_orderVO.getPoint()%>" /></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>
<script>
$("#twzipcode").twzipcode({
	zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
	css: ["city form-control", "town form-control"], // 自訂 "城市"、"地別" class 名稱 
	countyName: "city", // 自訂城市 select 標籤的 name 值
	districtName: "town", // 自訂區別 select 標籤的 name 值
	countySel: "<%=(live_orderVO==null)? "" :live_orderVO.getCity()%>",
	districtSel: "<%=(live_orderVO==null)? "" :live_orderVO.getTown()%>",
	zipcodeSel: "<%=(live_orderVO==null)? "" :live_orderVO.getZipcode()%>"
	});
</script>
</html>