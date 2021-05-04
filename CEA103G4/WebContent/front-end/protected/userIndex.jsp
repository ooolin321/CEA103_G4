<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="com.user.model.*"%>

<%
	UserVO userVO = (UserVO) session.getAttribute("account"); 
	session.setAttribute("userVO", userVO);
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
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Main CSS-->
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-template/css/usermain.css">
  <!-- Font-icon css-->
  <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body class="app sidebar-mini rtl">
 <%@include file="/front-end/user/userSidebar.jsp"%>
              <main class="app-content">
                <div class="app-title">
                  <div>
                    <h1><i class="fa fa-drivers-license-o"></i> 會員首頁</h1>
                  </div>
                  <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp"><i class="fa fa-home fa-lg"></i></a></li>
                    <li class="breadcrumb-item">會員首頁</li>
                  </ul>
                </div>
                <div class="row">
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small primary coloured-icon"><i class="icon fa fa-users fa-3x"></i>
                      <div class="info">
                        <h4>點數</h4>
                        <p><b>${userVO.user_point}</b></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small info coloured-icon"><i class="icon fa fa-thumbs-o-up fa-3x"></i>
                      <div class="info">
                        <h4>賣家評價</h4>
                        <p><b>${userVO.user_comment}</b></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small warning coloured-icon"><i class="icon fa fa-files-o fa-3x"></i>
                      <div class="info">
                        <h4>錢包</h4>
                        <p><b>${userVO.cash}</b></p>
                      </div>
                    </div>
                  </div>
                  <div class="col-md-6 col-lg-3">
                    <div class="widget-small danger coloured-icon"><i class="icon fa fa-star fa-3x"></i>
                      <div class="info">
                        <h4>違約次數</h4>
                        <p><b>${userVO.violation}</b></p>
                      </div>
                    </div>
                  </div>
                </div>

                <div class="row">
                  <div class="col-md-6">
                    <div class="tile">
                      <h3 class="tile-title">購物車明細</h3>
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
         </body>
         </html>