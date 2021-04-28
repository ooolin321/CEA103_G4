<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.user.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
    UserService userSvc = new UserService();
    List<UserVO> list = userSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>所有會員資料 - listAllUser.jsp</title>

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
		 <h3>所有會員資料 - listAllUser.jsp</h3>
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
		<th>帳號</th>
		<th>密碼</th>
		<th>姓名</th>
		<th>身分証字號</th>
		<th>性別</th>
		<th>生日</th>
		<th>Email</th>
		<th>電話</th>
		<th>手機號碼</th>
		<th>地址</th>
		<th>註冊日期</th>
		<th>點數</th>
		<th>違約次數</th>
		<th>狀態</th>
		<th>賣家評價</th>
		<th>評價人數</th>
		<th>錢包</th>
		<th>修改</th>
		<th>刪除</th>
		<th>曾經檢舉過的直播</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="userVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${userVO.user_id}</td>
			<td>${userVO.user_pwd}</td>
			<td>${userVO.user_name}</td>
			<td>${userVO.id_card}</td>
			<td>			
			${(userVO.user_gender=='M')? '男':''}
			${(userVO.user_gender=='F')? '女':''}
			</td>
			<td><fmt:formatDate value="${userVO.user_dob}" pattern="yyyy-MM-dd"/></td>
<%-- 		                       <td>${userVO.user_dob}</td> --%>
			<td>${userVO.user_mail}</td>
			<td>${userVO.user_phone}</td>
			<td>${userVO.user_mobile}</td> 
<%-- 			<td>${userVO.user_addr}</td> --%>
			<td>${userVO.zipcode}${userVO.city}${userVO.town}${userVO.user_addr}</td>
			<td><fmt:formatDate value="${userVO.regdate}" pattern="yyyy-MM-dd"/></td>
			<td>${userVO.user_point}</td>
			<td>${userVO.violation}</td>
			<td>			
			${(userVO.user_state==0)? '停權':''}
			${(userVO.user_state==1)? '正常':''}
			</td>
			<td>${userVO.user_comment}</td>
			<td>${userVO.comment_total}</td>
			<td>${userVO.cash}</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/user/user.do" style="margin-bottom: 0px;">
			     <input type="submit" value="修改">
			     <input type="hidden" name="user_id"  value="${userVO.user_id}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/user/user.do" style="margin-bottom: 0px;">
			     <input type="submit" value="刪除">
			     <input type="hidden" name="user_id"  value="${userVO.user_id}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/front-end/user/user.do" style="margin-bottom: 0px;">
			    <input type="submit" value="送出查詢"> 
			    <input type="hidden" name="user_id" value="${userVO.user_id}">
			    <input type="hidden" name="action" value="listLive_report_ByUser_id_A"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>
<%if (request.getAttribute("listLive_report_ByUser_id")!=null){%>
       <jsp:include page="listLive_report_ByUser_id.jsp" />
<%} %>
</body>
</html>