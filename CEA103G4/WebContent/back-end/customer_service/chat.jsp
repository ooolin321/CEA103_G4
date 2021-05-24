<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.user.model.*"%>

<%
EmpVO empVO = (EmpVO) session.getAttribute("empAccount");
session.setAttribute("empVO", empVO);
UserVO userVO2 = (UserVO) session.getAttribute("account");
session.setAttribute("userVO", userVO2);
	// out.println(userVO2.getUser_name());
	// out.println(empVO.getEname());
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
	<title>所有員工資料</title>
	<link rel="icon" href="${pageContext.request.contextPath}/front-template/images/favicon.ico" type="image/x-icon">
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1">
	<!-- Main CSS-->
	<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
	<!-- Font-icon css-->
	<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

	<%-- <link rel="stylesheet" href="<%=request.getContextPath()%>/back-end/customer_service/assets/css/amazeui.min.css"> --%>

	<!-- <link rel="stylesheet" -->
	<%-- 	href="<%=request.getContextPath()%>/back-end/customer_service/css/friendchat.css" --%>
	<!-- 	type="text/css" /> -->

	<style type="text/css">
.app-title h1 {
    display: inline-block;
    margin-right: 15px;
}
h2, .h2{
font-size: 1.75rem;
    background-color: #007d71;
}
    
.SellerHomeBtn .btn-outline-warning:hover {
	color:white;

}

.widget-small .info p {
    margin: 0;
    font-size: 14px;
}


/*  		.panel {  */
/* 			overflow: right;  */
/*  			border: 2px solid #0078ae;  */
/* 			border-radius: 5px;  */
/* 			width: 50%; */
/* 		}  */

		.message-area {
			height: 400px;
			resize: none;
			box-sizing: border-box;
			overflow: auto;
			background-color: #ffffff;
		}

		.input-area {
			background: #007d71	;
			box-shadow: inset 0 0 10px #00568c;
		}

		.input-area input {
			margin: 0.5em 0em 0.5em 0.5em;
			
		}

		.text-field {
			border: 1px solid grey;
			padding: 0.2em;
			box-shadow: 0 0 5px #000000;
		}

		h1 {
			font-size: 1.5em;
			padding: 5px;
			margin: 5px;
		}

		#message {
			min-width: 50%;
			max-width: 60%;
		}

		.statusOutput {
			background: #007d71;
			text-align: center;
			color: #ffffff;
			border: 1px solid grey;
			padding: 0.2em;
			box-shadow: 0 0 5px #000000;
			width: 30%;
			margin-top: 10%;
			margin-left: 60%;
		}
		.column {
			float: left;
			width: 50%;
			padding: 5%;
			margin-bottom: 5px;
			background-color: #fff;
		}

		#area{
			list-style: none;
			margin: 0;
			padding: 0;
		}

		#lis{
			display:inline;
			clear: both;
			padding: 20px;
			border-radius: 30px;
			margin-bottom: 2px;
			font-family: Helvetica, Arial, sans-serif;
		}

		#time{
			width:100%;
			float: right;
			color: black;
			text-align: right;

		}

		#friendtime {
			width:100%;
			float: left;
			color: black;
		}

		.friend{
			background: #eee;
			float: left;
		}

		.me{
			float: right;
			background: #007d71;
			color: #fff;
		}

		.friend + .me{
			border-bottom-right-radius: 5px;
		}

		.me + .me{
			border-top-right-radius: 5px;
			border-bottom-right-radius: 5px;
		}

		.me:last-of-type {
			border-bottom-right-radius: 30px;
		}


	</style>
	<title>Made Femme 客服聊天室</title>
</head>
<body class="app sidebar-mini rtl  pace-done" onload="connect()	" onunload="disconnect(); ">
	<jsp:include page="/back-end/backendMenu.jsp" />
	<main class="app-content">
		<div class="app-title">
			<div>
				<h1>
					<i class="fa fa-group"></i> 客服訊息
				</h1>			
			</div>

			<ul class="app-breadcrumb breadcrumb">
				<li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
				<li class="breadcrumb-item"><a
					href="<%=request.getContextPath()%>/back-end/backendIndex.jsp">回到首頁</a></li>
				</ul>
			</div>

			<!-- 	<div id="showbox"> -->

				<!-- 	</div> -->
				<h3 id="statusOutput" class="statusOutput"></h3>
				<div id="row"></div>
				<div id="messagesArea" class="panel message-area" >
	
				</div>
				<div class="panel input-area">
					<input id="message" class="text-field" type="text" placeholder="Message" onkeydown="if (event.keyCode == 13) sendMessage();" /> 
					<input type="submit" id="sendMessage" class="button" value="Send" onclick="sendMessage();" /> 
					<input type="button" id="connect" class="button" value="Connect" onclick="connect();" /> 
					<input type="button" id="disconnect" class="button" value="Disconnect" onclick="disconnect();" />
					<input type="button" onclick="history.back()" value="回到上一頁"></input>
				</div>
			</main>
			<jsp:include page="/back-end/backendfooter.jsp" />
			<script>
				var MyPoint = "/CustomerWS/${empName}";
				var host = window.location.host;
				var path = window.location.pathname;
				var webCtx = path.substring(0, path.indexOf('/', 1));
				var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;

				var statusOutput = document.getElementById("statusOutput");
				var messagesArea = document.getElementById("messagesArea");
				var self = '${empName}';
				var webSocket;

				function connect() {
		// create a websocket
		webSocket = new WebSocket(endPointURL);

		webSocket.onopen = function(event) {
			console.log("Connect Success!");
			document.getElementById('sendMessage').disabled = false;
			document.getElementById('connect').disabled = true;
			document.getElementById('disconnect').disabled = false;
		};

		webSocket.onmessage = function(event) {
			var jsonObj = JSON.parse(event.data);
			if("openEmp"===jsonObj.type){
				refreshFriendList(jsonObj);
			}
			else if("empNotAvailable"===jsonObj.type){
				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				var li = document.createElement('li');
				li.id = "lis";
				li.className = 'me'
				li.innerHTML = "目前客服不在線";
				ul.appendChild(li);
			}
			else if ("history" === jsonObj.type) {

				messagesArea.innerHTML = '';
				var ul = document.createElement('ul');
				ul.id = "area";
				messagesArea.appendChild(ul);
				// 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
				var messages = JSON.parse(jsonObj.message);
				for (var i = 0; i < messages.length; i++) {
					var historyData = JSON.parse(messages[i]);
					var showMsg = historyData.message;
					
					var li = document.createElement('li');
					li.id = "lis";
					var span = document.createElement('span');
					span.id = "time";
					// 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
					historyData.sender === self ? li.className = 'me' : li.className = 'friend' ;
					historyData.time === self ? span.id = 'time' : span.id = 'friendtime' ;
					li.innerHTML = showMsg;
					$(span).text(historyData.time);
					ul.appendChild(li);
					li.appendChild(span);
				}
				messagesArea.scrollTop = messagesArea.scrollHeight;
			} else if ("chat" === jsonObj.type) {

				// var li = document.getElementsByClassName("msg-left");
				// var div = document.getElementsByClassName("msg-left-sub");
				// var div = document.getElementsByClassName("msg-desc");
				// var small = document.createElement("small");
				// jsonObj.sender === self?li.className="msg-left":li.className="msg-right";
				// jsonObj.sender === self?small.className='time' :li.className='friendtime';
				// li.innerHTML === jsonObj.message;
				// small.innerHTML === jsonObj.time;
				// document.getElementById("area").appendChild("li");
				// document.getElementById("area").appendChild("small");
				// messagesArea.scrollTop = messagesArea.scrollHeight;

				var li = document.createElement('li');
				li.id = "lis";
				var span = document.createElement('span');
				span.id = "time";
				jsonObj.sender === self ? li.className = 'me' : li.className = 'friend';
				jsonObj.sender === self ? span.id = 'time' : span.id = 'friendtime';
				li.innerHTML = jsonObj.message;
				span.innerHTML = jsonObj.time;
				document.getElementById("area").appendChild(li);
				document.getElementById("area").appendChild(span);
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
		var friend = statusOutput.textContent;
		var message = inputMessage.value.trim();
		var time = new Date();
		var timeStr = time.getFullYear() + "-" + (time.getMonth()+1).toString().padStart(2, "0") + "-" 
		+ time.getDate() + " " + time.getHours().toString().padStart(2, "0") + ":" + time.getMinutes().toString().padStart(2, "0");
		if (message === "") {
			alert("Input a message");
			inputMessage.focus();
		} 
		else if (friend === "") {
			alert("Choose a friend");
		} 
		else {
			var jsonObj = {
				"type" : "chat",
				"sender" : self,
				"receiver" : friend,
				"message" : message,
				"time":timeStr
			};
			webSocket.send(JSON.stringify(jsonObj));
			inputMessage.value = "";
			inputMessage.focus();
		}
	}
	
	// 有好友上線或離線就更新列表
	function refreshFriendList(jsonObj) {
		var friends = jsonObj.users;
		console.log(jsonObj);
		var row = document.getElementById("row");
		row.innerHTML = '';
		for (var i = 0; i < friends.length; i++) {
			if (friends[i] === self) { continue; }//從所有好友列表排除自己的帳號
			row.innerHTML +='<div id=' + i + ' class="column" name="friendName" value=' + friends[i] + ' >  <h2>' + friends[i] + '</h2></div>';
		}
		addListener();
	}
	// 註冊列表點擊事件並抓取好友名字以取得歷史訊息
	function addListener() {
		var container = document.getElementById("row");
		container.addEventListener("click", function(e) {
			var friend = e.srcElement.textContent;
			updateFriendName(friend);
			var jsonObj = {
				"type" : "history",
				"sender" : self,
				"receiver" : friend,
				"message" : "",
				"time":""
			};
			console.log(jsonObj);
			webSocket.send(JSON.stringify(jsonObj));
		});
	}
	
	function disconnect() {
		webSocket.close();
		document.getElementById('sendMessage').disabled = true;
		document.getElementById('connect').disabled = false;
		document.getElementById('disconnect').disabled = true;
	}
	
	function updateFriendName(name) {
		statusOutput.innerHTML = name;
	}

	function ShowTime(){
		　var NowDate=new Date();
		　var h=NowDate.getHours();
		　var m=NowDate.getMinutes();
		　var s=NowDate.getSeconds();　
		　document.getElementById('showbox').innerHTML = h+'時'+m+'分'+s+'秒';
		　setTimeout('ShowTime()',1000);
	}
</script>


</body>

</html>