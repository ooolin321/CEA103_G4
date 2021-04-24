<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.product_type.model.*"%>
<%@ page import="com.product.controller.*"%>

<%
    
	Product_TypeDAO dao2 = new Product_TypeDAO();
    List<Product_TypeVO> list2 = dao2.getAll();
    pageContext.setAttribute("list2",list2);
%>
<%-- <jsp:useBean id="products" scope="request" type="java.util.List<ProductVO>" /> <!-- 於EL此行可省略 --> --%>
<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />


<!DOCTYPE html>
<html lang="zh-Hant">
<head>
	<meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <meta http-equiv="X-UA-Compatible" content="ie=edge" />
    <title>Modefemme</title>
    <link rel="icon" href="${pageContext.request.contextPath}/front-template/images/favicon.ico" type="image/x-icon">

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
            <div class="breadcrumb-text">
              <a href="${pageContext.request.contextPath}/front-end/index.jsp"><i class="fa fa-home"></i> Home</a>
              <span>Shop</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb Section Begin -->

    <!-- Product Shop Section Begin -->
    <section class="product-shop spad">
      <div class="container">
        <div class="row">
          <div
            class="col-lg-3 col-md-6 col-sm-8 order-2 order-lg-1 produts-sidebar-filter"
          >
            <div class="filter-widget">
              <h4 class="fw-title">商品分類</h4>
              <ul class="filter-catagories">
              <c:forEach var="product_typeVO" items="${list2}" begin="0" end="${list2.size()-1}">
                <li><a href="#">${product_typeVO.pdtype_name}</a></li>
                </c:forEach>
              </ul>
            </div>
            <div class="filter-widget">
              <h4 class="fw-title">Brand</h4>
              <div class="fw-brand-check">
                <div class="bc-item">
                  <label for="bc-calvin">
                    Calvin Klein
                    <input type="checkbox" id="bc-calvin" />
                    <span class="checkmark"></span>
                  </label>
                </div>
                <div class="bc-item">
                  <label for="bc-diesel">
                    Diesel
                    <input type="checkbox" id="bc-diesel" />
                    <span class="checkmark"></span>
                  </label>
                </div>
                <div class="bc-item">
                  <label for="bc-polo">
                    Polo
                    <input type="checkbox" id="bc-polo" />
                    <span class="checkmark"></span>
                  </label>
                </div>
                <div class="bc-item">
                  <label for="bc-tommy">
                    Tommy Hilfiger
                    <input type="checkbox" id="bc-tommy" />
                    <span class="checkmark"></span>
                  </label>
                </div>
              </div>
            </div>
            <div class="filter-widget">
              <h4 class="fw-title">Price</h4>
              <div class="filter-range-wrap">
                <div class="range-slider">
                  <div class="price-input">
                    <input type="text" id="minamount" />
                    <input type="text" id="maxamount" />
                  </div>
                </div>
                <div
                  class="price-range ui-slider ui-corner-all ui-slider-horizontal ui-widget ui-widget-content"
                  data-min="33"
                  data-max="98"
                >
                  <div
                    class="ui-slider-range ui-corner-all ui-widget-header"
                  ></div>
                  <span
                    tabindex="0"
                    class="ui-slider-handle ui-corner-all ui-state-default"
                  ></span>
                  <span
                    tabindex="0"
                    class="ui-slider-handle ui-corner-all ui-state-default"
                  ></span>
                </div>
              </div>
              <a href="#" class="filter-btn">Filter</a>
            </div>
            <div class="filter-widget">
              <h4 class="fw-title">Color</h4>
              <div class="fw-color-choose">
                <div class="cs-item">
                  <input type="radio" id="cs-black" />
                  <label class="cs-black" for="cs-black">Black</label>
                </div>
                <div class="cs-item">
                  <input type="radio" id="cs-violet" />
                  <label class="cs-violet" for="cs-violet">Violet</label>
                </div>
                <div class="cs-item">
                  <input type="radio" id="cs-blue" />
                  <label class="cs-blue" for="cs-blue">Blue</label>
                </div>
                <div class="cs-item">
                  <input type="radio" id="cs-yellow" />
                  <label class="cs-yellow" for="cs-yellow">Yellow</label>
                </div>
                <div class="cs-item">
                  <input type="radio" id="cs-red" />
                  <label class="cs-red" for="cs-red">Red</label>
                </div>
                <div class="cs-item">
                  <input type="radio" id="cs-green" />
                  <label class="cs-green" for="cs-green">Green</label>
                </div>
              </div>
            </div>
            <div class="filter-widget">
              <h4 class="fw-title">Size</h4>
              <div class="fw-size-choose">
                <div class="sc-item">
                  <input type="radio" id="s-size" />
                  <label for="s-size">s</label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="m-size" />
                  <label for="m-size">m</label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="l-size" />
                  <label for="l-size">l</label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="xs-size" />
                  <label for="xs-size">xs</label>
                </div>
              </div>
            </div>
            <div class="filter-widget">
            </div>
          </div>
          <div class="col-lg-9 order-1 order-lg-2">
            <div class="product-show-option">
              <div class="row">
                <div class="col-lg-7 col-md-7">
                  <div class="select-option">
                    <select class="sorting">
                      <option value="">Default Sorting</option>
                    </select>
                    <select class="p-show">
                      <option value="">Show:</option>
                    </select>
                  </div>
                </div>
                <div class="col-lg-5 col-md-5 text-right" id="productheart">
                  <a href="${pageContext.request.contextPath}/front-end/productsell/productFavorite.html">
                  <span><i class="icon_heart_alt"></i>收藏商品清單</span>
                  </a>
                </div>
              </div>
            </div>
            <!-- <div class="row"> -->
            <div class="row" id="product-list">
<%--             <%@ include file="page1products.file" %>  --%>

            <c:forEach var="productVO" items="${products}" begin="0" end="${products.size()-1}">

          <div class="col-lg-4 col-sm-6">
        <div class="card mb-2 productcard">
            <div class="product-item" >
                <div class="pi-pic">
                <div class="pi-img">
                <a href="<%=request.getContextPath()%>/product/product.do?product_no=${productVO.product_no}">
                    <img class="card-img-top"  src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${productVO.product_no}" alt="${productVO.product_name}">           
                    </a>      	
                  </div>
                    <ul>
                        <li class="w-icon active">
                            <a href="#"><i class="icon_bag_alt"></i></a>
                        </li>   
                        <li class="w-heart" >
                            <i class="icon_heart_alt"  data-no="${productVO.product_no}"></i>
                        </li>
                    </ul>
                </div>
                <div class="pi-text">
                <a href="<%=request.getContextPath()%>/product/product.do?product_no=${productVO.product_no}">                  
                        <h5>${productVO.product_name}</h5>    
                    	<div class="product-price"><span>$</span>
                        ${productVO.product_price}
                    	</div>
                    </a>
                </div>
            </div>
        </div>
    </div>
          </c:forEach>
<%--           <%@ include file="page2.file" %> --%>

          </div>
        </div>
      </div>
    </section>
    <!-- Product Shop Section End -->
<!--     分頁樣式待跟老師的分頁檔案結合 -->
 <!-- pagination -->
    <nav aria-label="Page navigation">
      <ul class="pagination justify-content-center" id="pagination">
        <li class="page-item">
          <a class="page-link" href="#">1</a>
        </li>
      </ul>
    </nav>
    <!-- pagination END-->

    <!-- Footer Section Begin -->
	<%@include file="/front-end/footer.jsp"%>
    <!-- Footer Section End -->



    <!-- Js Plugins -->
    <script>
    

    //取到搜尋框的值
//     let search = document.querySelector(".ti-search");
//     search.addEventListener('click', () => {
//     	event.preventDefault()
//     	alert($("#search-input").val());
//     });
    	
    
    </script>
    
    
    
    <script src="https://unpkg.com/axios/dist/axios.min.js"></script>
    <script
      src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
      integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
      crossorigin="anonymous"
    ></script>
    <script
      src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
      integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
      crossorigin="anonymous"
    ></script>
    
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
<%--     <script src="${pageContext.request.contextPath}/front-template/js/shop.js"></script> --%>
    
  </body>
</html>
