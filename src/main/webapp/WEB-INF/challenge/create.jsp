<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>YamYam - 챌린지 만들기</title>
  <%@ include file="/WEB-INF/common/header.jsp" %>
  <style>
    .page-wrap { max-width: 900px; margin: 32px auto; padding: 0 16px; }
    .tab-row { display: flex; gap: 8px; margin-bottom: 28px; }
    .tab-btn { padding: 8px 22px; border-radius: 20px; border: 2px solid #3d5220;
               background: #fff; color: #3d5220; font-weight: 700; cursor: pointer;
               text-decoration: none; font-size: 14px; transition: all .2s; }
    .tab-btn.active, .tab-btn:hover { background: #3d5220; color: #fff; }
    .card-section { background:#fff; border-radius:14px; padding:24px;
                    box-shadow:0 2px 10px rgba(0,0,0,.07); margin-bottom:24px; }
    .stats-dashboard { display:grid; grid-template-columns:repeat(3,1fr); gap:12px; margin-bottom:24px; }
    .stat-box { background:#3d5220; color:#fff; border-radius:12px; padding:16px; text-align:center; }
    .stat-box .val { font-size:24px; font-weight:900; }
    .stat-box .lbl { font-size:11px; opacity:.8; margin-top:2px; }
    .day-block { border:1px solid #e8e8e0; border-radius:12px; padding:16px; margin-bottom:16px;
                 background:#fafafa; }
    .day-label { font-weight:800; color:#3d5220; margin-bottom:12px; font-size:14px; }
    .meal-section label { font-size:12px; font-weight:700; color:#555; }
    .meal-row { display:flex; gap:6px; margin-bottom:6px; position:relative; }
    .meal-row input[type=text]   { flex:1; }
    .meal-row input[type=number] { width:80px; }
    .autocomplete-box { position:absolute; top:100%; left:0; right:80px; background:#fff;
                        border:1px solid #ddd; border-radius:8px; z-index:500;
                        box-shadow:0 4px 12px rgba(0,0,0,.12); max-height:220px; overflow-y:auto; }
    .ac-item { padding:8px 12px; cursor:pointer; font-size:13px; display:flex;
               justify-content:space-between; border-bottom:1px solid #f5f5f0; }
    .ac-item:hover { background:#f0f5e8; }
    .ac-item .kcal { color:#3d5220; font-weight:700; }
    .feedback { display:none; margin-top:8px; }
    .btn-create { background:#3d5220; color:#fff; border:none; border-radius:10px;
                  padding:12px 32px; font-weight:800; font-size:15px; width:100%; cursor:pointer; }
    .btn-create:hover { background:#2c3d17; }
  </style>
</head>
<body>

<div class="page-wrap">
  <div class="tab-row">
    <a href="${root}/challenge?action=list" class="tab-btn">챌린지 탐색</a>
    <a href="${root}/challenge?action=myList" class="tab-btn">내 챌린지</a>
    <a href="${root}/challenge?action=createForm" class="tab-btn active">챌린지 만들기</a>
  </div>

  <%-- 실시간 통계 대시보드 --%>
  <div class="stats-dashboard">
    <div class="stat-box"><div class="val" id="totalKcal">0</div><div class="lbl">총 칼로리 (kcal)</div></div>
    <div class="stat-box"><div class="val" id="avgKcal">0</div><div class="lbl">일평균 칼로리</div></div>
    <div class="stat-box"><div class="val" id="achieveRate">0%</div><div class="lbl">목표 달성률</div></div>
  </div>
  <div id="statsFeedback" class="alert feedback"></div>

  <form method="post" action="${root}/challenge" id="createForm">
    <input type="hidden" name="action" value="create">

    <%-- 기본 정보 --%>
    <div class="card-section">
      <h5 class="fw-bold mb-3">기본 정보</h5>
      <div class="row g-3">
        <div class="col-md-6">
          <label class="form-label fw-bold">챌린지 이름</label>
          <input type="text" name="name" class="form-control" placeholder="예: 14일 다이어트 챌린지" required>
        </div>
        <div class="col-md-3">
          <label class="form-label fw-bold">난이도</label>
          <select name="difficulty" class="form-select" required>
            <option value="easy">🌱 초급</option>
            <option value="medium" selected>🔥 중급</option>
            <option value="hard">🏆 상급</option>
          </select>
        </div>
        <div class="col-md-3">
          <label class="form-label fw-bold">기간</label>
          <select name="duration" id="durationSelect" class="form-select" required>
            <option value="">선택</option>
            <option value="7">7일</option>
            <option value="14">14일</option>
            <option value="30">30일</option>
          </select>
        </div>
        <div class="col-md-4">
          <label class="form-label fw-bold">일일 목표 칼로리 (kcal)</label>
          <input type="number" name="targetCalories" id="targetCalories" class="form-control"
                 value="2000" min="500" max="5000" required>
        </div>
      </div>
    </div>

    <%-- 일별 식단 입력 (JS로 동적 생성) --%>
    <div id="mealPlanContainer"></div>

    <button type="submit" class="btn-create">✅ 챌린지 생성</button>
  </form>
</div>

<script>
const ROOT = '${root}';

/* ─── 기간 선택 시 일별 입력 생성 ─────────────────────────────── */
document.getElementById('durationSelect').addEventListener('change', function() {
  const days = parseInt(this.value) || 0;
  generateDayBlocks(days);
  updateStats();
});

document.getElementById('targetCalories').addEventListener('input', updateStats);

function generateDayBlocks(days) {
  const container = document.getElementById('mealPlanContainer');
  container.innerHTML = '';
  for (let d = 1; d <= days; d++) {
    container.insertAdjacentHTML('beforeend', dayBlockHtml(d));
  }
  // 자동완성 이벤트 연결
  container.querySelectorAll('.food-input').forEach(attachAutocomplete);
  container.querySelectorAll('.kcal-input').forEach(inp => {
    inp.addEventListener('input', updateStats);
  });
}

function dayBlockHtml(d) {
	  const meals = [['B','아침'],['L','점심'],['D','저녁']];
	  const cols = meals.map(([m, label]) => `
	    <div class="col-md-4">
	      <div class="meal-section">
	        <label>\${label}</label>
	        \${[1,2,3,4].map(s => `
	          <div class="meal-row">
	            <input type="text" class="form-control form-control-sm food-input"
	                   name="food_d\${d}_\${m}_\${s}" placeholder="메뉴 \${s}"
	                   data-day="\${d}" data-meal="\${m}" data-slot="\${s}" autocomplete="off">
	            <input type="number" class="form-control form-control-sm kcal-input"
	                   name="kcal_d\${d}_\${m}_\${s}" placeholder="kcal"
	                   data-day="\${d}" data-meal="\${m}" data-slot="\${s}" min="0">
	          </div>`).join('')}
	      </div>
	    </div>`).join('');

	  const total = `<span class="small text-muted ms-2">합계: <strong id="day\${d}Total">0</strong> kcal</span>`;

	  return `
	    <div class="day-block">
	      <div class="day-label">🗓️ \${d}일차 \${total}</div>
	      <div class="row g-3">\${cols}</div>
	    </div>`;
	}

/* ─── 음식 자동완성 ─────────────────────────────────────────────── */
function attachAutocomplete(input) {
  let box = null;
  input.addEventListener('input', async function() {
    const q = this.value.trim();
    removeBox();
    if (q.length < 1) return;

    const res = await fetch(`\${ROOT}/challenge?action=foodSearch&q=\${encodeURIComponent(q)}`);
    const foods = await res.json();
    if (!foods.length) return;

    box = document.createElement('div');
    box.className = 'autocomplete-box';
    foods.forEach(f => {
      const item = document.createElement('div');
      item.className = 'ac-item';
      item.innerHTML = `<span>\${f.foodName}</span><span class="kcal">\${Math.round(f.energy)}kcal</span>`;
      item.addEventListener('mousedown', e => {
        e.preventDefault();
        input.value = f.foodName;
        // 같은 행의 kcal 입력란에 값 채우기
        const { day, meal, slot } = input.dataset;
        const kcalInput = document.querySelector(
        		`.kcal-input[data-day="\${day}"][data-meal="\${meal}"][data-slot="\${slot}"]`
        if (kcalInput) { kcalInput.value = Math.round(f.energy); }
        removeBox();
        updateStats();
      });
      box.appendChild(item);
    });

    input.parentElement.style.position = 'relative';
    input.parentElement.appendChild(box);
  });

  input.addEventListener('blur', () => setTimeout(removeBox, 150));
  function removeBox() { if (box) { box.remove(); box = null; } }
}

/* ─── 실시간 통계 계산 ──────────────────────────────────────────── */
function updateStats() {
  const target = parseInt(document.getElementById('targetCalories').value) || 0;
  const dayBlocks = document.querySelectorAll('.day-block');
  let totalKcal = 0, activeDays = 0;

  dayBlocks.forEach((block, i) => {
    let daySum = 0;
    block.querySelectorAll('.kcal-input').forEach(inp => {
      daySum += parseFloat(inp.value) || 0;
    });
    const el = document.getElementById(`day\${i+1}Total`);
    if (el) el.textContent = Math.round(daySum);
    if (daySum > 0) { totalKcal += daySum; activeDays++; }
  });

  const avg = activeDays > 0 ? Math.round(totalKcal / activeDays) : 0;
  document.getElementById('totalKcal').textContent   = Math.round(totalKcal).toLocaleString();
  document.getElementById('avgKcal').textContent     = avg.toLocaleString();
  document.getElementById('achieveRate').textContent = target > 0
    ? Math.round((avg / target) * 100) + '%' : '0%';

  const fb = document.getElementById('statsFeedback');
  if (target > 0 && avg > 0) {
    const rate = (avg / target) * 100;
    fb.style.display = 'block';
    fb.className = `alert feedback alert-\${rate >= 100 ? 'success' : rate >= 70 ? 'info' : 'warning'}`;
    fb.textContent = rate >= 100 ? '🎉 목표 달성!' : rate >= 70 ? '👍 거의 다 왔어요!' : '💪 조금 더 채워보세요!';
  } else {
    fb.style.display = 'none';
  }
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
