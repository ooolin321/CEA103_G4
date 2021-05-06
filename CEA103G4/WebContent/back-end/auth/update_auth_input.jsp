<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.auth.model.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.fun.model.*"%>
<%
	List<AuthVO> authVO = (List<AuthVO> ) request.getAttribute("authVO");
%>
<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>
<%
	FunVO funVO = (FunVO) request.getAttribute("funVO");
%>

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="funSvc" scope="page" class="com.fun.model.FunService" />
<jsp:useBean id="authSvc" scope="page" class="com.auth.model.AuthService" />

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>權限資料修改 - addAuth.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
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
				<h3>權限資料新增 - updateAuth.jsp</h3>
			</td>
			<td>
				<h4>
					<a
						href="<%=request.getContextPath()%>/back-end/auth/selectAuth.jsp"><img
						src="<%=request.getContextPath()%>/images/tomcat.png" width="100"
						height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>資料新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/auth/auth.do" name="form1">
		<table>
			<tr>
				<td>員工編號:<font color=red><b>*</b></font></td>
				<td>${empVO.empno}</td>
				
			</tr>			
				<tr>
				<td>選擇功能名稱:<font color=red><b>*</b></font></td>
				<td>
					<ul>
					
							<li><input name = "funno" value="${funVO.funno}" type="hidden"></input>${funSvc.getOneFun(authVO.funno).funName}
							<select size="1" name="auth_no">
										<option value="1" ${(authVO.auth_no==1)? 'selected':''}>開</option>
										<option value="0" ${(authVO.auth_no==0)? 'selected':''}>關</option>
									</select></li>
						
					</ul> 
				</td>
			</tr>
		
		</table>
		<br> <input type="hidden" name="action" value="update"> 
		<input type="hidden" name="empno" value="${empVO.empno}">
		<input type="hidden" name="funno" value="${funVO.funno}">
		<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>">
		<input type="hidden" name="whichPage" value="<%=request.getParameter("whichPage")%>">
		<input type="submit" id = "submit" value="送出新增">
	</FORM>
</body>
<script>
/* 	let sub = document.getElementById("submit");
 	sub.addEventListener(type, callback, capture)

			sub.addEventListener("onclick", function(){
		let chx = document.getElementsByName("auth_no");
		for(let i = 0; i < chx.length; i++){
			let checkbox = chx[i];
			checkbox.checked = true;
	}
	},false); */


</script>


</html>