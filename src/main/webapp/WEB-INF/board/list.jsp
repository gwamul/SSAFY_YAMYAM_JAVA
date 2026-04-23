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
	<table>
		<tr>
			<th>번호</th>
			<th>제목</th>
			<th>글쓴이</th>
			<th>조회수</th>
		</tr>
		<c:forEach var="board" items="${boards}">
		<tr>
			<td>${board.no}</td>
			<td><a href="${root}/board?action=detail&no=${board.no}">${board.title}</a></td>
			<td>${board.writer}</td>
			<td>${board.viewCnt}</td>
		</tr>
		</c:forEach>	
	</table>
	<a href="${root}/board?action=writeForm">글쓰기</a>
</body>
</html>





























