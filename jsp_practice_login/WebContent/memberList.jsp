<%@ page import="java.util.ArrayList"%>
<%@ page import="dao.MemberDao"%>
<%@ page import="dto.MemberDto"%>


<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%
		MemberDao memberDao = new MemberDao();
		ArrayList<MemberDto> memberDto = memberDao.memberList();

		for (int i = 0; i < memberDto.size(); i++) {
			MemberDto dto = memberDto.get(i);
			String name = dto.getName();
			String id = dto.getId();
			String pw = dto.getPw();
			String phone = dto.getPhone1() + " - " + dto.getPhone2() + " - " + dto.getPhone3();

			out.println("이름: " + name + "<br/>");
			out.println("아이디: " + id + "<br/>");
			out.println("패스워드: " + pw + "<br/>");
			out.println("폰: " + phone + "<br/>");
			out.println("========================<br/>");
		}
	%>
</body>
</html>