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
	
	<h2>회원가입</h2>
	<form method="post" action="member">
		<input type="hidden" name="action" value="join" />
		<div>
			<div>
				<span>아이디</span>
				<input type="text" name="id" />
			</div>
			<div>
				<span>패스워드</span>
				<input type="password" name="password" />
			</div>
			<div>
				<span>이름</span>
				<input type="text" name="name" />
			</div>
			<div>
				<button>가입</button>
			</div>			
		</div>
	</form>
</body>
</html>







