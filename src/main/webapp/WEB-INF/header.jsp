<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
	<div>
		<a href="${root}">홈</a>
		<a href="${root}/board">게시판</a>
		<c:choose>
		<c:when test="${empty sessionScope.loginUser}">
			<a href="member?action=loginForm">로그인</a>
		</c:when>
		<c:otherwise>
			<a href="member?action=logout">로그아웃</a>
		</c:otherwise>
		</c:choose>
		
		<hr>	
	</div>
	