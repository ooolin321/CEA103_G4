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
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.6.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-Piv4xVNRyMGpqkS2by6br4gNJ7DXjqk09RmUpJ8jgGtD7zP9yug3goQfGII0yAns" crossorigin="anonymous"></script>
<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
  <!-- Font-icon css-->
<link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
  
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
</style>

<style>
table {
    width: 800px;
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
    
}
</style>
 
<body bgcolor='white'>
	<table id="table-1">
		<tr>
			<td>
				<h3>所有員工資料 - listAllEmp.jsp</h3>
				<h4>
					<a href="<%=request.getContextPath()%>/back-end/backendIndex.jsp"><img
						src="<%=request.getContextPath()%>/images/back1.gif" width="100"
						height="32" border="0">回首頁</a>
				</h4>
			</td>
		</tr>
	</table>

	<%-- 錯誤表列 --%>
	<c:if test="${not empty errorMsgs}">
		<font style="color: red">請修正以下錯誤:</font>
		<ul>
			<c:forEach var="message" items="${errorMsgs}">
				<li style="color: red">${message}</li>
			</c:forEach>
		</ul>
	</c:if>
<div class="row"	>
	<table class="table">
		<tr class="table-info">
			<th>員工編號</th>
			<th>員工姓名</th>
			<th>職位</th>
<!-- 			<th>身分證字號</th> -->
			<th>性別</th>
<!-- 			<th>生日</th> -->
<!-- 			<th>地址</th> -->
			<th>email</th>
			<th>薪水</th>
			<th>狀態</th>
			<th>到職日期</th>
<!-- 			<th>員工密碼</th> -->
			<th>修改</th>
			<th>刪除</th>
		</tr>
		<%@ include file="page1.file"%>
		<c:forEach var="empVO" items="${list}" begin="<%=pageIndex%>"
			end="<%=pageIndex+rowsPerPage-1%>">

			<tr ${(empVO.empno==param.empno) ? 'bgcolor=#CCCCFF':''}>
				<td class="table-success"><A href="<%=request.getContextPath()%>/emp/emp.do?empno=${empVO.empno}&action=getOne_From">${empVO.empno}</A></td>
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
				<td class="table-success">${empVO.sal}</td>
				
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
						<input type="hidden" name="requestURL" value="<%=request.getParameter("requestURL")%>"> <!--接收原送出修改的來源網頁路徑後,再送給Controller準備轉交之用-->
						<input type="hidden" name="whichPage"  value="<%=request.getParameter("whichPage")%>"> 
						<input type="hidden" name="empno" value="${empVO.empno}"> 
						<input type="hidden" name="action" value="delete">
					</FORM>
				</td>
			</tr>
		</c:forEach>
	</table>
</div>
	<%@ include file="page2.file"%>
	
	<c:if test="${openModal_Group!=null}">

  The Modal
    <div class="modal" id="myModal">
        <div class="modal-dialog modal-xl">
            <div class="modal-content">
                Modal Header
                <div class="modal-header">
                    <h4 class="modal-title">員工資料</h4>
                    <button type="button" class="close" data-dismiss="modal">&times;</button>
                </div>
                Modal body
                <div class="modal-body">
=========================================以下為原listOneEmp.jsp的內容==========================================
               <jsp:include page="listOneEmp.jsp" />
=========================================以上為原listOneEmp.jsp的內容==========================================
	Modal footer
                <div class="modal-footer">
                    <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                </div>
            </div>
        </div>
    	</div>
    </div>
     <script>
  		 $("#myModal").modal('show');
     </script>
 </c:if>


	          <script src="<%=request.getContextPath()%>/back-template/docs/js/jquery-3.2.1.min.js"></script>
              <script src="<%=request.getContextPath()%>/back-template/docs/js/popper.min.js"></script>
              <script src="<%=request.getContextPath()%>/back-template/docs/js/bootstrap.min.js"></script>
              <script src="<%=request.getContextPath()%>/back-template/docs/js/main.js"></script>
              <!-- The javascript plugin to display page loading on top-->
              <script src="js/plugins/pace.min.js"></script>
              <!-- Page specific javascripts-->
              <script type="text/javascript" src="<%=request.getContextPath()%>/back-template/docs/js/plugins/chart.js"></script>
              <script type="text/javascript">
                var data = {
                 labels: ["January", "February", "March", "April", "May"],
                 datasets: [
                 {
                   label: "My First dataset",
                   fillColor: "rgba(220,220,220,0.2)",
                   strokeColor: "rgba(220,220,220,1)",
                   pointColor: "rgba(220,220,220,1)",
                   pointStrokeColor: "#fff",
                   pointHighlightFill: "#fff",
                   pointHighlightStroke: "rgba(220,220,220,1)",
                   data: [65, 59, 80, 81, 56]
                 },
                 {
                   label: "My Second dataset",
                   fillColor: "rgba(151,187,205,0.2)",
                   strokeColor: "rgba(151,187,205,1)",
                   pointColor: "rgba(151,187,205,1)",
                   pointStrokeColor: "#fff",
                   pointHighlightFill: "#fff",
                   pointHighlightStroke: "rgba(151,187,205,1)",
                   data: [28, 48, 40, 19, 86]
                 }
                 ]
               };
               var pdata = [
               {
                value: 300,
                color: "#46BFBD",
                highlight: "#5AD3D1",
                label: "Complete"
              },
              {
                value: 50,
                color:"#F7464A",
                highlight: "#FF5A5E",
                label: "In-Progress"
              }
              ]
              
              var ctxl = $("#lineChartDemo").get(0).getContext("2d");
              var lineChart = new Chart(ctxl).Line(data);
              
              var ctxp = $("#pieChartDemo").get(0).getContext("2d");
              var pieChart = new Chart(ctxp).Pie(pdata);
            </script>
            <!-- Google analytics script-->
            <script type="text/javascript">
              if(document.location.hostname == 'pratikborsadiya.in') {
               (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
                 (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
                 m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
               })(window,document,'script','//www.google-analytics.com/analytics.js','ga');
               ga('create', 'UA-72504830-1', 'auto');
               ga('send', 'pageview');
             }
           </script>
	

</body>
<script src="<%=request.getContextPath()%>/static/admin/js/config.js"></script>
<script src="<%=request.getContextPath()%>/static/admin/js/script.js"></script>
</html>