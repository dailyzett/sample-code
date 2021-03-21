<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.sql.Timestamp"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:if test="${requestScope.check eq true }">
	<script>
		alert("예약은 회원만 가능합니다");
	</script>
</c:if>

<c:if test="${!empty sessionScope.sessionId}">
	<%
	request.setAttribute("alreadyLogin", "yes");
	%>
	<jsp:forward page="/home" />
</c:if>



<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/findId.css">
<script language="JavaScript" src="resources/js/login.js"></script>
</head>
<body>


	<div class="navbar">
		<a href="/park">홈</a>
		<a href="reservation?pn=kaya">예약하기</a>
		<div class="dropdown">
			<button class="dropbtn">
				알림마당
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

	<div class="space">
		<img src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
	</div>

	<div class="sidenav">
		<div class="menubar">로그인</div>
		<a href="register">회원가입</a>
		<a href="login" >로그인</a>
		<a href="findId" class="select">아이디찾기</a>
		<a href="findPassword">비밀번호찾기</a>
	</div>

	<div class="center">
		
	</div>


</body>
</html>
