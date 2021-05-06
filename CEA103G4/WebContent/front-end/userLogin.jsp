<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.user.model.*"%>

<%
	UserVO userVO = (UserVO) request.getAttribute("userVO");
%>

<!DOCTYPE html>
<html lang="zh-Hant">
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
  	<style type="text/css">
  	.register-login-section {
    padding-top: 0px;
	}
	input {
	border-radius: 5px;
	
	}
  	</style>
  </head>

  <body>
	<!-- Header Section Begin -->
    <%@include file="/front-end/header.jsp"%>
	<!-- Header End -->
	
	<%-- 錯誤表列 --%>
<%-- 	<c:if test="${not empty errorMsgs}"> --%>
<!-- 		<font style="color: red">請修正以下錯誤:</font> -->
<!-- 		<ul> -->
<%-- 			<c:forEach var="message" items="${errorMsgs}"> --%>
<%-- 				<li style="color: red">${message}</li> --%>
<%-- 			</c:forEach> --%>
<!-- 		</ul> -->
<%-- 	</c:if> --%>

    <!-- Breadcrumb Section Begin -->
    <div class="breacrumb-section">
      <div class="container">
        <div class="row">
          <div class="col-lg-12">
            <div class="breadcrumb-text">
              <a href="<%=request.getContextPath()%>/front-end/index.jsp"><i class="fa fa-home"></i> Home</a>
              <span>Login</span>
            </div>
          </div>
        </div>
      </div>
    </div>
    <!-- Breadcrumb Form Section Begin -->
    <!-- Register Section Begin -->
    <div class="register-login-section spad">
      <div class="container">
        <div class="row">
          <div class="col-lg-6 offset-lg-3">
            <div class="login-form">
              <h2>Login</h2>
              <form METHOD="post" class="login-form" action="<%=request.getContextPath()%>/FrondEnd_LoginHandler">
                <div class="group-input">
                  <label for="user_id">UserID</label>
                  <input type="text" name="user_id" value="${(userVO==null)? '' : userVO.user_id}" placeholder="會員帳號" autofocus/><td><font color=red><b>${errorMsgs.user_id}</b></td>
                </div>
                <div class="group-input">
                  <label for="user_pwd">Password</label>
                  <input type="password" name="user_pwd" placeholder="密碼"/><td><font color=red><b>${errorMsgs.user_pwd}</b></td>
                </div>
                <div class="group-input gi-check">
                  <div class="gi-more">
                    <label for="save-pass">
                      Save Password
                      <input type="checkbox" id="save-pass" />
                      <span class="checkmark"></span>
                    </label>
                    <a href="<%=request.getContextPath()%>/front-end/user/forgetPassword.jsp" class="forget-pass">忘記密碼</a>
                  </div>
                </div>
                
                <input type="hidden" name="action" value="signIn">
                
                <button type="submit" class="site-btn login-btn">
                	  登入
                </button>
              </form>
	        <div class="card-body px-5 py-4">
				<div class="small text-center" style="color:black">
				新朋友嗎?&nbsp;
					<a style="color:pink; font-weight:700;" href="<%=request.getContextPath()%>/front-end/user/register.jsp">來註冊吧 !</a>
				</div>
			</div>
			 </div>
          </div>
<!--               <div class="switch-login"> -->
<%--                 <a href="<%=request.getContextPath()%>/front-end/user/register.jsp" class="or-login" --%>
<!--                   >新朋友嗎?來註冊吧！</a> -->
<!--               </div> -->
           
        </div>
      </div>
    </div>
    <!-- Register Form Section End -->

    
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
