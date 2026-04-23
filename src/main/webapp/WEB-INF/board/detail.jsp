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
	
	<h2>게시판</h2>
	<div>
		<div>
			<label>번호 : </label>
			<span>${board.no}</span>
		</div>
		<div>
			<label>제목 : </label>
			<span>${board.title}</span>
		</div>
		<div>
			<label>글쓴이 : </label>
			<span>${board.writer}</span>
		</div>
		<div>
			<label>내용 : </label>
			<span>${board.content}</span>
		</div>
		<div>
			<a href="${root}/board?action=delete&no=${board.no}">삭제</a>
			<a href="${root}/board">목록</a>
		</div>
	</div>
</body>
</html>
















