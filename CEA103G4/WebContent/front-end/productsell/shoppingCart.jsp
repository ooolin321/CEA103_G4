<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.product.model.*" %>

<%
	Vector<ProductVO> buylist = (Vector<ProductVO>) session.getAttribute("shoppingcart");
	pageContext.setAttribute("buylist", buylist);
%>


<!DOCTYPE html>
<html lang="zxx">
  <head>
    <meta charset="UTF-8" />
    <meta name="description" content="Fashi Template" />
    <meta name="keywords" content="Fashi, unica, creative, html" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>ShoppingCart - Mode Femme</title>

    <!-- Google Font -->
    <link
      href="https://fonts.googleapis.com/css?family=Muli:300,400,500,600,700,800,900&display=swap"
      rel="stylesheet"
    />

    <!-- Css Styles -->
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/bootstrap.min.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/font-awesome.min.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/themify-icons.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/elegant-icons.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/owl.carousel.min.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/nice-select.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/jquery-ui.min.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/slicknav.min.css" type="text/css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/front-template/css/style.css" type="text/css" />
  <style>
  .shopping-cart {
    padding-top: 0px;
}
.cart-table table tr td.qua-col .productCounts {
  width: 123px;
  height: 46px;
  border: 2px solid #ebebeb;
  padding: 0 15px;
  float: left;
}

.cart-table table tr td.qua-col .productCounts .qtybtn {
  font-size: 24px;
  color: #b2b2b2;
  float: left;
  line-height: 38px;
  cursor: pointer;
  width: 18px;
}

.cart-table table tr td.qua-col .productCounts .qtybtn.dec {
  font-size: 30px;
}

.cart-table table tr td.qua-col .productCounts input {
  text-align: center;
  width: 52px;
  font-size: 14px;
  font-weight: 700;
  border: none;
  color: #4c4c4c;
  line-height: 40px;
  float: left;
}

  </style>
  
  </head>

  <body>
	<!-- Header Section Begin -->
    <%@include file="/front-end/header.jsp"%>
	<!-- Header End -->

    <!-- Breadcrumb Section Begin -->
    <div class="breacrumb-section">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="breadcrumb-text product-more">
              <a href="${pageContext.request.contextPath}/front-end/index.jsp"><i class="fa fa-home"></i> 首頁</a>
              <a href="${pageContext.request.contextPath}/front-end/productsell/shop.jsp">商品頁</a>
              <span>購物車清單</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb Section Begin -->

    <!-- Shopping Cart Section Begin -->
    <section class="shopping-cart spad">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="cart-table">
              <table>
                <thead>
                  <tr>
                    <th>商品圖</th>
                    <th>商品名稱</th>
                    <th>單價</th>
                    <th>數量</th>
                    <th>小計</th>
                    <th>操作</th>
                  </tr>
                </thead>

<%if (buylist != null && (buylist.size() > 0)) {%>   
    <%
// 	 for (int index = 0; index < buylist.size(); index++) {
// 		 ProductVO order = buylist.get(index);
// 		 pageContext.setAttribute("order", order);
	%>
	<c:set var="sum" value="0"> </c:set>
	<c:forEach var="order" items="${buylist}" varStatus="cartstatus">
                <tbody>
                	<tr><td>賣家:${order.product_no}</td></tr>
                  <tr>
                    <td class="cart-pic first-row">
                    <a href="<%=request.getContextPath()%>/product/product.do?product_no=${order.product_no}">
                      <img src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${order.product_no}" alt="${order.product_name}" />
                    </td>
                    <td class="p-name first-row">
                      <h5>${order.product_name }</h5>
                    </td>
                    <td class="PP${order.product_no} first-row" value="${order.product_price}">$${order.product_price }</td>
                    <td class="qua-col first-row">
                      <div class="quantity" style="margin-top: 30px;">
                      
                      
                     <div id="PC${order.product_no}" class="productCounts" >
                     <span id="decProduct" class="dec qtybtn">-</span>
                      <input name="${order.product_no}" id="PN${order.product_no}" type="text" value="${order.product_quantity}" />
                      <span id="Add${order.product_no}" class="inc qtybtn" style="none">+</span>
                    </div>
                    
                    
                      </div>
                      <span id="max${order.product_no}" value="${order.product_remaining}">在庫數量：${order.product_remaining}</span>
<%--                       <h5 style="color:#FF0000">在庫數量：${order.product_remaining}</h5> --%>
                    </td>
                    <td>
                    <div id="TP${order.product_no}" class="cartProductItemSumPrice" style="margin-top: 30px;color: #e7ab3c;">${order.product_price*order.product_quantity}</div>
<%--                     <td class="total-price first-row">${order.product_price*order.product_quantity}</td> --%>
					</td>
                    <form action="<%=request.getContextPath()%>/ShoppingServlet" method="POST">
		              <input type="hidden" name="action"  value="DELETE">
		              <input type="hidden" name="del" value="${cartstatus.index}">
		              <td class="close-td first-row">
		              <input type="submit" class="btn btn-info" value="刪 除">
		              </td>
		          	</form></td>
                  </tr>
                </tbody>
                <c:set var="sum" value="${sum + order.product_price*order.product_quantity}"> </c:set>
                </c:forEach>
<%}%>
              </table>
            </div>
            <div class="row">
              <div class="col-lg-4">
                <div class="cart-buttons">
                  <a href="<%=request.getContextPath()%>/front-end/productsell/shop.jsp" class="primary-btn continue-shop"
                    >繼續購物</a>
                </div>
<!--                 <div class="discount-coupon"> -->
<!--                   <h6>Discount Codes</h6> -->
<!--                   <form action="#" class="coupon-form"> -->
<!--                     <input type="text" placeholder="Enter your codes" /> -->
<!--                     <button type="submit" class="site-btn coupon-btn"> -->
<!--                       Apply -->
<!--                     </button> -->
<!--                   </form> -->
<!--                 </div> -->
              </div>
              <div class="col-lg-4 offset-lg-4">
                <div class="proceed-checkout">
                  <ul>
                    <li class="cart-total">合計 <span id="Sum">$${sum}</span></li>
                  </ul>
                  <a href="<%=request.getContextPath()%>/front-end/protected/check-out.html" class="proceed-btn">結帳</a>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>

    <!-- Shopping Cart Section End -->



    <!-- Footer Section Begin -->
	<%@include file="/front-end/footer.jsp"%>
    <!-- Footer Section End -->

    <!-- Js Plugins -->
    <script src="${pageContext.request.contextPath}/front-template/js/jquery-3.3.1.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/bootstrap.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery-ui.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery.countdown.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery.nice-select.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery.zoom.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery.dd.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/jquery.slicknav.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/owl.carousel.min.js"></script>
    <script src="${pageContext.request.contextPath}/front-template/js/main.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@10.16.7/dist/sweetalert2.all.min.js"></script>
    
    <script>
    // 各商品小計
     <c:forEach var="order" items="${buylist}" varStatus="cartstatus">
    	$("#PC${order.product_no}").click(function(e){
			let totalPrice = ($(".PP${order.product_no}").attr("value"))*($("#PN${order.product_no}").val());
			$("#TP${order.product_no}").text(totalPrice);
			sum();
	});
    </c:forEach>
	
    <c:forEach var="order" items="${buylist}" varStatus="cartstatus">
    
    var proQty = $('#PC${order.product_no}');
	proQty.on('click', '.qtybtn', function () {
		var $button = $(this);
		var oldValue = $button.parent().find('input').val();
		if ($button.hasClass('inc')) {
			var newVal = parseFloat(oldValue) + 1;
		} else {
			// Don't allow decrementing below zero
			if (oldValue > 0) {
				var newVal = parseFloat(oldValue) - 1;
			} else {
				newVal = 0;
			}
		}
		$button.parent().find('input').val(newVal);
	});
    
	//數量按鈕前端控制不可大於商品數量
	$('#Add${order.product_no}').on('click', function () {
    	var Count = $('input[name="${order.product_no}"]').val();
    	var maxRemaining = $("#max${order.product_no}").attr("value");
    	if (Count == maxRemaining) {
    		$("#Add${order.product_no}").prop('disabled',true);
    		Swal.fire("商品數量只剩下"+ maxRemaining +"個");
    	} 
    	if (Count < maxRemaining) {
    		$("#Add${order.product_no}").prop('disabled',false);
    	}

	});
	
	$("#PC${order.product_no}").change(function() {	
		
		var maxRemaining = $("#max${order.product_no}").attr("value");
// 		alert("商品數量只剩下"+ maxRemaining +"個");
		$('input[name="${order.product_no}"]').val(maxRemaining);
	});
	
	
	 </c:forEach>
	 
		//計算合計方法
		function sum(){
			//計算總價，編寫總價方法
	             var zong = 0;
	             $(".cartProductItemSumPrice").each(function () {
	                 var all = parseInt($(this).text());
	                 zong += all;
	             })
	             $("#Sum").text(zong);
		}
	 
    </script>
    
  </body>
</html>
