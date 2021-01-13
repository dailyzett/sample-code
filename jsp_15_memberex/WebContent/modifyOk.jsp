<%@ page import="com.javaEdu.ex.*"%>

<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	request.setCharacterEncoding("UTF-8");
%>

<jsp:useBean id="dto" class="com.javaEdu.ex.MemberDto" scope="page"></jsp:useBean>
<jsp:setProperty property="*" name="dto" />

<%
	String id = (String) session.getAttribute("id");
	dto.setId(id);
	
	MemberDao dao = MemberDao.getInstance();
	int ri = dao.updateMember(dto);
	
	if(ri == 1){
		
%>

<script>	
	alert('정보수정 완료');
	document.location.href="main.jsp";
</script>

<%
	}else{
%>

<script>
	alert('정보수정 실패');
	history.go(-1);
</script>


<%
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