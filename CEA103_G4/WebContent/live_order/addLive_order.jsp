<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live_order.model.*"%>

<%
	Live_orderVO live_ordeVO = (Live_orderVO) request.getAttribute("live_ordeVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>直播訂單資料新增 - addLive_order.jsp</title>

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
					<a href="select_page.jsp"><img src="images/tomcat.png"
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

	<FORM METHOD="post" ACTION="live_order.do" name="form1">
		<table>
			<tr>
				<td>直播訂單日期:</td>
				<td><input name="order_date" id="f_date1" type="DATE"></td>
			</tr>

			<tr>
				<td>直播訂單狀態:</td>
				<td><input type="TEXT" name="order_state" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getOrder_state()%>" /></td>
			</tr>

			<tr>
				<td>運費:</td>
				<td><input type="TEXT" name="order_shipping" size="45"
					value="<%=(live_ordeVO == null) ? "30" : live_ordeVO.getOrder_shipping()%>" /></td>
			</tr>

			<tr>
				<td>直播訂單商品價格:</td>
				<td><input type="TEXT" name="order_price" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getOrder_price()%>" /></td>
			</tr>

			<tr>
				<td>付款方式:</td>
				<td><input type="TEXT" name="pay_method" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getPay_method()%>" /></td>
			</tr>

			<tr>
				<td>直播訂單付款到期日期:</td>
				<td><input name="pay_deadline" id="f_date1" type="DATE"></td>
			</tr>

			<tr>
				<td>收件人姓名:</td>
				<td><input type="TEXT" name="rec_name" size="45"
					value="<%=(live_ordeVO == null) ? "測試人員" : live_ordeVO.getRec_name()%>" /></td>
			</tr>

			<tr>
				<td>收件人地址:</td>
				<td><input type="TEXT" name="rec_addr" size="45"
					value="<%=(live_ordeVO == null) ? "台灣" : live_ordeVO.getRec_addr()%>" /></td>
			</tr>

			<tr>
				<td>收件人電話:</td>
				<td><input type="TEXT" name="rec_phone" size="45"
					value="<%=(live_ordeVO == null) ? "035123456" : live_ordeVO.getRec_phone()%>" /></td>
			</tr>

			<tr>
				<td>收件人手機:</td>
				<td><input type="TEXT" name="rec_cellphone" size="45"
					value="<%=(live_ordeVO == null) ? "0912345678" : live_ordeVO.getRec_cellphone()%>" /></td>
			</tr>

			<tr>
				<td>物流方式:</td>
				<td><input type="TEXT" name="logistics" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getLogistics()%>" /></td>
			</tr>

			<tr>
				<td>物流狀態:</td>
				<td><input type="TEXT" name="logistics_state" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getLogistics_state()%>" /></td>
			</tr>


			<tr>
				<td>點數折扣:</td>
				<td><input type="TEXT" name="discount" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getDiscount()%>" /></td>
			</tr>

			<tr>
				<td>直播編號:</td>
				<td><input type="TEXT" name="live_no" size="45"
					value="<%=(live_ordeVO == null) ? "8001" : live_ordeVO.getLive_no()%>" /></td>
			</tr>

			<tr>
				<td>買家帳號:</td>
				<td><input type="TEXT" name="user_id" size="45"
					value="<%=(live_ordeVO == null) ? "abcd01" : live_ordeVO.getUser_id()%>" /></td>
			</tr>

			<tr>
				<td>賣家障號:</td>
				<td><input type="TEXT" name="seller_id" size="45"
					value="<%=(live_ordeVO == null) ? "abcd02" : live_ordeVO.getSeller_id()%>" /></td>
			</tr>

			<tr>
				<td>評分:</td>
				<td><input type="TEXT" name="srating" size="45"
					value="<%=(live_ordeVO == null) ? "5" : live_ordeVO.getSrating()%>" /></td>
			</tr>

			<tr>
				<td>評價內容:</td>
				<td><input type="TEXT" name="srating_content" size="45"
					value="<%=(live_ordeVO == null) ? "讚" : live_ordeVO.getSrating_content()%>" /></td>
			</tr>

			<tr>
				<td>回饋點數:</td>
				<td><input type="TEXT" name="point" size="45"
					value="<%=(live_ordeVO == null) ? "0" : live_ordeVO.getPoint()%>" /></td>
			</tr>
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>

</html>