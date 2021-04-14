<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.product.model.*"%>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>�ӫ~��Ʒs�W - addProduct.jsp</title>

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
		 <h3>�ӫ~��Ʒs�W - addProduct.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">�^����</a></h4>
	</td></tr>
</table>

<h3>��Ʒs�W:</h3>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="product.do" name="form1">
<table>
	<tr>
		<td>�ӫ~�s��:<font color=red><b>*</b></font></td>
		<td><%=productVO.getProduct_no()%></td>
	</tr>
	<tr>
		<td>�ӫ~�W��:</td>
		<td><input type="TEXT" name="product_name" size="45" value="<%=productVO.getProduct_name()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~����:</td>
		<td><input type="TEXT" name="product_info" size="45" value="<%=productVO.getProduct_info()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~����:</td>
		<td><input type="text" name="product_price" size="45" value="<%=productVO.getProduct_price()%>"  /></td>
	</tr>
	<tr>
		<td>�ӫ~��ƶq:</td>
		<td><input type="TEXT" name="product_quantity" size="45" value="<%=productVO.getProduct_quantity()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~�Ѿl�ƶq:</td>
		<td><%=productVO.getProduct_remaining()%>" /></td>
	</tr>
	<tr>
		<td>��ܰӫ~���A:</td>
		<td><input type="TEXT" name="product_state" size="45" value="<%=productVO.getProduct_state()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~�Ӥ�:</td>
		<td><img src="" alt="Product" class="" width="50" ></td>
	</tr>
	<tr>
		<td>�|���b��:</td>
		<td><%=productVO.getUser_id()%></td>
	</tr>
	<tr>
		<td>�ӫ~���O�s��:</td>
		<td><input type="TEXT" name="pdtype_no" size="45" value="<%=productVO.getPdtype_no()%>" /></td>
	</tr>
	<tr>
		<td>�ӫ~�_�л�:</td>
		<td><input type="TEXT" name="start_price" size="45" value="<%=productVO.getStart_price()%>" /></td>
	</tr>
	<tr>
		<td>�����s��:</td>
		<td><input type="TEXT" name="live_no" size="45" value="<%=productVO.getLive_no()%>" /></td>
	</tr>
	

</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="�e�X�s�W"></FORM>
</body>

</html>