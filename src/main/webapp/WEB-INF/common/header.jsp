<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800;900&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" crossorigin="anonymous"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
  body { font-family: 'Nunito', sans-serif; background: #f5f5f0; }
  .app-nav { background: #3d5220; padding: 12px 24px; display: flex; align-items: center; gap: 24px; }
  .app-nav a { color: #BCCA8C; text-decoration: none; font-weight: 600; font-size: 14px; }
  .app-nav a:hover, .app-nav a.active { color: #fff; }
  .app-nav .brand { font-size: 18px; font-weight: 800; color: #fff; margin-right: 16px; }
  .alert-msg { margin: 0; border-radius: 0; }
</style>

<nav class="app-nav">
  <span class="brand">YamYam</span>
  <a href="${root}/challenge?action=list" class="${param.page == 'challenge' ? 'active' : ''}">
    <i class="fa-solid fa-arrow-up-right-dots me-1"></i>챌린지
  </a>
  <%-- 다른 팀원 기능 링크는 여기에 추가 --%>
</nav>

<c:if test="${not empty sessionScope.alertMsg}">
  <div class="alert alert-warning alert-msg text-center">
    ${sessionScope.alertMsg}
    <c:remove var="alertMsg" scope="session"/>
  </div>
</c:if>
