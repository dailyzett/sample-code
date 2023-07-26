<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cen.park.config.Token"%>

<c:if test="${sessionScope.sessionRole ne 'admin' }">
	<script>
		alert('관리자 권한이 없습니다')
		document.location.href = "home";
	</script>
</c:if>


<%
if (request.getAttribute("TOKEN_KEY") == null)
		Token.set(request);
%>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/askBoard.css">
<link rel="stylesheet" href="resources/css/writeQBoard.css">
</head>
<body>

	<div class="navbar">
		<a href="home">홈</a>
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

		<c:if test="${sessionScope.sessionRole eq 'admin'}">
			<div class="dropdown">
				<button class="dropbtn">
					관리자 메뉴
					<i class="fa fa-caret-down"></i>
				</button>
				<div class="dropdown-content">
					<a href="memberlist">회원 관리</a>
					<a href="reservationManage">예약 관리</a>
				</div>
			</div>
		</c:if>


		<a href="listOne" style="float: right;">마이페이지</a>

		<c:if test="${empty sessionScope.sessionId }">
			<a href="login" style="float: right;">로그인</a>
		</c:if>

		<c:if test="${!empty sessionScope.sessionId  }">
			<a href="logout" style="float: right">로그아웃</a>
			<a href='#' onclick="return false" style="float: right;"><%=session.getAttribute("sessionId")%>님 안녕하세요
			</a>
		</c:if>
	</div>

	<div class="space">
		<img src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
	</div>

	<div class="sidenav">
		<div class="menubar">알림마당</div>
		<a href="notice"class="select">공지사항</a>
		<a href="question" >문의하기</a>
		<a href="visit">방문후기</a>
	</div>

	<h2 style="text-align: center">공지사항</h2>
	<form action="noticeWrite" method="post" name="qboardName">
		<table class="writeform">	
			<tr>
				<th>국립공원을 선택해주세요</th>
				<td>
					<select name="park_name">
						<option value="공통" selected="selected">공통</option>
						<option value="가야산">가야산</option>
						<option value="계룡산">계룡산</option>
						<option value="내장산">내장산</option>
						<option value="설악산">설악산</option>
					</select>
				</td>
			</tr>
			<tr>
				<th>제목</th>
				<td>
					<input type="text" name="title" style="width: 99%"  required="required"/>
				</td>
			</tr>
			<tr>
				<th>내용</th>
				<td>
					<textarea name="content" rows="30" cols="100" placeholder="내용 입력.." required="required"></textarea>
				</td>
			</tr>

		</table>
		<input type="hidden" name="TOKEN_KEY" value="<%=request.getAttribute("TOKEN_KEY")%>" />
		<div class="boardRegit">
			<div class="buttonDiv2">
				<button type="submit">등록</button>
			</div>
		</div>
	</form>
</body>
</html>