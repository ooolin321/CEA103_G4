<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>商品資料新增 - addProduct.jsp</title>

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
	<tr><td>
		 <h3>商品資料新增 - addProduct.jsp</h3></td><td>
		 <h4><a href="<%=request.getContextPath()%>/back-end/product/select_page.jsp">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/product/product.do" name="form1" enctype="multipart/form-data">
<table>
	
	<tr>
		<td>商品名稱:</td>
		<td><input type="TEXT" name="product_name" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_name()%>" /></td>
	</tr>
	<tr>
		<td>商品說明:</td>
		<td><input type="TEXT" name="product_info" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_info()%>" /></td>
	</tr>
	<tr>
		<td>商品價格:</td>
		<td><input type="text" name="product_price" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_price()%>"  /></td>
	</tr>
	<tr>
		<td>商品原數量:</td>
		<td><input type="TEXT" name="product_quantity" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_quantity()%>" /></td>
	</tr>
	<tr>
		<td>商品剩餘數量:</td>
		<td><input type="TEXT" name="product_remaining" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_remaining()%>" /></td>
	</tr>
	<tr>
		<td>選擇商品狀態:</td>
		<td><input type="TEXT" name="product_state" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_state()%>" /></td>
	</tr>
	<tr>
		<td>商品照片上傳:</td>
		<td><input type="file" name="product_photo" size="45" value="<%= (productVO==null)? "" : productVO.getProduct_photo()%>" /></td>
	</tr>
	<tr>
		<td>會員帳號:</td>
		<td><input type="TEXT" name="user_id" size="45" value="<%= (productVO==null)? "" : productVO.getUser_id()%>" /></td>
	</tr>
	<tr>
		<td>商品類別編號:</td>
		<td><input type="TEXT" name="pdtype_no" size="45" value="<%= (productVO==null)? "" : productVO.getPdtype_no()%>" /></td>
	</tr>
	<tr>
		<td>商品起標價:</td>
		<td><input type="TEXT" name="start_price" size="45" value="<%= (productVO==null)? "" : productVO.getStart_price()%>" /></td>
	</tr>
	<tr>
		<td>直播編號:</td>
		<td><input type="TEXT" name="live_no" size="45" value="<%= (productVO==null)? "" : productVO.getLive_no()%>" /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
</body>

</html>