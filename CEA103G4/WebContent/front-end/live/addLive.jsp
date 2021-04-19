<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.live.model.*"%>

<%
	LiveVO liveVO = (LiveVO) request.getAttribute("liveVO");
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>直播資料新增 - addLive.jsp</title>
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

</head>
<body bgcolor='white'>

	<table id="table-1">
		<tr>
			<td>
				<h3>直播新增 - addLive.jsp</h3>
			</td>
			<td>
				<h4>
					<a href="<%=request.getContextPath()%>/front-end/live/select_page.jsp"><img src="${pageContext.request.contextPath}/images/tomcat.png"
						width="100" height="100" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<h3>直播新增:</h3>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>

	<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/live/live.do" name="form1">
		<table>


			<jsp:useBean id="product_typeSvc" scope="page" class="com.product_type.model.Product_TypeService" />
			<tr>
				<td>直播分類:<font color=red><b>*</b></font></td>
				<td><select size="1" name="live_type">
					<c:forEach var="product_typeVO" items="${product_typeSvc.all}">
						<option value="${product_typeVO.pdtype_name}" ${(liveVO.live_type==product_typeVO.pdtype_name)? 'selected':'' } >${product_typeVO.pdtype_name}
					</c:forEach>
				</select></td>
			</tr>

			<tr>
				<td>直播名稱:<font color=red><b>*</b></font></td>
				<td><input type="TEXT" name="live_name" size="45" value="<%= (liveVO==null)? "未命名" : liveVO.getLive_name()%>" /></td>
			</tr>

			<tr>
				<td>直播時間:<font color=red><b>*</b></font></td>
				<td><input name="live_time" id="f_date1" type="DATE"></td>
			</tr>

			<tr>
				<td>直播狀態:</td>
				<td><select name="live_state">
						<option value="0" ${(liveVO.live_state==0)? 'selected':'' }>未直播</option>
						<option value="1" ${(liveVO.live_state==1)? 'selected':'' }>直播中</option>
						<option value="2" ${(liveVO.live_state==2)? 'selected':'' }>已結束</option>
				</select></td>
			</tr>

			<jsp:useBean id="userSvc" scope="page" class="com.user.model.UserService" />
			<tr>
				<td>會員帳號:<font color=red><b>*</b></font></td>
				<td><select size="1" name="user_id">
					<c:forEach var="userVO" items="${userSvc.all}">
						<option value="${userVO.user_id}" ${(liveVO.user_id==userVO.user_id)? 'selected':'' } >${userVO.user_id}
					</c:forEach>
				</select></td>
			</tr>
			
<%-- 			<jsp:useBean id="empSvc" scope="page" class="com.emp.model.EmpService" /> --%>
<!-- 			<tr> -->
<!-- 				<td>管理員編號:<font color=red><b>*</b></font></td> -->
<!-- 				<td><select size="1" name="empno"> -->
<%-- 					<c:forEach var="empVO" items="${empSvc.all}"> --%>
<%-- 						<option value="${empVO.empno}" ${(liveVO.empno==empVO.empno)? 'selected':'' } >${empVO.empno} --%>
<%-- 					</c:forEach> --%>
<!-- 				</select></td> -->
<!-- 			</tr> -->
			<tr>
				<td>管理員編號:<font color=red><b>*</b></font></td>
				<td><input type="TEXT" name="empno" size="45" value="<%= (liveVO==null)? "14001" : liveVO.getEmpno()%>" /></td>
			</tr>
			
			<tr>
				<td>圖片上傳:</td>
				<td><input type="file" name="live_photo"/></td>
			</tr>
			
			
		</table>
		<br> <input type="hidden" name="action" value="insert"> <input
			type="submit" value="送出新增">
	</FORM>
</body>

</html>