<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>YamYam - 챌린지 상세</title>
  <%@ include file="/WEB-INF/common/header.jsp" %>
  <style>
    .page-wrap { max-width: 860px; margin: 32px auto; padding: 0 16px; }
    .back-btn  { color: #3d5220; text-decoration: none; font-weight: 700; font-size: 14px;
                 display:inline-flex; align-items:center; gap:6px; margin-bottom:20px; }
    .hero-box  { background: linear-gradient(135deg,#3d5220,#68723D); color:#fff;
                 border-radius:16px; padding:28px 32px; margin-bottom:24px; }
    .hero-box h2 { font-size:24px; font-weight:900; margin:0 0 8px; }
    .diff-badge { display:inline-block; padding:4px 14px; border-radius:20px; font-size:12px;
                  font-weight:700; background:rgba(255,255,255,.2); margin-bottom:12px; }
    .meta-chips { display:flex; gap:12px; flex-wrap:wrap; margin-top:12px; }
    .chip { background:rgba(255,255,255,.15); border-radius:20px; padding:4px 14px;
            font-size:13px; font-weight:600; }
    .card-section { background:#fff; border-radius:14px; padding:20px 24px;
                    box-shadow:0 2px 10px rgba(0,0,0,.07); margin-bottom:20px; }
    .day-header { font-weight:800; color:#3d5220; border-bottom:2px solid #e8e8e0;
                  padding-bottom:8px; margin-bottom:12px; font-size:14px; }
    .meal-col-label { font-size:12px; font-weight:700; color:#888; margin-bottom:6px; }
    .food-chip { background:#f0f5e8; border-radius:8px; padding:4px 10px;
                 font-size:12px; margin:2px; display:inline-block; }
    .food-chip .kcal { color:#3d5220; font-weight:700; margin-left:4px; }
    .btn-subscribe { background:#3d5220; color:#fff; border:none; border-radius:10px;
                     padding:12px 36px; font-weight:800; font-size:15px; cursor:pointer;
                     width:100%; margin-top:8px; }
    .btn-subscribe:hover { background:#2c3d17; }
    .btn-delete { background:#dc3545; color:#fff; border:none; border-radius:10px;
                  padding:10px 24px; font-size:13px; font-weight:700; cursor:pointer; }
  </style>
</head>
<body>

<div class="page-wrap">
  <a href="${root}/challenge?action=list" class="back-btn">
    <i class="fa-solid fa-arrow-left"></i> 목록으로
  </a>

  <div class="hero-box">
    <div class="diff-badge">
      <c:choose>
        <c:when test="${challenge.difficulty == 'easy'}">🌱 초급</c:when>
        <c:when test="${challenge.difficulty == 'medium'}">🔥 중급</c:when>
        <c:otherwise>🏆 상급</c:otherwise>
      </c:choose>
    </div>
    <h2>${challenge.name}</h2>
    <div class="meta-chips">
      <span class="chip">📅 ${challenge.duration}일 플랜</span>
      <span class="chip">⚡ 일일 목표 ${challenge.targetCalories}kcal</span>
    </div>
  </div>

  <%-- 구독 버튼 --%>
  <div class="card-section text-center">
    <form method="post" action="${root}/challenge">
      <input type="hidden" name="action" value="subscribe">
      <input type="hidden" name="id" value="${challenge.id}">
      <button type="submit" class="btn-subscribe">🚀 이 챌린지 도전하기</button>
    </form>
    <a href="${root}/challenge?action=delete&id=${challenge.id}"
       class="btn-delete mt-3 d-inline-block"
       onclick="return confirm('정말 삭제하시겠습니까?')">삭제</a>
  </div>

  <%-- 일별 식단 --%>
  <c:forEach var="plan" items="${challenge.mealPlans}">
    <div class="card-section">
      <div class="day-header">
        🗓️ ${plan.day}일차
        <span class="fw-normal text-muted ms-2" style="font-size:13px">
          일일 합계: <strong><fmt:formatNumber value="${plan.totalKcal}" pattern="#,##0"/></strong> kcal
        </span>
      </div>
      <div class="row g-3">
        <%-- 아침 --%>
        <div class="col-md-4">
          <div class="meal-col-label">아침</div>
          <c:choose>
            <c:when test="${empty plan.breakfast}">
              <span class="text-muted small">-</span>
            </c:when>
            <c:otherwise>
              <c:forEach var="item" items="${plan.breakfast}">
                <span class="food-chip">${item.name}<span class="kcal">${item.kcal}kcal</span></span>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
        <%-- 점심 --%>
        <div class="col-md-4">
          <div class="meal-col-label">점심</div>
          <c:choose>
            <c:when test="${empty plan.lunch}">
              <span class="text-muted small">-</span>
            </c:when>
            <c:otherwise>
              <c:forEach var="item" items="${plan.lunch}">
                <span class="food-chip">${item.name}<span class="kcal">${item.kcal}kcal</span></span>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
        <%-- 저녁 --%>
        <div class="col-md-4">
          <div class="meal-col-label">저녁</div>
          <c:choose>
            <c:when test="${empty plan.dinner}">
              <span class="text-muted small">-</span>
            </c:when>
            <c:otherwise>
              <c:forEach var="item" items="${plan.dinner}">
                <span class="food-chip">${item.name}<span class="kcal">${item.kcal}kcal</span></span>
              </c:forEach>
            </c:otherwise>
          </c:choose>
        </div>
      </div>
    </div>
  </c:forEach>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
