<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.product_type.model.*"%>
<%@ page import="com.product.controller.*"%>

<%
	
	ProductDAO dao = new ProductDAO();
	List<ProductVO> products = dao.getAllShop();
	pageContext.setAttribute("products",products);
		
 %>
 
 
 
<!DOCTYPE html>
<html>
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

    <!-- Hero Section Begin -->
    <section class="hero-section">
      <div class="hero-items owl-carousel">
        <div class="single-hero-items set-bg" data-setbg="${pageContext.request.contextPath}/front-template/images/index/hero-1.jpg">
          <div class="container">
            <div class="row">
              <div class="col-lg-5 indexwords">
                <span>Second hand</span>
                <h1>Mode femme</h1>
                <h5>不追隨昂貴的品牌，尋找符合個人風格的穿搭</h5><br>
                <p> 高價商品已不再是時尚的代名詞，二手服飾讓所有人以平價的方式打造出自我風格的穿搭Style。
					來Mode Femme出售您的服飾、配件，或是尋找屬於你的風格搭配！
                </p>
                <a href="${pageContext.request.contextPath}/front-end/productsell/shop.jsp" class="primary-btn">Shop Now</a>
              </div>
            </div>
            <div class="off-card">
              <h2>Mode <span>femme</span></h2>
            </div>
          </div>
        </div>
<%--         <div class="single-hero-items set-bg" data-setbg="${pageContext.request.contextPath}/front-template/images/index/hero-2.jpg"> --%>
<!--           <div class="container"> -->
<!--             <div class="row"> -->
<!--               <div class="col-lg-5"> -->
<!--                 <span>Bag,kids</span> -->
<!--                 <h1>Black friday</h1> -->
<!--                 <p> -->
<!--                   Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed -->
<!--                   do eiusmod tempor incididunt ut labore et dolore -->
<!--                 </p> -->
<!--                 <a href="#" class="primary-btn">Shop Now</a> -->
<!--               </div> -->
<!--             </div> -->
<!--             <div class="off-card"> -->
<!--               <h2>Sale <span>50%</span></h2> -->
<!--             </div> -->
<!--           </div> -->
<!--         </div> -->
      </div>
    </section>
    <!-- Hero Section End -->

    <!-- Women Banner Section Begin -->
    <section class="women-banner spad">
      <div class="container-fluid">
        <div class="row">
          <div class="col-lg-3">
            <div
              class="product-large set-bg"
              data-setbg="${pageContext.request.contextPath}/front-template/images/products/women-large.jpg"
            >
              <h2>Women’s</h2>
            </div>
          </div>
          <div class="col-lg-8 offset-lg-1">
          <div class="product-slider-pdtype"><a class="cative" href="<%=request.getContextPath()%>/product_type/product_type.do?action=getProductsByPdtype&pdtype_no=4001">Clothes</a></div>
            <div class="product-slider owl-carousel">
            <c:forEach var="productVO" items="${products}" begin="0" end="${products.size()}">
            <c:if test="${productVO.pdtype_no == 4001}"> 
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
            </c:if>
            </c:forEach>
            </div>
            <div class="product-slider-pdtype"><a class="cative" href="<%=request.getContextPath()%>/product_type/product_type.do?action=getProductsByPdtype&pdtype_no=4004">Shoes</a></div>
           <div class="product-slider owl-carousel">  
            <c:forEach var="productVO" items="${products}" begin="0" end="${products.size()}">
            <c:if test="${productVO.pdtype_no == 4004}"> 
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
            </c:if>
            </c:forEach>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Women Banner Section End -->

    <!-- Deal Of The Week Section Begin-->
    <section class="deal-of-week set-bg spad" data-setbg="${pageContext.request.contextPath}/front-template/images/index/time-live.jpg">
      <div class="row container">
        <div class="col-lg-6 text-center">
          <div class="section-title">
            <h2>直播預告</h2>
			<h4>直播間名稱：</h4>			
            <div class="product-price">
              $35.00
              <span>/ HanBag</span>
            </div>
          </div>
          <div class="countdown-timer" id="countdown">
            <div class="cd-item">
              <span></span>
              <p>Days</p>
            </div>
            <div class="cd-item">
              <span></span>
              <p>Hrs</p>
            </div>
            <div class="cd-item">
              <span></span>
              <p>Mins</p>
            </div>
            <div class="cd-item">
              <span></span>
              <p>Secs</p>
            </div>
          </div>
          <a href="#" class="primary-btn">Shop Now</a>
        </div>
        <div class="col-lg-6 text-center">
        <div>
        <img width="350px" height="350px" src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=5081" class="rounded mx-auto d-block" alt="">
        
        </div>
        
        
        </div>
        
        
        
      </div>
    </section>
    <!-- Deal Of The Week Section End -->

    <!-- Man Banner Section Begin -->
    <!-- <section class="man-banner spad">
      <div class="container-fluid">
        <div class="row">
          <div class="col-lg-8">
            <div class="filter-control">
              <ul>
                <li class="active">Clothings</li>
                <li>HandBag</li>
                <li>Shoes</li>
                <li>Accessories</li>
              </ul>
            </div>
            <div class="product-slider owl-carousel">
              <div class="product-item">
                <div class="pi-pic">
                  <img src="img/products/man-1.jpg" alt="" />
                  <div class="sale">Sale</div>
                  <div class="icon">
                    <i class="icon_heart_alt"></i>
                  </div>
                  <ul>
                    <li class="w-icon active">
                      <a href="#"><i class="icon_bag_alt"></i></a>
                    </li>
                    <li class="quick-view"><a href="#">+ Quick View</a></li>
                    <li class="w-icon">
                      <a href="#"><i class="fa fa-random"></i></a>
                    </li>
                  </ul>
                </div>
                <div class="pi-text">
                  <div class="catagory-name">Coat</div>
                  <a href="#">
                    <h5>Pure Pineapple</h5>
                  </a>
                  <div class="product-price">
                    $14.00
                    <span>$35.00</span>
                  </div>
                </div>
              </div>
              <div class="product-item">
                <div class="pi-pic">
                  <img src="img/products/man-2.jpg" alt="" />
                  <div class="icon">
                    <i class="icon_heart_alt"></i>
                  </div>
                  <ul>
                    <li class="w-icon active">
                      <a href="#"><i class="icon_bag_alt"></i></a>
                    </li>
                    <li class="quick-view"><a href="#">+ Quick View</a></li>
                    <li class="w-icon">
                      <a href="#"><i class="fa fa-random"></i></a>
                    </li>
                  </ul>
                </div>
                <div class="pi-text">
                  <div class="catagory-name">Shoes</div>
                  <a href="#">
                    <h5>Guangzhou sweater</h5>
                  </a>
                  <div class="product-price">$13.00</div>
                </div>
              </div>
              <div class="product-item">
                <div class="pi-pic">
                  <img src="img/products/man-3.jpg" alt="" />
                  <div class="icon">
                    <i class="icon_heart_alt"></i>
                  </div>
                  <ul>
                    <li class="w-icon active">
                      <a href="#"><i class="icon_bag_alt"></i></a>
                    </li>
                    <li class="quick-view"><a href="#">+ Quick View</a></li>
                    <li class="w-icon">
                      <a href="#"><i class="fa fa-random"></i></a>
                    </li>
                  </ul>
                </div>
                <div class="pi-text">
                  <div class="catagory-name">Towel</div>
                  <a href="#">
                    <h5>Pure Pineapple</h5>
                  </a>
                  <div class="product-price">$34.00</div>
                </div>
              </div>
              <div class="product-item">
                <div class="pi-pic">
                  <img src="img/products/man-4.jpg" alt="" />
                  <div class="icon">
                    <i class="icon_heart_alt"></i>
                  </div>
                  <ul>
                    <li class="w-icon active">
                      <a href="#"><i class="icon_bag_alt"></i></a>
                    </li>
                    <li class="quick-view"><a href="#">+ Quick View</a></li>
                    <li class="w-icon">
                      <a href="#"><i class="fa fa-random"></i></a>
                    </li>
                  </ul>
                </div>
                <div class="pi-text">
                  <div class="catagory-name">Towel</div>
                  <a href="#">
                    <h5>Converse Shoes</h5>
                  </a>
                  <div class="product-price">$34.00</div>
                </div>
              </div>
            </div>
          </div>
          <div class="col-lg-3 offset-lg-1">
            <div
              class="product-large set-bg m-large"
              data-setbg="img/products/man-large.jpg"
            >
              <h2>Men’s</h2>
              <a href="#">Discover More</a>
            </div>
          </div>
        </div>
      </div>
    </section> -->
    <!-- Man Banner Section End -->

        <div class="benefit-items">
          <div class="row">
            <div class="col-lg-4">
              <div class="single-benefit">
                <div class="sb-icon">
                  <img src="${pageContext.request.contextPath}/front-template/images/index/icon-1.png" alt="" />
                </div>
                <div class="sb-text">
                  <h6>Free Shipping</h6>
                  <p>For all order over 99$</p>
                </div>
              </div>
            </div>
            <div class="col-lg-4">
              <div class="single-benefit">
                <div class="sb-icon">
                  <img src="${pageContext.request.contextPath}/front-template/images/index/icon-2.png" alt="" />
                </div>
                <div class="sb-text">
                  <h6>Delivery On Time</h6>
                  <p>If good have prolems</p>
                </div>
              </div>
            </div>
            <div class="col-lg-4">
              <div class="single-benefit">
                <div class="sb-icon">
                  <img src="${pageContext.request.contextPath}/front-template/images/index/icon-1.png" alt="" />
                </div>
                <div class="sb-text">
                  <h6>Secure Payment</h6>
                  <p>100% secure payment</p>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Latest Blog Section End -->

   
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
  </body>
</html>