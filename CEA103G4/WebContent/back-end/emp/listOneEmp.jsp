<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>
<%
	EmpVO empVO2 = (EmpVO)session.getAttribute("account");
	session.setAttribute("empVO2", empVO);
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
<title>員工${empVO.empno} :${empVO.ename}</title>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Main CSS-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

</head>


<body class="app sidebar-mini rtl">
	<jsp:include page="/back-end/backendMenu.jsp" />

	<div class="row">
		<div class="col-md-12">
			<div class="tile">
				<div class="tile-body">
					<table class="table table-hover" id="sampleTable" style="font-size:120%">
				<h2 class="text-dark h2">
							員工基本資料
						</h2>
						<thead>
							<tr role="row" class="table-info">
								<th class="sorting_asc">員工編號</th>
								<th>員工姓名</th>
								<th>職位</th>
								<th>身分證字號</th>
								<th>性別</th>
								<th>生日</th>
								<th>地址</th>
								<th>email</th>
								<th>薪水</th>
								<th>狀態</th>
								<th>到職日期</th>
								<th>員工密碼</th>
								<!-- 								<th>修改</th> -->
							</tr>
						</thead>
						<tbody>
							<tr>
								<td>${empVO.empno}</td>
								<td class="table-danger">${empVO.ename}</td>
								<td class="table-warning">${empVO.job}</td>
								<td class="table-success">${empVO.id}</td>
								<c:choose>
									<c:when test="${empVO.gender==0}">
										<td>女</td>
									</c:when>
									<c:when test="${empVO.gender==1}">
										<td>男</td>
									</c:when>
								</c:choose>
								<td class="table-danger">${empVO.dob}</td>
								<td class="table-warning">${empVO.city}${empVO.dist}${empVO.addr}</td>
								<td class="table-success">${empVO.email}</td>
								<td>${empVO.sal}</td>

								<c:choose>
									<c:when test="${empVO.state==0}">
										<td class="table-danger">離職</td>
									</c:when>
									<c:when test="${empVO.state==1}">
										<td class="table-danger">在職</td>
									</c:when>
								</c:choose>
								<td class="table-warning"><fmt:formatDate value="${empVO.hiredate}"
										pattern="yyyy-MM-dd" /></td>
								<td class="table-success">${empVO.emp_pwd}</td>
<%-- <jsp:include page="/back-end/auth/listOneAuth.jsp" /> --%>

								<!-- 								<td> -->
								<!-- 									<FORM METHOD="post" -->
								<%-- 										ACTION="<%=request.getContextPath()%>/emp/emp.do" --%>
								<!-- 										style="margin-bottom: 0px;"> -->
								<!-- 										<input class="btn btn-primary" type="submit" value="修改"> -->
								<%-- 										<input type="hidden" name="empno" value="${empVO.empno}"> --%>
								<!-- 										<input type="hidden" name="requestURL" -->
								<%-- 											value="<%=request.getServletPath()%>"> --%>
								<!-- 										送出本網頁的路徑給Controller -->

								<!-- 										<input type="hidden" name="action" value="getOne_For_Update"> -->
								<!-- 									</FORM> -->
								<!-- 								</td> -->

							</tr>

						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/back-end/backendfooter.jsp" />

</body>
</html>