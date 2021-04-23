<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.auth.model.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.fun.model.*"%>
<%
	AuthVO authVO = (AuthVO) request.getAttribute("authVO");
%>
<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>
<%
	FunVO funVO = (FunVO) request.getAttribute("funVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>權限資料新增 - addAuth.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
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

<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" />
<jsp:useBean id="funSvc" scope="page" class="com.fun.model.FunService" />


</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>權限資料新增 - addAuth.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/auth/selectAuth.jsp"><img
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
				<td>選擇員工姓名:${empVO.ename}<font color=red><b>*</b></font></td>
				<td><select size="1" name="empno">
					<c:forEach var="empVO" items="${empSvc.all}">
					<option value="${authVO.empno}" ${(authVO.empno==empVO.empno)? 'selected':'' } >${empVO.ename}
					</c:forEach>     
					</select>
				</td>
<!-- 				<input type="TEXT" name="ename" size="45" -->
<%-- 					value="<%=(authVO == null) ? "" : authVO.getEname()%>" /></td> --%>
<!-- 			</tr> -->

<!-- 			<tr> -->
<!-- 				<td>職位:</td> -->
<!-- 				<td><input type="TEXT" name="job" size="45" -->
<%-- 					value="<%=(authVO == null) ? "" : authVO.getJob()%>" /></td> --%>
<!-- 			</tr> -->

<!-- 			<tr> -->
<!-- 				<td>身份證字號:</td> -->
<!-- 				<td><input type="TEXT" name="id" size="45" -->
<%-- 					value="<%=(authVO == null) ? "A123456789" : authVO.getId()%>" /></td> --%>
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>性別:</td> -->
<!-- 				<td><select size="1" name="gender"> -->
<%-- 						<option value="1" ${(authVO.gender==0)? 'selected':''}>男</option> --%>
<%-- 						<option value="0" ${(authVO.gender==0)? 'selected':''}>女</option>        --%>
<!-- 					</select></td> -->

<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>生日:</td> -->
<!-- 				<td><input name="dob" id="f_date2" type="text"></td> -->
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>地址:</td> -->
<!-- 				<td> -->
<!-- 				<div id="twzipcode"></div> -->
			
<!-- 				<input type="TEXT" name="addr" size="45" -->
<%-- 					value="<%=(authVO==null) ? "" : authVO.getAddr()%>" /></td> --%>
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>e-mail:</td> -->
<!-- 				<td><input type="TEXT" name="email" size="45" -->
<%-- 					value="<%=(authVO == null) ? "" : authVO.getEmail()%>" /></td> --%>
<!-- 			</tr> -->
<!-- 			<tr> -->
<!-- 				<td>薪水:</td> -->
<!-- 				<td><input type="TEXT" name="sal" size="45" -->
<%-- 					value="<%=(authVO == null) ? "" : authVO.getSal()%>" /></td> --%>
<!-- 			</tr> -->

<!-- 			<tr> -->
<!-- 				<td>狀態:</td> -->
<!-- 				<td><select size="1" name="state"> -->
<%-- 						<option value="1" ${(authVO.state==1)? 'selected':''}>在職</option> --%>
<%-- 						<option value="0" ${(authVO.state==0)? 'selected':''}>離職</option> --%>
<!-- 					</select> -->
<!-- 				</td> -->
<!-- 			</tr> -->

<!-- 			<tr> -->
<!-- 				<td>到職日期:</td> -->
<!-- 				<td><input name="hiredate" id="f_date1" type="text"></td> -->
<!-- 			</tr> -->

		</table>
		<br> <input type="hidden" name="action" value="insert">
		
			<input type="submit" value="送出新增">
	</FORM>
</body>



</html>