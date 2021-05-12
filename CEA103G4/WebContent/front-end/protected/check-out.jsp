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
	
	UserVO userVO = (UserVO) session.getAttribute("account"); 
	session.setAttribute("userVO", userVO);
		
 %>

<!DOCTYPE html>
<html lang="zxx">
  <head>
    <meta charset="UTF-8" />
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
            <div class="breadcrumb-text product-more">
              <a href="./index.html"><i class="fa fa-home"></i> Home</a>
              <a href="./shop.html">Shop</a>
              <span>Check Out</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb Section Begin -->

    <!-- Shopping Cart Section Begin -->
    <section class="checkout-section spad">
      <div class="container">
        <form action="#" class="checkout-form">
          <div class="row">
            <div class="col-lg-6">
              <div class="checkout-content">
                <a href="#" class="content-btn">Click Here To Login</a>
              </div>
              <h4>Biiling Details</h4>
              <div class="row">
                <div class="col-lg-6">
                  <label for="fir">First Name<span>*</span></label>
                  <input type="text" id="fir" />
                </div>
                <div class="col-lg-6">
                  <label for="last">Last Name<span>*</span></label>
                  <input type="text" id="last" />
                </div>
                <div class="col-lg-12">
                  <label for="cun-name">Company Name</label>
                  <input type="text" id="cun-name" />
                </div>
                <div class="col-lg-12">
                  <label for="cun">Country<span>*</span></label>
                  <input type="text" id="cun" />
                </div>
                <div class="col-lg-12">
                  <label for="street">Street Address<span>*</span></label>
                  <input type="text" id="street" class="street-first" />
                  <input type="text" />
                </div>
                <div class="col-lg-12">
                  <label for="zip">Postcode / ZIP (optional)</label>
                  <input type="text" id="zip" />
                </div>
                <div class="col-lg-12">
                  <label for="town">Town / City<span>*</span></label>
                  <input type="text" id="town" />
                </div>
                <div class="col-lg-6">
                  <label for="email">Email Address<span>*</span></label>
                  <input type="text" id="email" />
                </div>
                <div class="col-lg-6">
                  <label for="phone">Phone<span>*</span></label>
                  <input type="text" id="phone" />
                </div>
                <div class="col-lg-12">
                  <div class="create-item">
                    <label for="acc-create">
                      Create an account?
                      <input type="checkbox" id="acc-create" />
                      <span class="checkmark"></span>
                    </label>
                  </div>
                </div>
              </div>
            </div>
            <div class="col-lg-6">
              <div class="checkout-content">
                <input type="text" placeholder="Enter Your Coupon Code" />
              </div>
              <div class="place-order">
                <h4>Your Order</h4>
                <div class="order-total">
                  <ul class="order-table">
                    <li>Product <span>Total</span></li>
                    <li class="fw-normal">
                      Combination x 1 <span>$60.00</span>
                    </li>
                    <li class="fw-normal">
                      Combination x 1 <span>$60.00</span>
                    </li>
                    <li class="fw-normal">
                      Combination x 1 <span>$120.00</span>
                    </li>
                    <li class="fw-normal">Subtotal <span>$240.00</span></li>
                    <li class="total-price">Total <span>$240.00</span></li>
                  </ul>
                  <div class="payment-check">
                    <div class="pc-item">
                      <label for="pc-check">
                        Cheque Payment
                        <input type="checkbox" id="pc-check" />
                        <span class="checkmark"></span>
                      </label>
                    </div>
                    <div class="pc-item">
                      <label for="pc-paypal">
                        Paypal
                        <input type="checkbox" id="pc-paypal" />
                        <span class="checkmark"></span>
                      </label>
                    </div>
                  </div>
                  <div class="order-btn">
                    <button type="submit" class="site-btn place-btn">
                      Place Order
                    </button>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </form>
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
    <footer class="footer-section">
      <div class="container">
        <div class="row">
          <div class="col-lg-3">
            <div class="footer-left">
              <div class="footer-logo">
                <a href="#"><img src="img/footer-logo.png" alt="" /></a>
              </div>
              <ul>
                <li>Address: 60-49 Road 11378 New York</li>
                <li>Phone: +65 11.188.888</li>
                <li>Email: hello.colorlib@gmail.com</li>
              </ul>
              <div class="footer-social">
                <a href="#"><i class="fa fa-facebook"></i></a>
                <a href="#"><i class="fa fa-instagram"></i></a>
                <a href="#"><i class="fa fa-twitter"></i></a>
                <a href="#"><i class="fa fa-pinterest"></i></a>
              </div>
            </div>
          </div>
          <div class="col-lg-2 offset-lg-1">
            <div class="footer-widget">
              <h5>Information</h5>
              <ul>
                <li><a href="#">About Us</a></li>
                <li><a href="#">Checkout</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="#">Serivius</a></li>
              </ul>
            </div>
          </div>
          <div class="col-lg-2">
            <div class="footer-widget">
              <h5>My Account</h5>
              <ul>
                <li><a href="#">My Account</a></li>
                <li><a href="#">Contact</a></li>
                <li><a href="#">Shopping Cart</a></li>
                <li><a href="#">Shop</a></li>
              </ul>
            </div>
          </div>
          <div class="col-lg-4">
            <div class="newslatter-item">
              <h5>Join Our Newsletter Now</h5>
              <p>
                Get E-mail updates about our latest shop and special offers.
              </p>
              <form action="#" class="subscribe-form">
                <input type="text" placeholder="Enter Your Mail" />
                <button type="button">Subscribe</button>
              </form>
            </div>
          </div>
        </div>
      </div>
      <div class="copyright-reserved">
        <div class="container">
          <div class="row">
            <div class="col-lg-12">
              <div class="copyright-text">
                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
                Copyright &copy;
                <script>
                  document.write(new Date().getFullYear());
                </script>
                All rights reserved | This template is made with
                <i class="fa fa-heart-o" aria-hidden="true"></i> by
                <a href="https://colorlib.com" target="_blank">Colorlib</a>
                <!-- Link back to Colorlib can't be removed. Template is licensed under CC BY 3.0. -->
              </div>
              <div class="payment-pic">
                <img src="img/payment-method.png" alt="" />
              </div>
            </div>
          </div>
        </div>
      </div>
    </footer>
    <!-- Footer Section End -->
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
