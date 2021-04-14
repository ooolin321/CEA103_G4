<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

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
				<h3>員工資料 - ListOneEmp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/emp/selectEmp.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<table>
		<tr>
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>職位</th>
			<th>身分證字號</th>
			<th>性別</th>
			<th>生日</th>
			<th>地址</th>
			<th>薪水</th>
			<th>狀態</th>
			<th>到職日期</th>
			<th>員工密碼</th>
		</tr>
		<tr>
			<td><%=empVO.getEmpno()%></td>
			<td><%=empVO.getEname()%></td>
			<td><%=empVO.getJob()%></td>
			<td><%=empVO.getId()%></td>
			<td><%=empVO.getGender()%></td>
			<td><%=empVO.getDob()%></td>
			<td><%=empVO.getAddr()%></td>
			<td><%=empVO.getSal()%></td>
			<td><%=empVO.getState()%></td>
			<td><%=empVO.getHiredate()%></td>
			<td><%=empVO.getEmp_pwd()%></td>
		</tr>
	</table>

</body>
</html>