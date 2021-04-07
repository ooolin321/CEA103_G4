<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.msg.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  MsgVO msgVO = (MsgVO) request.getAttribute("msgVO"); //MsgServlet.java(Concroller), 存入req的msgVO物件
%>

<html>
<head>
<title>訊息資料 - listOneMsg.jsp</title>

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
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
  }
  table, th, td {
    border: 1px solid #CCCCFF;
  }
  th, td {
    padding: 5px;
    text-align: center;
  }
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>訊息資料 - ListOneMsg.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>訊息編號</th>
		<th>買家編號</th>
		<th>內容</th>
		<th>賣家編號</th>
		<th>訊息日期</th>
	</tr>
	<tr>
		<td><%=msgVO.getMessage_id()%></td>
		<td><%=msgVO.getUser_id()%></td>
		<td><%=msgVO.getContent()%></td>
		<td><%=msgVO.getSeller_id()%></td>
		<td><%=msgVO.getMessage_time()%></td>
	</tr>
</table>

</body>
</html>