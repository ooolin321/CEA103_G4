<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.live.model.*"%>

<%
	LiveVO liveVO = (LiveVO) request.getAttribute("liveVO");
%>
<jsp:useBean id="liveSvc" scope="page"
	class="com.live.model.LiveService" />

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8" />
<meta name="viewport" content="width=device-width, initial-scale=1.0" />
<meta http-equiv="X-UA-Compatible" content="ie=edge" />
<title>Modefemme</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/front-template/images/favicon.ico"
	type="image/x-icon">

<!-- Google Font -->
<link
	href="https://fonts.googleapis.com/css?family=Muli:300,400,500,600,700,800,900&display=swap"
	rel="stylesheet" />

<!-- Css Styles -->
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/bootstrap.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/font-awesome.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/themify-icons.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/elegant-icons.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/owl.carousel.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/nice-select.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/jquery-ui.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/slicknav.min.css"
	type="text/css" />
<link rel="stylesheet"
	href="${pageContext.request.contextPath}/front-template/css/style.css"
	type="text/css" />
<style>
#messagesArea {
	height: 500px;
	width: 100%;
	border: 1px solid #bbbbbb;
	overflow-y: scroll;
	display: inline-block;
	right: 30px;
}

#stream {
	height: 500px;
	width: 100%;
	padding: 5px;
	border: 1px solid #bbbbbb;
	display: inline-block;
}

#messagesArea>p {
	margin: 0;
	color: #bbbbbb;
	font-size: 1rem;
}

input {
	width: 100%;
	border: 1px solid #bbbbbb;
	border-top-width: 0;
	box-sizing: border-box;
	color: #bbbbbb;
	background-color: #222222;
	outline: none;
	font-family: Monospace;
	font-size: 1rem;
	right: 0px;
}

@media ( min-width : 768px) {
	.col-md-3 {
		padding: 0;
		max-width: 100%;
	}
	@media ( min-width : 768px) {
		.col-md-9 {
			padding: 0;
			max-width: 100%;
		}
	}
}
</style>
</head>

<body onload="connect();" onunload="disconnect();">

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
									Mode femme <br /> <small>Second&nbsp;Hand </small>
								</h2>
							</a>
						</div>
					</div>
					<div class="col-lg-7 col-md-7"></div>
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
											class="primary-btn view-card">購物車清單</a> <a
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
						<li><a href="#">會員專區<i class="icon_profile"></i></a></li>
					</ul>
				</nav>
				<div id="mobile-menu-wrap"></div>
			</div>
		</div>
	</header>
	<!-- Header End -->

	<!-- Breadcrumb Section Begin -->
	<div class="breacrumb-section">
		<div class="container">
			<div class="row">
				<div class="col-lg-12">
					<div class="breadcrumb-text live-more">
						<a href="${pageContext.request.contextPath}/front-end/index.jsp"><i
							class="fa fa-home"></i> 首頁</a> <a
							href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播</a>
						<span>直播室</span>
					</div>
				</div>
			</div>
		</div>
	</div>
	<!-- Breadcrumb Section Begin -->
<body>

	<div class="row">
		<div class="col-md-9">
			<div id="stream">
				<img src="https://via.placeholder.com/1000x550">
				</video>
			</div>
		</div>

		<div class="col-md-3">
			<textarea id="messagesArea" class="panel message-area" readonly></textarea>
			<div class="input">
				<input id="userName" class="text-field" type="text"
					placeholder="User name" /> <input id="message" class="text-field"
					type="text" placeholder="Message"
					onkeydown="if (event.keyCode == 13) sendMessage();" />
			</div>
		</div>

	</div>
	<div class="row">
		<div class="col-md-12">123123</div>
	</div>



</body>


<!-- Footer Section Begin -->
<%@include file="/front-end/footer.jsp"%>
<!-- Footer Section End -->



<!-- Js Plugins -->


<script src="https://unpkg.com/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.3.1.slim.min.js"
	integrity="sha384-q8i/X+965DzO0rT7abK41JStQIAqVgRVzpbzo5smXKp4YfRvH+8abtTE1Pi6jizo"
	crossorigin="anonymous">
	
</script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.0/umd/popper.min.js"
	integrity="sha384-cs/chFZiN24E4KMATLdqdvsezGxaGsi4hLGOzlXwp5UZB1LY//20VyM2taTB4QvJ"
	crossorigin="anonymous">
	
</script>


<script
	src="${pageContext.request.contextPath}/front-template/js/jquery-3.3.1.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/bootstrap.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery-ui.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery.countdown.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery.nice-select.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery.zoom.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery.dd.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/jquery.slicknav.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/owl.carousel.min.js"></script>
<script
	src="${pageContext.request.contextPath}/front-template/js/main.js"></script>

<script>
	var MyPoint = "/TogetherWS/james";
	var host = window.location.host;
	var path = window.location.pathname;
	var webCtx = path.substring(0, path.indexOf('/', 1));
	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

	var statusOutput = document.getElementById("statusOutput");
	var webSocket;

	function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			updateStatus("WebSocket Connected");
			document.getElementById('sendMessage').disabled = false;
			document.getElementById('connect').disabled = true;
			document.getElementById('disconnect').disabled = false;
		};

		webSocket.onmessage = function(event) {
			var messagesArea = document.getElementById("messagesArea");
			var jsonObj = JSON.parse(event.data);
			var message = jsonObj.userName + ": " + jsonObj.message + "\r\n";
			messagesArea.value = messagesArea.value + message;
			messagesArea.scrollTop = messagesArea.scrollHeight;
		};

		webSocket.onclose = function(event) {
			updateStatus("WebSocket Disconnected");
		};
	}

	var inputUserName = document.getElementById("userName");
	inputUserName.focus();

	function sendMessage() {
		var userName = inputUserName.value.trim();
		if (userName === "") {
			alert("Input a user name");
			inputUserName.focus();
			return;
		}

		var inputMessage = document.getElementById("message");
		var message = inputMessage.value.trim();

		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else {
			var jsonObj = {
				"userName" : userName,
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}

	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
		document.getElementById('connect').disabled = false;
		document.getElementById('disconnect').disabled = true;
	}

	function updateStatus(newStatus) {
		statusOutput.innerHTML = newStatus;
	}
</script>
</html>