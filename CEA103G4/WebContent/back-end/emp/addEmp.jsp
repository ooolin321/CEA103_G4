<%@ page contentType="text/html; charset=UTF-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.emp.model.*"%>
<%@ page import="com.auth.model.*"%>
<%@page import="java.util.List"%>
<%
	EmpVO empVO = (EmpVO) request.getAttribute("empVO");
%>

<%
	EmpVO empVO2 = (EmpVO)session.getAttribute("account");
	session.setAttribute("empVO", empVO2);
%> 
 

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
<title>員工資料新增 - addEmp.jsp</title>
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
<!-- Main CSS-->
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/back-template/docs/css/main.css">
<!-- Font-icon css-->
<link rel="stylesheet" type="text/css"
	href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" type="text/css"
	href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />

</head>
<jsp:include page="/back-end/backendMenu.jsp" />

<body bgcolor='white' class="app sidebar-mini rtl">
	<div class="main-container">
		<div class="pd-ltr-20 xs-pd-20-10">
			<div class="min-height-200px">
				<div class="pd-20 card-box mb-30">
					<div class="clearfix">
						<h2 class="text-dark h2">
							<i class="fas fa-user-plus"></i>&nbsp;新增員工資料  <%=empVO2.getEmpno() %>
						</h2>
					</div>
					<div class="tile-body">
						<%-- 錯誤表列 --%>
						<c:if test="${not empty errorMsgs}">
							<font style="color: red">請修正以下錯誤:</font>
							<ul>
								<c:forEach var="message" items="${errorMsgs}">
									<li style="color: red">${message}</li>
								</c:forEach>
							</ul>
						</c:if>

						<FORM METHOD="post"
							ACTION="<%=request.getContextPath()%>/emp/emp.do" name="form1">
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>員工姓名:</label><input
											class="form-control" type="TEXT" name="ename" size="45"
											value="<%=(empVO == null) ? "" : empVO.getEname()%>" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>職位:</label><input
											class="form-control" type="TEXT" name="job" size="45"
											value="<%=(empVO == null) ? "" : empVO.getJob()%>" />
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>身份證字號:</label><input
											class="form-control" type="TEXT" name="id" size="45"
											value="<%=(empVO == null) ? "A123456789" : empVO.getId()%>" />
									</div>
								</div>

								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>性別:</label><select
											class="form-control" size="1" name="gender">
											<option value="1" ${(empVO.gender==0)? 'selected':''}>男</option>
											<option value="0" ${(empVO.gender==0)? 'selected':''}>女</option>
										</select>
									</div>
								</div>
							</div>
							
							<div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>生日:</label> <input
											class="form-control" name="dob" id="f_date2" type="text">
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>e-mail:</label> <input
											class="form-control" type="email" name="email"
											value="<%=(empVO == null) ? "" : empVO.getEmail()%>" />
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>薪水:</label> <input
											class="form-control" type="TEXT" name="sal" size="45"
											value="<%=(empVO == null) ? "" : empVO.getSal()%>" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>狀態:</label> <select
											class="form-control" size="1" name="state">
											<option value="1" ${(empVO.state==1)? 'selected':''}>在職</option>
											<option value="0" ${(empVO.state==0)? 'selected':''}>離職</option>
										</select>
									</div>
								</div>
							</div>
							 <div class="row">
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>地址:</label>
										<div id="twzipcode"></div>
										<input class="form-control" type="TEXT" name="addr" size="45"
											value="<%=(empVO == null) ? "" : empVO.getAddr()%>" />
									</div>
								</div>
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>到職日期:</label> <input
											class="form-control" name="hiredate" id="f_date1" type="text">
									</div>
								</div>
								</div>
								<div class="row">					
								<div class="col-md-6">
									<div class="form-group">
										<label><i class="fas fa-user-alt"></i>權限:</label>
                                        <div class="form-group">
                                           <jsp:useBean id="funSvc" scope="page"
												class="com.fun.model.FunService" />
											<ul>
												<c:forEach var="funVO" items="${funSvc.all}">
													<li><input name="funno" value="${funVO.funno}" type="hidden">
														${funVO.funName}
														<label><select size="1" name="auth_no">
															<option value="1" ${(authVO.auth_no==1)? 'selected':''}>開</option>
															<option value="0" ${(authVO.auth_no==0)? 'selected':''}>關</option>
													</select></label></li>
												</c:forEach>
												
											</ul>
										</div>
										</div>
								</div>
							</div><br>
							<input type="hidden" name="action" value="insert">
							<div class="row">
	<%-- 						<input type="hidden" name="empno" value="<%=empVO.getEmpno()%>"> --%>
							<input class="btn btn-primary" type="submit" value="送出新增">
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<script>
$("#twzipcode").twzipcode({
	zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
	css: ["city form-control", "town form-control"], // 自訂 "城市"、"地別" class 名稱 
	countyName: "city", // 自訂城市 select 標籤的 name 值
	districtName: "dist" // 自訂區別 select 標籤的 name 值
	});
</script>




	<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

	<%
		java.sql.Date hiredate = null;
		java.sql.Date dob = null;
		try {
			hiredate = empVO.getHiredate();
			dob = empVO.getDob();
		} catch (Exception e) {
			hiredate = new java.sql.Date(System.currentTimeMillis());
			dob = new java.sql.Date(System.currentTimeMillis());
		}
	%>

<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script
		src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
.xdsoft_datetimepicker .xdsoft_datepicker {
	width: 300px; /* width:  300px; */
}

.xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
	height: 151px; /* height:  151px; */
}
</style>

	<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=hiredate%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $('#f_date2').datetimepicker({
 	       theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=dob%> ', // value:   new Date(),
		//disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
		//startDate:	            '2017/07/10',  // 起始日
		//minDate:               '-1970-01-01', // 去除今日(不含)之前
		//maxDate:               '+1970-01-01'  // 去除今日(不含)之後
		});

		// ----------------------------------------------------------以下用來排定無法選擇的日期-----------------------------------------------------------

		//      1.以下為某一天之前的日期無法選擇
		//      var somedate1 = new Date('2017-06-15');
		//      $('#f_date1').datetimepicker({
		//          beforeShowDay: function(date) {
		//        	  if (  date.getYear() <  somedate1.getYear() || 
		//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
		//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
		//              ) {
		//                   return [false, ""]
		//              }
		//              return [true, ""];
		//      }});

		//      2.以下為某一天之後的日期無法選擇
		//      var somedate2 = new Date('2017-06-15');
		//      $('#f_date1').datetimepicker({
		//          beforeShowDay: function(date) {
		//        	  if (  date.getYear() >  somedate2.getYear() || 
		//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
		//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
		//              ) {
		//                   return [false, ""]
		//              }
		//              return [true, ""];
		//      }});

		//      3.以下為兩個日期之外的日期無法選擇 (也可按需要換成其他日期)
		//      var somedate1 = new Date('2017-06-15');
		//      var somedate2 = new Date('2017-06-25');
		//      $('#f_date1').datetimepicker({
		//          beforeShowDay: function(date) {
		//        	  if (  date.getYear() <  somedate1.getYear() || 
		//		           (date.getYear() == somedate1.getYear() && date.getMonth() <  somedate1.getMonth()) || 
		//		           (date.getYear() == somedate1.getYear() && date.getMonth() == somedate1.getMonth() && date.getDate() < somedate1.getDate())
		//		             ||
		//		            date.getYear() >  somedate2.getYear() || 
		//		           (date.getYear() == somedate2.getYear() && date.getMonth() >  somedate2.getMonth()) || 
		//		           (date.getYear() == somedate2.getYear() && date.getMonth() == somedate2.getMonth() && date.getDate() > somedate2.getDate())
		//              ) {
		//                   return [false, ""]
		//              }
		//              return [true, ""];
		//      }});
	</script>

	<jsp:include page="/back-end/backendfooter.jsp" />

</body>
</html>