<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="sql" uri="http://java.sun.com/jsp/jstl/sql"%>
<%@ page import="com.user.model.*"%>
<%@ page import="com.seller_follow.model.*"%>

<%
	UserVO userVO = (UserVO) session.getAttribute("account"); 
	session.setAttribute("userVO", userVO);
	
	Seller_FollowDAO dao = new Seller_FollowDAO();
    List<Seller_FollowVO> list = dao.getAll();
    pageContext.setAttribute("list",list);
%>


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
  <meta property="og:url" content="http://pratikborsadiya.in/blog/vali-admin">
  <meta property="og:image" content="http://pratikborsadiya.in/blog/vali-admin/hero-social.png">
  <meta property="og:description" content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
  <title>Mode Femme 會員專區</title>
  <link rel="icon" href="${pageContext.request.contextPath}/front-template/images/favicon.ico" type="image/x-icon">
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Main CSS-->
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-template/css/usermain.css">
  <!-- Font-icon css-->
  <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<style>
.app-title h1 {
    display: inline-block;
    margin-right: 15px;
}

.SellerHomeBtn .btn-outline-warning:hover {
	color:white;

}

.widget-small .info p {
    margin: 0;
    font-size: 14px;
}
.mini-chat{	
	width: 240px;
    height: 300px;
    box-sizing: border-box;
    border: 2px solid antiquewhite;
    background:	pink;
    transition: width .25s cubic-bezier(.4,.8,.74,1),height .25s cubic-bezier(.4,.8,.74,1);
    position: fixed;
	right: 74px;
	bottom: 0px;
	z-index: 99999;
	visibility: hidden;
	border-radius: 8px;
	color: white;
}
.mini-chat .chat {
	position: absolute;
	bottom: 0.5px;
	border-radius: 10px;
	padding-left: 5px;
}

#statusOutput {
	display: inline-block;
	position: absolute;
	right: 39%;
	font-weight: 700;
	font-size: 16px;
}
.cancel {
	position: absolute;
	right: 3px;
	display: inline-block;
	cursor: pointer; 
}
.mini-chat .chat-input {
	border: 1px;
	display: inline;
	border-radius: 15px;
	padding-left: 5px;
	margin-bottom: 3px;
	width:200px;
	font-size: 16px;
}
.mini-chat .middle {
	position: absolute;
	display: block;
	margin: 3px auto;
	top: 20px;
	width: 238px;
	height: 245px;
	background: white;
	resize: none;
	padding: 5px;
	overflow: auto;
}
.me {
	float: right;
	background-color: lightblue;
	margin-right: 5px;
	margin-bottom:10px;
	padding:5px;
	border-radius: 10px 10px 0px 10px;
	width: 60%;
	text-align:right;
	padding-right: 10px; 
}
.me img{
	max-width: 130px;
}
.friend {
	float: left;
	background-color: lightgray;
	margin-left: 5px;
	margin-bottom:10px;
	padding:5px;
	border-radius: 10px 10px 10px 0px;
	width: auto;
	text-align:left;
	padding-left: 10px;
}
.friend img{
	max-width: 130px;
}
#messagesArea ul {
	list-style: none;
	margin: 0;
	padding: 0;
}

#messagesArea ul li {
	display: inline-block;
	clear: both;
	font-family: Helvetica, Arial, sans-serif;
	color: white;
}

.mini-chat #sendMessage, .ti-angle-left{
	cursor: pointer; 
}
 .chat input {
 outline: none;

} 

.top-bar{
	font-weight: 700px;
}

.top-bar .list, .ti-close{
	display: inline-block;
	font-size:20px;
}
.top-bar .list{
	left:40%;
	position: absolute;
	font-weight: 500;
}
#sendMessage{
	font-size: 20px;
}
</style>

</head>
<body class="app sidebar-mini rtl">
 <%@include file="/front-end/user/userSidebar.jsp"%>
              <main class="app-content">
                <div class="app-title">
                  <div>
                    <h1><i class="fa fa-user fa-lg"></i> 會員首頁</h1>
                    </div>
                  <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp"><i class="fa fa-home fa-lg"></i></a></li>
                    <li class="breadcrumb-item">會員首頁</li>
                  </ul>
                </div>
                <div class="row">
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small primary coloured-icon"><i class="icon fa fa-product-hunt fa-3x"></i>
                      <div class="info">
                        <h4>會員點數</h4>
						<sql:setDataSource dataSource="jdbc/admin" var="xxx"
							scope="application" />
						<sql:query var="rs" dataSource="${xxx}" startRow="0">
    						 SELECT USER_POINT FROM cea103_g4.USER WHERE USER_ID='${userVO.user_id}'
 						 </sql:query>
 						 <c:forEach var="row" items="${rs.rows}">
						<p><b>${row.user_point}點</b></p>
						</c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small info coloured-icon"><i class="icon fa fa-thumbs-o-up fa-3x"></i>
                      <div class="info">
                        <c:if test="${userVO.user_comment == 0}">
                        <h4>賣家評價</h4>
                        <p><b>無</b></p>
                        </c:if>
                        <c:if test="${userVO.user_comment != 0}">
                        <h4>賣家評價</h4>
                        <sql:query var="rs" dataSource="${xxx}" startRow="0">
    						 SELECT USER_COMMENT,COMMENT_TOTAL FROM cea103_g4.USER WHERE USER_ID='${userVO.user_id}'
 						 </sql:query>
 						 <c:forEach var="row" items="${rs.rows}">
                        <p><b>
                        	  <input type="hidden" name="srating" value="<fmt:formatNumber type="number" value="${row.user_comment/row.comment_total}" maxFractionDigits="0"/>" id="con"/>
                        	  <ion-icon name="star" class="star all-star" id="s1"></ion-icon>
							  <ion-icon name="star" class="star all-star" id="s2"></ion-icon>
							  <ion-icon name="star" class="star all-star" id="s3"></ion-icon>
							  <ion-icon name="star" class="star all-star" id="s4"></ion-icon>
							  <ion-icon name="star" class="star all-star" id="s5"></ion-icon>
							  (${row.comment_total})
						</b></p>
						</c:forEach>
                        </c:if>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small warning coloured-icon"><i class="icon fa fa-dollar fa-3x"></i>
                      <div class="info">
                        <h4>會員錢包</h4>
                         <sql:query var="rs" dataSource="${xxx}" startRow="0">
    						 SELECT CASH FROM cea103_g4.USER WHERE USER_ID='${userVO.user_id}'
 						 </sql:query>
 						 <c:forEach var="row" items="${rs.rows}">
                        <p><b>${row.cash}元</b></p>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small danger coloured-icon"><i class="icon fa fa-thumbs-o-down fa-3x"></i>
                      <div class="info">
                        <h4>違約次數</h4>
                         <sql:query var="rs" dataSource="${xxx}" startRow="0">
    						 SELECT VIOLATION FROM cea103_g4.USER WHERE USER_ID='${userVO.user_id}'
 						 </sql:query>
 						 <c:forEach var="row" items="${rs.rows}">
                        <p><b>${row.violation}次</b></p>
                        </c:forEach>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6">
                    <div class="tile">
                      <h3 class="tile-title">商品概況
                      <a class="btn btn-primary" type="button" style="background-color : #F9F900; color : black;padding: 1;" href="<%=request.getContextPath()%>/front-end/productManagement/productList.jsp">未上架</a>
                      <a class="btn btn-primary" type="button" style="background-color : #46BFBD; color : black;padding: 1;" href="<%=request.getContextPath()%>/front-end/productManagement/productList.jsp">直售商品</a>
                      <a class="btn btn-primary" type="button" style="background-color : #B766AD; color : black;padding: 1;" href="<%=request.getContextPath()%>/front-end/productManagement/productList.jsp">已售出</a>
                      <a class="btn btn-primary" type="button" style="background-color : #F7464A; color : black;padding: 1;" href="<%=request.getContextPath()%>/front-end/productManagement/productList.jsp">違規下架</a>
                      </h3>
                      <div class="embed-responsive embed-responsive-16by9">
                        <canvas class="embed-responsive-item" id="pieChartDemo"></canvas>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 productList" style="margin-top:0px;">
                    <div class="tile userSeller">
                      <h3 class="tile-title">關注賣家清單</h3>
                      <table class="table">
                        <thead class="thead">
                         <tr>
                          <th scope="col">前往賣場</th>
                          <th scope="col">私訊賣家</th>
                          <th scope="col">取消關注</th>
                         </tr>
                        </thead>
                        <tbody>
                        <c:forEach var="sellerFollow" items="${list}" begin="0" end="${list.size()}">
                        <c:if test="${sellerFollow.user_id == userVO.getUser_id()}">
                          <tr>
                           <td><a href="<%=request.getContextPath()%>/SellerProducts?user_id=${sellerFollow.seller_id}" target="_blank">${sellerFollow.seller_id}</a></td>
                           <td><a href="javascript:void(0)"><i id="chat-seller" class="fa fa-commenting-o"></i><input type="hidden" value="${sellerFollow.seller_id}"></a></td>
                          
                           <td>
                            <FORM METHOD="post" ACTION="<%=request.getContextPath()%>/seller_follow/seller_follow.do" style="margin-bottom: 0px;">
                             <button class="btn btn-outline-info" type="submit" name="tracer_no" value="${sellerFollow.tracer_no}">取消關注</button>
                             <input type="hidden" name="action" value="deleteUserIndex">
			                </FORM>
			               </td>
                          </tr>
                          </c:if>
                         </c:forEach>
                          </tbody>
                          </table>
                      </div>
                    </div>
                  </div>
                  
      <div class="mini-chat">
		<div class="content">
			<div class="top-bar">
				<div id="statusOutput"></div>
				<div class="cancel">
					<i class="ti-close fa fa-times" aria-hidden="true"></i>
				</div>
			</div>
				<div class="middle" id="messagesArea" style="height: 240px"></div>
					<div class="bottom-bar">
						<div class="chat">
							<input id="message" class="chat-input" type="text"
								placeholder="Type a message..." size="21"
								onkeydown="if (event.keyCode == 13) sendMessage();">
								 <i id="sendMessage" class="fa fa-paper-plane-o"
								onclick="sendMessage();"></i>
						</div>
					</div>
				</div>
		</div>
<!--                 <div class="row"> -->
<!--                   <div class="col-md-6"> -->
<!--                     <div class="tile"> -->
<!--                       <h3 class="tile-title">Features</h3> -->
<!--                       <ul> -->
<!--                         <li>Built with Bootstrap 4, SASS and PUG.js</li> -->
<!--                         <li>Fully responsive and modular code</li> -->
<!--                         <li>Seven pages including login, user profile and print friendly invoice page</li> -->
<!--                         <li>Smart integration of forgot password on login page</li> -->
<!--                         <li>Chart.js integration to display responsive charts</li> -->
<!--                         <li>Widgets to effectively display statistics</li> -->
<!--                         <li>Data tables with sort, search and paginate functionality</li> -->
<!--                         <li>Custom form elements like toggle buttons, auto-complete, tags and date-picker</li> -->
<!--                         <li>A inbuilt toast library for providing meaningful response messages to user's actions</li> -->
<!--                       </ul> -->
<!--                       <p>Vali is a free and responsive admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.</p> -->
<!--                       <p>Vali is is light-weight, expendable and good looking theme. The theme has all the features required in a dashboard theme but this features are built like plug and play module. Take a look at the <a href="http://pratikborsadiya.in/blog/vali-admin" target="_blank">documentation</a> about customizing the theme colors and functionality.</p> -->
<!--                       <p class="mt-4 mb-4"><a class="btn btn-primary mr-2 mb-2" href="http://pratikborsadiya.in/blog/vali-admin" target="_blank"><i class="fa fa-file"></i>Docs</a><a class="btn btn-info mr-2 mb-2" href="https://github.com/pratikborsadiya/vali-admin" target="_blank"><i class="fa fa-github"></i>GitHub</a><a class="btn btn-success mr-2 mb-2" href="https://github.com/pratikborsadiya/vali-admin/archive/master.zip" target="_blank"><i class="fa fa-download"></i>Download</a></p> -->
<!--                     </div> -->
<!--                   </div> -->
<!--                   <div class="col-md-6"> -->
<!--                     <div class="tile"> -->
<!--                       <h3 class="tile-title">Compatibility with frameworks</h3> -->
<!--                       <p>This theme is not built for a specific framework or technology like Angular or React etc. But due to it's modular nature it's very easy to incorporate it into any front-end or back-end framework like Angular, React or Laravel.</p> -->
<!--                       <p>Go to <a href="http://pratikborsadiya.in/blog/vali-admin" target="_blank">documentation</a> for more details about integrating this theme with various frameworks.</p> -->
<!--                       <p>The source code is available on GitHub. If anything is missing or weird please report it as an issue on <a href="https://github.com/pratikborsadiya/vali-admin" target="_blank">GitHub</a>. If you want to contribute to this theme pull requests are always welcome.</p> -->
<!--                     </div> -->
<!--                   </div> -->
<!--                 </div> -->
         <jsp:include page="/front-end/protected/userIndex_footer.jsp" />
         <script src="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js"></script>
         </body>
         <script>
     	const miniChat = document.querySelector(".mini-chat");
     	const closeChatBtn = document.querySelector(".ti-close");
		$(".fa-commenting-o").click(function(){
	     		var friend = $(this).next('input').val();
	     		miniChat.style.visibility="visible";
	     		console.log(friend);
	     		addListener2(friend);
		})
		
		closeChatBtn.addEventListener("click", function() {
	     miniChat.style.visibility = "hidden";
	})
     	//WebSocket開始
     	var MyPoint = "/FriendChatWS/${userVO.user_id}";
     	var host = window.location.host;
     	var path = window.location.pathname;
     	var webCtx = path.substring(0, path.indexOf('/', 1));
     	var endPointURL = "ws://" + window.location.host + webCtx + MyPoint;
     	                
     	const statusOutput = document.getElementById("statusOutput");
     	const messagesArea = document.getElementById("messagesArea");
     	var self = '${userVO.user_id}';
     	var webSocket;
             connect(); //websocket直接啟動
             function connect() {
                 // create a websocket
                 webSocket = new WebSocket(endPointURL);
                 webSocket.onopen = function(event) {
                     console.log("Connect Success!");
                 };
     			// OnMessaage
                 webSocket.onmessage = function(event) {
                     var jsonObj = JSON.parse(event.data);

                     if ("open" === jsonObj.type) {
                     } else if ("history" === jsonObj.type) {
     					messagesArea.innerHTML = "";
                         var ul = document.createElement('ul');
                         ul.id = "area";
                         messagesArea.appendChild(ul);
                         // 這行的jsonObj.message是從redis撈出跟好友的歷史訊息，再parse成JSON格式處理
                         var messages = JSON.parse(jsonObj.message);
                         for (var i = 0; i < messages.length; i++) {
                             var historyData = JSON.parse(messages[i]);
                             var showMsg = historyData.message;
                             var li = document.createElement('li');
                             // 根據發送者是自己還是對方來給予不同的class名, 以達到訊息左右區分
                             historyData.sender === self ? li.className += 'me' : li.className += 'friend';
                             li.innerHTML = showMsg;
                             ul.appendChild(li);
                         }
                         messagesArea.scrollTop = messagesArea.scrollHeight;
                     } else if ("chat" === jsonObj.type) {
                         var li = document.createElement('li');
     					if(jsonObj.sender === statusOutput.textContent || jsonObj.sender ===self){ //完善版本 不會看到別人訊息
                         jsonObj.sender === self ? li.className += 'me' : li.className += 'friend'; 
     					}
     					li.innerHTML = jsonObj.message;
                         document.getElementById("area").appendChild(li);
                         messagesArea.scrollTop = messagesArea.scrollHeight;
                     } else if ("close" === jsonObj.type) {
                     }

                 };
     			//OnClose
                 webSocket.onclose = function(event) {
                     console.log("Disconnected!");
                 };
             }
     		
             function sendMessage() {
                 var inputMessage = document.getElementById("message");
                 var friend = statusOutput.textContent;
                 var message = inputMessage.value.trim();
                 if (message === "") {
                     alert("Input a message");
                     inputMessage.focus();
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
             // 註冊列表點擊事件並抓取好友名字以取得歷史訊息
     		function addListener2(friend) { //給私訊賣家用
     		             updateFriendName(friend);
     		            var jsonObj = {
     		                "type" : "history",
     		                "sender" : self,
     		                "receiver" : friend,
     		                "message" : ""
     		            };
     		            webSocket.send(JSON.stringify(jsonObj));
             }
             function disconnect() {
                 webSocket.close();
             };

             function updateFriendName(name) {
                 statusOutput.innerHTML = name;
             };
         
         
         $(document).ready(function(){
        		switch($("#con").val()){
        		case "1":
        			$("#s1").css("color","#f6d04d");
        			break;
        		case "2":
        			$("#s1,#s2").css("color","#f6d04d");
        			break;
        		case "3":
        			$("#s1,#s2,#s3").css("color","#f6d04d");
        			break;
        		case "4":
        			$("#s1,#s2,#s3,#s4").css("color","#f6d04d");
        			break;
        		case "5":
        			$(".all-star").css("color","#f6d04d");
        			break;
        		default:
        			$(".all-star").css("color","black");
        		}
        	})
        </script>
        
        <script>
        // 會員首頁圓餅圖
        <sql:query var="rs" dataSource="${xxx}" startRow="0">
			SELECT PRODUCT_STATE FROM cea103_g4.PRODUCT WHERE USER_ID = '${userVO.user_id}' and PRODUCT_STATE = 1 ;
 		</sql:query>
 		
        <sql:query var="rs2" dataSource="${xxx}" startRow="0">
		SELECT PRODUCT_STATE FROM cea103_g4.PRODUCT WHERE USER_ID = '${userVO.user_id}' and PRODUCT_STATE = 0 ;
		</sql:query>
		
        <sql:query var="rs3" dataSource="${xxx}" startRow="0">
		SELECT PRODUCT_STATE FROM cea103_g4.PRODUCT WHERE USER_ID = '${userVO.user_id}' and PRODUCT_STATE = 3 ;
		</sql:query>
		
        <sql:query var="rs4" dataSource="${xxx}" startRow="0">
		SELECT PRODUCT_STATE FROM cea103_g4.PRODUCT WHERE USER_ID = '${userVO.user_id}' and PRODUCT_STATE = 5 ;
		</sql:query>
 				
		var pdata = [ {
			value : ${rs.rowCount},
			color : "#46BFBD",
			highlight : "#5AD3D1",
			label : "直售商品"
		}, {
			value : ${rs3.rowCount},
			color : "#B766AD",
			highlight : "#CA8EC2",
			label : "已售出"
		}, {
			value : ${rs2.rowCount},
			color : "#F9F900",
			highlight : "#FFFF93",
			label : "未上架"
		}, {
			value : ${rs4.rowCount},
			color : "#F7464A",
			highlight : "#FF5A5E",
			label : "違規下架"
		}  ]

        var ctxp = $("#pieChartDemo").get(0).getContext("2d");
		var pieChart = new Chart(ctxp).Pie(pdata);
         </script>
         </html>