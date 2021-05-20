<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.auth.model.*"%>


<jsp:useBean id="getOne_For_Update" scope="request" type="java.util.Set<AuthVO>" />

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="funSvc" scope="page" class="com.fun.model.FunService" />
<jsp:useBean id="authSvc" scope="page" class="com.auth.model.AuthService" />
<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

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
	max-width: 89px;
	min-width: 89px;
}

table th .AutoNewline {
	width: 500px;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁暫練習採用 Script 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>網站功能資料 - ListOneFun.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/fun/selectFun.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
				<th>員工編號</th>
				<th>員工姓名</th>
				<th>狀態</th>
	
		</tr>
		<tr>
<%-- 										<td>${authVO.funno}</td> --%>
<%-- 										<td>${funSvc.getOneFun(authVO.funno).funName}</td> --%>
										<c:forEach var="authVO" items="${authSvc.all}">
										<td>${empVO.empno}</td>
										<td>${empSvc.getOneEmp(authVO.empno).ename}</td>
										</c:forEach>
<%-- 											<c:choose> --%>
<%-- 											<c:when test="${authVO.auth_no==0}"> --%>
<!-- 												<td>無權限</td> -->
<%-- 											</c:when> --%>
<%-- 											<c:when test="${authVO.auth_no==1}"> --%>
<!-- 												<td>正常</td> -->
<%-- 											</c:when> --%>
<%-- 										</c:choose> --%>
		<tr>
<%-- 			<td>${funVO.funno}</td> --%>
<%-- 			<td>${funVO.funName}</td> --%>
<%-- 			<c:choose> --%>
<%-- 					<c:when test="${funVO.state==0}"> --%>
<!-- 						<td>關閉</td> -->
<%-- 					</c:when> --%>
<%-- 					<c:when test="${funVO.state==1}"> --%>
<!-- 						<td>開啟</td> -->
<%-- 					</c:when> --%>
<%-- 				</c:choose> --%>
			
<!-- 		</tr> -->
	</table>

</body>
</html>