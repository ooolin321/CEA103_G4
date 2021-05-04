<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%
	EmpVO empVO = (EmpVO) session.getAttribute("account");
	session.setAttribute("empVO", empVO);
%>
<!-- Navbar-->
<header class="app-header">
	<a class="app-header__logo"
		href="<%=request.getContextPath()%>/back-end/backendIndex.jsp">Mode
		Femme</a>
	<!-- Sidebar toggle button-->
	<a class="app-sidebar__toggle" href="#" data-toggle="sidebar"
		aria-label="Hide Sidebar"></a>
	<!-- Navbar Right Menu-->
	<ul class="app-nav">
		<li class="app-search"><input class="app-search__input"
			type="search" placeholder="Search">
			<button class="app-search__button">
				<i class="fa fa-search"></i>
			</button></li>
		<!--Notification Menu-->
		<li class="dropdown"><a class="app-nav__item" href="#"
			data-toggle="dropdown" aria-label="Show notifications"><i
				class="fa fa-bell-o fa-lg"></i></a>
			<ul class="app-notification dropdown-menu dropdown-menu-right">
				<li class="app-notification__title">You have 4 new
					notifications.</li>
				<div class="app-notification__content">
					<li><a class="app-notification__item" href="javascript:;"><span
							class="app-notification__icon"><span
								class="fa-stack fa-lg"><i
									class="fa fa-circle fa-stack-2x text-primary"></i><i
									class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
							<div>
								<p class="app-notification__message">Lisa sent you a mail</p>
								<p class="app-notification__meta">2 min ago</p>
							</div></a></li>
					<li><a class="app-notification__item" href="javascript:;"><span
							class="app-notification__icon"><span
								class="fa-stack fa-lg"><i
									class="fa fa-circle fa-stack-2x text-danger"></i><i
									class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
							<div>
								<p class="app-notification__message">Mail server not working</p>
								<p class="app-notification__meta">5 min ago</p>
							</div></a></li>
					<li><a class="app-notification__item" href="javascript:;"><span
							class="app-notification__icon"><span
								class="fa-stack fa-lg"><i
									class="fa fa-circle fa-stack-2x text-success"></i><i
									class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
							<div>
								<p class="app-notification__message">Transaction complete</p>
								<p class="app-notification__meta">2 days ago</p>
							</div></a></li>
					<div class="app-notification__content">
						<li><a class="app-notification__item" href="javascript:;"><span
								class="app-notification__icon"><span
									class="fa-stack fa-lg"><i
										class="fa fa-circle fa-stack-2x text-primary"></i><i
										class="fa fa-envelope fa-stack-1x fa-inverse"></i></span></span>
								<div>
									<p class="app-notification__message">Lisa sent you a mail</p>
									<p class="app-notification__meta">2 min ago</p>
								</div></a></li>
						<li><a class="app-notification__item" href="javascript:;"><span
								class="app-notification__icon"><span
									class="fa-stack fa-lg"><i
										class="fa fa-circle fa-stack-2x text-danger"></i><i
										class="fa fa-hdd-o fa-stack-1x fa-inverse"></i></span></span>
								<div>
									<p class="app-notification__message">Mail server not
										working</p>
									<p class="app-notification__meta">5 min ago</p>
								</div></a></li>
						<li><a class="app-notification__item" href="javascript:;"><span
								class="app-notification__icon"><span
									class="fa-stack fa-lg"><i
										class="fa fa-circle fa-stack-2x text-success"></i><i
										class="fa fa-money fa-stack-1x fa-inverse"></i></span></span>
								<div>
									<p class="app-notification__message">Transaction complete</p>
									<p class="app-notification__meta">2 days ago</p>
								</div></a></li>
					</div>
				</div>
				<li class="app-notification__footer"><a href="#">See all
						notifications.</a></li>
			</ul></li>
		<!-- User Menu-->
		<li class="dropdown"><a class="app-nav__item" href="#"
			data-toggle="dropdown" aria-label="Open Profile Menu"><i
				class="fa fa-user fa-lg"></i></a>
			<ul class="dropdown-menu settings-menu dropdown-menu-right">
				<li><a class="dropdown-item" href="page-user.html"><i
						class="fa fa-cog fa-lg"></i> Settings</a></li>
					<li><a class="dropdown-item"
						href="<%=request.getContextPath()%>/emp/emp.do?empno=${empVO.empno}&action=getOne_For_Display"><i
							class="fa fa-user fa-lg"></i> Profile ${empVO.ename}
					</a></li>
							<li><FORM id="userLogOut" METHOD="post" class="logout-form" action="<%=request.getContextPath()%>/loginhandler">
				     <input type="hidden" name="action" value="signOut">
					<a class="dropdown-item"
						href="#" onclick="document.getElementById('userLogOut').submit();"><i
							class="fa fa-sign-out fa-lg"></i> Logout</a></FORM></li>
			</ul></li>
	</ul>
</header>
<!-- Sidebar menu-->
<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
<aside class="app-sidebar">
	<div class="app-sidebar__user">
		<img class="app-sidebar__user-avatar" src="#" alt="User Image">
		<div>
			<p class="app-sidebar__user-name">${empVO.ename}</p>
			<p class="app-sidebar__user-designation">Frontend Developer</p>
		</div>
	</div>
	<ul class="app-menu">
		<li class="treeview"><a class="app-menu__item active" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-dashboard"></i><span
				class="app-menu__label">員工管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
			<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp"><i
						class="icon fa fa-circle-o"></i>所有員工</a></li>
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/emp/addEmp.jsp"
					target="_blank" rel="noopener"><i class="icon fa fa-circle-o"></i>新增員工</a></li>
			</ul>	
				</li>
		<li class="treeview"><a class="app-menu__item active" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-dashboard"></i><span
				class="app-menu__label">會員管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
			<li><a class="treeview-item"
					href="#"><i
						class="icon fa fa-circle-o"></i>所有會員(none)</a></li>
			</ul>	
				</li>		
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-laptop"></i><span
				class="app-menu__label">權限管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				
				<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/auth/listAllAuth.jsp"><i
							class="icon fa fa-circle-o"></i>員工權限管理</a></li>
<!-- 				<li><a class="treeview-item" -->
<%-- 					href="<%=request.getContextPath()%>/back-end/fun/listAllFun.jsp"><i --%>
<!-- 						class="icon fa fa-circle-o"></i>功能管理</a></li> -->
			</ul></li>

		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-edit"></i><span
				class="app-menu__label">直播管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item" href="<%=request.getContextPath()%>/back-end/liveManagement/listAllLive.jsp"><i
						class="icon fa fa-circle-o"></i>所有直播專案</a></li>
				<li><a class="treeview-item" href="<%=request.getContextPath()%>/back-end/liveManagement/listAllLiveProduct.jsp"><i
						class="icon fa fa-circle-o"></i> 所有直播商品</a></li>
			</ul></li>
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-edit"></i><span
				class="app-menu__label">直售商品管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/product_type/listAllProduct_Type.jsp"><i
						class="icon fa fa-circle-o"></i>商品類別管理</a></li>
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/product_type/addProduct_Type.jsp"><i
						class="icon fa fa-circle-o"></i> 新增商品類別</a></li>
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/product/backProductList.jsp"><i
						class="icon fa fa-circle-o"></i>所有直售商品</a></li>
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/product/addProduct.jsp"><i
						class="icon fa fa-circle-o"></i> 新增直售商品</a></li>
			</ul></li>
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-th-list"></i><span
				class="app-menu__label">直售檢舉管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/product_report/listAllProduct_Report.jsp"><i
						class="icon fa fa-circle-o"></i> 直售商品檢舉</a></li>
			</ul></li>
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-th-list"></i><span
				class="app-menu__label">直播檢舉管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/live_report/listAllLive_report.jsp"><i
						class="icon fa fa-circle-o"></i> 直播檢舉管理</a></li>
			</ul></li>
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-file-text"></i><span
				class="app-menu__label">廣告管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/ad/listAllAd.jsp"><i
						class="icon fa fa-circle-o"></i> 所有廣告</a></li>
				<li><a class="treeview-item"
					href="<%=request.getContextPath()%>/back-end/ad/addAd.jsp"><i
						class="icon fa fa-circle-o"></i> 新增廣告</a></li>
			</ul></li>			
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-file-text"></i><span
				class="app-menu__label">Q&A管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="#"><i
						class="icon fa fa-circle-o"></i> 所有Q&A(none)</a></li>
				<li><a class="treeview-item"
					href="#"><i
						class="icon fa fa-circle-o"></i> 新增Q&A(none)</a></li>
			</ul></li>			
		<li class="treeview"><a class="app-menu__item" href="#"
			data-toggle="treeview"><i class="app-menu__icon fa fa-pie-chart"></i><span
				class="app-menu__label">客服管理</span><i
				class="treeview-indicator fa fa-angle-right"></i></a>
			<ul class="treeview-menu">
				<li><a class="treeview-item"
					href="#"><i
						class="icon fa fa-circle-o"></i> 客服訊息(none)</a></li>
				
			</ul></li>
<!-- 		<li><a class="app-menu__item" href="charts.html"><i
				class="app-menu__icon fa fa-pie-chart"></i><span
				class="app-menu__label">Charts</span></a></li> -->
	</ul>
</aside>
