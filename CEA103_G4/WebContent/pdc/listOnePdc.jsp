<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.pdc.model.*"%>
<%-- 此頁暫練習採用 Script 的寫法取值 --%>

<%
  PdcVO pdcVO = (PdcVO) request.getAttribute("pdcVO"); //MsgServlet.java(Concroller), 存入req的pdcVO物件
%>

<html>
<head>
<title>商品資料 - listOnePdc.jsp</title>

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
		 <h3>商品資料 - ListOneMsg.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<table>
	<tr>
		<th>商品編號</th>
		<th>商品名稱</th>
		<th>商品說明</th>
		<th>商品價格</th>
		<th>商品原數量</th>
		<th>商品剩餘數量</th>
		<th>商品狀態</th>
		<th>商品照片</th>
		<th>會員帳號</th>
		<th>商品類別編號</th>
		<th>起標價</th>
		<th>直播編號</th>
	</tr>
	<tr>
		<td><%=pdcVO.getProduct_id()%></td>
		<td><%=pdcVO.getProduct_name()%></td>
		<td><%=pdcVO.getProduct_info()%></td>
		<td><%=pdcVO.getProduct_price()%></td>
		<td><%=pdcVO.getProduct_quantity()%></td>
		<td><%=pdcVO.getProduct_remaining()%></td>
		<td><%=pdcVO.getProduct_state()%></td>
		<td><%=pdcVO.getProduct_photo()%></td>
		<td><%=pdcVO.getUser_id()%></td>
		<td><%=pdcVO.getPdtype_id()%></td>
		<td><%=pdcVO.getStart_price()%></td>
		<td><%=pdcVO.getLive_id()%></td>
	</tr>
</table>

</body>
</html>