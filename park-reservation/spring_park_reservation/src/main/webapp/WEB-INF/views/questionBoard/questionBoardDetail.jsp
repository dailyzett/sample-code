<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.cen.park.config.Token"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>



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

		if (confirm("삭제 시 게시글 복구가 불가능 합니다. 정말 삭제하시겠습니까?") == true) { //확인
			location.href = 'deletequestion?qid=${qdto.qb_id}'
		} else { //취소
			return false;
		}

	}
</script>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/askBoard.css">
<link rel="stylesheet" href="resources/css/detailQBoard.css">
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
		<a href="notice">공지사항</a>
		<a href="question" class="select">문의하기</a>
		<a href="visit">방문후기</a>
	</div>
	<h2 style="text-align: center">Q&A</h2>

	<div class="div1">
		<form action="reply" method="post">
		<fmt:formatDate var="resultRegDt" value="${qdto.qb_date}" pattern="yyyy-MM-dd"/>
			<table class="writeform">
				<tr>
					<th>등록일자</th>
					<td>${resultRegDt }</td>
				</tr>
				
				<tr>
					<th>제목</th>
					<td style="font-weight: bold;"><c:out value="${qdto.qb_title }"/></td>
				</tr>

				<tr>
					<th>이름</th>
					<td>${qdto.writer_name }</td>
				</tr>
				<tr>
					<th>내용</th>
					<td style="white-space:pre-line; ">
						<c:out value="${qdto.qb_content }" />
					</td>
				</tr>
			</table>

			<button type="button" onclick='location.href="question"' style="margin-left: 20px;">목록보기</button>
			<c:if test="${sessionScope.sessionId eq qdto.writer_id && sessionScope.sessionRole ne 'admin'}">
				<button type="button" onclick="location.href='modifyQuestion?qid=${qdto.qb_id}'">수정</button>
				<button type="button" onclick="removeCheck()">삭제</button>
			</c:if>
			<c:if test="${sessionScope.sessionRole eq 'admin'}">
				<button type="button" onclick="location.href='modifyQuestion?qid=${qdto.qb_id}'">수정</button>
				<button type="button" onclick="removeCheck()">삭제</button>
			</c:if>


			<c:if test="${sessionScope.sessionRole eq 'admin'}">
				<div>
					<table class="writeform">
						<tr>
							<th style="padding: 8px;">답변</th>
							<td>
								<textarea style="width: 100%; height: 500px;" name="content"></textarea>
							</td>
						</tr>
					</table>

					<input type="hidden" value="${qdto.qb_id}" name="re_fk_id">
					<input type="hidden" value="${qdto.qb_id }" name="qid" />
					
					<button type="submit" style="float: right; margin-right: 20px;">제출</button>
				</div>
			</c:if>
		</form>
	</div>


</body>
</html>