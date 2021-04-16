<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.live.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	LiveJDBCDAO dao = new LiveJDBCDAO();
    List<LiveVO> list = dao.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有直播資料- listAllLive1_byDAO.jsp</title>

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
	width: 800px;
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

<h4>此頁練習採用 EL 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>所有直播資料- listAllLive1_byDAO.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>直播編號</th>
		<th>直播分類</th>
		<th>直播名稱</th>
		<th>直播時間</th>
		<th>直播狀態</th>
		<th>直播使用者ID</th>
		<th>EMPNO</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="liveVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${liveVO.live_no}</td>
			<td>${liveVO.live_type}</td>
			<td>${liveVO.live_name}</td>
			<td>${liveVO.live_time}</td>
			<td>${liveVO.live_state}</td>
			<td>${liveVO.user_id}</td>
			<td>${liveVO.empno}</td>
		</tr>
	</c:forEach>
</table>

<%@ include file="page2.file" %>
</body>
</html>