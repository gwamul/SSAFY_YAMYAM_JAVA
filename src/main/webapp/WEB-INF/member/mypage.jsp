<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>${targetUser.name}님의 프로필 - Yamyam</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/style.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/css/user/user.css">
    <style>
        .tab-content.d-none { display: none !important; }
        .tab-item.active { background: white; color: var(--primary-color); border-radius: 15px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
    </style>
</head>
<body>
    <div class="app-container">
        <!-- 사이드바 -->
        <nav class="icon-sidebar">
            <a href="${pageContext.request.contextPath}/main" class="sidebar-icon"><i class="fa-solid fa-house"></i></a>
            <a href="${pageContext.request.contextPath}/member?action=mypage" class="sidebar-icon active"><i class="fa-regular fa-user"></i></a>
        </nav>

        <main class="main-content">
            <div class="container user-auth-container">
                <header class="content-header mb-5">
                    <div id="pageTitle">
                        <span class="header-eyebrow" style="font-size: 0.8rem; font-weight: 700; color: var(--text-muted); text-transform: uppercase; letter-spacing: 0.1em;">
                            ${targetUser.userId eq loginUser.userId ? 'My Profile' : 'User Profile'}
                        </span>
                        <h1 class="header-title" style="font-size: 2.5rem; font-weight: 800;">${targetUser.name}님의 프로필</h1>
                    </div>
                    <p id="pageSubtitle" class="text-muted mt-2">@${targetUser.userId} 사용자의 상세 정보와 건강 데이터를 확인하세요.</p>
                </header>

                <!-- 마이페이지 탭 메뉴 -->
                <div class="user-tab-nav mb-4 d-inline-flex bg-light p-1 rounded-4">
                    <button class="tab-item active border-0 px-4 py-2 fw-bold" onclick="switchUserTab(event, 'profile')">내 프로필</button>
                    <button class="tab-item border-0 px-4 py-2 fw-bold text-muted" onclick="switchUserTab(event, 'challenges')">구독 챌린지</button>
                </div>

                <div class="auth-section">
                    <!-- 1. 프로필 탭 내용 -->
                    <div id="profileTab" class="tab-content">
                        <div class="profile-main-card ${targetUser.userId ne loginUser.userId ? 'others-profile' : ''} p-5 bg-white rounded-5 shadow-sm border">
                            <c:if test="${targetUser.userId ne loginUser.userId}">
                                <div class="others-badge mb-3 d-inline-block bg-primary text-white px-3 py-1 rounded-pill small fw-bold">타인 프로필</div>
                                <a href="${pageContext.request.contextPath}/member?action=mypage" class="btn-back-to-my d-block mb-4 text-decoration-none text-primary fw-bold">← 내 프로필로 돌아가기</a>
                            </c:if>

                            <div class="profile-header-group d-flex align-items-center gap-4 mb-5">
                                <div class="profile-avatar d-flex align-items-center justify-content-center bg-primary text-white rounded-4" style="width: 100px; height: 100px; font-size: 2.5rem; font-weight: 800;">
                                    ${targetUser.name.substring(0, 1)}
                                </div>
                                <div class="profile-title-info">
                                    <h2 class="mb-1" style="font-weight: 800;">${targetUser.name}</h2>
                                    <span class="user-id-tag text-muted">@${targetUser.userId}</span>
                                </div>
                                <div class="ms-auto d-flex gap-2">
                                    <c:choose>
                                        <c:when test="${targetUser.userId eq loginUser.userId}">
                                            <a href="${pageContext.request.contextPath}/member?action=editForm" class="btn btn-outline-primary rounded-pill px-4 fw-bold">정보 수정</a>
                                            <a href="${pageContext.request.contextPath}/member?action=logout" class="btn btn-outline-secondary rounded-pill px-4 fw-bold">로그아웃</a>
                                        </c:when>
                                        <c:otherwise>
                                            <!-- 팔로우 버튼 로직 -->
                                            <c:set var="isFollowing" value="false" />
                                            <c:forEach var="f" items="${loginUser.followers}">
                                                <c:if test="${f eq targetUser.userId}"><c:set var="isFollowing" value="true" /></c:if>
                                            </c:forEach>
                                            <c:choose>
                                                <c:when test="${isFollowing}">
                                                    <a href="${pageContext.request.contextPath}/member?action=removeFollower&targetId=${targetUser.userId}" class="btn btn-outline-danger rounded-pill px-4 fw-bold">언팔로우</a>
                                                </c:when>
                                                <c:otherwise>
                                                    <a href="${pageContext.request.contextPath}/member?action=addFollower&targetId=${targetUser.userId}" class="btn btn-primary rounded-pill px-4 fw-bold">팔로우</a>
                                                </c:otherwise>
                                            </c:choose>
                                        </c:otherwise>
                                    </c:choose>
                                </div>
                            </div>

                            <!-- 건강 지표 그리드 -->
                            <div class="profile-stats-grid row g-3 mb-5">
                                <div class="col-md-2 col-6">
                                    <div class="stat-box bg-light rounded-4 text-center border">
                                        <span class="stat-label d-block text-muted small fw-bold mb-2">성별</span>
                                        <span class="stat-value h4 fw-800">${targetUser.gender eq 'male' ? '남성' : '여성'}</span>
                                    </div>
                                </div>
                                <div class="col-md-3 col-6">
                                    <div class="stat-box bg-light rounded-4 text-center border">
                                        <span class="stat-label d-block text-muted small fw-bold mb-2">생년월일</span>
                                        <span class="stat-value h5 fw-800">${targetUser.birthDate}</span>
                                    </div>
                                </div>
                                <div class="col-md-2 col-4">
                                    <div class="stat-box bg-light rounded-4 text-center border">
                                        <span class="stat-label d-block text-muted small fw-bold mb-2">키</span>
                                        <span class="stat-value h4 fw-800">${targetUser.height}<small class="small">cm</small></span>
                                    </div>
                                </div>
                                <div class="col-md-2 col-4">
                                    <div class="stat-box bg-light rounded-4 text-center border">
                                        <span class="stat-label d-block text-muted small fw-bold mb-2">몸무게</span>
                                        <span class="stat-value h4 fw-800">${targetUser.weight}<small class="small">kg</small></span>
                                    </div>
                                </div>
                                <div class="col-md-3 col-4">
                                    <div class="stat-box bg-light rounded-4 text-center border">
                                        <span class="stat-label d-block text-muted small fw-bold mb-2">BMI 지수</span>
                                        <fmt:formatNumber var="bmi" value="${targetUser.weight / ((targetUser.height/100) * (targetUser.height/100))}" pattern="#.0" />
                                        <span class="stat-value h4 fw-800 text-primary">${bmi}</span>
                                    </div>
                                </div>
                            </div>

                            <!-- 기타 정보 -->
                            <div class="profile-footer-info pt-4 border-top">
                                <div class="row align-items-center">
                                    <div class="col-md-6 border-end">
                                        <h6 class="fw-bold text-muted mb-2">보유 질환</h6>
                                        <p class="h5 mb-0 fw-bold">${targetUser.disease}</p>
                                    </div>
                                    <div class="col-md-6 ps-md-5">
                                        <h6 class="fw-bold text-muted mb-3">팔로워 (${targetUser.followers.size()})</h6>
                                        <div class="d-flex flex-wrap gap-2">
                                            <c:forEach var="fId" items="${targetUser.followers}">
                                                <a href="${pageContext.request.contextPath}/member?action=mypage&id=${fId}" class="badge rounded-pill bg-white text-dark border p-2 px-3 text-decoration-none shadow-sm">@${fId}</a>
                                            </c:forEach>
                                            <c:if test="${empty targetUser.followers}">
                                                <p class="text-muted small mb-0">팔로워가 아직 없습니다.</p>
                                            </c:if>
                                        </div>
                                    </div>
                                </div>
                            </div>

                            <c:if test="${targetUser.userId eq loginUser.userId}">
                                <div class="mt-5 pt-4 border-top d-flex justify-content-between align-items-center opacity-75">
                                    <div>
                                        <h6 class="text-muted mb-1 fw-bold">계정 관리</h6>
                                        <p class="text-muted small mb-0">탈퇴 시 모든 정보가 즉시 삭제됩니다.</p>
                                    </div>
                                    <a href="${pageContext.request.contextPath}/member?action=delete" class="btn btn-link text-danger text-decoration-none fw-bold" onclick="return confirm('정말로 계정을 삭제하시겠습니까?')">회원 탈퇴</a>
                                </div>
                            </c:if>
                        </div>
                    </div>

                    <!-- 2. 챌린지 탭 내용 (구 프로젝트 느낌만 유지) -->
                    <div id="challengesTab" class="tab-content d-none">
                        <div class="bg-white p-5 rounded-5 shadow-sm border text-center">
                            <div class="mb-4">
                                <i class="fa-solid fa-fire-flame-curved" style="font-size: 4rem; color: var(--primary-color); opacity: 0.3;"></i>
                            </div>
                            <h3 class="fw-bold mb-3">참여 중인 챌린지</h3>
                            <p class="text-muted">현재 도전 중인 건강 챌린지 정보가 마이그레이션 대기 중입니다.</p>
                            <div class="mt-4 p-4 bg-light rounded-4 d-inline-block">
                                <span class="fw-bold text-primary">Challenge Module</span> <span class="text-muted ms-2">Coming Soon</span>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
    </div>

    <script>
        function switchUserTab(event, tabName) {
            // 모든 탭 내용 숨기기
            document.querySelectorAll('.tab-content').forEach(el => el.classList.add('d-none'));
            // 모든 탭 버튼 비활성화
            document.querySelectorAll('.tab-item').forEach(el => {
                el.classList.remove('active');
                el.classList.add('text-muted');
            });
            
            // 선택한 탭 보이기
            document.getElementById(tabName + 'Tab').classList.remove('d-none');
            // 클릭한 버튼 활성화
            event.currentTarget.classList.add('active');
            event.currentTarget.classList.remove('text-muted');
        }
    </script>
</body>
</html>
