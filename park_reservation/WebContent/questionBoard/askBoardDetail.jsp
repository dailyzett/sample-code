<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.common.etc.Token"%>

<c:if test="${empty sessionScope.sessionId }">
	<a href="login.jsp" style="float: right;">로그인</a>
	<script>
		alert('로그인이 필요한 서비스입니다. \n로그인 페이지로 이동하시겠습니까?')
		document.location.href = "login.jsp";
	</script>
</c:if>

<c:if test="${requestScope.modifyAlert eq '1' }">
	<script>
		alert('게시글 수정이 완료되었습니다.');
	</script>
</c:if>



<c:if test="${requestScope.deleteCheck eq false }">
	<script>
		alert('게시글 삭제는 본인만 할 수 있습니다.');
	</script>
</c:if>

<script>
function removeCheck() {

	 if (confirm("삭제 시 게시글 복구가 불가능 합니다. 정말 삭제하시겠습니까?") == true){    //확인
		 location.href='deleteQuestion.do?qid=${qdto.id}'
	 }else{   //취소
	     return false;
	 }

}
</script>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/askBoard.css">
<link rel="stylesheet" href="css/detailQBoard.css">
</head>

<body>

	<div class="navbar">
		<a href="home.do">홈</a> <a href="reservation.do?pn=kaya">예약하기</a>


		<div class="dropdown">
			<button class="dropbtn">
				알림마당 <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="notice.do">공지사항</a> <a href="question.do">문의하기</a> <a
					href="visit.do">방문후기</a>
			</div>
		</div>
		<a href="history.do">나의 예약 내역</a>

		<c:if test="${sessionScope.sessionId eq 'admin' }">
			<div class="dropdown">
				<button class="dropbtn">
					관리자 메뉴 <i class="fa fa-caret-down"></i>
				</button>
				<div class="dropdown-content">
					<a href="memberlist.do">회원 관리</a><a href="reservationManage.do">예약 관리</a>
				</div>
			</div>
		</c:if>


		<a href="listOne.do" style="float: right;">마이페이지</a>

		<c:if test="${empty sessionScope.sessionId }">
			<a href="login.jsp" style="float: right;">로그인</a>
		</c:if>

		<c:if test="${!empty sessionScope.sessionId  }">
			<a href="logout.do" style="float: right">로그아웃</a>
			<a href='#' onclick="return false" style="float: right;"><%=session.getAttribute("sessionId")%>님
				안녕하세요</a>
		</c:if>
	</div>

	<div class="space">
		<img
			src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
	</div>

	<div class="sidenav">
		<div class="menubar">알림마당</div>
		<a href="notice.do">공지사항</a> 
		<a href="question.do" class="select">문의하기</a> 
		<a href="visit.do">방문후기</a>
	</div>
	<h2 style="text-align: center">Q&A</h2>

	<div class="div1">
		<form action="reply.do" method="post">
			<input type="hidden" value="${qdto.id }" name="qid" />
			<table class="writeform">
				<tr>
					<th>등록일자</th>
					<td>${qdto.stringFormatDate }</td>
				</tr>

				<tr>
					<th>이름</th>
					<td>${qdto.writerName }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td style="white-space: pre;"><c:out value="${qdto.content }" /></td>
				</tr>

			</table>
			<button type="button" onclick='location.href="question.do"'
				style="margin-left: 20px;">목록보기</button>
			<c:if test="${sessionScope.sessionId eq qdto.writerId }">
				<button type="button" onclick="location.href='modifyQuestion.do?qid=${qdto.id}'">수정</button>
				<button type="button" onclick="removeCheck()">삭제</button>
			</c:if>
			<c:if test="${sessionScope.sessionId eq 'admin' }">
				<button type="button" onclick="location.href='modifyQuestion.do?qid=${qdto.id}'">수정</button>
				<button type="button" onclick="removeCheck()">삭제</button>
			</c:if>
			<c:if test="${sessionScope.sessionId eq 'admin' }">

				<div>
					<table class="writeform">

						<tr>
							<th style="padding: 8px;">답변</th>
							<td><textarea style="width: 100%; height: 500px;"
									name="reContent"></textarea></td>
						</tr>
					</table>
					<button type="submit" style="float: right; margin-right: 20px;">제출</button>
			
				</div>
			</c:if>
		</form>
	</div>


</body>
</html>