<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${empty sessionScope.username }">
	<%
		request.setAttribute("check", "true");
	%>
	<jsp:forward page="login.jsp"/>
</c:if>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		String id = (String)session.getAttribute("username");
	%>
	
	<%= id %> 님 반갑습니다 <br/>
	예약 페이지
</body>
</html>