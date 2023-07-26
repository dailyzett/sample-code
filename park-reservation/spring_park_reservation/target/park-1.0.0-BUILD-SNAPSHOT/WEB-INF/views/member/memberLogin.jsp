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
<link rel="stylesheet" href="resources/css/login.css">
<link rel="stylesheet" href="resources/css/login2.css">
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

	<div class="sidenav"></div>

	<div class="center">
		<div class="tab">
			<button class="tablinks" onclick="opentab(event, 'login')" id="defaultOpen">로그인</button>
			<button class="tablinks" onclick="opentab(event, 'join')">회원가입</button>
		</div>

		<div id="login" class="tabcontent">
			<div class="loginform">
				<form action="login" style="border: 1px solid #ccc" method="post">
					<div class="container">
						<h1>로그인</h1>

						<label for="loginId">
							<b>아이디</b>
						</label>
						<br />
						<c:if test="${requestScope.tempId ne null }">
							<input type="text" placeholder="아이디를 입력하세요" id="loginId" name="loginId" value=<%=(String) request.getAttribute("tempId")%> required>
							<br />
						</c:if>

						<c:if test="${requestScope.tempId eq null }">
							<input type="text" placeholder="아이디를 입력하세요" id="loginId" name="loginId" required>
							<br />
						</c:if>


						<c:if test="${requestScope.loginStatus eq 'idfail' }">
							<font color="red">
								아이디를 찾을 수 없습니다.
								<br />
								<br />
							</font>
						</c:if>

						<label for="loginPw">
							<b>패스워드</b>
						</label>
						<br />
						<input type="password" placeholder="패스워드를 입력하세요" name="loginPw" required>


						<c:if test="${requestScope.loginStatus eq 'pwfail' }">
							<font color="red">비밀번호가 일치하지 않습니다.</font>
						</c:if>

						<div class="clearfix">
							<button type="submit" class="signupbtn">로그인</button>
						</div>
					</div>
				</form>
			</div>
		</div>


		<div id="join" class="tabcontent">
			<div class="joinform">
				<form action="joinSuccess" style="border: 1px solid #ccc" method="post" name="userInfo" onsubmit="return checkValue()">
					<div class="container">
						<h1>회원 가입</h1>
						<p>국립 공원 예약 홈페이지에 온 것을 환영합니다</p>
						<hr>

						<label for="username">
							<b>아이디</b>
						</label>
						<br />
						<input type="text" placeholder="아이디를 입력하세요" name="username" required onkeydown="inputUsernameCheck()" style="width: 80%">

						<button type="button" onclick="openUsernameCheck()" style="width: 15%; display: inline-block; padding: 15px; margin: 5px 0 22px 0;">증복 확인</button>
						<input type="hidden" name="usernameDuplication" value="usernameUncheck">
						<br />
						<label for="name">
							<b>이름</b>
						</label>
						<br />
						<input type="text" placeholder="이름을 입력하세요" name="name" required>
						<br />
						<label for="pw">
							<b>패스워드</b>
						</label>
						<br />
						<input type="password" placeholder="패스워드를 입력하세요" name="pw" required>
						<br />
						<label for="pw-repeat">
							<b>패스워드 확인</b>
						</label>
						<br />
						<input type="password" placeholder="패스워드 확인을 입력하세요" name="pwr" required>
						<br />
						<label for="email">
							<b>이메일</b>
						</label>
						<br />
						<input type="text" placeholder="이메일을 입력하세요" name="email" required>
						<br />
						<label for="phone1">
							<b>전화번호</b>
						</label>
						<br />
						<input type="text" value="010" name="phone_1" style="width: 10%" required>
						-
						<input type="text" name="phone_2" style="width: 10%" required>
						-
						<input type="text" name="phone_3" style="width: 10%" required>

						 

						<div class="clearfix">
							<button type="submit" class="signupbtn">회원 가입</button>
						</div>
					</div>
				</form>
			</div>
		</div>
	</div>

	<script>
		function opentab(evt, param) {
			var i, tabcontent, tablinks;
			tabcontent = document.getElementsByClassName("tabcontent");
			for (i = 0; i < tabcontent.length; i++) {
				tabcontent[i].style.display = "none";
			}
			tablinks = document.getElementsByClassName("tablinks");
			for (i = 0; i < tablinks.length; i++) {
				tablinks[i].className = tablinks[i].className.replace(
						" active", "");
			}
			document.getElementById(param).style.display = "block";
			evt.currentTarget.className += " active";
		}

		// Get the element with id="defaultOpen" and click on it
		document.getElementById("defaultOpen").click();
	</script>

</body>
</html>
