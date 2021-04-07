<%@ page contentType="text/html; charset=UTF-8" pageEncoding="Big5"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<html>
<head>
<title>Product: Home</title>

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
   <tr><td><h3>Product: Home</h3><h4>( MVC )</h4></td></tr>
</table>

<p>This is the Home page for Product: Home</p>

<h3>資料查詢:</h3>
	
<%-- 錯誤表列 --%>
<c:if test="${not empty errorMsgs}">
	<font style="color:red">請修正以下錯誤:</font>
	<ul>
		<c:forEach var="message" items="${errorMsgs}">
			<li style="color:red">${message}</li>
		</c:forEach>
	</ul>
</c:if>

<ul>
  <li><a href='listAllProduct.jsp'>List</a> all Product    <h4>(byDAO).         </h4></li>
  <li><a href='product.do?action=getAll'> List</a> all Product    <h4>(getFromSession).</h4> <br><br><br></li>
  
  <li>
    <FORM METHOD="post" ACTION="product.do" >
        <b>輸入商品編號 (如7001):</b>
        <input type="text" name="product_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="submit" value="送出">                   <h4>(資料格式驗證  by Controller ).</h4> 
    </FORM>
  </li>
  
  <li>
    <FORM METHOD="post" ACTION="product.do" name="form1">
        <b>輸入商品編號 (如01):</b>
        <input type="text" name="product_id">
        <input type="hidden" name="action" value="getOne_For_Display">
        <input type="button" value="送出" onclick="fun1()">  <h4>(資料格式驗證  by Java Script).</h4> 
    </FORM>
  </li>

  <jsp:useBean id="dao" scope="page" class="com.product.model.ProductDAO" />
   
  <li>
     <FORM METHOD="post" ACTION="product.do" >
       <b>選擇商品編號 :</b>
       <select size="1" name="product_id">
         <c:forEach var="productVO" items="${dao.all}" > 
          <option value="${productVO.product_id}">${productVO.product_id}
         </c:forEach>   
       </select>
       <input type="hidden" name="action" value="getOne_For_Display">
       <input type="submit" value="送出">
    </FORM>
  </li>
  
  <li>
     <FORM METHOD="post" ACTION="product.do" >
       <b>選擇員工姓名:</b>
       <select size="1" name="product_id">
         <c:forEach var="productVO" items="${dao.all}" > 
          <option value="${productVO.product_id}">${productVO.product_id}
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
         else if ((product_id < 1) || (product_id.value > 100)) 
             alert("請填寫介於1和100之間的數量!");
         else
             submit();
      }
   }
</script>

</body>
</html>