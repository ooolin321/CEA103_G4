<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>
 <link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css">
 <link rel="stylesheet" href="<%=request.getContextPath()%>/static/admin/css/style.css">

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
  <!-- Font-icon css-->
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  
 <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
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
				<h3>員工資料 - ListOneEmp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/backendIndex.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table class="table">
		<tr class="table-info">
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>職位</th>
			<th>身分證字號</th>
			<th>性別</th>
			<th>生日</th>
			<th>地址</th>
			<th>email</th>
			<th>薪水</th>
			<th>狀態</th>
			<th>到職日期</th>
			<th>員工密碼</th>
			<th>修改</th>

		</tr>

			<tr>
				<td><A href="<%=request.getContextPath()%>/emp/emp.do?empno=${empVO.empno}&action=getOne_From">${empVO.empno}</A></td>
				<td>${empVO.ename}</td>
				<td>${empVO.job}</td>
				<td >${empVO.id}</td>	
				<c:choose>
					<c:when test="${empVO.gender==0}">
						<td>女</td>
					</c:when>
					<c:when test="${empVO.gender==1}">
						<td>男</td>
					</c:when>
				</c:choose>
				<td>${empVO.dob}</td>
				<td>${empVO.city}${empVO.dist}${empVO.addr}</td>
				<td >${empVO.email}</td>
				<td>${empVO.sal}</td>
				
					<c:choose>
					<c:when test="${empVO.state==0}">
						<td>離職</td>
					</c:when>
					<c:when test="${empVO.state==1}">
						<td>在職</td>
					</c:when>
				</c:choose>
				<td><fmt:formatDate value="${empVO.hiredate}"
						pattern="yyyy-MM-dd" /></td>
				<td>${empVO.emp_pwd}</td>


				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/emp/emp.do"
						style="margin-bottom: 0px;">
						<input class="btn btn-primary" type="submit" value="修改"> 
						<input type="hidden" name="empno" value="${empVO.empno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->

						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>

			</tr>

	</table>

</body>
</html>