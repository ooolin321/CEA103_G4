<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<jsp:useBean id="orderSvc" scope="page"
	class="com.order.model.OrderService" />

<html>
<head>
<title>訂單明細 - listDetails_ByNo.jsp</title>



</head>
<body bgcolor='white'>


	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>


	<div class="row">
		<div class="col-xl-12">
			<div class="tile">
				<h3 class="tile-title">訂單明細</h3>
				<table class="table table-hover">
				<thead>
					<tr>
						<th>訂單編號</th>
						<th>商品編號</th>
						<th>價格</th>
						<th>數量</th>
						<th></th>
						<th></th>
					</tr>
				</thead>
					<c:forEach var="order_detailVO" items="${listDetails_ByNo}">
						<tr
							${(order_detailVO.order_no==param.order_no)&&((order_detailVO.product_no==param.product_no)) ? 'bgcolor=#CCCCFF':''}>
							<!--將修改的那一筆加入對比色-->
							<td>${order_detailVO.order_no}</td>
							<td>${order_detailVO.product_no}</td>
							<td>${order_detailVO.price}</td>
							<td>${order_detailVO.product_num}</td>
						</tr>
					</c:forEach>
				</table>
			</div>
		</div>
	</div>

</body>
</html>