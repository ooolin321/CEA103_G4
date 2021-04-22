<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.fun.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	FunVO funVO = (FunVO) request.getAttribute("funVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<%
	FunService funSvc = new FunService();
	List<FunVO> list = funSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>所有網站功能 - listAllFun.jsp</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css">
 <link rel="stylesheet" href="<%=request.getContextPath()%>/static/admin/css/style.css">
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
	max-width: 92px;
	min-width: 92px;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有功能名稱 - listAllFun.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/fun/selectFun.jsp"><img src="<%=request.getContextPath()%>/images/back1.gif" width="100"
						height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<table>
		<tr>
			<th>網站功能編號</th>
			<th>功能名稱</th>
			<th>網站功能狀態</th>
			<th>修改</th>
<!-- 			<th>刪除</th> -->
		</tr>
<%-- 		<%@ include file="page1.file"%> --%>
		<c:forEach var="funVO" items="${list}" >

			<tr>
				<td>${funVO.funno}</td>
				<td>${funVO.funName}</td>
				<c:choose>
					<c:when test="${funVO.state==0}">
						<td>關閉</td>
					</c:when>
					<c:when test="${funVO.state==1}">
						<td>開啟</td>
					</c:when>
				</c:choose>
<!-- 				<td><select size="1" name="state"> -->
<%-- 						<option value="1" ${(funVO.state==0)? 'selected':''}>開啟</option> --%>
<%-- 						<option value="0" ${(funVO.state==0)? 'selected':''}>關閉</option> --%>
<!-- 				</select></td> -->

				<td>
					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fun/fun.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> 
						<input type="hidden" name="funno" value="${funVO.funno}"> 
						<input type="hidden" name="action" value="getOne_For_Update">
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>">  <!--只用於:istAllEmp.jsp-->
						
					</FORM>
				</td>


<!-- 				<td> -->
<%-- 					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fun/fun.do" --%>
<!-- 						style="margin-bottom: 0px;"> -->
<!-- 						<input type="submit" value="刪除"> <input type="hidden" -->
<%-- 							name="funno" value="${funVO.funno}"> <input type="hidden" --%>
<!-- 							name="action" value="delete"> -->
<!-- 					</FORM> -->
<!-- 				</td> -->
			</tr>
		</c:forEach>
	</table>

<%-- 	<%@ include file="page2.file"%> --%>

</body>
</html>