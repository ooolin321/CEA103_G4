<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.product_type.model.*"%>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");

	Product_TypeDAO dao2 = new Product_TypeDAO();
	List<Product_TypeVO> list2 = dao2.getAll();
	pageContext.setAttribute("list2",list2);
%>
<jsp:useBean id="product_typeSvc" scope="page" class="com.product_type.model.Product_TypeService" />
<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />

<!DOCTYPE html>
<html>
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
              <a href="${pageContext.request.contextPath}/front-end/index.jsp"><i class="fa fa-home"></i> Home</a>
              <a href="${pageContext.request.contextPath}/front-end/productsell/shop.jsp">Shop</a>
              <span>Detail</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb Section Begin -->

    <!-- Product Shop Section Begin -->
    <section class="product-shop spad page-details">
      <div class="container">
        <div class="row">
          <div class="col-lg-3">
            <div class="filter-widget">
              <h4 class="fw-title">Categories</h4>
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
              <h4 class="fw-title">Tags</h4>
              <div class="fw-tags">
                <a href="#">Towel</a>
                <a href="#">Shoes</a>
                <a href="#">Coat</a>
                <a href="#">Dresses</a>
                <a href="#">Trousers</a>
                <a href="#">Men's hats</a>
                <a href="#">Backpack</a>
              </div>
            </div>
          </div>
          <div class="col-lg-9">
            <div class="row">
              <div class="col-lg-6">
                <div class="product-pic-zoom">
                  <img
                    class="product-big-img"
                    src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${productVO.product_no}" alt="${productVO.product_name}"
                  />
                  <div class="zoom-icon">
                    <i class="fa fa-search-plus"></i>
                  </div>
                </div>
              </div>
              <div class="col-lg-6">
                <div class="product-details">
                  <div class="pd-title">
                    <!-- 動態串商品名 -->
                    <h3>${productVO.product_name}</h3>
                    <a href="#" class="heart-icon"
                      ><i class="icon_heart_alt"></i
                    ></a>
                  </div>
                  <div class="pd-desc">
                    <!-- <p>
                      Lorem ipsum dolor sit amet, consectetur ing elit, sed do
                      eiusmod tempor sum dolor sit amet, consectetur adipisicing
                      elit, sed do mod tempor
                    </p> -->
                    <h4><span>$</span>
                        ${productVO.product_price}</h4>
                  </div>
                  <div class="quantity">
                    <div class="pro-qty">
                      <input type="text" value="1" />
                    </div>
                    <a href="#" class="primary-btn pd-cart">加入購物車</a>
                  </div>
                  <ul class="pd-tags">
                    <li>
                      <!-- 商品類別改動態 -->
                      <span>${product_typeSvc.getOneProduct_Type(productVO.pdtype_no).pdtype_name}</span>: More Accessories, Wallets & Cases
                    </li>
                    <!-- <li><span>TAGS</span>: Clothing, T-shirt, Woman</li> -->
                  </ul>
                  <div class="pd-share">
                    <div class="pd-social">
                      <a href="#"><i class="ti-facebook"></i></a>
                      <a href="#"><i class="ti-twitter-alt"></i></a>
                      <a href="#"><i class="ti-linkedin"></i></a>
                    </div>
                  </div>
                  <div class="pd-fungroup">
                    <div>
                      <a href="#" class="primary-btn pd-buy">直接購買</a>
                    </div>
                    <div class="pd-function">
                      <a href="#" class="primary-btn">私訊賣家</a>
                      <a href="#" class="primary-btn">關注賣家</a>
                      <a href="#" class="primary-btn">商品檢舉</a>
                    </div>
                    <div class="p-code"><span>Pno : </span> ${productVO.product_no}</div>
                  </div>
                </div>
              </div>
            </div>
            <div class="product-tab">
              <div class="tab-item">
                <ul class="nav" role="tablist">
                  <li>
                    <a class="active" data-toggle="tab" href="#tab-1" role="tab"
                      >商品詳情</a
                    >
                  </li>
                  <li>
                    <a data-toggle="tab" href="#tab-2" role="tab">關於賣家</a>
                  </li>
                  <li>
                    <a data-toggle="tab" href="#tab-3" role="tab"
                      >賣家評價 (02)</a
                    >
                  </li>
                </ul>
              </div>
              <div class="tab-item-content">
                <div class="tab-content">
                  <div
                    class="tab-pane fade-in active"
                    id="tab-1"
                    role="tabpanel"
                  >
                    <div class="product-content">
                      <div class="row">
                        <div class="col-lg-7">
                          <h5>商品說明</h5>
                          <p>
							${productVO.product_info}
                          </p>
                          <h5>Features</h5>
                          <p>
                            Lorem ipsum dolor sit amet, consectetur adipisicing
                            elit, sed do eiusmod tempor incididunt ut labore et
                            dolore magna aliqua. Ut enim ad minim veniam, quis
                            nostrud exercitation ullamco laboris nisi ut aliquip
                            ex ea commodo consequat. Duis aute irure dolor in
                          </p>
                        </div>
                        <div class="col-lg-5">
                          <img src="${pageContext.request.contextPath}/front-template/images/productsell/tab-desc.jpg" alt="" />
                        </div>
                      </div>
                    </div>
                  </div>
                  <div class="tab-pane fade" id="tab-2" role="tabpanel">
                    <div class="specification-table">
                      <table>
                        <tr>
                          <td class="p-catagory">賣家帳號</td>
                          <td>
                            <div class="p-price">${productVO.user_id}</div>
                          </td>
                        </tr>
                        <tr>
                          <td class="p-catagory">賣家評價</td>
                          <td>
                            <div class="pd-rating">
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star-o"></i>
                              <span>(5)</span>
                            </div>
                          </td>
                        </tr>

                        <tr>
                          <td class="p-catagory">加入時間</td>
                          <td>
                            <div class="cart-add">24個月 前</div>
                          </td>
                        </tr>
                        <tr>
                          <td class="p-catagory">粉絲</td>
                          <td>
                            <div class="p-stock">22</div>
                          </td>
                        </tr>
                        <tr>
                          <td class="p-catagory">查看賣場</td>
                          <td>
                            <div class="p-weight">1,3kg</div>
                          </td>
                        </tr>
                        <tr>
                          <td class="p-catagory">Size</td>
                          <td>
                            <div class="p-size">Xxl</div>
                          </td>
                        </tr>
                        <tr>
                          <td class="p-catagory">Color</td>
                          <td><span class="cs-color"></span></td>
                        </tr>
                        <tr>
                          <td class="p-catagory">Sku</td>
                          <td>
                            <div class="p-code">00012</div>
                          </td>
                        </tr>
                      </table>
                    </div>
                  </div>
                  <div class="tab-pane fade" id="tab-3" role="tabpanel">
                    <div class="customer-review-option">
                      <h4>2 Comments</h4>
                      <div class="comment-option">
                        <div class="co-item">
                          <div class="avatar-pic">
                            <img src="${pageContext.request.contextPath}/front-template/images/productsell/avatar-1.png" alt="" />
                          </div>
                          <div class="avatar-text">
                            <div class="at-rating">
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star-o"></i>
                            </div>
                            <h5>Brandon Kelley <span>27 Aug 2019</span></h5>
                            <div class="at-reply">Nice !</div>
                          </div>
                        </div>
                        <div class="co-item">
                          <div class="avatar-pic">
                            <img src="${pageContext.request.contextPath}/front-template/images/productsell/avatar-2.png" alt="" />
                          </div>
                          <div class="avatar-text">
                            <div class="at-rating">
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star"></i>
                              <i class="fa fa-star-o"></i>
                            </div>
                            <h5>Roy Banks <span>27 Aug 2019</span></h5>
                            <div class="at-reply">Nice !</div>
                          </div>
                        </div>
                      </div>
                      <div class="personal-rating">
                        <h6>Your Ratind</h6>
                        <div class="rating">
                          <i class="fa fa-star"></i>
                          <i class="fa fa-star"></i>
                          <i class="fa fa-star"></i>
                          <i class="fa fa-star"></i>
                          <i class="fa fa-star-o"></i>
                        </div>
                      </div>
                      <div class="leave-comment">
                        <h4>Leave A Comment</h4>
                        <form action="#" class="comment-form">
                          <div class="row">
                            <div class="col-lg-6">
                              <input type="text" placeholder="Name" />
                            </div>
                            <div class="col-lg-6">
                              <input type="text" placeholder="Email" />
                            </div>
                            <div class="col-lg-12">
                              <textarea placeholder="Messages"></textarea>
                              <button type="submit" class="site-btn">
                                Send message
                              </button>
                            </div>
                          </div>
                        </form>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </section>
    <!-- Product Shop Section End -->

    <!-- Related Products Section End -->
    <div class="related-products spad">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="section-title">
              <h2>Related Products</h2>
            </div>
          </div>
        </div>
        <div class="row">
          <div class="col-lg-3 col-sm-6">
            <div class="product-item">
              <div class="pi-pic">
                <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-1.jpg" alt="" />
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
          </div>
          <div class="col-lg-3 col-sm-6">
            <div class="product-item">
              <div class="pi-pic">
                <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-2.jpg" alt="" />
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
          </div>
          <div class="col-lg-3 col-sm-6">
            <div class="product-item">
              <div class="pi-pic">
                <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-3.jpg" alt="" />
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
          </div>
          <div class="col-lg-3 col-sm-6">
            <div class="product-item">
              <div class="pi-pic">
                <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-4.jpg" alt="" />
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
      </div>
    </div>
    <!-- Related Products Section End -->


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
