<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/joinSuccess.css">
</head>


<body>


<div class="navbar">
  <a href="home">홈</a>
  <a href="reservation?pn=kaya">예약하기</a>
  

  
  <div class="dropdown">
    <button class="dropbtn">알림마당 
      <i class="fa fa-caret-down"></i>
    </button>
    <div class="dropdown-content">
      <a href="notice">공지사항</a>
      <a href="question">문의하기</a>
      <a href="visit">방문후기</a>
    </div>
  </div>
  
  <a href="history">나의 예약 내역</a>
</div>

<%
	session.invalidate();
%>


<h1>회원 탈퇴가 완료되었습니다.</h1><br/>
<h2>앞으로도 더 나은 서비스를 제공하기 위해 노력하겠습니다.</h2>
<div class="loginDiv">
	
</div>
</body>
</html>
