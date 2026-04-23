<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <title>YamYam - 내 챌린지</title>
  <%@ include file="/WEB-INF/common/header.jsp" %>
  <style>
    .page-wrap { max-width: 860px; margin: 32px auto; padding: 0 16px; }
    .tab-row { display: flex; gap: 8px; margin-bottom: 28px; }
    .tab-btn { padding: 8px 22px; border-radius: 20px; border: 2px solid #3d5220;
               background: #fff; color: #3d5220; font-weight: 700;
               text-decoration: none; font-size: 14px; transition: all .2s; }
    .tab-btn.active, .tab-btn:hover { background: #3d5220; color: #fff; }
    .my-card { background:#fff; border-radius:16px; padding:0; overflow:hidden;
               box-shadow:0 2px 12px rgba(0,0,0,.08); margin-bottom:20px; }
    .my-card-header { background: linear-gradient(135deg,#3d5220,#68723D);
                      color:#fff; padding:16px 20px;
                      display:flex; justify-content:space-between; align-items:center; }
    .my-card-header h6 { margin:0; font-size:16px; font-weight:800; }
    .my-card-header .day-info { font-size:12px; opacity:.85; }
    .streak { font-size:20px; }
    .my-card-body { padding:20px; }
    .today-meal { background:#f0f5e8; border-left:4px solid #3d5220;
                  border-radius:0 8px 8px 0; padding:12px 16px; margin-bottom:16px; }
    .today-meal p { margin:0 0 6px; font-size:13px; font-weight:700; color:#3d5220; }
    .today-meal li { font-size:13px; color:#444; }
    .progress-wrap { margin-top:8px; }
    .progress-wrap .labels { display:flex; justify-content:space-between;
                             font-size:12px; margin-bottom:4px; }
    .progress { height:8px; background:#e8e8e0; border-radius:4px; overflow:hidden; }
    .progress-fill { height:100%; background:#3d5220; border-radius:4px;
                     transition:width .4s; }
    .empty-box { text-align:center; padding:60px 0; color:#bbb; }
    .btn-explore { background:#3d5220; color:#fff; border:none; border-radius:20px;
                   padding:10px 28px; font-weight:700; text-decoration:none; font-size:14px; }
    .btn-explore:hover { background:#2c3d17; color:#fff; }
  </style>
</head>
<body>

<div class="page-wrap">
  <div class="tab-row">
    <a href="${root}/challenge?action=list" class="tab-btn">챌린지 탐색</a>
    <a href="${root}/challenge?action=myList" class="tab-btn active">내 챌린지</a>
    <a href="${root}/challenge?action=createForm" class="tab-btn">챌린지 만들기</a>
  </div>

  <c:choose>
    <c:when test="${empty myList}">
      <div class="empty-box">
        <p style="font-size:48px">🥗</p>
        <p style="font-size:16px; font-weight:700">참여 중인 챌린지가 없습니다</p>
        <a href="${root}/challenge?action=list" class="btn-explore">챌린지 탐색하기</a>
      </div>
    </c:when>
    <c:otherwise>
      <c:forEach var="sub" items="${myList}">
        <div class="my-card">
          <div class="my-card-header">
            <div>
              <h6>${sub.challengeName}</h6>
              <span class="day-info">
                ${sub.currentDay}일차 / ${sub.duration}일 &nbsp;·&nbsp;
                시작: ${sub.startDate}
              </span>
            </div>
            <div class="streak">🔥 ${sub.successDays}</div>
          </div>
          <div class="my-card-body">
            <div class="today-meal">
              <p>📋 오늘 목표 칼로리: <strong>${sub.targetCalories} kcal</strong></p>
              <ul class="list-unstyled mb-0">
                <li>상세 식단은 챌린지 상세 페이지에서 확인하세요.</li>
              </ul>
            </div>
            <div class="progress-wrap">
              <div class="labels">
                <span>전체 진행률</span>
                <span><strong>${sub.progressPercent}%</strong></span>
              </div>
              <div class="progress">
                <div class="progress-fill" style="width:${sub.progressPercent}%"></div>
              </div>
            </div>
          </div>
        </div>
      </c:forEach>
    </c:otherwise>
  </c:choose>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
