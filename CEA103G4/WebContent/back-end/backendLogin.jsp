<%@ page language="java" contentType="text/html; charset=BIG5"
    pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<title>Insert title here</title>
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/layui/css/layui.css">
<link rel="stylesheet" href="<%=request.getContextPath()%>/static/admin/css/login.css">
</head>
<body>
<body id="login">
	<div class="login">
		<h2>後台管理系統</h2>
		<form class="layui-form" method="post" target="_blank" action="">
			<div class="layui-form-item">
				<input type="username" name="user" placeholder="員工登入"
					class="layui-input"> <i class="layui-icon input-icon">&#xe66f;</i>
			</div>
			<div class="layui-form-item">
				<input type="password" name="pwd" placeholder="密碼"
					class="layui-input"> <i class="layui-icon input-icon">&#xe673;</i>
			</div>
<!-- 			<div class="layui-form-item"> -->
<!-- 				<input type="checkbox" name="box" lay-skin="primary" title="記住密碼" -->
<!-- 					checked=""> <a class="back" href="javascript:;" -->
<!-- 					style="margin-top: 10px">忘記密碼</a> -->
<!-- 			</div> -->
			<div class="layui-form-item">
				<button style="width: 100%" class="layui-btn" lay-submit
					lay-filter="login">登入</button>

			</div>
		</form>
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