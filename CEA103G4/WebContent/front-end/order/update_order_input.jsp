<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.order.model.*"%>

<%
  OrderVO orderVO = (OrderVO) request.getAttribute("orderVO"); //OrderServlet.java (Concroller) 存入req的orderVO物件 (包括幫忙取出的orderVO, 也包括輸入資料錯誤時的orderVO物件)
%>
<%= orderVO == null %>
<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訊息資料修改 - update_order_input.jsp</title>

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
	width: auto;
	background-color: white;
	margin-top: 1px;
	margin-bottom: 1px;
  }
  table, th, td {
    border: 0px solid #CCCCFF;
  }
  th, td {
    padding: 1px;
  }
</style>

</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>訂單資料修改 - update_order_input.jsp</h3>
		 <h4><a href="select_page.jsp"><img src="images/back1.gif" width="100" height="32" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>訂單修改:</h3>

<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<FORM METHOD="post" ACTION="order.do" name="form1">
<table>
	<tr>
		<td>訂單編號:<font color=red><b>*</b></font></td>
		<td><%=orderVO.getOrder_no()%></td>
	</tr>
	<tr>
		<td>訂單日期:</td>
		<td><input name="order_date" id="f_date1" type="text" ></td>
	</tr>
	<tr>
		<td>訂單狀態:</td>
		<td><select name="order_state">
						<option value="0" ${(orderVO.order_state==0)? 'selected':''}>未付款</option>
						<option value="1" ${(orderVO.order_state==1)? 'selected':''}>已付款</option>
		</select></td>
	</tr>
	<tr>
		<td>訂單運費:</td>
		<td><input type="TEXT" name="order_shipping" size="45"	value="<%=orderVO.getOrder_shipping()%>" /></td>
	</tr>
	<tr>
		<td>訂單金額:</td>
		<td><input type="TEXT" name="order_price" size="45"	value="<%=orderVO.getOrder_price()%>" /></td>
	</tr>
	<tr>
		<td>付款方式:</td>
		<td><select name="pay_method">
						<option value="0" ${(orderVO.pay_method==0)? 'selected':''}>信用卡</option>
						<option value="1" ${(orderVO.pay_method==1)? 'selected':''}>轉帳</option>
		</select></td>	
	<tr>
		<td>付款截止時間:</td>
		<td><input type="TEXT" name="pay_deadline" size="45"	value="<%=orderVO.getPay_deadline()%>" /></td>
	</tr>
	<tr>
		<td>收件人姓名:</td>
		<td><input type="TEXT" name="rec_name" size="45"	value="<%=orderVO.getRec_name()%>" /></td>
	</tr>
	<tr>
		<td>收件人地址:</td>
		<td><input type="TEXT" name="rec_addr" size="45"	value="<%=orderVO.getRec_addr()%>" /></td>
	</tr>
	<tr>
		<td>收件人電話:</td>
		<td><input type="TEXT" name="rec_phone" size="45"	value="<%=orderVO.getRec_phone()%>" /></td>
	</tr>
	<tr>
		<td>收件人手機:</td>
		<td><input type="TEXT" name="rec_cellphone" size="45"	value="<%=orderVO.getRec_cellphone()%>" /></td>
	</tr>
	<tr>
		<td>物流方式:</td>
		<td><select name="logistics">
						<option value="0" ${(orderVO.logistics==0)? 'selected':''}>超商</option>
						<option value="1" ${(orderVO.logistics==1)? 'selected':''}>宅配</option>
		</select></td>	
	<tr>
	<tr>
		<td>物流狀態:</td>
		<td><select name="logisticsstate">
						<option value="0" ${(orderVO.logisticsstate==0)? 'selected':''}>未出貨</option>
						<option value="1" ${(orderVO.logisticsstate==1)? 'selected':''}>已出貨</option>
						<option value="2" ${(orderVO.logisticsstate==2)? 'selected':''}>已取貨</option>
		</select></td>	
	<tr>
	<tr>
		<td>使用點數折抵:</td>
		<td><input type="TEXT" name="discount" size="45" value="<%=orderVO.getDiscount()%>" /></td>
	</tr>
	<tr>
		<td>買家帳號:</td>
		<td><input type="TEXT" name="user_id" size="45"	value="<%=orderVO.getUser_id()%>" /></td>
	</tr>
	<tr>
		<td>賣家帳號:</td>
		<td><input type="TEXT" name="seller_id" size="45"	value="<%=orderVO.getSeller_id()%>" /></td>
	</tr>
	<tr>
		<td>賣家評價分數:</td>
		<td><input type="TEXT" name="srating" size="45"	value="<%=orderVO.getSrating()%>" /></td>
	</tr>
	<tr>
		<td>賣家評價內容:</td>
		<td><input type="TEXT" name="srating_content" size="45"	value="<%=orderVO.getSrating_content()%>" /></td>
	</tr>
	<tr>
		<td>點數回饋:</td>
		<td><input type="TEXT" name="point" size="45"	value="<%=orderVO.getPoint()%>" /></td>
	</tr>
</table>
<br>
		<input type="hidden" name="action" value="update">
 		<input type="hidden" name="order_no" value="<%=orderVO.getOrder_no()%>">
		<input type="submit" value="送出修改">
	</FORM>
</body>



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
 		   value: '<%=orderVO.getOrder_date()%>',  // value:   new Date(),
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
</html>