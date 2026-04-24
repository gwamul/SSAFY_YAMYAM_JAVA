<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<link href="https://fonts.googleapis.com/css2?family=Nunito:wght@300;400;600;700;800;900&family=Gowun+Batang:wght@400;700&display=swap" rel="stylesheet">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css" crossorigin="anonymous"/>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
<style>
  body { font-family: 'Nunito', sans-serif; background: #f5f5f0; margin: 0; }

  /* ── 상단 네비게이션 ── */
  .app-nav {
    background: #3d5220;
    padding: 0 24px;
    display: flex;
    align-items: center;
    height: 56px;
    gap: 4px;
    position: sticky;
    top: 0;
    z-index: 1000;
    box-shadow: 0 2px 12px rgba(0,0,0,0.15);
  }
  .app-nav .brand {
    font-family: 'Gowun Batang', serif;
    font-size: 20px;
    font-weight: 700;
    color: #fff;
    text-decoration: none;
    margin-right: 20px;
    letter-spacing: 1px;
  }
  .app-nav .brand:hover { color: #BCCA8C; }
  .nav-divider { width: 1px; height: 20px; background: rgba(255,255,255,0.2); margin: 0 8px; }
  .nav-link-item {
    color: #BCCA8C;
    text-decoration: none;
    font-weight: 600;
    font-size: 13px;
    padding: 6px 14px;
    border-radius: 20px;
    display: flex;
    align-items: center;
    gap: 6px;
    transition: all 0.2s;
  }
  .nav-link-item:hover { background: rgba(255,255,255,0.12); color: #fff; }
  .nav-link-item.active { background: rgba(255,255,255,0.18); color: #fff; }
  .nav-spacer { flex: 1; }
  .nav-user {
    color: #BCCA8C;
    font-size: 13px;
    font-weight: 600;
    display: flex;
    align-items: center;
    gap: 6px;
  }

  /* ── 세션 알림 메시지 ── */
  .alert-msg { margin: 0; border-radius: 0; font-size: 14px; text-align: center; }
</style>

<nav class="app-nav">
  <%-- 브랜드 로고 → 홈 --%>
  <a href="${root}/" class="brand">YamYam</a>
  <div class="nav-divider"></div>

  <%-- 식단 관리 (팀원 구현 시 href 수정) --%>
  <a href="${root}/diet" class="nav-link-item ${param.page == 'diet' ? 'active' : ''}">
    <i class="fa-solid fa-bowl-food"></i> 식단 관리
  </a>

  <%-- 챌린지 --%>
  <a href="${root}/challenge?action=list" class="nav-link-item ${param.page == 'challenge' ? 'active' : ''}">
    <i class="fa-solid fa-arrow-up-right-dots"></i> 챌린지
  </a>

  <%-- AI 챗봇 (팀원 구현 시 href 수정) --%>
  <a href="${root}/chat" class="nav-link-item ${param.page == 'chat' ? 'active' : ''}">
    <i class="fa-solid fa-robot"></i> AI 영양사
  </a>

  <div class="nav-spacer"></div>

  <%-- 로그인 상태에 따라 표시 --%>
  <c:choose>
    <c:when test="${not empty sessionScope.loginUser}">
      <a href="${root}/member?action=mypage" class="nav-link-item">
        <i class="fa-regular fa-user"></i> ${sessionScope.loginUser.name}님 (마이페이지)
      </a>
      <div class="nav-divider"></div>
      <a href="${root}/member?action=logout" class="nav-link-item">
        <i class="fa-solid fa-right-from-bracket"></i> 로그아웃
      </a>
    </c:when>
    <c:otherwise>
      <a href="${root}/member?action=loginForm" class="nav-link-item ${param.page == 'login' ? 'active' : ''}">
        <i class="fa-solid fa-right-to-bracket"></i> 로그인
      </a>
      <a href="${root}/member?action=joinForm" class="nav-link-item">
        <i class="fa-solid fa-user-plus"></i> 회원가입
      </a>
    </c:otherwise>
  </c:choose>
</nav>

<c:if test="${not empty sessionScope.alertMsg}">
  <div class="alert alert-warning alert-msg">
    ${sessionScope.alertMsg}
    <c:remove var="alertMsg" scope="session"/>
  </div>
</c:if>
