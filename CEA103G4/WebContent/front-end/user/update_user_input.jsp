<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page import="com.user.model.*"%>

<%
  UserVO userVO = (UserVO) request.getAttribute("userVO"); //UserServlet.java (Controller) 存入req的userVO物件 (包括幫忙取出的userVO, 也包括輸入資料錯誤時的userVO物件)
%>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>會員資料修改</title>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>
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

  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <!-- Main CSS-->
  <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/front-template/css/usermain.css">
  <!-- Font-icon css-->
  <link rel="stylesheet" type="text/css" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css">
</head>
<body bgcolor='white'>
<jsp:include page="/front-end/user/userSidebar.jsp" />
<main class="app-content">
                <div class="app-title">
                  <div>
                    <h1><i class="fa fa-user fa-lg"></i> 修改我的資料</h1>
                  </div>
                  <ul class="app-breadcrumb breadcrumb">
                    <li class="breadcrumb-item"><a href="<%=request.getContextPath()%>/front-end/protected/userIndex.jsp"><i class="fa fa-home fa-lg"></i></a></li>
                    <li class="breadcrumb-item">修改我的資料</li>
                  </ul>
                </div>
<FORM METHOD="post" ACTION="<%=request.getContextPath() %>/front-end/user/user.do" name="form1">
<table>
	<tr>
		<td>帳號:<font color=red><b>*</b></font></td>
		<td><%=userVO.getUser_id()%></td>
	</tr>
<!-- 	<tr> -->
<!-- 		<td>密碼:</td> -->
<%-- 		<td><input type="TEXT" name="user_pwd" size="45" value="<%=userVO.getUser_pwd()%>" /></td> --%>
<!-- 	</tr> -->
	<tr>
		<td>姓名:</td>
		<td><input type="TEXT" name="user_name" size="45"	value="<%=userVO.getUser_name()%>" /></td>
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_name}</b></td></tr>
	<tr>
		<td>身分証字號:</td>
		<td><%=userVO.getId_card()%></td>
	</tr>
<!-- 	<tr> -->
<!-- 		<td>性別:</td> -->
<!-- 		<td><select name="user_gender"> -->
<%-- 					<option value="0" ${(userVO.user_gender==0)? 'selected':'' }>女</option> --%>
<%-- 					<option value="1" ${(userVO.user_gender==1)? 'selected':'' }>男</option> --%>
<!-- 			</select></td>  -->
<%-- 	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_gender}</b></td></tr> --%>
	<tr>
	<td>性別 *</td>
	<td><input type="radio" id="user_gender" name="user_gender" value="1" ${(userVO.user_gender==1)? 'checked':'' }>
	<label for="male">男</label>
	<input type="radio" id="user_gender" name="user_gender" value="0" ${(userVO.user_gender==0)? 'checked':'' }>
	<label for="female">女</label></td> 
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_gender}</b></td></tr>
	
	<tr>
		<td>生日:</td>
		<td><input name="user_dob" size="45" id="f_date1" type="text" ></td>
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_dob}</b></td></tr>
	<tr>
		<td>Email:</td>
		<td><input type="TEXT" name="user_mail" size="45"	value="<%=userVO.getUser_mail()%>" /></td>
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_mail}</b></td></tr>
	<tr>
		<td>電話:</td>
		<td><input type="TEXT" name="user_phone" size="45"	value="<%=userVO.getUser_phone()%>" /></td>
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_phone}</b></td></tr>
	<tr>
		<td>手機號碼:</td>
		<td><input type="TEXT" name="user_mobile" size="45" value="<%=userVO.getUser_mobile()%>" /></td>
	</tr><tr><td></td><td><font color=red><b>${errorMsgs.user_mobile}</b></td></tr>
	<tr>
		<td>地址 *</td>
		<td>
		<div id="twzipcode"></div><font color=red><b>${errorMsgs.city}</b>
		<input type="TEXT" name="user_addr" size="45" value="<%=userVO.getUser_addr()%>"></td>
		</td><tr><td></td><td><font color=red><b>${errorMsgs.user_addr}</b></td></tr>
	</tr>
<!-- 	<tr> -->
<!-- 		<td>註冊日期:</td> -->
<!-- 		<td><input name="regdate" id="f_date2" type="text" ></td> -->
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>點數:</td> -->
<%-- 		<td><input type="TEXT" name="user_point" size="45" value="<%=userVO.getUser_point()%>" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>違約次數:</td> -->
<%-- 		<td><input type="TEXT" name="violation" size="45" value="<%=userVO.getViolation()%>" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>狀態:</td> -->
<%-- 		<td><input type="TEXT" name="user_state" size="45" value="<%=userVO.getUser_state()%>" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>賣家評價:</td> -->
<%-- 		<td><input type="TEXT" name="user_comment" size="45" value="<%=userVO.getUser_comment()%>" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>評價人數:</td> -->
<%-- 		<td><input type="TEXT" name="comment_total" size="45" value="<%=userVO.getComment_total()%>" /></td> --%>
<!-- 	</tr> -->
<!-- 	<tr> -->
<!-- 		<td>錢包:</td> -->
<%-- 		<td><input type="TEXT" name="cash" size="45" value="<%=userVO.getCash()%>" /></td> --%>
<!-- 	</tr> -->

<%-- 	<jsp:useBean id="deptSvc" scope="page" class="com.dept.model.DeptService" /> --%>
<!-- 	<tr> -->
<!-- 		<td>部門:<font color=red><b>*</b></font></td> -->
<!-- 		<td><select size="1" name="deptno"> -->
<%-- 			<c:forEach var="deptVO" items="${deptSvc.all}"> --%>
<%-- 				<option value="${deptVO.deptno}" ${(userVO.deptno==deptVO.deptno)?'selected':'' } >${deptVO.dname} --%>
<%-- 			</c:forEach> --%>
<!-- 		</select></td> -->
<!-- 	</tr> -->

</table>
<br>
<input type="hidden" name="action" value="update">
<input type="hidden" name="user_id" value="<%=userVO.getUser_id()%>">
<input type="hidden" name="id_card" value="<%=userVO.getId_card()%>">
<input type="submit" value="送出修改"></FORM>
<jsp:include page="/front-end/protected/userIndex_footer.jsp" />
</body>

<script>
        $("#twzipcode").twzipcode({
        	zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
        	css: ["city form-control", "town form-control"], // 自訂 "城市"、"地別" class 名稱 
        	countyName: "city", // 自訂城市 select 標籤的 name 值
        	districtName: "town", // 自訂區別 select 標籤的 name 值
        	countySel: "<%=userVO.getCity()%>",
        	districtSel: "<%=userVO.getTown()%>",
        	zipcodeSel: "<%=userVO.getZipcode()%>"
        	});
</script>

<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.css" />
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.js"></script>
<script src="<%=request.getContextPath()%>/datetimepicker/jquery.datetimepicker.full.js"></script>

<style>
  .xdsoft_datetimepicker .xdsoft_datepicker {
           width:  300px;   /* width:  300px; */
  }
  .xdsoft_datetimepicker .xdsoft_timepicker .xdsoft_time_box {
           height: 151px;   /* height:  151px; */
  }
</style>

<script>
        $.datetimepicker.setLocale('zh');
        $('#f_date1').datetimepicker({
           theme: '',              //theme: 'dark',
 	       timepicker:false,       //timepicker:true,
 	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
 	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
 		   value: '<%=userVO.getUser_dob()%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        
//         $.datetimepicker.setLocale('zh');
//         $('#f_date2').datetimepicker({
//            theme: '',              //theme: 'dark',
//  	       timepicker:false,       //timepicker:true,
//  	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
//  	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
<%--  		   value: '<%=userVO.getRegdate()%>', // value:   new Date(), --%>
//            //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
//            //startDate:	            '2017/07/10',  // 起始日
//            //minDate:               '-1970-01-01', // 去除今日(不含)之前
//            //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
//         });
        

        
   
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
</html>