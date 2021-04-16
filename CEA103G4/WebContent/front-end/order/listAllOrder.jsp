<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%@ page import="java.util.*"%>
<%@ page import="com.order.model.*"%>
<%-- �����m�߱ĥ� EL ���g�k���� --%>

<%
    OrderService orderSvc = new OrderService();
    List<OrderVO> list = orderSvc.getAll();
    pageContext.setAttribute("list",list);
%>


<html>
<head>
<title>�Ҧ���Ѹ�� - listAllOrder.jsp</title>

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
	width: auto;
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

<h4>�����m�߱ĥ� EL ���g�k����:</h4>
<table id="table-1">
	<tr><td>
		 <h3>�Ҧ��q���� - listAllOrder.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">�^����</a></h4>
	</td></tr>
</table>

<%-- ���~��C --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">�Эץ��H�U���~:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<table>
	<tr>
		<th>�q��s��</th>
		<th>�q����</th>
		<th>�q�檬�A</th>
		<th>�q��B�O</th>
		<th>�q�����</th>
		<th>�I�ڤ覡</th>
		<th>�I�ںI�����</th>
		<th>����H�m�W</th>
		<th>����H�a�}</th>
		<th>����H�q��</th>
		<th>����H���</th>
		<th>���y�覡</th>
		<th>���y���p</th>
		<th>�I�Ƨ��</th>
		<th>�R�a�b��</th>
		<th>��a�b��</th>
		<th>��a��������</th>
		<th>��a�������e</th>
		<th>�I�Ʀ^�X</th>
	</tr>
	<%@ include file="page1.file" %> 
	<c:forEach var="orderVO" items="${list}" begin="<%=pageIndex%>" end="<%=pageIndex+rowsPerPage-1%>">
		
		<tr>
			<td>${orderVO.order_no}</td>
			<td>${orderVO.order_date}</td>
			<td>${orderVO.order_state}</td>
			<td>${orderVO.order_shipping}</td>
			<td>${orderVO.order_price}</td>
			<td>${orderVO.pay_method}</td>
			<td>${orderVO.pay_deadline}</td>
			<td>${orderVO.rec_name}</td>
			<td>${orderVO.rec_addr}</td>
			<td>${orderVO.rec_phone}</td>
			<td>${orderVO.rec_cellphone}</td>
			<td>${orderVO.logistics}</td>
			<td>${orderVO.logisticsstate}</td>
			<td>${orderVO.discount}</td>
			<td>${orderVO.user_id}</td>
			<td>${orderVO.seller_id}</td>
			<td>${orderVO.srating}</td>
			<td>${orderVO.srating_content}</td>
			<td>${orderVO.point}</td>
			<td><fmt:formatDate value="${orderVO.order_date}" pattern="yyyy-MM-dd"/></td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/order/order.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�ק�">
			     <input type="hidden" name="order_no"  value="${orderVO.order_no}">
			     <input type="hidden" name="action"	value="getOne_For_Update"></FORM>
			</td>
			<td>
			  <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/order/order.do" style="margin-bottom: 0px;">
			     <input type="submit" value="�R��">
			     <input type="hidden" name="order_no"  value="${orderVO.order_no}">
			     <input type="hidden" name="action" value="delete"></FORM>
			</td>
		</tr>
	</c:forEach>
</table>
<%@ include file="page2.file" %>

</body>
</html>