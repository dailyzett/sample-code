<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:if test="${requestScope.modifyOk eq true }">
	<script>
		alert('회원정보 수정이 완료되었습니다.');
	</script>
</c:if>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/listOne.css">


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
  	<script>
  		alert('로그인이 필요한 서비스입니다. \n로그인 페이지로 이동하시겠습니까?')
  		document.location.href="login.jsp";
  	</script>
  </c:if>
  
  <c:if test="${!empty sessionScope.sessionId  }">
  	<a href="logout.do" style="float:right">로그아웃</a>
  	<a href='#' onclick="return false" style="float:right;"><%= session.getAttribute("sessionId") %>님 안녕하세요</a>
  </c:if>
</div>
<div class="space">
	<img src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
</div>
<div class="sidenav">
	<div class="menubar">
		마이페이지
	</div>
  <a href="listOne.do" class="select">기본 회원 정보</a>
  <a href="modifyMember.do">회원 정보 수정</a>
  <a href="myQuestion.do">나의 질문</a>
  <a href="signOut.do">회원 탈퇴</a>
</div>

	<div class="center">
 		<table class="memberOne">
 			<tr>
 				<th>아이디</th>
 				<td>${member.username }</td>
 			</tr>
 			<tr>
 				<th>이름</th>
 				<td>${member.name }</td>
 			</tr>
 			<tr>
 				<th>이메일</th>
 				<td>${member.email }</td>
 			</tr>
 			<tr>
 				<th>전화번호</th>
 				<td>${member.phone1 } - ${member.phone2 } - ${member.phone3 }</td>
 			</tr>
 		</table>
	</div>




<div class="footer">
</div>


</body>
</html>
