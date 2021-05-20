<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="java.util.*"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.product_type.model.*"%>
<%@ page import="com.product.model.*"%>
<%
Product_TypeDAO dao2 = new Product_TypeDAO();
List<Product_TypeVO> list2 = dao2.getAll();
pageContext.setAttribute("list2", list2);

Vector<ProductVO> buylist = (Vector<ProductVO>) session.getAttribute("shoppingcart");
pageContext.setAttribute("buylist", buylist);

UserVO userVO2 = (UserVO) session.getAttribute("account");
session.setAttribute("userVO", userVO2);
%>

<!-- Page Preloder -->
<div id="preloder">
	<div class="loader"></div>
</div>
<style>
#chatBtn {
	position: fixed;
	right: 10px;
	bottom: 0px;
	z-index: 99999;
}

.mini-chat {
	position: fixed;
	right: 10px;
	bottom: 0px;
	z-index: 99999;
	visibility: hidden;
	border-radius: 10px;
}

.chat {
	position: absolute;
	bottom: 0.5px;
	border-radius: 10px;
}

.friend_id {
	display: inline-block;
	position: absolute;
	right: 50%;
}

.cancel {
	position: absolute;
	right: 1px;
	display: inline-block;
}

/* .plane { */
/* 	position: absolute; */
/* 	display: inline-block; */
/* 	margin-right: 1px; */
/* 	border-radius: 10px; */
/* } */
.chat-input {
	border: 1px;
	display: inline-block;
	border-radius: 10px;
}

.middle {
	position: absolute;
	display: block;
	margin: 3px auto;
	top: 20px;
	width: 238px;
	heigh: 245px;
	background: white;
	resize: none;
	padding: 5px;
	overflow: auto;
}

.me {
	float: right;
	background: #0084ff;
	margin-right: 4px;
	border-radius: 6px 6px 0px 6px;
}

.friend {
	float: left;
	background: #0084ff;
	margin-left: 4px;
	border-radius: 6px 6px 6px 0px;
}

ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

ul li {
	display: inline-block;
	clear: both;
	padding: 2px;
	margin-bottom: 2px;
	font-family: Helvetica, Arial, sans-serif;
}
</style>
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
				<!-- 				<div class="col-lg-7 col-md-7"> -->
				<!-- 					<div class="advanced-search"> -->
				<%-- 						<form class="input-group" id="search" METHOD="post" ACTION="<%=request.getContextPath()%>/ProductSearch"> --%>
				<!-- 							<input type="text" id="search-input" name="product_name" placeholder="What do you need?" />  -->
				<!-- 							<input type="hidden" name="action" value="search" /> -->
				<!-- 							<button type="submit"><i class="ti-search"></i></button> -->
				<!-- 						</form> -->
				<!-- 					</div> -->
				<!-- 				</div> -->
				<!-- ------------JSP先不用↑------------ -->
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
					<c:if test="${not empty userVO.user_id}">
						<div class="header-right">
							<FORM id="userLogOut" METHOD="post" class="logout-form"
								action="<%=request.getContextPath()%>/User_LogoutHandler">
								<a
									href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp">
									<span class="userLogin" style="cursor: pointer"><img
										class="rounded-circle" width="45px" height="40px"
										src="${pageContext.request.contextPath}/UserShowPhoto?user_id=${userVO.user_id}" />&nbsp;
										${userVO.user_name} </span>
								</a> <input type="hidden" name="action" value="signOut"> <a
									href="#"
									onclick="document.getElementById('userLogOut').submit();"><button
										type="button" class="btn">登出</button></a>
							</FORM>
						</div>
					</c:if>
					<c:if test="${empty userVO.user_name}">
						<div class="header-right">
							<a
								href="<%=request.getContextPath()%>/front-end/user/register.jsp"><button
									type="button" class="btn">註冊</button></a> <a
								href="<%=request.getContextPath()%>/front-end/userLogin.jsp"
								target="_blank"><button type="button" class="btn">登入</button></a>
						</div>
					</c:if>
					<!-- 鈴鐺/購物車顯示的數字+購物車預覽圖要改 -->
					<ul class="nav-right">
						<li class="bell-icon"><a href="#"> <svg
									xmlns="http://www.w3.org/2000/svg" width="20" height="20"
									fill="currentColor" class="bi bi-bell" viewBox="0 0 16 16">
                      <path
										d="M8 16a2 2 0 0 0 2-2H6a2 2 0 0 0 2 2zM8 1.918l-.797.161A4.002 4.002 0 0 0 4 6c0 .628-.134 2.197-.459 3.742-.16.767-.376 1.566-.663 2.258h10.244c-.287-.692-.502-1.49-.663-2.258C12.134 8.197 12 6.628 12 6a4.002 4.002 0 0 0-3.203-3.92L8 1.917zM14.22 12c.223.447.481.801.78 1H1c.299-.199.557-.553.78-1C2.68 10.2 3 6.88 3 6c0-2.42 1.72-4.44 4.005-4.901a1 1 0 1 1 1.99 0A5.002 5.002 0 0 1 13 6c0 .88.32 4.2 1.22 6z" />
                    </svg> 
                    <span>0</span>
						</a></li>
						<li class="cart-icon"><a
							href="${pageContext.request.contextPath}/front-end/productsell/shoppingCart.jsp">
								<i class="icon_bag_alt"></i> <c:if test="${buylist != null}">
									<span id="iba">${buylist.size()}</span>
								</c:if> <c:if test="${buylist == null}">
									<span id="iba">0</span>
								</c:if>
						</a> <%-- 						<c:if test="${buylist != null && buylist.size() > 0}"> --%>
							<div class="cart-hover">
								<div class="select-items">
									<table>
										<tbody id="carts">
											<c:set var="sum" value="0">
											</c:set>
											<c:forEach var="order" items="${buylist}"
												varStatus="cartstatus">
												<tr>
													<td class="si-pic"><img
														src="${pageContext.request.contextPath}/ProductShowPhoto?product_no=${order.product_no}"
														alt="${order.product_name}"
														style="width: 100px; height: 100px; border-radius: 10px;" /></td>
													<td class="si-text">
														<div class="product-selected">
															<p>$${order.product_price } x
																${order.product_quantity}</p>
															<h6>${order.product_name }</h6>
														</div>
													</td>
												</tr>
												<c:set var="sum"
													value="${sum + order.product_price*order.product_quantity}"></c:set>
											</c:forEach>
										</tbody>
									</table>
								</div>
								<div class="select-total">
									<span>total:</span>
									<h5 id="cartHoverTotal">$${sum}</h5>
								</div>
								<div class="select-button">
									<a
										href="${pageContext.request.contextPath}/front-end/productsell/shoppingCart.jsp"
										class="primary-btn view-card">購物車清單</a> <a
										href="${pageContext.request.contextPath}/front-end/protected/check-out.jsp"
										class="primary-btn view-card">購物車清單</a>
									<a
										href="${pageContext.request.contextPath}/front-end/protected/check-out.html"
										class="primary-btn checkout-btn">結帳</a>
								</div>
							</div> <%-- 							</c:if> --%></li>
						<c:if test="${buylist.size() > 0 }">
							<li class="cart-price" id="totalprice">$${sum}</li>
						</c:if>
						<c:if test="${buylist == null}">
							<li class="cart-price">$0</li>
						</c:if>
					</ul>
				</div>
			</div>
		</div>
	</div>
	<div class="nav-item">
		<div class="container">
			<div class="nav-depart">
				<div class="depart-btn">
					<i class="ti-menu"></i> <span>商品分類</span>
					<!-- 					<i class="fa fa-hand-o-down" id="ti-fa-hand"></i> -->
					<ul class="depart-hover">
						<c:forEach var="product_typeVO" items="${list2}" begin="0"
							end="${list2.size()}">
							<a
								href="<%=request.getContextPath()%>/product_type/product_type.do?action=getProductsByPdtype&pdtype_no=${product_typeVO.pdtype_no}"><li><div>${product_typeVO.pdtype_name}</div></li></a>
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
					<li><a
						href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播專區</a>
						<ul class="dropdown">
							<li><a
								href="<%=request.getContextPath()%>/front-end/live/liveWall.jsp">直播牆</a></li>
							<li><a
								href="<%=request.getContextPath()%>/front-end/live/livePreview.jsp">直播預告</a></li>
							<!-- <li><a href="#">Kid's</a></li> -->
						</ul></li>
					<li><a
						href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp">會員專區<i
							class="icon_profile"></i></a></li>
					<li>
<<<<<<< HEAD
						<form id="myForm" action="<%=request.getContextPath()%>/chat.do"
							method="POST">
							<input value="${userVO.user_id}" name="userName" type="hidden" />
							<a href="#" onclick="document.getElementById('myForm').submit();">線上客服&nbsp;<i
								class="fa fa-comment-o"></i></a>
						</form>
=======
					<form  id="myForm" action="<%=request.getContextPath() %>/chat.do" method="POST">
					<input value="${userVO.user_name}" name="userName" type="hidden"/>
					<a href="#" onclick="document.getElementById('myForm').submit();">線上客服&nbsp;<i class="fa fa-comment-o"></i></a>
					</form>
>>>>>>> b81e7df55e21aa42830abd929d20726bdd1d2dc8
					</li>
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

	<div id="chatBtn">
		<!-- <div class="chat-notice">1</div> -->
		<div class="chat-btn">
			私訊&nbsp;<i class="fa fa-commenting-o"></i>
		</div>
	</div>

	<div class="mini-chat">
		<div class="content">
			<div class="top-bar">
				<div id="statusOutput" class="friend_id"></div>
				<div class="cancel">
					<i class="ti-close"></i>
				</div>
			</div>
			<div class="middle" id="messagesArea" style="height: 240px"></div>
			<div class="bottom-bar">
				<div class="chat">
					<input id="message" class="chat-input" type="text"
						placeholder="Type a message..." size="21"
						onkeydown="if (event.keyCode == 13) sendMessage();"> <i
						id="sendMessage" class="fa fa-paper-plane"
						onclick="sendMessage();"></i>
				</div>
			</div>

		</div>
	</div>


</header>
<!-- Header End -->
<!-- heade搜尋 -->
<script>
const chatBtn = document.querySelector(".chat-btn");
const miniChat = document.querySelector(".mini-chat");
const closeChatBtn = document.querySelector(".ti-close");
// let seller;
chatBtn.addEventListener("click", function() {
	miniChat.style.visibility = "visible";
	chatBtn.style.visibility = "hidden";
});
closeChatBtn.addEventListener("click", function() {
	miniChat.style.visibility = "hidden";
	chatBtn.style.visibility = "visible";
})

 function getSellerId(){

	seller = "${productVO.user_id}";
// 	if(seller == "${userVO.user_id}"){
// 		this.disabled=true;
// 		window.alert("請勿私聊自己");
// 	}else{
	chatBtn.style.visibility="hidden";
	miniChat.style.visibility="visible";
	connect();
// 	}
}
    	if("${userVO.user_id}" == ""){
		    document.getElementById("chatBtn").style.visibility="hidden";
    	}
    var MyPoint = "/FriendChatWS/${userVO.user_id}";
    var host = window.location.host;
    var path = window.location.pathname;
    var webCtx = path.substring(0, path.indexOf('/', 1));
    var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
			
    const statusOutput = document.getElementById("statusOutput");
    const messagesArea = document.getElementById("messagesArea");
    var self = '${userVO.user_id}';
    var webSocket;
    		
   
    function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);
		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			updateFriendName(seller);
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if ("open" === jsonObj.type) {
// 				refreshFriendList(jsonObj);
				addListener(seller);
			} else if ("history" === jsonObj.type) {
				statusOutput.innerHTML = seller;
				const ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					var li = document.createElement('li');
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className += 'me'
							: li.className += 'friend';
					li.innerHTML = showMsg;
					ul.appendChild(li);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {
				var li = document.createElement('li');
				jsonObj.sender === self ? li.className += 'me'
						: li.className += 'friend';
				li.innerHTML = jsonObj.message;
				console.log(li);
				document.getElementById("area").appendChild(li);
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("close" === jsonObj.type) {
				refreshFriendList(jsonObj);
			}

		};

		webSocket.onclose = function(event) {
			console.log("Disconnected!");
		};
	}

	function sendMessage() {
		var inputMessage = document.getElementById("message");
		let friend = statusOutput.textContent;
		var message = inputMessage.value.trim();

		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} else if (friend === "") {
			alert("Choose a friend");
		} else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"message" : message
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}

	// 有好友上線或離線就更新列表
	/* function refreshFriendList(jsonObj) {
		var friends = jsonObj.users;
		var row = document.getElementById("row");
		row.innerHTML = '';
		for (var i = 0; i < friends.length; i++) {
			if (friends[i] === self) { continue; }
			row.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' ><h2>' + friends[i] + '</h2></div>';
		}
		addListener();
	} */
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener(seller) {
// 		const friend = seller;
// 		updateFriendName(friend);
		
		var jsonObj = {
			"type" : "history",
			"sender" : self,
			"receiver" : seller,
			"message" : ""
		};
		webSocket.send(JSON.stringify(jsonObj));
	}

	function disconnect() {
		webSocket.close();
	}

	function updateFriendName(id) {
		const friend = id;
		statusOutput.innerHTML = friend;
	}
    
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
					window.location.href='<%=request.getContextPath()%>
	/front-end/productsell/shop.jsp?data='
									+ encodeURI(data);
						}
					},
					error : function() {
						alert('很抱歉,查無此商品');
					},

				})
	}
</script>