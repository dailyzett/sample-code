<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/login.css">
<link rel="stylesheet" href="resources/css/login2.css">
<script language="JavaScript" src="resources/js/login.js"></script>

<title>Insert title here</title>
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
		<a href="register" class="select">회원가입</a>
		<a href="login">로그인</a>
		<a href="findId">아이디찾기</a>
		<a href="findPassword">비밀번호찾기</a>
	</div>

	<div class="center">

		<div class="joinform">
			<form action="joinSuccess" style="border: 1px solid #ccc" method="post" name="userInfo" onsubmit="return checkValue()">
				<div class="container">
					<div class="loginTitle">
						<h1 style="color: green;">회원가입</h1>
				
					</div>

					<div class="loginSubTitle">
						<p>
							국립공원공단 회원으로 가입하시면 사전예약, 마이페이지 등
							<br />
							다양한 회원 서비스를 쉽고 편리하게 이용하실 수 있습니다.
						</p>
					</div>
					
					<div class="line2"></div>
					
					<div style="margin: 150px 0px 0px 20%;">
						<table>
							<tr>
								<th>아이디</th>
								<td>
									<input type="text" id="username" name="username" required onkeydown="inputUsernameCheck()">
									<button type="button" onclick="openUsernameCheck()">확인</button>
								</td>
							</tr>

							<tr>
								<th>이름</th>
								<td>
									<input type="text" name="name" required>
								</td>
							</tr>

							<tr>
								<th>패스워드</th>
								<td>
									<input type="password" name="pw" required>
								</td>
							</tr>

							<tr>
								<th>패스워드 확인</th>
								<td>
									<input type="password" name="pwr" required>
								</td>
							</tr>

							<tr>
								<th>이메일</th>
								<td>
									<input type="text" name="email" required>
								</td>
							</tr>

							<tr>
								<th>전화번호</th>
								<td>
									<input type="text" value="010" name="phone_1" style="width: 14.3%;" required>
									-
									<input type="text" name="phone_2" style="width: 14.3%;" required>
									-
									<input type="text" name="phone_3" style="width: 14.3%;" required>
								</td>
							</tr>
							<tr>
								<th></th>
								<td>
										<button type="submit" style="width: 50%;">회원가입</button>
								</td>
							</tr>
						</table>
					</div>
					<input type="hidden" name="member_role" value="normal" />
					<input type="hidden" name="usernameDuplication" value="usernameUncheck">
				</div>
			</form>
		</div>
	</div>


</body>
</html>