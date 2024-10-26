<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:if test="${requestScope.alreadyLogin eq 'yes'}">
	<script>
		alert("이미 로그인 중입니다");
	</script>
</c:if>

<c:if test="${sessionScope.sessionId ne 'itcen1234' }">
	<script>
		alert('관리자만 접근 가능한 게시판입니다');
		document.location.href = "home";
	</script>
</c:if>

<c:set var="count" value="${count }" />
<c:set var="currentPage" value="${currentPage }" />
<c:set var="pageSize" value="${pageSize }" />

<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/memberList.css">

</head>
<body>


	<div class="navbar">
		<a href="home">홈</a> <a href="reservation?pn=kaya">예약하기</a>


		<div class="dropdown">
			<button class="dropbtn">
				알림마당 <i class="fa fa-caret-down"></i>
			</button>
			<div class="dropdown-content">
				<a href="notice">공지사항</a> <a href="question">문의하기</a> <a
					href="visit">방문후기</a>
			</div>
		</div>
		<a href="history">나의 예약 내역</a>

		<c:if test="${sessionScope.sessionId eq 'itcen1234' }">
			<div class="dropdown">
				<button class="dropbtn">
					관리자 메뉴 <i class="fa fa-caret-down"></i>
				</button>
				<div class="dropdown-content">
					<a href="memberlist">회원 관리</a> <a href="reservationManage">예약
						관리</a>
				</div>
			</div>
		</c:if>


		<a href="listOne" style="float: right;">마이페이지</a>

		<c:if test="${empty sessionScope.sessionId }">
			<a href="login" style="float: right;">로그인</a>
		</c:if>

		<c:if test="${!empty sessionScope.sessionId  }">
			<a href="logout" style="float: right">로그아웃</a>
			<a href='#' onclick="return false" style="float: right;"><%=session.getAttribute("sessionId")%>님
				안녕하세요</a>
		</c:if>
	</div>

	<div class="space">
		<img
			src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
	</div>


	<div class="sidenav">
		<div class="menubar">관리자 메뉴</div>
		<a href="memberlist">회원 관리</a> <a href="reservationManage"
			class="select">예약 관리</a>
	</div>

	<div class="center">
		<h4>[총예약수 : ${count }]</h4>

		<div class="search-container">
			<form action="reservationListSearch" method="post">
				<input type="text" placeholder="고유번호 검색.." name="search">
				<button type="submit">
					<i class="fa fa-search"></i>
				</button>
			</form>
		</div>
		<table class="memberlist">
			<tr>
				<th style="width: 10%;">고유번호</th>
				<th>국립공원</th>
				<th>예약일</th>
				<th>예약인원</th>
				<th>가격</th>
				<th>예약현황</th>
			</tr>

			<c:forEach items="${dtos }" var="reservation">
				<tr>
					<td style="width: 7%;">${reservation.r_id }</td>
					<c:if test="${reservation.park_name eq 'kaya' }">
						<c:set var="view_park" value="가야산"/>
					</c:if>
					<c:if test="${reservation.park_name eq 'naejang' }">
						<c:set var="view_park" value="내장산"/>
					</c:if>
					<c:if test="${reservation.park_name eq 'kelong' }">
						<c:set var="view_park" value="계룡산"/>
					</c:if>
					<c:if test="${reservation.park_name eq 'seolak' }">
						<c:set var="view_park" value="설악산"/>
					</c:if>
					<td>${view_park }</td>
					<td>${reservation.reservation_date}</td>
					<td style="width: 50%;">${reservation.count }명</td>
					<td><fmt:formatNumber value="${reservation.price }"
							pattern="#,###" />원</td>

					<td><button type="button"
							onclick="document.location.href='cancelAdmin?rid=${reservation.r_id}'">예약취소</button></td>
				</tr>
			</c:forEach>

			<tr>
				<td colspan="6" align="center"><c:if
						test="${requestScope.count gt 0 }">
						<%
						int count = Integer.parseInt(request.getAttribute("count").toString());
								int pageSize = Integer.parseInt(request.getAttribute("pageSize").toString());
								int currentPage = Integer.parseInt(request.getAttribute("currentPage").toString());

								//총 페이지의 수
								int pageCount = count / pageSize + (count % pageSize == 0 ? 0 : 1);

								// 한 페이지에 보여줄 페이지 블럭 수
								int pageBlock = 10;

								int startPage = ((currentPage - 1) / pageBlock) * pageBlock + 1;
								int endPage = startPage + pageBlock - 1;

								pageContext.setAttribute("startPage", startPage);
								pageContext.setAttribute("endPage", endPage);
								pageContext.setAttribute("pageBlock", pageBlock);
								pageContext.setAttribute("pageCount", pageCount);

								if (endPage > pageCount) {
									endPage = pageCount;
									pageContext.setAttribute("endPage", endPage);
								}
						%>
						<c:if test="${pageScope.startPage gt pageScope.pageBlock }">
							<a href="reservationManage?pageNum=<%=startPage - 10%>"
								class="beforenext">이전</a>
						</c:if>
						<c:set var="loop_flag" value="false" />
						<c:if test="${not loog_flag }">
							<c:forEach var="i" begin="${startPage }" end="${endPage }">

								<c:if test="${i eq currentPage}">
								${i }
							</c:if>
								<c:if test="${i ne currentPage }">
									<a href="reservationManage?pageNum=${i }" class="pagenum">${i }</a>
								</c:if>

							</c:forEach>
						</c:if>
						<c:if test="${pageScope.endPage lt pageScope.pageCount }">
							<a href="reservationManage?pageNum=<%=startPage + 10%>"
								class="beforenext">다음</a>
						</c:if>

					</c:if></td>
			</tr>
		</table>
	</div>
	<div class="footer"></div>

</body>
</html>