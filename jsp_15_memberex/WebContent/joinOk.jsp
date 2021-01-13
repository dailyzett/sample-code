<%@ page import="java.sql.Timestamp"%>
<%@ page import="com.javaEdu.ex.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="dto" class="com.javaEdu.ex.MemberDto"></jsp:useBean>
<jsp:setProperty name="dto" property="*" />
<%
	dto.setrDate(new Timestamp(System.currentTimeMillis()));
	MemberDao dao = MemberDao.getInstance();
	if (dao.confirmId(dto.getId()) == MemberDao.MEMBER_EXISTENT){
	
%>

<script>
	alert("아이디가 이미 존재합니다");
	history.back();
</script>

<%
	}else{
		int ri = dao.insertMember(dto);
		if(ri == MemberDao.MEMBER_JOIN_SUCCESS){
			session.setAttribute("id", dto.getId());
%>

<script>
	alert("회원가입을 축하합니다");
	document.location.href="login.jsp";
</script>
<%
		}else{
%>

<script>
	alert("회원가입에 실패했습니다");
	document.location.href="login.jsp";
</script>

<%	
		}
	}
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

</body>
</html>