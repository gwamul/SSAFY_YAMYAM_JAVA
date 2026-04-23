<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core"%>
<%@ taglib prefix="fn" uri="jakarta.tags.functions"%>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>YamYam - 헬스 챌린지</title>
<%@ include file="/WEB-INF/common/header.jsp"%>
<style>
.page-wrap {
	max-width: 960px;
	margin: 32px auto;
	padding: 0 16px;
}

.page-title {
	font-size: 28px;
	font-weight: 800;
	color: #3d5220;
	margin-bottom: 4px;
}

.page-sub {
	color: #888;
	font-size: 14px;
	margin-bottom: 24px;
}

.filter-bar {
	background: #fff;
	border-radius: 12px;
	padding: 16px 20px;
	display: flex;
	align-items: center;
	gap: 12px;
	margin-bottom: 28px;
	box-shadow: 0 2px 8px rgba(0, 0, 0, 0.06);
}

.tab-row {
	display: flex;
	gap: 8px;
	margin-bottom: 28px;
}

.tab-btn {
	padding: 8px 22px;
	border-radius: 20px;
	border: 2px solid #3d5220;
	background: #fff;
	color: #3d5220;
	font-weight: 700;
	cursor: pointer;
	text-decoration: none;
	font-size: 14px;
	transition: all .2s;
}

.tab-btn.active, .tab-btn:hover {
	background: #3d5220;
	color: #fff;
}

.diff-section {
	margin-bottom: 40px;
}

.diff-banner {
	border-left: 6px solid;
	padding: 10px 16px;
	border-radius: 0 8px 8px 0;
	background: #f9f9f5;
	margin-bottom: 16px;
}

.diff-banner h4 {
	margin: 0 0 2px;
	font-size: 17px;
	font-weight: 800;
}

.diff-banner p {
	margin: 0;
	font-size: 12px;
	color: #888;
}

.challenge-item {
	display: flex;
	align-items: center;
	justify-content: space-between;
	background: #fff;
	border: 1px solid #e8e8e0;
	border-radius: 12px;
	padding: 16px 20px;
	margin-bottom: 10px;
	box-shadow: 0 1px 4px rgba(0, 0, 0, 0.05);
	transition: box-shadow .2s;
}

.challenge-item:hover {
	box-shadow: 0 4px 14px rgba(0, 0, 0, 0.1);
}

.duration-box {
	text-align: center;
	min-width: 52px;
}

.duration-box .days {
	font-size: 26px;
	font-weight: 900;
	color: #3d5220;
	display: block;
}

.duration-box .unit {
	font-size: 11px;
	color: #aaa;
	font-weight: 700;
	letter-spacing: 1px;
}

.challenge-info {
	margin-left: 20px;
	flex: 1;
}

.challenge-info h6 {
	margin: 0 0 4px;
	font-size: 15px;
	font-weight: 700;
}

.meta-tag {
	font-size: 12px;
	color: #666;
	margin-right: 12px;
}

.btn-start {
	background: #3d5220;
	color: #fff;
	border: none;
	border-radius: 20px;
	padding: 8px 22px;
	font-weight: 700;
	font-size: 13px;
	cursor: pointer;
	text-decoration: none;
	transition: background .2s;
}

.btn-start:hover {
	background: #2c3d17;
	color: #fff;
}

.empty-msg {
	text-align: center;
	padding: 60px;
	color: #bbb;
	font-size: 15px;
}
</style>
</head>
<body>

	<div class="page-wrap">
		<h1 class="page-title">헬스 챌린지</h1>
		<p class="page-sub">함께 목표를 달성하는 건강한 습관</p>

		<%-- 탭 --%>
		<div class="tab-row">
			<a href="${root}/challenge?action=list" class="tab-btn active">챌린지
				탐색</a> <a href="${root}/challenge?action=myList" class="tab-btn">내
				챌린지</a> <a href="${root}/challenge?action=createForm" class="tab-btn">챌린지
				만들기</a>
		</div>

		<%-- 필터 --%>
		<form method="get" action="${root}/challenge" class="filter-bar">
			<input type="hidden" name="action" value="list"> <label
				class="fw-bold small text-muted me-1">🔎 필터</label> <select
				name="difficulty" class="form-select form-select-sm"
				style="width: 140px" onchange="this.form.submit()">
				<option value="" ${empty param.difficulty ? 'selected' : ''}>전체
					난이도</option>
				<option value="easy"
					${param.difficulty == 'easy'   ? 'selected' : ''}>초급</option>
				<option value="medium"
					${param.difficulty == 'medium' ? 'selected' : ''}>중급</option>
				<option value="hard"
					${param.difficulty == 'hard'   ? 'selected' : ''}>상급</option>
			</select> <select name="duration" class="form-select form-select-sm"
				style="width: 120px" onchange="this.form.submit()">
				<option value="" ${empty param.duration ? 'selected' : ''}>전체
					기간</option>
				<option value="7" ${param.duration == '7'  ? 'selected' : ''}>7일</option>
				<option value="14" ${param.duration == '14' ? 'selected' : ''}>14일</option>
				<option value="30" ${param.duration == '30' ? 'selected' : ''}>30일</option>
			</select>
		</form>

		<%-- 난이도별 그룹 렌더링 (Controller에서 List<DifficultyMeta>로 전달) --%>
		<c:forEach var="meta" items="${diffMetas}">
			<c:set var="hasAny" value="false" />
			<c:forEach var="ch" items="${challenges}">
				<c:if test="${ch.difficulty == meta.id}">
					<c:set var="hasAny" value="true" />
				</c:if>
			</c:forEach>

			<c:if test="${hasAny}">
				<div class="diff-section">
					<div class="diff-banner" style="border-color:${meta.color}">
						<h4 style="color:${meta.color}">${meta.label}</h4>
						<p>${meta.desc}</p>
					</div>
					<c:forEach var="ch" items="${challenges}">
						<c:if test="${ch.difficulty == meta.id}">
							<div class="challenge-item">
								<div class="duration-box">
									<span class="days">${ch.duration}</span> <span class="unit">DAYS</span>
								</div>
								<div class="challenge-info">
									<h6>${ch.name}</h6>
									<span class="meta-tag">⚡ ${ch.targetCalories}kcal</span> <span
										class="meta-tag">📋 ${ch.duration}일 플랜</span>
								</div>
								<div style="display: flex; gap: 8px">
									<a href="${root}/challenge?action=detail&id=${ch.id}"
										class="btn-start" style="background: #68723D">상세보기</a>
									<form method="post" action="${root}/challenge"
										style="margin: 0">
										<input type="hidden" name="action" value="subscribe">
										<input type="hidden" name="id" value="${ch.id}">
										<button type="submit" class="btn-start">도전 시작</button>
									</form>
								</div>
							</div>
						</c:if>
					</c:forEach>
				</div>
			</c:if>
		</c:forEach>
		

		<c:if test="${empty challenges}">
			<div class="empty-msg">
				해당 조건의 챌린지가 없습니다.<br> <a
					href="${root}/challenge?action=createForm"
					class="btn-start mt-3 d-inline-block">챌린지 만들기</a>
			</div>
		</c:if>
	</div>

	<script
		src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
