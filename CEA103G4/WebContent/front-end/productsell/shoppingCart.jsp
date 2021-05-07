<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.* , com.product.model.*" %>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");
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
 <% @SuppressWarnings("unchecked")
   Vector<ProductVO> buylist = (Vector<ProductVO>) session.getAttribute("shoppingcart");%>
<%if (buylist != null && (buylist.size() > 0)) {%>   
    <%
	 for (int index = 0; index < buylist.size(); index++) {
		 ProductVO order = buylist.get(index);
		 pageContext.setAttribute("order", order);
	%>
                <tbody>
                  <tr>
                    <td class="cart-pic first-row">
                    <a href="<%=request.getContextPath()%>/product/product.do?product_no=${order.product_no}">
                      <img src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=<%=order.getProduct_no()%>" alt="<%=order.getProduct_name()%>" />
                    </td>
                    <td class="p-name first-row">
                      <h5>${order.product_name }</h5>
                    </td>
                    <td class="p-price first-row">$<%=order.getProduct_price()%></td>
                    <td class="qua-col first-row">
                      <div class="quantity" style="margin-top: 30px;">
                     <div class="pro-qty">
                     <span id="decProduct" class="dec qtybtn">-</span>
                      <input name="proqty" type="text" value="<%=order.getProduct_quantity()%>" />
                      <span id="addProduct" class="inc qtybtn" style="none">+</span>
                    </div>
                    
<!-- 					<div class="pro-qty"> -->
<!--                      <a href="javascript:;" id="decrement" class="dec qtybtn">-</a> -->
<%--                       <input name="proqty" id="product_num" class="itxt" type="text" value="<%=order.getProduct_quantity()%>" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')"/> --%>
<!--                       <a href="javascript:;" id="increment" class="inc qtybtn" style="none">+</a> -->
<!--                     </div> -->
                    
                      </div>
                      <span id="maxRemaining" value="${order.product_remaining}">在庫數量：${order.product_remaining}</span>
<%--                       <h5 style="color:#FF0000">在庫數量：${order.product_remaining}</h5> --%>
                    </td>
                    <td class="total-price first-row">${order.product_price*order.product_quantity }</td>
                    <form action="<%=request.getContextPath()%>/ShoppingServlet" method="POST">
		              <input type="hidden" name="action"  value="DELETE">
		              <input type="hidden" name="del" value="<%= index %>">
		              <td class="close-td first-row">
		              <input type="submit" class="btn btn-info" value="刪 除">
		              </td>
		          	</form></td>
                  </tr>
                </tbody>
     <%}%>
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
<!--                     <li class="subtotal">Subtotal <span>$240.00</span></li> -->
                    <li class="cart-total">Total <span>$240.00</span></li>
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

    <!-- Partner Logo Section Begin -->
    <div class="partner-logo">
      <div class="container">
        <div class="logo-carousel owl-carousel">
          <div class="logo-item">
            <div class="tablecell-inner">
              <img src="img/logo-carousel/logo-1.png" alt="" />
            </div>
          </div>
          <div class="logo-item">
            <div class="tablecell-inner">
              <img src="img/logo-carousel/logo-2.png" alt="" />
            </div>
          </div>
          <div class="logo-item">
            <div class="tablecell-inner">
              <img src="img/logo-carousel/logo-3.png" alt="" />
            </div>
          </div>
          <div class="logo-item">
            <div class="tablecell-inner">
              <img src="img/logo-carousel/logo-4.png" alt="" />
            </div>
          </div>
          <div class="logo-item">
            <div class="tablecell-inner">
              <img src="img/logo-carousel/logo-5.png" alt="" />
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Partner Logo Section End -->

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
    
    <script>
    
    let buyCount = $('input[name="proqty"]').val();
    
    
// $(function(){
// 	//增減商品,修改價格
// 	//點選加號時
// 	$("#increment").click(function(){
// 		//獲得輸入框的數量
// 		var num = $(this).siblings(".itxt").val();
// 		//加一
// 		num++;
// 		that = this;
// 		//重新整理小計
// 		flushSum(that,num);
// 	})
// 	//點選減號時
// 	$("#decrement").click(function(){
// 		//獲得輸入框的數量
// 		var num = $(this).siblings(".itxt").val();
// 		//如果數量大於一
// 		if(num > 1){
// 			//減一
// 			num--;
// 		}
// 		that = this;
// 		//重新整理小計
// 		flushSum(that,num);
// 	})
		
// 	//使用者修改輸入框
// 	$(".itxt").change(function(){
// 		//獲得輸入框的數量
// 		var num = $(this).val();
// 		//判斷是否輸入有誤
// 		if(num == ""){
// 			alert("輸入有誤");
// 			num = 1;
// 			$(this).val(1);
// 		}
// 		that = this;
// 		//重新整理小計
// 		flushSum(that,num);
// 	})
		
// 	//重新整理小計
// 	function flushSum(that,num){
// 		//判斷庫存
// 		if(num > 999){
// 			alert("庫存不足");
// 			num = 1;
// 			$(that).val(1);
// 		}
// 		//重新整理商品數量
// 		$(that).siblings(".itxt").val(num);
// 		//獲得商品的價格
// 		var price = $(that).siblings(".p-price first-row").text();
// 		//擷取字串並轉型
// 		price = parseFloat(price.substr(1));
// 		//獲得商品小計
// 		sum = num * price;
// 		//重新整理商品小計,商品小計保留兩位小數
// 		$(that).siblings(".total-price first-row").text("$"+sum.toFixed(2));
// 	}
// })
    </script>
    
  </body>
</html>
