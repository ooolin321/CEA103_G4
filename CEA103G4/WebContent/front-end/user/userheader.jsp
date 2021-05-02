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
				<div class="col-lg-7 col-md-7">
					<!-- Sidebar toggle button-->
					<a class="app-sidebar__toggle" href="#" data-toggle="sidebar" aria-label="Hide Sidebar"></a>
				</div>
				<div class="col-lg-3 text-right col-md-3">
					<div class="header-right">
      <!--Notification Menu-->
      <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Show notifications"><i class="fa fa-bell-o fa-lg"></i></a>
        <ul class="app-notification dropdown-menu dropdown-menu-right">
          <li class="app-notification__title">You have 4 new notifications.</li>
          <div class="app-notification__content">
            <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-primary"></i><i class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
              <div>
                <p class="app-notification__message">Lisa sent you a mail</p>
                <p class="app-notification__meta">2 min ago</p>
              </div></a></li>
              <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-danger"></i><i class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
                <div>
                  <p class="app-notification__message">Mail server not working</p>
                  <p class="app-notification__meta">5 min ago</p>
                </div></a></li>
                <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-success"></i><i class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
                  <div>
                    <p class="app-notification__message">Transaction complete</p>
                    <p class="app-notification__meta">2 days ago</p>
                  </div></a></li>
                  <div class="app-notification__content">
                    <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-primary"></i><i class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
                      <div>
                        <p class="app-notification__message">Lisa sent you a mail</p>
                        <p class="app-notification__meta">2 min ago</p>
                      </div></a></li>
                      <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-danger"></i><i class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
                        <div>
                          <p class="app-notification__message">Mail server not working</p>
                          <p class="app-notification__meta">5 min ago</p>
                        </div></a></li>
                        <li><a class="app-notification__item" href="javascript:;"><span class="app-notification__icon"><span class="fa-stack fa-lg"><i class="fa fa-circle fa-stack-2x text-success"></i><i class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
                          <div>
                            <p class="app-notification__message">Transaction complete</p>
                            <p class="app-notification__meta">2 days ago</p>
                          </div></a></li>
                        </div>
                      </div>
                      <li class="app-notification__footer"><a href="#">See all notifications.</a></li>
                    </ul>
                  </li>
                  <!-- User Menu-->
                  <li class="dropdown"><a class="app-nav__item" href="#" data-toggle="dropdown" aria-label="Open Profile Menu"><i class="fa fa-user fa-lg"></i></a>
                    <ul class="dropdown-menu settings-menu dropdown-menu-right">
                      <li><a class="dropdown-item" href="page-user.html"><i class="fa fa-cog fa-lg"></i> Settings</a></li>
 <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/user/user.do?user_id=${userVO.user_id}&action=getOne_For_Display"><i class="fa fa-user fa-lg"></i> Profile</a></li>
                      <li><a class="dropdown-item" href="<%=request.getContextPath()%>/front-end/userLogin.jsp"><i class="fa fa-sign-out fa-lg"></i> Logout</a></li>
                    </ul>
                  </li>
                </ul>
              </header>
              <!-- Sidebar menu-->
              <div class="app-sidebar__overlay" data-toggle="sidebar"></div>
              <aside class="app-sidebar">
                <div class="app-sidebar__user"><img class="app-sidebar__user-avatar" src="https://s3.amazonaws.com/uifaces/faces/twitter/jsa/48.jpg" alt="User Image">
                  <div>
                    <p class="app-sidebar__user-designation">Welcome</p>
                    <p class="app-sidebar__user-name">${userVO.user_name}</p>
                  </div>
                </div>
                <ul class="app-menu">
                  <li><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp"><i class="app-menu__icon fa fa-drivers-license-o"></i><span class="app-menu__label">會員首頁</span></a></li>
                  <li class="treeview"><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/user/user.do?user_id=${userVO.user_id}&action=getOne_For_Update" data-toggle="treeview"><i class="app-menu__icon fa fa-gear"></i><span class="app-menu__label">會員資料管理</span></a></li>                  
                   <li class="treeview"><a class="app-menu__item" href="<%=request.getContextPath()%>/front-end/seller/productList.jsp" data-toggle="treeview"><i class="app-menu__icon fa fa-shopping-bag"></i><span class="app-menu__label">商品管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="<%=request.getContextPath()%>/front-end/productManagement/productList.jsp"><i class="icon fa fa-archive"></i>我的商品</a></li>
                      <li><a class="treeview-item" href="<%=request.getContextPath()%>/front-end/productManagement/productAdd.jsp"><i class="icon fa fa-edit"></i>新增商品</a></li>
                    </ul>
                  </li>
                  
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-edit"></i><span class="app-menu__label">直售訂單管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="form-components.html"><i class="icon fa fa-circle-o"></i> Form Components</a></li>
                      <li><a class="treeview-item" href="form-custom.html"><i class="icon fa fa-circle-o"></i> Custom Components</a></li>
                      <li><a class="treeview-item" href="form-samples.html"><i class="icon fa fa-circle-o"></i> Form Samples</a></li>
                      <li><a class="treeview-item" href="form-notifications.html"><i class="icon fa fa-circle-o"></i> Form Notifications</a></li>
                    </ul>
                  </li>
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-th-list"></i><span class="app-menu__label">直播訂單管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="table-basic.html"><i class="icon fa fa-circle-o"></i> Basic Tables</a></li>
                      <li><a class="treeview-item" href="table-data-table.html"><i class="icon fa fa-circle-o"></i> Data Tables</a></li>
                    </ul>
                  </li>
                  <li class="treeview"><a class="app-menu__item" href="#" data-toggle="treeview"><i class="app-menu__icon fa fa-file-text"></i><span class="app-menu__label">直播專案管理</span><i class="treeview-indicator fa fa-angle-right"></i></a>
                    <ul class="treeview-menu">
                      <li><a class="treeview-item" href="blank-page.html"><i class="icon fa fa-circle-o"></i> Blank Page</a></li>
                      <li><a class="treeview-item" href="page-login.html"><i class="icon fa fa-circle-o"></i> Login Page</a></li>
                      <li><a class="treeview-item" href="page-lockscreen.html"><i class="icon fa fa-circle-o"></i> Lockscreen Page</a></li>
                      <li><a class="treeview-item" href="page-user.html"><i class="icon fa fa-circle-o"></i> User Page</a></li>
                      <li><a class="treeview-item" href="page-invoice.html"><i class="icon fa fa-circle-o"></i> Invoice Page</a></li>
                      <li><a class="treeview-item" href="page-calendar.html"><i class="icon fa fa-circle-o"></i> Calendar Page</a></li>
                      <li><a class="treeview-item" href="page-mailbox.html"><i class="icon fa fa-circle-o"></i> Mailbox</a></li>
                      <li><a class="treeview-item" href="page-error.html"><i class="icon fa fa-circle-o"></i> Error Page</a></li>
                    </ul>
                  </li>
                </ul>
              </aside>
	<div class="nav-item">
		<div class="container">
			<div class="nav-depart">
				<div class="depart-btn">
					<i class="ti-menu"></i> <span>商品分類</span> 
<!-- 					<i class="fa fa-hand-o-down" id="ti-fa-hand"></i> -->
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
					<li><a href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播專區</a>
						<ul class="dropdown">
							<li><a href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播牆</a></li>
							<li><a href="#">直播預告</a></li>
							<!-- <li><a href="#">Kid's</a></li> -->
						</ul></li>
					<li><a href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp">會員專區<i class="icon_profile"></i></a></li>
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