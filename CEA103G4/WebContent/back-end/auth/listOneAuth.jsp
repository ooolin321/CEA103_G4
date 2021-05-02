<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="java.util.*"%>
<%@ page import="com.auth.model.*"%>
<%-- 此頁練習採用 EL 的寫法取值 --%>
<%
	AuthVO funVO = (AuthVO) request.getAttribute("funVO"); //EmpServlet.java (Concroller) 存入req的empVO物件 (包括幫忙取出的empVO, 也包括輸入資料錯誤時的empVO物件)
%>
<%
	AuthService funSvc = new AuthService();
	List<AuthVO> list = funSvc.getAll();
	pageContext.setAttribute("list", list);
%>


<html>
<head>
<title>員工資料 - listOneEmp.jsp</title>

<style>
table#table-1 {
	background-color: #CCCCFF;
	border: 2px solid black;
	text-align: center;
}

table#table-1 h4 {
	color: red;
	display: block;
	margin-bottom: 1px;
}

h4 {
	color: blue;
	display: inline;
}

table {
	width: 600px;
	background-color: white;
	margin-top: 5px;
	margin-bottom: 5px;
}

table, th, td {
	border: 1px solid #CCCCFF;
}

th, td {
	padding: 5px;
	text-align: center;
	max-width: 89px;
	min-width: 89px;
}

table th .AutoNewline {
	width: 500px;
}
</style>

</head>
<body bgcolor='white'>

	<h4>此頁暫練習採用 Script 的寫法取值:</h4>
	<table id="table-1">
		<tr>
			<td>
				<h3>網站功能資料 - ListOneFun.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/fun/selectFun.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100" height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>
					<table class="col-md-12" id="sampleTable">
						<thead>
							<tr role="row" class="table-info">
								<th>員工名稱</th>
								<th>功能名稱</th>
								<th>狀態</th>
								<!-- 								<th>修改</th> -->
								<!-- 			<th>刪除</th> -->
							</tr>
						</thead>
						<tbody>
							<%-- 		<%@ include file="page1.file"%> --%>
							<c:forEach var="futhVO" items="${list}">

								<tr>
									<td>${authVO.funno}</td>
									<td>${funSvc.getOneFun(authVO.funno).funName}</td>
									<td>${authVO.empno}</td>
									<td>${empSvc.getOneEmp(authVO.empno).ename}</td>
									<c:choose>
										<c:when test="${futhVO.state==0}">
											<td>關閉</td>
										</c:when>
										<c:when test="${futhVO.state==1}">
											<td>開啟</td>
										</c:when>
									</c:choose>
									<!-- 				<td><select size="1" name="state"> -->
									<%-- 						<option value="1" ${(futhVO.state==0)? 'selected':''}>開啟</option> --%>
									<%-- 						<option value="0" ${(futhVO.state==0)? 'selected':''}>關閉</option> --%>
									<!-- 				</select></td> -->

									<td>
										<FORM METHOD="post"
											ACTION="<%=request.getContextPath()%>/fun/fun.do"
											style="margin-bottom: 0px;">
											<input type="submit" value="修改"> <input type="hidden"
												name="funno" value="${futhVO.funno}"> <input
												type="hidden" name="action" value="getOne_For_Update">
											<input type="hidden" name="requestURL"
												value="<%=request.getParameter("requestURL")%>">
											<!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
											<input type="hidden" name="whichPage"
												value="<%=request.getParameter("whichPage")%>">
											<!--只用於:istAllEmp.jsp-->

										</FORM>
									</td>


									<!-- 				<td> -->
									<%-- 					<FORM METHOD="post" ACTION="<%=request.getContextPath()%>/fun/fun.do" --%>
									<!-- 						style="margin-bottom: 0px;"> -->
									<!-- 						<input type="submit" value="刪除"> <input type="hidden" -->
									<%-- 							name="funno" value="${futhVO.funno}"> <input type="hidden" --%>
									<!-- 							name="action" value="delete"> -->
									<!-- 					</FORM> -->
									<!-- 				</td> -->
								</tr>
							</c:forEach>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>

	<jsp:include page="/back-end/backendfooter.jsp" />

</body>
</html>