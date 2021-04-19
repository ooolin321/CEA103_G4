<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.user.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  UserVO userVO = (UserVO) request.getAttribute("userVO"); //UserServlet.java(Controller), 存入req的userVO物件
%>

<html>
<head>
<title>會員資料 - listOneUser.jsp</title>

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
</style>

</head>
<body bgcolor='white'>

<h4>此頁暫練習採用 Script 的寫法取值:</h4>
<table id="table-1">
	<tr><td>
		 <h3>會員資料 - ListOneUser.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

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
		<th>縣市</th>
		<th>鄉鎮</th>
		<th>郵遞區號</th>
		<th>地址</th>
		<th>註冊日期</th>
		<th>點數</th>
		<th>違約次數</th>
		<th>狀態</th>
		<th>賣家評價</th>
		<th>評價人數</th>
		<th>錢包</th>
	</tr>
	<tr>
		<td><%=userVO.getUser_id()%></td>
		<td><%=userVO.getUser_pwd()%></td>
		<td><%=userVO.getUser_name()%></td>
		<td><%=userVO.getId_card()%></td>
		<td><%=userVO.getUser_gender()%></td>
		<td><%=userVO.getUser_dob()%></td>
		<td><%=userVO.getUser_mail()%></td>
		<td><%=userVO.getUser_phone()%></td>
		<td><%=userVO.getUser_mobile()%></td>
		<td><%=userVO.getCity()%></td>
		<td><%=userVO.getTown()%></td>
		<td><%=userVO.getZipcode()%></td>
		<td><%=userVO.getUser_addr()%></td>
<%-- 		<td>${userVO.zipcode}${userVO.city}${userVO.town}${userVO.user_addr}</td> --%>
		<td><%=userVO.getRegdate()%></td>
		<td><%=userVO.getUser_point()%></td>
		<td><%=userVO.getViolation()%></td>
		<td><%=userVO.getUser_state()%></td>
		<td><%=userVO.getUser_comment()%></td>
		<td><%=userVO.getComment_total()%></td>
		<td><%=userVO.getCash()%></td>
	</tr>
</table>

</body>
</html>