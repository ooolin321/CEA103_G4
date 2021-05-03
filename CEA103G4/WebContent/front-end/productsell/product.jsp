<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.product.model.*"%>
<%@ page import="com.product_type.model.*"%>
<%@ page import="com.product_report.model.*"%>

<%
  ProductVO productVO = (ProductVO) request.getAttribute("productVO");

	Product_TypeDAO dao2 = new Product_TypeDAO();
	List<Product_TypeVO> list2 = dao2.getAll();
	pageContext.setAttribute("list2",list2);
	
%>
<jsp:useBean id="product_typeSvc" scope="page" class="com.product_type.model.Product_TypeService" />
<jsp:useBean id="productSvc" scope="page" class="com.product.model.ProductService" />
<jsp:useBean id="product_reportSvc" scope="page" class="com.product_report.model.Product_ReportService" />

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
              <h4 class="fw-title">商品分類</h4>
              <ul class="filter-catagories">
              <c:forEach var="product_typeVO" items="${list2}" begin="0" end="${list2.size()-1}">
              <li><div class="catagoriesQuery" value="${product_typeVO.pdtype_no}">${product_typeVO.pdtype_name}</div></li>
                </c:forEach>
              </ul>
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
                  data-min="0"
                  data-max="2000"
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
              <div id="moneyRange" class="filter-btn">價格篩選</div>

            </div>
            <div class="filter-widget">
              <h4 class="fw-title">進階查詢</h4>
              <div class="fw-all-choose" id="fw-all-choose">
              <div class="fw-cs" id="fw-cs">
               <c:forEach var="product_typeVO" items="${list2}" begin="0" end="${list2.size()-1}">
                <div class="cs-item">
                 <label for="${product_typeVO.pdtype_name}">
                    ${product_typeVO.pdtype_name}
                   <input type="checkbox"  id="${product_typeVO.pdtype_name}" name="pdtypeNo" value="${product_typeVO.pdtype_no}" />
                   <span class="checkmark"></span>
                  </label>
                 </div>
                </c:forEach>
                </div>
                <div class="fw-price">
                <div class="sc-item">
                  <input type="radio" id="a-price" name="productPrice" value="A"/>
                  <label for="a-price">$300<i class="fa fa-arrow-circle-down"></i></label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="b-price"name="productPrice" value="B"/>
                  <label for="b-price">$301~$500</label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="c-price" name="productPrice" value="C"/>
                  <label for="c-price">$501~$1000</label>
                </div>
                <div class="sc-item">
                  <input type="radio" id="d-price" name="productPrice" value="D"/>
                  <label for="d-price">$1001<i class="fa fa-arrow-circle-up"></i></label>
                </div>
                </div>
                <div class="fw-all-btn">
                <div class="filter-btn" id="fw-all-btn">送出查詢</div>
                <div  class="filter-btn" id="clearallbtn">清除全部</div>
                </div>
             </div>
          </div>
          </div>
          <!-- 左邊功能列結束 -->
          
          <!-- 右邊商品區塊 -->
          <div class="col-lg-9">
            <div class="row">
              <div class="col-lg-6">
                <div class="product-pic-zoom">
                  <img
                    class="product-big-img"
                    src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${productVO.product_no}" alt="${productVO.product_name}"
                  />
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

                    <h4><span>$</span>
                        ${productVO.product_price}</h4>
                  </div>
                  <div class="quantity">
                    <div class="pro-qty">
                     <span id="decProduct" class="dec qtybtn">-</span>
                      <input name="proqty" type="text" value="1" />
                      <span id="addProduct" class="inc qtybtn" style="none">+</span>
                    </div>
                    <a href="#" class="primary-btn pd-cart">加入購物車</a>
                  </div>
                  <ul class="pd-tags">
                    <li>
                      <span id="maxRemaining" value="${productVO.product_remaining}">商品數量：${productVO.product_remaining}</span>
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
                      <a href="javascript:;" id="reportLink" class="primary-btn">商品檢舉</a>
                    </div>
<!--                     檢舉燈箱 -->
<div  class="report-bg">
                    <div class="report">
      <div class="report-title" value="${productVO.product_no}">
        檢舉商品名稱：${productVO.product_name}
        <span><a href="javascript:;" id="closeBtn">關閉</a></span>
      </div>

      <div class="report-input-content">
        <div class="report-input">
          <label for="">檢舉內容</label>
          <input
            type="text"
            placeholder="請輸入檢舉原因"
            name="pro_report_content"
            id="username"
            size="50"
            value=""
          />
        </div>
      </div>
      <div class="report-button">
        <div id="report-submit">提交檢舉</div>
      </div>
    </div>
    </div>
    <!--遮蓋層-->
<!--     燈箱結束 -->
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
                          <textarea class="form-control" id="product_info" style="resize:none; white-space:pre-wrap;" maxlength="300" rows="6" name="product_info" readonly><%=productVO.getProduct_info()%></textarea>
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
<!--     <div class="related-products spad"> -->
<!--       <div class="container"> -->
<!--         <div class="row"> -->
<!--           <div class="col-lg-12"> -->
<!--             <div class="section-title"> -->
<!--               <h2>Related Products</h2> -->
<!--             </div> -->
<!--           </div> -->
<!--         </div> -->
<!--         <div class="row"> -->
<%--         <c:forEach var="productVO" items="${product_typeSvc.ProductsByPdtype_no(4003)}" begin="0" end="3"> --%>
<!--           <div class="col-lg-3 col-sm-6"> -->
<!--        <div class="card mb-2 productcard"> -->
<!--             <div class="product-item" > -->
<!--                 <div class="pi-pic"> -->
<!--                 <div class="pi-img"> -->
<%--                 <a href="<%=request.getContextPath()%>/product/product.do?product_no=${productVO.product_no}"> --%>
<%--                     <img class="card-img-top"  src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${productVO.product_no}" alt="${productVO.product_name}">            --%>
<!--                     </a>      	 -->
<!--                   </div> -->
<!--                     <ul> -->
<!--                         <li class="w-icon active"> -->
<!--                             <a href="#"><i class="icon_bag_alt"></i></a> -->
<!--                         </li>    -->
<!--                         <li class="w-heart" > -->
<%--                             <i class="icon_heart_alt"  data-no="${productVO.product_no}"></i> --%>
<!--                         </li> -->
<!--                     </ul> -->
<!--                 </div> -->
<!--                 <div class="pi-text"> -->
<%--                   <a href="<%=request.getContextPath()%>/product/product.do?product_no=${productVO.product_no}">                   --%>
<%--                         <h5>${productVO.product_name}</h5>     --%>
<!--                     	 <div class="product-price"><span>$</span> -->
<%--                           ${productVO.product_price} --%>
<!--                     	</div> -->
<!--                     </a> -->
<!--                 </div> -->
<!--             </div> -->
<!--         </div> -->
<!--         </div> -->
<%--           </c:forEach> --%>
<!--           </div> -->
<!--           <div class="col-lg-3 col-sm-6"> -->
<!--             <div class="product-item"> -->
<!--               <div class="pi-pic"> -->
<%--                 <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-2.jpg" alt="" /> --%>
<!--                 <div class="icon"> -->
<!--                   <i class="icon_heart_alt"></i> -->
<!--                 </div> -->
<!--                 <ul> -->
<!--                   <li class="w-icon active"> -->
<!--                     <a href="#"><i class="icon_bag_alt"></i></a> -->
<!--                   </li> -->
<!--                   <li class="quick-view"><a href="#">+ Quick View</a></li> -->
<!--                   <li class="w-icon"> -->
<!--                     <a href="#"><i class="fa fa-random"></i></a> -->
<!--                   </li> -->
<!--                 </ul> -->
<!--               </div> -->
<!--               <div class="pi-text"> -->
<!--                 <div class="catagory-name">Shoes</div> -->
<!--                 <a href="#"> -->
<!--                   <h5>Guangzhou sweater</h5> -->
<!--                 </a> -->
<!--                 <div class="product-price">$13.00</div> -->
<!--               </div> -->
<!--             </div> -->
<!--           </div> -->
<!--           <div class="col-lg-3 col-sm-6"> -->
<!--             <div class="product-item"> -->
<!--               <div class="pi-pic"> -->
<%--                 <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-3.jpg" alt="" /> --%>
<!--                 <div class="icon"> -->
<!--                   <i class="icon_heart_alt"></i> -->
<!--                 </div> -->
<!--                 <ul> -->
<!--                   <li class="w-icon active"> -->
<!--                     <a href="#"><i class="icon_bag_alt"></i></a> -->
<!--                   </li> -->
<!--                   <li class="quick-view"><a href="#">+ Quick View</a></li> -->
<!--                   <li class="w-icon"> -->
<!--                     <a href="#"><i class="fa fa-random"></i></a> -->
<!--                   </li> -->
<!--                 </ul> -->
<!--               </div> -->
<!--               <div class="pi-text"> -->
<!--                 <div class="catagory-name">Towel</div> -->
<!--                 <a href="#"> -->
<!--                   <h5>Pure Pineapple</h5> -->
<!--                 </a> -->
<!--                 <div class="product-price">$34.00</div> -->
<!--               </div> -->
<!--             </div> -->
<!--           </div> -->
<!--           <div class="col-lg-3 col-sm-6"> -->
<!--             <div class="product-item"> -->
<!--               <div class="pi-pic"> -->
<%--                 <img src="${pageContext.request.contextPath}/front-template/images/productsell/women-4.jpg" alt="" /> --%>
<!--                 <div class="icon"> -->
<!--                   <i class="icon_heart_alt"></i> -->
<!--                 </div> -->
<!--                 <ul> -->
<!--                   <li class="w-icon active"> -->
<!--                     <a href="#"><i class="icon_bag_alt"></i></a> -->
<!--                   </li> -->
<!--                   <li class="quick-view"><a href="#">+ Quick View</a></li> -->
<!--                   <li class="w-icon"> -->
<!--                     <a href="#"><i class="fa fa-random"></i></a> -->
<!--                   </li> -->
<!--                 </ul> -->
<!--               </div> -->
<!--               <div class="pi-text"> -->
<!--                 <div class="catagory-name">Towel</div> -->
<!--                 <a href="#"> -->
<!--                   <h5>Converse Shoes</h5> -->
<!--                 </a> -->
<!--                 <div class="product-price">$34.00</div> -->
<!--               </div> -->
<!--             </div> -->
<!--           </div> -->
<!--         </div> -->
<!--       </div> -->
<!--     </div> -->
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

    
    
    	<script>

	//ajax header搜尋框	
	
    $("#sendQuery").on('click', () => { 
		var datas = {
		  "product_name":$("#product_name").val(),      				
		  "action":"search_ajax" 
		}; 
	  sendQuery(datas); 
  }); 
	$("#search").on('submit',(event) => { 
	  event.preventDefault()
	  	var datas = {
		  "product_name":$("#product_name").val(),      				
		  "action":"search_ajax" 
		};
	  sendQuery(datas); 
  }); 
	
	//功能列 分類
		$(".catagoriesQuery").click(function() {
			var datas = { 
	 		  	"pdtype_no":$(this).attr("value"),
	 			  "action":"search_ajax" 
	 			}; 
	 		  sendQuery(datas); 
	 	  });
	
	//價格篩選
	$("#moneyRange").click(function(){
	  var min = $("#minamount").val();
	  var max = $("#maxamount").val();
	  min = min.substring(min.indexOf("$")+1);
	  max = max.substring(max.indexOf("$")+1);
	  var datas = {
			  minPrice: min,
			  maxPrice: max,
			  action: 'moneyRange'
			}
			sendQuery(datas);
	})
	
	
	//進階查詢ajax
	$("#fw-all-btn").click(function(){
		var arr = [];
		$('input[name="pdtypeNo"]:checked').each(function(i) {
	     	arr.push($(this).attr("value"));
		});
		var type = $('input[name="productPrice"]:checked').val();
		var datas = {
			pdtypeNo: arr,
			productPrice: type,
			action: 'fw-all-choose'
		}
		sendQuery(datas);
	});
	
	function sendQuery(datas){ 
		
		$.ajax({ 
		  url:"<%=request.getContextPath()%>/ProductSearch",
		  type:"POST", 
		  data:datas,
		  success: function(result) { 
			const obj  = JSON.parse(result);
				if(obj["results"].length == 0){
					alert('很抱歉,查無此商品');
	            } else {
	            	var data = JSON.stringify(result);
<%-- 	            	window.location.href='<%=request.getContextPath()%>/ProductSearch?action=productToShop'; --%>
					window.location.href='<%=request.getContextPath()%>/front-end/productsell/shop.jsp?data='+encodeURI(data);

	            }
		  }, 
		  
		  error:function () {
			  alert('很抱歉,查無此商品');
		  },
			
		 }) 
	}
	
	//檢舉燈箱js 

	 var reportLink = document.querySelector("#reportLink");
	  var closeBtn = document.querySelector("#closeBtn");
	  var report = document.querySelector(".report");
	  var report_bg = document.querySelector(".report-bg");

	  //1,燈箱顯示/隱藏
	  reportLink.addEventListener("click", function () {
	    report.style.display = "block";
	    report_bg.style.display = "block";
	  });
	  closeBtn.addEventListener("click", function () {
	    report.style.display = "none";
	    report_bg.style.display = "none";
	  });

	  //2，拖曳
	  var report_title = document.querySelector(".report-title");
	  report_title.addEventListener("mousedown", function (e) {
	    //鼠標按下的時候,得到鼠標在框裡的座標
	    var x = e.pageX - report.offsetLeft;
	    var y = e.pageY - report.offsetTop;
	    document.addEventListener("mousemove", move); //鼠標移動的時候，得到狀態框座標
	    function move(e) {
	      report.style.left = e.pageX - x + "px";
	      report.style.top = e.pageY - y + "px";
	    }
	    document.addEventListener("mouseup", function () {
	      //鼠標彈起,解除移動事件
	      document.removeEventListener("mousemove", move);
	    });
	  });
	  
	  //商品檢舉AJAX  

	  $(".report-button").click(function(){
			
			$.ajax({ 
			  url:"<%=request.getContextPath()%>/product_report/product_report.do",
			  type:"POST", 
			  data:{
				  "pro_report_content": $('input[name="pro_report_content"]').val(),
				  "product_no": $(".report-title").attr("value"),
				  "user_id": "abcd01",
				  "action": "insert"
			  },
			  success: function() { 
						alert('商品檢舉已提交');
					    report.style.display = "none";
					    report_bg.style.display = "none";	
		            }, 	  
			  error:function () {
				  alert('很抱歉,檢舉提交失敗,請重新提交。');
			  },				
			 }) 
	  });
	  
	  
	  
	  
       	</script>	
    
    
    
  </body>
</html>
