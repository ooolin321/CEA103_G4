<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <!-- Main CSS-->
    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
    <!-- Font-icon css-->
    <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
    <script>
	     $(document).ready(function() {  
	         $("#formLogin").ajaxForm(function(data){  
	               alert("post success.");  
	               //Alert("post success.");  
	         });            
	  });  

		</script>
    <title>Login - Vali Admin</title>
  </head>
  <body>
  	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
    <section class="material-half-bg">
      <div class="cover"></div>
    </section>
    <section class="login-content">
      <div class="logo">
        <h1>Vali</h1>
      </div>
      <div class="login-box">
        <form METHOD="post" class="login-form" action="<%=request.getContextPath()%>/loginhandler">
          <h3 class="login-head"><i class="fa fa-lg fa-fw fa-user"></i>SIGN IN</h3>
          <div class="form-group">
            <label class="control-label">USERNAME</label>
            <input class="form-control" type="username" name="account" placeholder="UserID" autofocus>
          </div>
          <div class="form-group">
            <label class="control-label">PASSWORD</label>
            <input class="form-control" type="password" name ="password" placeholder="Password" autofocus>
          </div>
          <div class="form-group">
            <div class="utility">
              <div class="animated-checkbox">
                <label>
                  <input type="checkbox"><span class="label-text">Stay Signed in</span>
                </label>
              </div>
              <p class="semibold-text mb-2"><a href="#" data-toggle="flip">Forgot Password ?</a></p>
            </div>
          </div>
          <div class="form-group btn-container">
          
            <input type="hidden" name="action" value="signIn">
            
            <button class="btn btn-primary btn-block"><i class="fa fa-sign-in fa-lg fa-fw"></i>登入 SIGN IN
            </button>
          </div>
        </form>
        <form class="forget-form" action="<%=request.getContextPath()%>/loginhandler">
          <h3 class="login-head"><i class="fa fa-lg fa-fw fa-lock"></i>Forgot Password ?</h3>
          <div class="form-group">
            <label class="control-label">EMAIL</label>
            <input class="form-control" type="text" placeholder="Email">
          </div>
          <div class="form-group btn-container">
            <button class="btn btn-primary btn-block"><i class="fa fa-unlock fa-lg fa-fw"></i>RESET</button>
          </div>
          <div class="form-group mt-3">
            <p class="semibold-text mb-0"><a href="#" data-toggle="flip"><i class="fa fa-angle-left fa-fw"></i> Back to Login</a></p>
          </div>
        </form>
      </div>
    </section>
    <!-- Essential javascripts for application to work-->
    <script src="<%=request.getContextPath()%>/back-template/docs/js/jquery-3.2.1.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-template/docs/js/popper.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-template/docs/js/bootstrap.min.js"></script>
    <script src="<%=request.getContextPath()%>/back-template/docs/js/main.js"></script>
    <!-- The javascript plugin to display page loading on top-->
    <script src="<%=request.getContextPath()%>/back-template/docs/js/plugins/pace.min.js"></script>
    <script type="text/javascript">
      // Login Page Flipbox control
      $('.login-content [data-toggle="flip"]').click(function() {
      	$('.login-box').toggleClass('flipped');
      	return false;
      });
    </script>
    <script>
        layui.use('form', function () {
            var form = layui.form,
                layer = layui.layer,
                $ = layui.jquery;

            form.on('submit(login)', function (data) {
                layer.load({
                    shade: 0.5,
                    time: 0,
                });
                setTimeout(function () {
                    window.location.href = 'index.html';
                }, 1000)

                return false;
            });
        });
    </script>
  </body>
</html>