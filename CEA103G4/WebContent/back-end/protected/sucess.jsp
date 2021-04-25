<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<%@page import="java.sql.*"%>
<html>
<head>
<title>Login successful!</title>
 
</head>
<body>
<%
	String name = request.getParameter("get");
%>
<center>
<h1>Hello ! Dear <font color="blue"><%=name%></font>~! </h1>
<br/>
<%!
	public static final String DBDRIVER = "org.gjt.mm.mysql.Driver";
	public static final String DBURL = "jdbc:mysql://localhost:3306/client";
	public static final String DBUSER = "root";
	public static final String DBPASS = "pass";
	
	public static final int PAGEITEMS = 5;
%>
<%
	Connection conn = null;
	PreparedStatement pstmt = null;
	ResultSet rs = null;
	String sql = null;
	String empno = null;
	String ename = null;
	String job = null;
	double sal = 0;
	int pagenum = 0;
	String currentpage_tmp = null;
	int currentpage = 1;
	String flag_tmp = null;
	int flag = 0;
%>
<hr>
<caption><h3>empire list</h3></caption>
	<table border = "2" width="400">
	<tr>
	<td>empno</td>
	<td>ename</td>
	<td>job</td>
	<td>sal</td>
	</tr>
<%	
	
	Class.forName(DBDRIVER);
	conn = DriverManager.getConnection(DBURL,DBUSER,DBPASS);
	pstmt = conn.prepareStatement("select * from data",ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	rs = pstmt.executeQuery();
	rs.last();
	pagenum = rs.getRow()/PAGEITEMS==0?(rs.getRow()/PAGEITEMS):(rs.getRow()/PAGEITEMS+1);
	
	currentpage_tmp = request.getParameter("currentpage");
	flag_tmp = request.getParameter("flag");
	if(currentpage_tmp == null){
		currentpage_tmp = "1";
	}
	if(flag_tmp == null){
		flag_tmp = "0";
	}
	currentpage = Integer.parseInt(currentpage_tmp);
	flag = Integer.parseInt(flag_tmp);
	if(flag==2){
		currentpage++;
	}else if(flag==1){
		currentpage--;
	}
	if(currentpage == 0){
		currentpage = 1;
	}else if(currentpage == pagenum+1){
		currentpage = pagenum;
	}
	sql = "select empno,ename,job,sal from data limit "+(currentpage-1)*5+","+PAGEITEMS;
	
	pstmt = conn.prepareStatement(sql,ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
	rs = pstmt.executeQuery();
	while(rs.next()){
		empno = rs.getString(1);
		ename = rs.getString(2);
		job = rs.getString(3);
		sal = rs.getDouble(4);
%>
	<tr>
	<td><%=empno%></td>
	<td><%=ename%></td>
	<td><%=job%></td>
	<td><%=sal%></td>
	</tr>
<%
	}
%>
	
</table>
	<a href="success.jsp?currentpage=1&flag=0&get=<%=name%>">FP</a>  
	<a href="success.jsp?currentpage=<%=currentpage%>&flag=1&get=<%=name%>">Back</a> 
	<a href="success.jsp?currentpage=<%=currentpage%>&flag=2&get=<%=name%>">Forw</a> 
	<a href="success.jsp?currentpage=<%=pagenum%>&flag=0&get=<%=name%>">LP</a>
</center>
</body>
</html>