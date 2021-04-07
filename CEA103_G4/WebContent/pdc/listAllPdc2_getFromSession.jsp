<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.pdc.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
   // List<EmpVO> list = (List<EmpVO>)session.getAttribute("list"); //EmpServlet.java(Concroller), 存入session的list物件
%> 

<%-- 以下等同第8行--%>
<jsp:useBean id="list" scope="session" type="java.util.List<PdcVO>" />

<html>
<head>
<title>所有商品資料 - listAllPdc2_getFromSession.jsp</title>

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
		 <h3>所有商品資料 - listAllPdc2_getFromSession.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="product" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>商品編號</th>
		<th>商品名稱</th>
		<th>商品說明</th>
		<th>商品價格</th>
		<th>商品原數量</th>
		<th>商品剩餘數量</th>
		<th>商品狀態</th>
		<th>商品照片</th>
		<th>會員帳號</th>
		<th>商品類別編號</th>
		<th>起標價</th>
		<th>直播編號</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="pdcVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		<tr>
			<td>${pdcVO.product_id}</td>
			<td>${pdcVO.product_name}</td>
			<td>${pdcVO.product_info}</td>
			<td>${pdcVO.product_price}</td>
			<td>${pdcVO.product_quantity}</td>
			<td>${pdcVO.product_remaining}</td>
			<td>${pdcVO.product_state}</td>
			<td>${pdcVO.product_photo}</td>
			<td>${pdcVO.user_id}</td>
			<td>${pdcVO.pdtype_id}</td>
			<td>${pdcVO.start_price}</td>
			<td>${pdcVO.live_id}</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>