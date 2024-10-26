<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%! String name, id; %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		name = (String)session.getAttribute("name");
		id = (String) session.getAttribute("id");
		
		
	%>
	
	<%= name %> 님 안녕하세요. <br/>
	<%= id %> <a href="logout.jsp"> 로그아웃 </a> <br/>
	<a href="memberList.jsp">회원 목록</a>
</body>
</html>