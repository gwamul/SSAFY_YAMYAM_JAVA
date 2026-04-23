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
	<form method="post" action="${root}/board">
		<input type="hidden" name="action" value="write" />
		<div>
			<div>
				<label>제목</label>
				<input type="text" name="title" />
			</div>
			<div>
				<label>작성자</label>
				<input type="text" name="writer" />
			</div>
			<div>
				<label>내용</label>
				<textarea name="content" rows="10" cols="70"></textarea>
			</div>
			<div>
				<button>등록</button>
				<button type="button" onclick="location.href='${root}/board'">목록</button>
			</div>
		</div>
	</form>
</body>
</html>





