<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${empty sessionScope.sessionId }">
	<%
		request.setAttribute("check", true);
	%>
	<jsp:forward page="login.jsp"/>
</c:if>
<!DOCTYPE html>
<html>
<head>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

<div class="navbar">
  <a href="home.do">홈</a>
  <a href="reservation.do">예약하기</a>
  

  
  <div class="dropdown">
    <button class="dropbtn">알림마당 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="notice.do">공지사항</a>
      <a href="question.do">문의하기</a>
      <a href="visit.do">방문후기</a>
    </div>
  </div>
  
  <a href="history.do">나의 예약 내역</a>
  
  <c:if test="${sessionScope.sessionId eq 'admin' }">
  	<div class="dropdown">
  		<button class="dropbtn">관리자 메뉴
  			<i class="fa fa-caret-down"></i>
  		</button>
  		<div class="dropdown-content">
  			<a href="memberlist.do">회원 관리</a>
  		</div>
  	</div>
  </c:if>
  
  
  <a href="listOne.do" style="float:right;">마이페이지</a>
  
  <c:if test="${empty sessionScope.sessionId }">
  	<a href="login.jsp" style="float:right;">로그인</a>
  </c:if>
  
  <c:if test="${!empty sessionScope.sessionId  }">
  	<a href="logout.do" style="float:right">로그아웃</a>
  	<a href='#' onclick="return false" style="float:right;"><%= session.getAttribute("sessionId") %>님 안녕하세요</a>
  </c:if>
 
</div>

	<%
		String id = (String)session.getAttribute("sessionId");
	%>
	
	<%= id %> 님 반갑습니다 <br/>
	예약 페이지
</body>
</html>