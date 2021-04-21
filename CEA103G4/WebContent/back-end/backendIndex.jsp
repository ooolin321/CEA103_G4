<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>


<!DOCTYPE html>
<html>

<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, maximum-scale=1">
<title>后台首页 - QAdmin后台模板 - 普通版演示</title>

<meta name="keywords" content="qadmin,qadmin模板,后台模板,qadmin后台模板">
<meta name="description" content="qadmin是一个轻量级后台模板,基于layui框架开发,简洁而不简单">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/layui/css/layui.css">
<link rel="stylesheet"
	href="<%=request.getContextPath()%>/static/admin/css/style.css">
<script src="<%=request.getContextPath()%>/static/layui/layui.js"></script>
<script
	src="<%=request.getContextPath()%>/static/js/jquery-3.3.1.min.js"></script>
<script src="<%=request.getContextPath()%>/static/js/vue.min.js"></script>
<style>
.right h2 {
	margin: 10px 0;
}

.right li {
	margin-bottom: 10px;
}
</style>
</head>
<body>
	<div id="app">
		    <!-- Header Begin -->

    <header class="header-section">
      <div class="container">
        <div class="inner-header">
          <div class="row">
            <div class="col-lg-2 col-md-2">
              <div class="logo">
                <a href="./index.html">
                  <h2>Mode femme <br /><small>Second&nbsp;Hand </small></h2>
                </a>
              </div>
            </div>
            <div class="col-lg-2 col-md-2">
              <div class="product">商品管理</div>
            </div>
          </div>
        </div>
      </div>
    </header>
    <!-- Header END -->

		<div class="main">
			<!--左栏-->
			<div class="left">
				<ul class="cl">
					<!--顶级分类-->
					<li v-for="vo,index in menu" :class="{hidden:vo.hidden}"><a
						href="javascript:;" :class="{active:vo.active}"
						@click="onActive(index)"> <i class="layui-icon"
							v-html="vo.icon"></i> <span v-text="vo.name"></span> <i
							class="layui-icon arrow" v-show="vo.url.length==0">&#xe61a;</i> <i
							v-show="vo.active" class="layui-icon active">&#xe623;</i>
					</a> <!--子级分类-->
						<div v-for="vo2,index2 in vo.list">
							<a :href="vo2.url" target="main" :class="{active:vo2.active}"
								v-text="vo2.name"></a> <i v-show="vo2.active"
								class="layui-icon active">&#xe623;</i>
						</div></li>
				</ul>
			</div>

			<!--右侧-->
			<div class="right">
				<iframe src="main.html" name="main" marginwidth="0" marginheight="0"
					frameborder="0" scrolling="auto" target="_self"></iframe>
			</div>
		</div>
	</div>


	<script src="<%=request.getContextPath()%>/static/admin/js/config.js"></script>
	<script src="<%=request.getContextPath()%>/static/admin/js/script.js"></script>
</body>
</html>
