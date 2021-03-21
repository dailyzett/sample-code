<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%
	session.invalidate();
%>

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/joinSuccess.css">
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




<h1>탈퇴가 완료되었습니다.</h1><br/>
<h2>다음에 더 좋은 서비스로 보답하겠습니다.</h2>
<div class="loginDiv">
	<button type="button" onclick="location.href='login'">로그인</button>
</div>
</body>
</html>
