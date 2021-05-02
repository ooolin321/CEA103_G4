<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.emp.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>

<%
	EmpService empSvc = new EmpService();
	List<EmpVO> list = empSvc.getAll();
	pageContext.setAttribute("list", list);
%>

<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO"); //EmpServlet.java(Concroller), 存入req的empVO物件
%>


<meta name="viewport" content="width=device-width, initial-scale=1.0, user-scalable=yes">
<html>
<head>
<title>所有員工資料 - listAllEmp.jsp</title>


<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js" integrity="sha384-DfXdz2htPH0lsSSs5nCTpuj/zy4C+OGpamoFVy38MVBnE+IbbVYUew+OrCXaRkfj" crossorigin="anonymous"></script>



  <!-- Font-icon css-->
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">

</head>  
<jsp:include page="/back-end/backendMenu.jsp" />


<body bgcolor='white' class="app sidebar-mini rtl">

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
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
              <table class="table table-hover table-bordered" id="sampleTable">
                <thead>
		<tr role="row" class="table-info">
			<th class="sorting_asc">員工編號</th>
			<th>員工姓名</th>
			<th>職位</th>
<!-- 			<th>身分證字號</th> -->
			<th>性別</th>
<!-- 			<th>生日</th> -->
<!-- 			<th>地址</th> -->
			<th>email</th>
<!-- 			<th>薪水</th> -->
			<th>狀態</th>
			<th>到職日期</th>
<!-- 			<th>員工密碼</th> -->
			<th>修改</th>
			<th>刪除</th>
		</tr>
		</thead>
		<tbody>
		<%@ include file="page1.file"%>
		<c:forEach var="empVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr ${(empVO.empno==param.empno) ? 'bgcolor=#CCCCFF':''}>
				<td class="sorting_1"><A href="<%=request.getContextPath()%>/emp/emp.do?empno=${empVO.empno}&action=getOne_From">${empVO.empno}</A></td>
				<td class="table-danger">${empVO.ename}</td>
				<td class="table-warning">${empVO.job}</td>
<%-- 				<td >${empVO.id}</td>	 --%>
				<c:choose>
					<c:when test="${empVO.gender==0}">
						<td class="table-success">女</td>
					</c:when>
					<c:when test="${empVO.gender==1}">
						<td class="table-success">男</td>
					</c:when>
				</c:choose>
<%-- 				<td class="table-danger">${empVO.dob}</td> --%>
<%-- 				<td class="table-warning">${empVO.city}${empVO.dist}${empVO.addr}</td> --%>
				<td >${empVO.email}</td>
<%-- 				<td class="table-success">${empVO.sal}</td> --%>
				
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
<%-- 				<td>${empVO.emp_pwd}</td> --%>


				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/emp/emp.do"
						style="margin-bottom: 0px;">
						<input class="btn btn-primary" type="submit" value="修改"> 
						<input type="hidden" name="empno" value="${empVO.empno}">
			     <input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     <input type="hidden" name="whichPage"	value="<%=whichPage%>">      
						<input type="hidden" name="action" value="getOne_For_Update">
					</FORM>
				</td>
				<td>
					<FORM METHOD="post"
						ACTION="<%=request.getContextPath()%>/emp/emp.do"
						style="margin-bottom: 0px;">
						<input class="btn btn-warning" type="submit" value="刪除"> 
						<input type="hidden" name="requestURL"	value="<%=request.getServletPath()%>"><!--送出本網頁的路徑給Controller-->
			     		<input type="hidden" name="whichPage"	value="<%=whichPage%>"> 
						<input type="hidden" name="empno" value="${empVO.empno}"> 
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
	
	<jsp:include page="/back-end/backendfooter.jsp" />

</body>

</html>