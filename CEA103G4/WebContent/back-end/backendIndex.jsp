<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%
	EmpVO empVO = (EmpVO)session.getAttribute("account");
	session.setAttribute("empVO", empVO);
%>

<!DOCTYPE html>
<html lang="zh-tw">
<head>
<meta name="description" content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
<!-- Twitter meta-->
<meta property="twitter:card" content="summary_large_image">
<meta property="twitter:site" content="@pratikborsadiya">
<meta property="twitter:creator" content="@pratikborsadiya">
<!-- Open Graph Meta-->
<meta property="og:type" content="website">
<meta property="og:site_name" content="Vali Admin">
<meta property="og:title" content="Vali - Free Bootstrap 4 admin theme">
<meta property="og:url"
	content="http://pratikborsadiya.in/blog/vali-admin">
<meta property="og:image"
	content="http://pratikborsadiya.in/blog/vali-admin/hero-social.png">
<meta property="og:description"
	content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
<title>Mode Femme 後台管理系統</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Main CSS-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

</head>
<body class="app sidebar-mini rtl">
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
						href="<%=request.getContextPath()%>/emp/emp.do?empno=${empVO.empno}&action=getOne_For_Display">${empVO.empno}<i
							class="fa fa-user fa-lg"></i> Profile
					</a></li>
					<li><a class="dropdown-item"
						href="<%=request.getContextPath()%>/back-end/backendLogin.jsp"><i
							class="fa fa-sign-out fa-lg"></i> Logout</a></li>
				</ul></li>
		</ul>
	</header>
	<!-- Sidebar menu-->
	<div class="app-sidebar__overlay" data-toggle="sidebar"></div>
	<aside class="app-sidebar">
		<div class="app-sidebar__user">
<!-- 			<img class="app-sidebar__user-avatar" -->
<!-- 				src="#" -->
<!-- 				alt="User Image"> -->
			<div>
				<p class="app-sidebar__user-name">${empVO.ename}</p>
				<p class="app-sidebar__user-designation">Frontend Developer</p>
			</div>
		</div>
		<ul class="app-menu">
			<li><a class="app-menu__item active"
				href="<%=request.getContextPath()%>/back-end/backendIndex.jsp""><i
					class="app-menu__icon fa fa-dashboard"></i><span
					class="app-menu__label">後台首頁</span></a></li>
			<li class="treeview"><a class="app-menu__item" href="#"
				data-toggle="treeview"><i class="app-menu__icon fa fa-laptop"></i><span
					class="app-menu__label">後台管理</span><i
					class="treeview-indicator fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/emp/listAllEmp.jsp"><i
							class="icon fa fa-circle-o"></i>員工管理</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/emp/addEmp.jsp"><i
							class="icon fa fa-circle-o"></i>新增員工</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/fun/listAllFun.jsp"><i
							class="icon fa fa-circle-o"></i>功能管理</a></li>
				</ul></li>

			<li class="treeview"><a class="app-menu__item" href="#"
				data-toggle="treeview"><i class="app-menu__icon fa fa-edit"></i><span
					class="app-menu__label">商品管理</span><i
					class="treeview-indicator fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/product_type/listAllProduct_Type.jsp"><i
							class="icon fa fa-circle-o"></i>商品類別管理</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/product_type/addProduct_Type.jsp"><i
							class="icon fa fa-circle-o"></i> 新增商品類別</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/product/listAllProduct.jsp"><i
							class="icon fa fa-circle-o"></i>直售商品管理</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/product/addProduct.jsp"><i
							class="icon fa fa-circle-o"></i> 新增直售商品</a></li>
					<li><a class="treeview-item" href="#"><i
							class="icon fa fa-circle-o"></i>直撥商品管理(none)</a></li>
					<li><a class="treeview-item" href="#"><i
							class="icon fa fa-circle-o"></i> 新增直撥商品(none)</a></li>
				</ul></li>
			<li class="treeview"><a class="app-menu__item" href="#"
				data-toggle="treeview"><i class="app-menu__icon fa fa-th-list"></i><span
					class="app-menu__label">檢舉管理</span><i
					class="treeview-indicator fa fa-angle-right"></i></a>
				<ul class="treeview-menu">
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/product_report/listAllProduct_Report.jsp"><i
							class="icon fa fa-circle-o"></i> 商品檢舉管理</a></li>
					<li><a class="treeview-item"
						href="<%=request.getContextPath()%>/back-end/live_report/listAllLive_report.jsp"><i
							class="icon fa fa-circle-o"></i> 直撥檢舉管理</a></li>
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
					<li><a class="treeview-item" href="page-lockscreen.html"><i
							class="icon fa fa-circle-o"></i> Lockscreen Page</a></li>
					<li><a class="treeview-item" href="page-user.html"><i
							class="icon fa fa-circle-o"></i> User Page</a></li>
					<li><a class="treeview-item" href="page-invoice.html"><i
							class="icon fa fa-circle-o"></i> Invoice Page</a></li>
					<li><a class="treeview-item" href="page-calendar.html"><i
							class="icon fa fa-circle-o"></i> Calendar Page</a></li>
					<li><a class="treeview-item" href="page-mailbox.html"><i
							class="icon fa fa-circle-o"></i> Mailbox</a></li>
					<li><a class="treeview-item" href="page-error.html"><i
							class="icon fa fa-circle-o"></i> Error Page</a></li>
				</ul></li>
			<li><a class="app-menu__item" href="charts.html"><i
					class="app-menu__icon fa fa-pie-chart"></i><span
					class="app-menu__label">Charts</span></a></li>
		</ul>
	</aside>
	<main class="app-content">
		<div class="app-title">
			<div>
				<h1>
					<i class="fa fa-dashboard"></i> Dashboard
				</h1>
				<p>A free and open source Bootstrap 4 admin template</p>
			</div>
			<ul class="app-breadcrumb breadcrumb">
				<li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
				<li class="breadcrumb-item"><a href="#">Dashboard</a></li>
			</ul>
		</div>

		<%--                 <jsp:include page = "/back-end/emp/selectEmp.jsp" /> --%>

		<div class="row">
			<div class="col-md-6 col-lg-3">
				<div class="widget-small primary coloured-icon">
					<i class="icon fa fa-users fa-3x"></i>
					<div class="info">
						<h4>Users</h4>
						<p>
							<b>5</b>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-lg-3">
				<div class="widget-small info coloured-icon">
					<i class="icon fa fa-thumbs-o-up fa-3x"></i>
					<div class="info">
						<h4>Likes</h4>
						<p>
							<b>25</b>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-lg-3">
				<div class="widget-small warning coloured-icon">
					<i class="icon fa fa-files-o fa-3x"></i>
					<div class="info">
						<h4>Uploades</h4>
						<p>
							<b>10</b>
						</p>
					</div>
				</div>
			</div>
			<div class="col-md-6 col-lg-3">
				<div class="widget-small danger coloured-icon">
					<i class="icon fa fa-star fa-3x"></i>
					<div class="info">
						<h4>Stars</h4>
						<p>
							<b>500</b>
						</p>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">Monthly Sales</h3>
					<div class="embed-responsive embed-responsive-16by9">
						<canvas class="embed-responsive-item" id="lineChartDemo"></canvas>
					</div>
				</div>
			</div>
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">Support Requests</h3>
					<div class="embed-responsive embed-responsive-16by9">
						<canvas class="embed-responsive-item" id="pieChartDemo"></canvas>
					</div>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">Features</h3>
					<ul>
						<li>Built with Bootstrap 4, SASS and PUG.js</li>
						<li>Fully responsive and modular code</li>
						<li>Seven pages including login, user profile and print
							friendly invoice page</li>
						<li>Smart integration of forgot password on login page</li>
						<li>Chart.js integration to display responsive charts</li>
						<li>Widgets to effectively display statistics</li>
						<li>Data tables with sort, search and paginate functionality</li>
						<li>Custom form elements like toggle buttons, auto-complete,
							tags and date-picker</li>
						<li>A inbuilt toast library for providing meaningful response
							messages to user's actions</li>
					</ul>
					<p>Vali is a free and responsive admin theme built with
						Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.</p>
					<p>
						Vali is is light-weight, expendable and good looking theme. The
						theme has all the features required in a dashboard theme but this
						features are built like plug and play module. Take a look at the <a
							href="http://pratikborsadiya.in/blog/vali-admin" target="_blank">documentation</a>
						about customizing the theme colors and functionality.
					</p>
					<p class="mt-4 mb-4">
						<a class="btn btn-primary mr-2 mb-2"
							href="http://pratikborsadiya.in/blog/vali-admin" target="_blank"><i
							class="fa fa-file"></i>Docs</a><a class="btn btn-info mr-2 mb-2"
							href="https://github.com/pratikborsadiya/vali-admin"
							target="_blank"><i class="fa fa-github"></i>GitHub</a><a
							class="btn btn-success mr-2 mb-2"
							href="https://github.com/pratikborsadiya/vali-admin/archive/master.zip"
							target="_blank"><i class="fa fa-download"></i>Download</a>
					</p>
				</div>
			</div>
			<div class="col-md-6">
				<div class="tile">
					<h3 class="tile-title">Compatibility with frameworks</h3>
					<p>This theme is not built for a specific framework or
						technology like Angular or React etc. But due to it's modular
						nature it's very easy to incorporate it into any front-end or
						back-end framework like Angular, React or Laravel.</p>
					<p>
						Go to <a href="http://pratikborsadiya.in/blog/vali-admin"
							target="_blank">documentation</a> for more details about
						integrating this theme with various frameworks.
					</p>
					<p>
						The source code is available on GitHub. If anything is missing or
						weird please report it as an issue on <a
							href="https://github.com/pratikborsadiya/vali-admin"
							target="_blank">GitHub</a>. If you want to contribute to this
						theme pull requests are always welcome.
					</p>
				</div>
			</div>
		</div>
	</main>
	<!-- Essential javascripts for application to work-->
	<script src="<%=request.getContextPath()%>/back-template/docs/js/jquery-3.2.1.min.js"></script>
	<script src="<%=request.getContextPath()%>/back-template/docs/js/popper.min.js"></script>
	<script src="<%=request.getContextPath()%>/back-template/docs/js/bootstrap.min.js"></script>
	<script src="<%=request.getContextPath()%>/back-template/docs/js/main.js"></script>
	<!-- The javascript plugin to display page loading on top-->
	<script src="<%=request.getContextPath()%>/back-template/docs/js/plugins/pace.min.js"></script>
	<!-- Page specific javascripts-->
	<script src="<%=request.getContextPath()%>/back-template/docs/js/plugins/chart.js"></script>
	<script>
	</script>
	<script>
		var data = {
			labels : [ "January", "February", "March", "April", "May" ],
			datasets : [ {
				label : "My First dataset",
				fillColor : "rgba(220,220,220,0.2)",
				strokeColor : "rgba(220,220,220,1)",
				pointColor : "rgba(220,220,220,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(220,220,220,1)",
				data : [ 65, 59, 80, 81, 56 ]
			}, {
				label : "My Second dataset",
				fillColor : "rgba(151,187,205,0.2)",
				strokeColor : "rgba(151,187,205,1)",
				pointColor : "rgba(151,187,205,1)",
				pointStrokeColor : "#fff",
				pointHighlightFill : "#fff",
				pointHighlightStroke : "rgba(151,187,205,1)",
				data : [ 28, 48, 40, 19, 86 ]
			} ]
		};
		var pdata = [ {
			value : 300,
			color : "#46BFBD",
			highlight : "#5AD3D1",
			label : "Complete"
		}, {
			value : 50,
			color : "#F7464A",
			highlight : "#FF5A5E",
			label : "In-Progress"
		} ]

		var ctxl = $("#lineChartDemo").get(0).getContext("2d");
		var lineChart = new Chart(ctxl).Line(data);

		var ctxp = $("#pieChartDemo").get(0).getContext("2d");
		var pieChart = new Chart(ctxp).Pie(pdata);
	</script>
	<!-- Google analytics script-->
	<script type="text/javascript">
		if (document.location.hostname == 'pratikborsadiya.in') {
			(function(i, s, o, g, r, a, m) {
				i['GoogleAnalyticsObject'] = r;
				i[r] = i[r] || function() {
					(i[r].q = i[r].q || []).push(arguments)
				}, i[r].l = 1 * new Date();
				a = s.createElement(o), m = s.getElementsByTagName(o)[0];
				a.async = 1;
				a.src = g;
				m.parentNode.insertBefore(a, m)
			})(window, document, 'script',
					'//www.google-analytics.com/analytics.js', 'ga');
			ga('create', 'UA-72504830-1', 'auto');
			ga('send', 'pageview');
		}
	</script>

</body>
</html>