<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<!-- Page Preloder -->
<div id="preloder">
	<div class="loader"></div>
</div>
<!-- Header Section Begin -->
<header class="header-section">
	<div class="container">
		<div class="inner-header">
			<div class="row">
				<div class="col-lg-2 col-md-2">
					<div class="logo">
						<a href="${pageContext.request.contextPath}/front-end/index.jsp">
							<h2>
								Mode femme <br />
								<small>Second&nbsp;Hand </small>
							</h2>
						</a>
					</div>
				</div>
<!-- 				<div class="col-lg-7 col-md-7"> -->
<!-- 					<div class="advanced-search"> -->
<%-- 						<form class="input-group" id="search" METHOD="post" ACTION="<%=request.getContextPath()%>/ProductSearch"> --%>
<!-- 							<input type="text" id="search-input" name="product_name" placeholder="What do you need?" />  -->
<!-- 							<input type="hidden" name="action" value="search" /> -->
<!-- 							<button type="submit"><i class="ti-search"></i></button> -->
<!-- 						</form> -->
<!-- 					</div> -->
<!-- 				</div> -->
<!-- ------------ajax先不用------------ -->
				<div class="col-lg-7 col-md-7">
					<div class="advanced-search">
						<form class="input-group" id="search">
							<input type="text" id="product_name" name="product_name" value=""
								placeholder="What do you need?" />
							<button type="button" id="sendQuery" onclick="sendQuery">
								<i class="ti-search"></i>
							</button>
						</form>
					</div>
				</div>
				<div class="col-lg-3 text-right col-md-3">
					<div class="header-right">
						<a
							href="${pageContext.request.contextPath}/front-end/user/register.html"><button
								type="button" class="btn">註冊</button></a> <a
							href="${pageContext.request.contextPath}/front-end/user/login.html"><button
								type="button" class="btn">登入</button></a>
					</div>
					<!-- 鈴鐺/購物車顯示的數字+購物車預覽圖要改 -->
					<ul class="nav-right">
						<li class="bell-icon"><a href="#"> <svg
									xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
                      <path
										d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
                    </svg> <span>1</span>
						</a></li>
						<li class="cart-icon"><a
							href="${pageContext.request.contextPath}/front-end/productsell/shopping-cart.html">
								<i class="icon_bag_alt"></i> <span>3</span>
						</a>
							<div class="cart-hover">
								<div class="select-items">
									<table>
										<tbody>
											<tr>
												<td class="si-pic"><img
													src="${pageContext.request.contextPath}/front-template/images/productsell/select-product-1.jpg"
													alt="" /></td>
												<td class="si-text">
													<div class="product-selected">
														<p>$60.00 x 1</p>
														<h6>Kabino Bedside Table</h6>
													</div>
												</td>
												<td class="si-close"><i class="ti-close"></i></td>
											</tr>
											<tr>
												<td class="si-pic"><img
													src="${pageContext.request.contextPath}/front-template/images/productsell/select-product-2.jpg"
													alt="" /></td>
												<td class="si-text">
													<div class="product-selected">
														<p>$60.00 x 1</p>
														<h6>Kabino Bedside Table</h6>
													</div>
												</td>
												<td class="si-close"><i class="ti-close"></i></td>
											</tr>
										</tbody>
									</table>
								</div>
								<div class="select-total">
									<span>total:</span>
									<h5>$120.00</h5>
								</div>
								<div class="select-button">
									<a
										href="${pageContext.request.contextPath}/front-end/productsell/shopping-cart.html"
										class="primary-btn view-card">購物車清單</a>
									<a
										href="${pageContext.request.contextPath}/front-end/productsell/check-out.html"
										class="primary-btn checkout-btn">結帳</a>
								</div>
							</div></li>
						<li class="cart-price">$150.00</li>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="nav-item">
		<div class="container">
			<div class="nav-depart">
				<div class="depart-btn">
					<i class="ti-menu"></i> <span>商品分類</span> <i
						class="fa fa-hand-o-down" id="ti-fa-hand"></i>
					<ul class="depart-hover">
						<c:forEach var="product_typeVO" items="${list2}" begin="0" end="${list2.size()}">
						<li><div class="catagoriesQuery" value="${product_typeVO.pdtype_no}">${product_typeVO.pdtype_name}</div></li>
               			 </c:forEach>
					</ul>
				</div>
			</div>
			<nav class="nav-menu mobile-menu">
				<ul>
					<li class="active" id="nav-index"><a
						href="${pageContext.request.contextPath}/front-end/index.jsp">首頁</a></li>
					<li><a
						href="<%=request.getContextPath()%>/front-end/productsell/shop.jsp">商品專區</a></li>
					<li><a href="#">直播專區</a>
						<ul class="dropdown">
							<li><a href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播牆</a></li>
							<li><a href="#">直播預告</a></li>
							<!-- <li><a href="#">Kid's</a></li> -->
						</ul></li>
					<li><a href="#">會員專區<i class="icon_profile"></i></a></li>
					<!-- <li>
                <a href="#">Pages</a>
                <ul class="dropdown">
                  <li><a href="./blog-details.html">Blog Details</a></li>
                  <li><a href="./shopping-cart.html">Shopping Cart</a></li>
                  <li><a href="./check-out.html">Checkout</a></li>
                  <li><a href="./faq.html">Faq</a></li>
                  <li><a href="./register.html">Register</a></li>
                  <li><a href="./login.html">Login</a></li>
                </ul>
              </li> -->
				</ul>
			</nav>
			<div id="mobile-menu-wrap"></div>
		</div>
	</div>
	
</header>
<!-- Header End -->