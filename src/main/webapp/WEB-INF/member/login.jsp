<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>로그인 - Yamyam</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/user.css">
</head>
<body>
    <div class="app-container">
        <!-- 사이드바 (기능 유지를 위해 간단히 포함) -->
        <nav class="icon-sidebar">
            <a href="${pageContext.request.contextPath}/main" class="sidebar-icon"><i class="fa-solid fa-house"></i></a>
            <a href="${pageContext.request.contextPath}/member?action=loginForm" class="sidebar-icon active"><i class="fa-regular fa-user"></i></a>
        </nav>

        <main class="main-content">
            <div class="container user-auth-container">
                <div class="auth-section">
                    <div class="card shadow-sm border-0 rounded-4 p-4 mx-auto" style="max-width: 450px;">
                        <h3 class="text-center mb-4" style="font-weight: 800; color: var(--text-main);">로그인</h3>
                        <form action="${pageContext.request.contextPath}/member" method="post">
                            <input type="hidden" name="action" value="login">
                            <div class="mb-3">
                                <label for="id" class="form-label fw-bold">아이디</label>
                                <input type="text" class="form-control" id="id" name="id" value="${cookie.saveId.value}" required>
                            </div>
                            <div class="mb-4">
                                <label for="password" class="form-label fw-bold">비밀번호</label>
                                <input type="password" class="form-control" id="password" name="password" required>
                            </div>
                            <div class="mb-3 form-check">
                                <input type="checkbox" class="form-check-input" id="saveId" name="saveId" ${cookie.saveId != null ? 'checked' : ''}>
                                <label class="form-check-label" for="saveId">아이디 저장</label>
                            </div>
                            <button type="submit" class="btn btn-primary w-100 py-3 mb-3 fw-bold">로그인하기</button>
                            <a href="${pageContext.request.contextPath}/member?action=joinForm" class="btn btn-link w-100 text-decoration-none text-center">계정이 없으신가요? 가입하기</a>
                        </form>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <c:if test="${not empty sessionScope.alertMsg}">
        <script>alert("${sessionScope.alertMsg}");</script>
        <c:remove var="alertMsg" scope="session"/>
    </c:if>
</body>
</html>
