<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.auth.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	AuthService authSvc = new AuthService();
	List<AuthVO> list = authSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<jsp:useBean id="funSvc" scope="page" class="com.fun.model.FunService" />
<html>
<head>
<title>所有權限資料 - listAllAuth.jsp</title>
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
	
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁練習採用 EL 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有權限資料 - listAllAuth.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/auth/selectAuth.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100"
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

	<table  class="layui-table layui-form">
		<tr>
			<th>功能編號</th>
			<th>員工編號</th>
			<th>狀態</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="authVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr ${(authVO.empno==param.empno) ? 'bgcolor=#CCCCFF':''}>
				<td>${authVO.empno}</td>
				<td>${authVO.funno}</td>
				<td>${authVO.auth_no}</td>
				
<%-- 			<td><c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%--                     <c:if test="${empVO.deptno==deptVO.deptno}"> --%>
<%-- 	                    ${deptVO.deptno}【${deptVO.dname} - ${deptVO.loc}】 --%>
<%--                     </c:if> --%>
<%--                 </c:forEach> --%>
<!-- 			</td> -->

<%-- ${deptSvc.getOneDept(empVO.deptno).dname} --%>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/auth/auth.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="修改"> 
						<input type="hidden" name="authno" value="${authVO.auth_no}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">      
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/auth/auth.do"
						style="margin-bottom: 0px;">
						<input type="submit" value="刪除"> 
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>"> 
						<input type="hidden" name="empno" value="${authVO.empno}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
	<%@ include file="page2.file"%>

</body>
<script src="<%=request.getContextPath()%>/static/admin/js/config.js"></script>
<script src="<%=request.getContextPath()%>/static/admin/js/script.js"></script>
</html>