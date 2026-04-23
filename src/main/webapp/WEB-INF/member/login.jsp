<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<%@ include file="/WEB-INF/header.jsp" %>
	
	<h2>로그인</h2>
	<form method="post" action="member">
		<input type="hidden" name="action" value="login" />
		<div>
			<div>
				<span>아이디</span>
				<input type="text" name="id" value="${cookie.saveId.value}" />
			</div>
			<div>
				<span>패스워드</span>
				<input type="password" name="password" />
			</div>
			<div>
				<span>아이디 저장</span>
				<input type="checkbox" name="saveId" 
					   ${cookie.saveId != null ? "checked" : ""} 
				/>
			</div>
			<div>
				<button>로그인</button>
			</div>			
		</div>
	</form>
	
	<script>
		if ("${sessionScope.alertMsg}") {
			alert("${sessionScope.alertMsg}");
		}
	</script>
	
	<c:remove var="alertMsg" />
</body>
</html>



















