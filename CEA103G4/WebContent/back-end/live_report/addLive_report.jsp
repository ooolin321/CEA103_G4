<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live_report.model.*"%>

<%
	Live_reportVO live_reportVO = (Live_reportVO) request.getAttribute("live_reportVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>直播檢舉資料新增 - addLive_report.jsp</title>

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
				<h3>直播檢舉新增 - addLive_report.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="select_page.jsp"><img src="images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>直播檢舉新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="live_report.do" name="form1">
		<table>

			<tr>
				<td>直播檢舉內容:</td>
				<td><input type="TEXT" name="live_report_content" size="45"
					value="live_reportVO.getLive_report_content()%>" /></td>
			</tr>

			<tr>
				<td>直播編號:</td>
				<td><input type="TEXT" name="live_no" size="45"
					value="<%=(live_reportVO == null) ? "8001" : live_reportVO.getLive_no()%>" /></td>
			</tr>

			<tr>
				<td>檢舉者ID:</td>
				<td><input type="TEXT" name="user_id" size="45"
					value="<%=(live_reportVO == null) ? "abcd01" : live_reportVO.getUser_id()%>" /></td>
			</tr>


			<tr>
				<td>檢舉處理狀態:</td>
				<td><select name="live_report_state">
						<option value="0">未處理</option>
						<option value="1">審核處理</option>
						<option value="2">審核不通過</option>
				</select></td>
			</tr>

			<tr>
				<td>圖片上傳:</td>
				<td><input type="FILE" name="photo" /></td>
			</tr>

		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>

</html>