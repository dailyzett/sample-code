<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>


<c:if test="${requestScope.alreadyLogin eq 'yes'}">
	<script>
		alert("이미 로그인 중입니다");
	</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/common2.css">

</head>
<body>


<div class="navbar">
  <a href="home.do">홈</a>
  <a href="reservation.do?pn=kaya">예약하기</a>
  
  
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

<div class="space">
	<img src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
</div>

<div class="home">
	<img src="https://user-images.githubusercontent.com/73692337/105320957-2aa7cb00-5c0a-11eb-8873-09a7d0cb3ba9.jpg" style="width:100%; height:650px">
</div>

</body>
</html>
