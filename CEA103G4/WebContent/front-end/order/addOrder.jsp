<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.order.model.*"%>

<%
  OrderVO orderVO = (OrderVO) request.getAttribute("orderVO");
%>

<html>
<head>
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1"/>
<title>訂單資料新增 - addOrder.jsp</title>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/2.0.0/jquery.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/jquery-twzipcode@1.7.14/jquery.twzipcode.min.js"></script>

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
  .fa-star {
  	font-size:65px
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
  .star {
  	width: 20px;
  	height: 20px;
  }
  textarea{
  resize : none
  }
</style>
</head>
<body bgcolor='white'>

<table id="table-1">
	<tr><td>
		 <h3>訂單資料新增 - addOrder.jsp</h3></td><td>
		 <h4><a href="select_page.jsp"><img src="images/tomcat.png" width="100" height="100" border="0">回首頁</a></h4>
	</td></tr>
</table>

<h3>資料新增:</h3>

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
	<!-- <tr>
		<td>訂單日期:</td>
		<td><input name="order_date" id="f_date1" type="date"></td>
	</tr> -->
	<tr>
		<td>訂單狀態:</td>
		<td><select name="order_state">
			<option value="0">未付款</option>
			<option value="1">已付款</option>
		</select>
		</td>
	</tr>
	<tr>
		<td>訂單運費:</td>
		<td><input type="TEXT" name="order_shipping" size="45" value="60" /></td>
	</tr>
	<tr>
		<td>訂單金額:</td>
		<td><input type="TEXT" name="order_price" size="45" value="" /></td>
	</tr>
	<tr>
		<td>付款方式:</td>
		<td><select name="pay_method">
			<option value="0">信用卡</option>
			<option value="1">轉帳</option>
		</select>
		</td>
	</tr>
	<!-- <tr>
		<td>付款截止日期:</td>
		<td><input name="pay_deadline" id="f_date2" type="date"></td>
	</tr> -->
	<tr>
		<td>收件人姓名:</td>
		<td><input type="TEXT" name="rec_name" size="45" /></td>
	</tr>
	<tr>
		<td>收件人地址:</td>
		<td>
		<div id="twzipcode"></div>
		<input type="TEXT" name="rec_addr" size="45"/>
		</td>
	</tr>
	<tr>
		<td>收件人電話:</td>
		<td><input type="TEXT" name="rec_phone" size="45"  /></td>
	</tr>
	<tr>
		<td>收件人手機:</td>
		<td><input type="TEXT" name="rec_cellphone" size="45"  /></td>
	</tr>
	<tr>
		<td>物流方式:</td>
		<td><select name="logistics">
			<option value="0">超商</option>
			<option value="1">宅配</option>
		</select>
		</td>
	</tr>
	<tr>
		<td>物流狀態:</td>
		<td><select name="logisticsstate">
			<option value="0">未出貨</option>
			<option value="1">已出貨</option>
			<option value="2">已取貨</option>
		</select>
		</td>
	</tr>
	<tr>
		<td>使用點數折抵:</td>
		<td><input type="TEXT" name="discount" size="45"  /></td>
	</tr>
	<tr>
		<td>買家帳號:</td>
		<td><input type="TEXT" name="user_id" size="45"  /></td>
	</tr>
	<tr>
		<td>賣家帳號:</td>
		<td><input type="TEXT" name="seller_id" size="45"  /></td>
	</tr>
	<tr>
		<td>賣家評價:</td>
		
		<td>
		<input type="hidden" name="srating" value="0" id="con"/>
		<ion-icon name="star" class="star all-star" id="s1"></ion-icon>
		<ion-icon name="star" class="star all-star" id="s2"></ion-icon>
		<ion-icon name="star" class="star all-star" id="s3"></ion-icon>
		<ion-icon name="star" class="star all-star" id="s4"></ion-icon>
		<ion-icon name="star" class="star all-star" id="s5"></ion-icon>
		</td>
	</tr>
	<tr>
		<td>賣家評價內容:</td>
		<td><textarea ame="srating_content" rows="10" cols="43"></textarea></td>
		<!-- <td><input type="TEXT" name="srating_content" size="45" /></td> -->
	</tr>
	<tr>
		<td>點數回饋:</td>
		<td><input type="TEXT" name="point" size="45" /></td>
	</tr>
	
</table>
<br>
<input type="hidden" name="action" value="insert">
<input type="submit" value="送出新增"></FORM>
<script src="https://unpkg.com/ionicons@5.4.0/dist/ionicons.js"></script>
</body>
<script>
	$(document).ready(function(){
		$("#s1").click(function(){
			$(".all-star").css("color","black");
			$("#s1").css("color","yellow");
			$("#con").val("1");
		})
		$("#s2").click(function(){
			$(".all-star").css("color","black");
			$("#s1,#s2").css("color","yellow");
			$("#con").val("2");
		})
		$("#s3").click(function(){
			$(".all-star").css("color","black");
			$("#s1,#s2,#s3").css("color","yellow");
			$("#con").val("3");
		})
		$("#s4").click(function(){
			$(".all-star").css("color","black");
			$("#s1,#s2,#s3,#s4").css("color","yellow");
			$("#con").val("4");
		})
		$("#s5").click(function(){
			$(".all-star").css("color","black");
			$(".all-star").css("color","yellow");
			$("#con").val("5");
		})
	})
</script>
<script>
		$("#twzipcode").twzipcode({
		zipcodeIntoDistrict: true, // 郵遞區號自動顯示在區別選單中
		css: ["city form-control", "town form-control"], // 自訂 "城市"、"地別" class 名稱 
		countyName: "city", // 自訂城市 select 標籤的 name 值
		districtName: "town" // 自訂區別 select 標籤的 name 值
		}); 
</script>


<!-- =========================================以下為 datetimepicker 之相關設定========================================== -->

<% 
  java.sql.Date order_date = null;
  try {
	  order_date = orderVO.getOrder_date();
   } catch (Exception e) {
	   order_date = new java.sql.Date(System.currentTimeMillis());
   }
%>

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
		   value: '<%=order_date%>', // value:   new Date(),
           //disabledDates:        ['2017/06/08','2017/06/09','2017/06/10'], // 去除特定不含
           //startDate:	            '2017/07/10',  // 起始日
           //minDate:               '-1970-01-01', // 去除今日(不含)之前
           //maxDate:               '+1970-01-01'  // 去除今日(不含)之後
        });
        $.datetimepicker.setLocale('zh');
        $('#f_date2').datetimepicker({
	       theme: '',              //theme: 'dark',
	       timepicker:false,       //timepicker:true,
	       step: 1,                //step: 60 (這是timepicker的預設間隔60分鐘)
	       format:'Y-m-d',         //format:'Y-m-d H:i:s',
		   value: '<%=order_date%>', // value:   new Date(),
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