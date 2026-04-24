<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>YamYam - 헬스 케어 플랫폼</title>
  <%@ include file="/WEB-INF/common/header.jsp" %>
  <style>
    /* ── 홈은 네비 바로 아래 전체화면 영상 ── */
    .home-wrap {
      position: fixed;
      top: 56px; /* nav 높이 */
      left: 0; right: 0; bottom: 0;
      overflow: hidden;
    }

    /* 영상 배경 */
    .video-background { position: absolute; inset: 0; z-index: 0; }
    .overlay {
      position: absolute; inset: 0;
      background: linear-gradient(to bottom,
        rgba(44,26,0,0.52) 0%,
        rgba(44,26,0,0.25) 50%,
        rgba(44,26,0,0.65) 100%);
      z-index: 1;
    }
    .bg-video {
      width: 100%; height: 100%;
      object-fit: cover;
      position: absolute; inset: 0;
    }

    /* 중앙 히어로 텍스트 */
    .hero-text-center {
      position: absolute;
      top: 50%; left: 50%;
      transform: translate(-50%, -50%);
      text-align: center;
      color: white;
      z-index: 2;
      width: 100%;
      padding: 0 24px;
    }
    .tagline {
      font-size: 11px;
      letter-spacing: 4px;
      text-transform: uppercase;
      color: #D8E4A8;
      margin-bottom: 18px;
      font-weight: 700;
      opacity: 0.8;
      animation: heroFade 0.7s ease 0.2s both;
    }
    .brand-logo {
      font-family: 'Gowun Batang', serif;
      font-size: clamp(64px, 12vw, 140px);
      font-weight: 700;
      color: white;
      letter-spacing: 4px;
      margin: 0 0 14px 0;
      text-shadow: 0 4px 32px rgba(0,0,0,0.35);
      line-height: 1;
      animation: heroFade 0.7s ease 0.4s both;
    }
    .main-copy {
      font-size: clamp(1rem, 2vw, 1.4rem);
      font-weight: 300;
      opacity: 0.9;
      margin-bottom: 36px;
      letter-spacing: 0.5px;
      animation: heroFade 0.7s ease 0.6s both;
    }
    .btn-start {
      padding: 14px 52px;
      border-radius: 100px;
      border: 2px solid rgba(255,255,255,0.8);
      background: rgba(255,255,255,0.12);
      backdrop-filter: blur(12px);
      color: white;
      font-family: 'Nunito', sans-serif;
      font-weight: 700;
      font-size: 1rem;
      letter-spacing: 1px;
      cursor: pointer;
      transition: all 0.3s ease;
      animation: heroFade 0.7s ease 0.8s both;
    }
    .btn-start:hover { background: rgba(255,255,255,0.25); transform: translateY(-2px); }

    @keyframes heroFade {
      from { opacity: 0; transform: translateY(20px); }
      to   { opacity: 1; transform: translateY(0); }
    }

    /* 하단 페이지 이동 Pills */
    .bottom-pills {
      position: absolute;
      bottom: 44px; left: 50%;
      transform: translateX(-50%);
      display: flex;
      gap: 12px;
      z-index: 3;
      flex-wrap: wrap;
      justify-content: center;
    }
    .pill {
      background: rgba(255,255,255,0.12);
      backdrop-filter: blur(14px);
      border: 1px solid rgba(255,255,255,0.2);
      border-radius: 100px;
      padding: 10px 22px;
      color: white;
      font-size: 13px;
      font-weight: 700;
      display: flex;
      align-items: center;
      gap: 8px;
      cursor: pointer;
      text-decoration: none;
      transition: all 0.22s ease;
      font-family: 'Nunito', sans-serif;
    }
    .pill:hover { background: rgba(255,255,255,0.25); transform: translateY(-2px); color: white; }
    .pill i { font-size: 13px; color: #BCCA8C; }

    /* 스플래시 오버레이 */
    #splash-overlay {
      position: fixed; inset: 0;
      background: linear-gradient(145deg, #3d5220 0%, #2c3d17 100%);
      z-index: 9999;
      display: flex;
      flex-direction: column;
      align-items: center;
      justify-content: center;
      gap: 20px;
      opacity: 0;
      pointer-events: none;
      transition: opacity 0.35s ease;
    }
    #splash-overlay.show { opacity: 1; pointer-events: all; }
    .splash-text {
      font-family: 'Gowun Batang', serif;
      font-size: 32px;
      font-weight: 700;
      color: #BCCA8C;
      letter-spacing: 2px;
      opacity: 0;
      transform: translateY(10px);
      transition: all 0.4s ease 0.25s;
    }
    .splash-text.show { opacity: 1; transform: translateY(0); }
    .splash-sub {
      font-size: 13px;
      color: rgba(255,255,255,0.55);
      font-weight: 500;
      letter-spacing: 3px;
      text-transform: uppercase;
      opacity: 0;
      transition: all 0.4s ease 0.4s;
    }
    .splash-sub.show { opacity: 1; }
    .splash-dots { display: flex; gap: 7px; margin-top: 8px; opacity: 0; transition: opacity 0.3s ease 0.55s; }
    .splash-dots.show { opacity: 1; }
    .splash-dots span {
      width: 7px; height: 7px;
      background: rgba(188,202,140,0.55);
      border-radius: 50%;
      animation: sdot 1.2s ease-in-out infinite;
    }
    .splash-dots span:nth-child(2) { animation-delay: 0.18s; }
    .splash-dots span:nth-child(3) { animation-delay: 0.36s; }
    @keyframes sdot {
      0%,60%,100% { transform: scale(0.7); opacity: 0.4; }
      30%          { transform: scale(1.2); opacity: 1; }
    }
  </style>
</head>
<body>

<%-- 스플래시 오버레이 --%>
<div id="splash-overlay">
  <div class="splash-text" id="splashText">YamYam</div>
  <div class="splash-sub" id="splashSub">건강한 식단 관리</div>
  <div class="splash-dots" id="splashDots">
    <span></span><span></span><span></span>
  </div>
</div>

<div class="home-wrap">
  <%-- 영상 배경 --%>
  <div class="video-background">
    <div class="overlay"></div>
    <video autoplay muted loop playsinline class="bg-video">
      <%-- video 파일을 src/main/webapp/resources/video/background.mp4 에 넣으세요 --%>
      <source src="${root}/resources/video/background.mp4" type="video/mp4">
    </video>
  </div>

  <%-- 중앙 히어로 --%>
  <div class="hero-text-center">
    <p class="tagline">건강한 식단 관리</p>
    <h1 class="brand-logo">YamYam</h1>
    <p class="main-copy">당신의 식단을 관리해드릴게요</p>
    <button class="btn-start" id="btnStart">시작하기</button>
  </div>

  <%-- 하단 페이지 이동 Pills --%>
  <div class="bottom-pills">
    <a href="${root}/diet" class="pill">
      <i class="fa-solid fa-bowl-food"></i> 식단 기록
    </a>
    <a href="${root}/challenge?action=list" class="pill">
      <i class="fa-solid fa-arrow-up-right-dots"></i> 챌린지
    </a>
    <a href="${root}/chat" class="pill">
      <i class="fa-solid fa-robot"></i> AI 영양사
    </a>
    <a href="${root}/member?action=loginForm" class="pill">
      <i class="fa-regular fa-user"></i> 마이페이지
    </a>
  </div>
</div>

<script>
document.getElementById('btnStart').addEventListener('click', function() {
  var overlay = document.getElementById('splash-overlay');
  var txt     = document.getElementById('splashText');
  var sub     = document.getElementById('splashSub');
  var dots    = document.getElementById('splashDots');

  overlay.classList.add('show');
  requestAnimationFrame(function() {
    setTimeout(function() { txt.classList.add('show'); },  180);
    setTimeout(function() { sub.classList.add('show'); },  320);
    setTimeout(function() { dots.classList.add('show'); }, 480);
  });

  setTimeout(function() {
    window.location.href = '${root}/challenge?action=list';
  }, 1600);
});
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
