<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ page import="com.common.etc.Token" %>

<c:if test="${empty sessionScope.sessionId }">
	<%
		request.setAttribute("check", true);
	%>
	<jsp:forward page="/login.jsp" />
</c:if>

<c:if test="${requestScope.reservationCheck eq true }">
	<script>
		alert('예약이 완료되었습니다');
	</script>
</c:if>

<c:if test="${requestScope.reservationCheck eq false }">
	<script>
		alert('해당 날짜는 예약할 수 없습니다. 다른 날짜로 예약해주세요.');
	</script>
</c:if>

<c:if test="${requestScope.overMax eq true }">
	<script>
		alert('예약 인원수가 예약하고자 하는 야영장의 예약 가능 인원수보다 큽니다.');
	</script>
</c:if>


<c:if test="${requestScope.doublesubmit eq true }">
	<script>
		alert('잘못된 처리형식입니다');
		document.location.href = "reservation.do?pn=kaya";
	</script>
</c:if>

<%
	if(request.getAttribute("TOKEN_KEY")==null) 
		Token.set(request);
%>



<!DOCTYPE html>
<html>
<head>

<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script type="text/javascript" src="js/reservation.js"></script>
<link rel="stylesheet"
	href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="css/common.css">
<link rel="stylesheet" href="css/reservation.css">

<meta charset="UTF-8">
<title>Insert title here</title>
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
					<a href="memberlist.do">회원 관리</a>
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

	<div class="header">
		<img
			src="https://user-images.githubusercontent.com/73692337/105316214-eb767b80-5c03-11eb-9af8-7631150565f0.png">
	</div>

	<div class="title">
		<h3>야영장 예약하기</h3>
	</div>


	<div id="content">
		<div id="tab">
			<ul class="tab_menu">
				<li class="on"><a href="reservation.do?pn=kaya">가야산</a></li>
				<li><a href="reservation.do?pn=kelong">계룡산</a></li>
				<li><a href="reservation.do?pn=naejang">내장산</a></li>
				<li><a href="reservation.do?pn=seolak">설악산</a></li>
			</ul>
		</div>

		<div class="tableLayout">
			<table class="">
				<thead class="">
					<tr>
						<c:if test="${!empty beforeMonth}">
							<th rowspan="2" colspan="3">야영장</th>
							<th style="padding-right: 0px; padding-left: 0px;"
								colspan="${mCount1 }"><c:out value="${beforeMonth }" />월</th>
						</c:if>
						<c:if test="${!empty afterMonth}">
							<th style="padding-right: 0px; padding-left: 0px;"
								colspan="${mCount2 }"><c:out value="${afterMonth }" />월</th>
						</c:if>
					</tr>
					<tr>
						<c:forEach var="kaya" items="${dates }">
							<td><c:out value="${kaya }" /></td>
						</c:forEach>
					</tr>
					<tr>
						<th colspan="3">전체 수용인원 수</th>
						<c:forEach var="kaya" items="${dtos }" varStatus="status">
							<td><c:out value="${kaya.maxPeople }" /></td>
							<td id="date${status.index}" style="display: none;"><c:out value="${kaya.reservationDate }"/></td>
							<td id="price${status.index }" style="display: none;"><c:out value="${kaya.parkPrice }"/></td>
						</c:forEach>
					</tr>
					<tr>
						<th colspan="3">예약가능 인원 수</th>
						<c:forEach var="kaya" items="${dtos }">
							<td><c:out value="${kaya.enablePeople }" /></td>
						</c:forEach>
					</tr>
				</thead>
				<tbody>
					<tr>
						<th colspan="3">야영장 예약</th>
						<c:forEach var="i" begin="0" end="15">
							<td>
								<button type="button" id="btn${i }">
									<img
										src="https://user-images.githubusercontent.com/73692337/106408161-36ed1d00-6481-11eb-94bd-7edacf57357d.png"
										style="width: 30px; height: 30px;" />
								</button>
							</td>
						</c:forEach>
					</tr>
				</tbody>
			</table>
		</div>
	</div>

	<div class="detail">
		<table class="detailTable">
			<caption>선택한 야영장, 입실일, 정원안내, 인원수선택, 결제금액을 알 수 있는 표입니다.</caption>
			<colgroup>
				<col width="350px">
				<col width="200px">
				<col width="400px">
				<col>
			</colgroup>
			<thead>
				<tr>
					<th scope="col">야영장</th>
					<th scope="col">입실일</th>
					<th scope="col">인원수 선택</th>
					<th scope="col" class="end">결제금액</th>
				</tr>
			</thead>
			<tbody>
				<tr>
					<td class="eovl_tit" id="shelterNDP" style="text-align: center;">미선택</td>
					<td class="date" id="doe" style="text-align: center;"></td>
					<td class="select_peo">
						<input type="radio" id="btn01" name="check_per" value="1" checked=""><label for="btn01">1명</label>
						<input type="radio" id="btn02" name="check_per" value="2"><label for="btn02">2명</label> 
						<input type="radio" id="btn03" name="check_per" value="3"><label for="btn03">3명</label> 
						<input type="radio" id="btn04" name="check_per" value="4"><label for="btn04">4명</label> 
						<input type="radio" id="btn05" name="check_per" value="5"><label for="btn05">5명</label>
					</td>
					<td class="end cash" style="text-align:center;"><span class="cash_num" id="price">0</span>원</td>
				</tr>
			</tbody>
		</table>
		<div style="margin-top: 30px; margin-right:80px; float:right;">
		<form action="reservationProcess.do?pn=kaya" method="post" id="reservationForm">
			<input type="hidden" name="inputDate" id="inputDate" value="없음"/>
			<input type="hidden" name="peopleCount" id="peopleCount" value="없음"/>
			<input type="hidden" name="inputPrice" id="inputPrice" value="없음"/>		
			<input type="hidden" name="TOKEN_KEY" value="<%=request.getAttribute("TOKEN_KEY")%>"/>
			<button type="submit" class="submit" id="reservationBtn">예약하기</button>
		</form>
		</div>
	</div>
	
</body>
</html>