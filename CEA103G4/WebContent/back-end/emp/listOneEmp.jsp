<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="com.emp.model.*"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>

<meta name="viewport"
	content="width=device-width, initial-scale=1.0, user-scalable=yes">
<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"
	integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj"
	crossorigin="anonymous"></script>
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns"
	crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
	
	<jsp:include page="/back-end/backendMenu.jsp" />
	
<body bgcolor='white' class="app sidebar-mini rtl">

	<div class="row">
		<div class="col-md-12">
			<div class="tile">
				<div class="tile-body">
					<table class="table table-hover table-bordered" id="sampleTable">
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
								<td>${empVO.empno}</A></td>
								<td>${empVO.ename}</td>
								<td>${empVO.job}</td>
								<td>${empVO.id}</td>
								<c:choose>
									<c:when test="${empVO.gender==0}">
										<td>女</td>
									</c:when>
									<c:when test="${empVO.gender==1}">
										<td>男</td>
									</c:when>
								</c:choose>
								<td>${empVO.dob}</td>
								<td>${empVO.city}${empVO.dist}${empVO.addr}</td>
								<td>${empVO.email}</td>
								<td>${empVO.sal}</td>

								<c:choose>
									<c:when test="${empVO.state==0}">
										<td>離職</td>
									</c:when>
									<c:when test="${empVO.state==1}">
										<td>在職</td>
									</c:when>
								</c:choose>
								<td><fmt:formatDate value="${empVO.hiredate}"
										pattern="yyyy-MM-dd" /></td>
								<td>${empVO.emp_pwd}</td>


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