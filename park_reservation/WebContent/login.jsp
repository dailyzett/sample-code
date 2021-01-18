<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:if test="${requestScope.check }">

 	<script>
 		alert("예약은 회원만 가능합니다");
 	</script>
</c:if>


<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/login.css">

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
  
</div>

<div class="tab">
  <button class="tablinks" onclick="openCity(event, 'login')" id="defaultOpen">로그인</button>
  <button class="tablinks" onclick="openCity(event, 'join')">회원가입</button>
</div>

<div id="login" class="tabcontent">
  <h3>로그인</h3>
  <p>로그인 탭</p>
</div>

<div id="join" class="tabcontent">

<div class="joinform">
  <form action="/action_page.php" style="border:1px solid #ccc">
  <div class="container">
    <h1>회원 가입</h1>
    <p>국립 공원 예약 홈페이지에 온 것을 환영합니다</p>
    <hr>
    
    <label for="username"><b>아이디</b></label>
    <input type="text" placeholder="아이디를 입력하세요" name="username" required>

    <label for="pw"><b>패스워드</b></label>
    <input type="password" placeholder="패스워드를 입력하세요" name="pw" required>

    <label for="pw-repeat"><b>패스워드 확인</b></label>
    <input type="password" placeholder="패스워드 확인을 입력하세요" name="pw-repeat" required>
    
    <label for="email"><b>이메일</b></label>
    <input type="text" placeholder="이메일을 입력하세요" name="email" required>
    
	<label for="phone1"><b>전화번호</b></label>
	<br/>
    <input type="text" value="010" name="phone1" style="width:20%"required>
    
    -
    
    <input type="text" name="phone2" style="width:20%"required>
	-
    <input type="text" name="phone3" style="width:20%"required>
    

    <div class="clearfix">
      <button type="submit" class="signupbtn">회원 가입</button>
    </div>
  </div>
</form>
</div>
</div>



<script>
function openCity(evt, param) {
	  var i, tabcontent, tablinks;
	  tabcontent = document.getElementsByClassName("tabcontent");
	  for (i = 0; i < tabcontent.length; i++) {
	    tabcontent[i].style.display = "none";
	  }
	  tablinks = document.getElementsByClassName("tablinks");
	  for (i = 0; i < tablinks.length; i++) {
	    tablinks[i].className = tablinks[i].className.replace(" active", "");
	  }
	  document.getElementById(param).style.display = "block";
	  evt.currentTarget.className += " active";
	}

	// Get the element with id="defaultOpen" and click on it
	document.getElementById("defaultOpen").click();
</script>

</body>
</html>
