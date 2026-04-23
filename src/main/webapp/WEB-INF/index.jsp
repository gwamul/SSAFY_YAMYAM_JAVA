<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp" %>
<!-- 	<a href="/prj05">게시판</a> -->
	<a href="${pageContext.request.contextPath}/board">게시판</a>
	<a href="${pageContext.request.contextPath}/member?action=joinForm">회원가입</a>

</body>
</html>














