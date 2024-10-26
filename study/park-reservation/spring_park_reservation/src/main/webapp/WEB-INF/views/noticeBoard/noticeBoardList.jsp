<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>


<c:if test="${requestScope.doublesubmit eq true }">
	<script>
		alert('잘못된 처리형식입니다');
		document.location.href = "question";
	</script>
</c:if>

<c:if test="${requestScope.deleteAlert eq '1' }">
	<script>
		alert('게시글 삭제가 완료되었습니다.');
	</script>
</c:if>


<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<link rel="stylesheet" href="resources/css/common.css">
<link rel="stylesheet" href="resources/css/askBoard.css">
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
		<a href="notice" class="select">공지사항</a>
		<a href="question">문의하기</a>
		<a href="visit">방문후기</a>
	</div>
	<form action="noticeSearch" method="post">
		<div class="selectBoxDiv" style="margin-left:224.5px;">
			<select name="park">
				<option value="공통" selected="selected">공통</option>
				<option value="가야산">가야산</option>
				<option value="계룡산">계룡산</option>
				<option value="내장산">내장산</option>
				<option value="설악산">설악산</option>
			</select>
		</div>
		<div class="searchDiv">
			<input type="text" name="search" placeholder="제목 입력...">
			<button type="submit">
				<i class="fa fa-search"></i>
			</button>
		</div>
	</form>
	<c:if test="${sessionScope.sessionRole eq 'admin'}">
		<div class="buttonDiv">
			<button type="button" onclick="location.href='noticeWrite'">글쓰기</button>
		</div>
	</c:if>


	<div class="center">
		<table class="qblist">
			<tr>
				<th>번호</th>
				<th>공원명</th>
				<th>제목</th>
				<th>조회수</th>
				<th>등록일</th>
			</tr>

			<c:forEach items="${noticeVo }" var="notice">
			<fmt:formatDate var="resultRegDt" value="${notice.notice_date}" pattern="yyyy-MM-dd" />
			<c:if test="${notice.park_name eq '공통' }">
				<tr style="background-color:#003458; color: white;">
					<td style="width: 7%;">${notice.id }</td>
					
						<td>${notice.park_name }</td>
					
					<td style="width: 50%;">
						<a href="noticeDetail?nid=${notice.id}" style="font-weight: bold; color: #f7e600">${notice.title }</a>
					</td>
					<td style="width: 7%;">${notice.hit }</td>
					<td>${resultRegDt}</td>
				</tr>			
			</c:if>
			
			<c:if test="${notice.park_name ne '공통' }">
				<tr>
					<td style="width: 7%;">${notice.id }</td>
					
						<td>${notice.park_name }</td>
					
					<td style="width: 50%;">
						<a href="noticeDetail?nid=${notice.id}" style="font-weight: bold">${notice.title }</a>
					</td>
					<td style="width: 7%;">${notice.hit }</td>
					<td>${resultRegDt}</td>
				</tr>			
			</c:if>
	

			</c:forEach>

			<tr>
				<td colspan="7" align="center">
					<c:if test="${requestScope.count gt 0 }">
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
							<a href="notice?pageNum=<%=startPage - 10%>" class="beforenext">이전</a>
						</c:if>

						<c:if test="${not loog_flag }">
							<c:forEach var="i" begin="${startPage }" end="${endPage }">

								<c:if test="${i eq currentPage}">
								${i }
							</c:if>
								<c:if test="${i ne currentPage }">
									<a href="notice?pageNum=${i }" class="pagenum">${i }</a>
								</c:if>

							</c:forEach>
						</c:if>
						<c:if test="${pageScope.endPage lt pageScope.pageCount }">
							<a href="notice?pageNum=<%=startPage + 10%>" class="beforenext">다음</a>
						</c:if>

					</c:if>
				</td>
			</tr>
		</table>


	</div>

</body>
</html>