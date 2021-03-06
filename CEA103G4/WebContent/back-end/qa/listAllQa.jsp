<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.qa.model.*"%>

<%
	QaService qaSvc = new QaService();
	List<QaVO> list = qaSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<!DOCTYPE html>
<html lang="zh-tw">
<head>
<meta name="description"
	content="Vali is a responsive and free admin theme built with Bootstrap 4, SASS and PUG.js. It's fully customizable and modular.">
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
<title>ęęQ&Ač³ę</title>
<link rel="icon"
	href="${pageContext.request.contextPath}/front-template/images/favicon.ico"
	type="image/x-icon">
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1">
<!-- Main CSS-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

<style>
.form-control {
    border: none;
    background: white !important;
    margin-top: -10px;
    margin-left: -12px;
    resize: none;
    white-space: pre-wrap;
    font-size: 0.875rem;
    font-weight: 400;
    line-height: 1.5;
    color: #212529;
    padding-top: 10px;
    box-sizing: border-box;
}
</style>
</head>
<body class="app sidebar-mini rtl">
	<jsp:include page="/back-end/backendMenu.jsp" />
	<main class="app-content">
		<div class="app-title">
			<div>
				<h1>
					<i class="fa fa-file-text"></i> Q&Aē®”ē
				</h1>

			</div>
			<ul class="app-breadcrumb breadcrumb">
				<li class="breadcrumb-item"><i class="fa fa-home fa-lg"></i></li>
				<li class="breadcrumb-item"><a
					href="<%=request.getContextPath()%>/back-end/backendIndex.jsp">åå°é¦é </a></li>
			</ul>
		</div>
		<%-- éÆčŖ¤č”Øå --%>
		<c:if test="${not empty errorMsgs}">
			<font style="color: red">č«äæ®ę­£ä»„äøéÆčŖ¤:</font>
			<ul>
				<c:forEach var="message" items="${errorMsgs}">
					<li style="color: red">${message}</li>
				</c:forEach>
			</ul>
		</c:if>

		<div class="row">
			<div class="col-md-12">
				<div class="tile">
					<div class="tile-body">
						<table class="table table-hover" id="sampleTable"
							style="font-size: 120%">
							<thead>
								<tr role="row" class="table-info">
									<th class="sorting_asc">Q&Aē·Øč</th>
									<th>Q&Aé”å</th>
									<th>å”å·„ē·Øč</th>
									<th>å»ŗē«ę„ę</th>
									<th>åé”</th>
									<th>č§£ē­</th>
									<th></th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<%@ include file="page1.file"%>
								<c:forEach var="qaVO" items="${list}" begin="<%=pageIndex%>"
									end="<%=pageIndex+rowsPerPage-1%>">

									<tr>
										<td>${qaVO.qa_no}</td>
										<td>${(qaVO.qa_type==1)? 'åø³åēøé':''}
											${(qaVO.qa_type==2)? 'ååēøé':''}
											${(qaVO.qa_type==3)? 'čØå®ēøé':''}
											${(qaVO.qa_type==4)? 'ęå”ēøé':''}
										</td>
										<td>${qaVO.empno}</td>
										<td><fmt:formatDate value="${qaVO.qa_date}"
												pattern="yyyy-MM-dd" /></td>
										<td>${qaVO.question}</td>
										<td><textarea class="form-control" maxlength="300" rows="5" readonly>${qaVO.answer}</textarea></td>
										<td>
											<FORM METHOD="post"
												ACTION="<%=request.getContextPath()%>/QaServlet"
												style="margin-bottom: 0px;">
												<input class="btn btn-info" type="submit" value="äæ®ę¹">
												<input type="hidden" name="qa_no" value="${qaVO.qa_no}"> 
												<input type="hidden" name="action" value="getOne_For_Update">
											</FORM>
										</td>
											<td>
												<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/QaServlet" style="margin-bottom: 0px;">
													<input class="btn btn-info" type="submit" value="åŖé¤">
													<input type="hidden" name="qa_no"  value="${qaVO.qa_no}">
													<input type="hidden" name="action" value="delete">
												</FORM>
											</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</div>
		<%@ include file="page2.file"%>
	</main>
	<jsp:include page="/back-end/backendfooter.jsp" />
</body>
</html>