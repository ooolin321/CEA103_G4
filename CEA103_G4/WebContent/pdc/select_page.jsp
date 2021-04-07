<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>CEA Pdc: Home</title>

<style>
  table#table-1 {
	width: 450px;
	background-color: #CCCCFF;
	margin-top: 5px;
	margin-bottom: 10px;
    border: 3px ridge Gray;
    height: 80px;
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

</head>
<body bgcolor='white'>

<table id="table-1">
   <tr><td><h3>CEA Pdc: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for CEA Pdc: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="product" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllPdc1_byDAO.jsp'>List</a> all Pdc    <h4>(byDAO).         </h4></li>
  <li><a href='pdc.do?action=getAll'> List</a> all Pdc    <h4>(getFromSession).</h4> <br><br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="pdc.do" >
        <b>輸入商品編號 (如1001):</b>
        <input type="text" name="product_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
  <li>
    <FORM METHOD="post" ACTION="pdc.do" name="form1">
        <b>輸入商品編號 (如1001):</b>
        <input type="text" name="product_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="button" value="送出" onclick="fun1()">  <h4>(資料格式驗證  by Java Script).</h4> 
    </FORM>
  </li>

  <jsp:useBean id="dao" scope="page" class="com.pdc.model.PdcDAO" />
   
  <li>
     <FORM METHOD="post" ACTION="pdc.do" >
       <b>選擇商品編號:</b>
       <select size="1" name="product_id">
         <c:forEach var="pdcVO" items="${dao.all}" > 
          <option value="${pdcVO.product_id}">${pdcVO.product_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="pdc.do" >
       <b>選擇直播編號:</b>
       <select size="1" name="product_id">
         <c:forEach var="pdcVO" items="${dao.all}" > 
          <option value="${pdcVO.live_id}">${pdcVO.live_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
     </FORM>
  </li>
</ul>

<script>    
   function fun1(){
      with(document.form1){
         if (product_id.value=="") 
             alert("請輸入商品編號!");
         else if (isNaN(product_id.value)) 
             alert("商品編號格式不正確!");
         else if ((product_id.value < 1001) || (product_id.value > 1999)) 
             alert("請填寫介於1001和1999之間的數字!");
         else
             submit();
      }
   }
</script>

</body>
</html>