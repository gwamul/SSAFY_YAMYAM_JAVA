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
    .template-section h6 { font-weight:800; color:#3d5220; margin-bottom:12px; font-size:14px; }
    .template-grid { display:grid; grid-template-columns:repeat(4,1fr); gap:10px; }
    .tpl-btn { border:2px solid #e8e8e0; border-radius:12px; padding:14px 10px;
               background:#fff; cursor:pointer; text-align:center; transition:all .2s;
               font-size:13px; font-weight:700; color:#444; }
    .tpl-btn:hover { border-color:#3d5220; background:#f0f5e8; color:#3d5220; }
    .tpl-btn .tpl-icon { font-size:24px; display:block; margin-bottom:6px; }
    .tpl-btn .tpl-kcal { font-size:11px; color:#888; font-weight:400; margin-top:4px; }
    .stats-dashboard { display:grid; grid-template-columns:repeat(3,1fr); gap:12px; margin-bottom:24px; }
    .stat-box { background:#3d5220; color:#fff; border-radius:12px; padding:16px; text-align:center; }
    .stat-box .val { font-size:24px; font-weight:900; }
    .stat-box .lbl { font-size:11px; opacity:.8; margin-top:2px; }
    .day-block { border:1px solid #e8e8e0; border-radius:12px; padding:16px; margin-bottom:16px; background:#fafafa; }
    .day-label { font-weight:800; color:#3d5220; margin-bottom:12px; font-size:14px; }
    .meal-section label { font-size:12px; font-weight:700; color:#555; display:block; margin-bottom:4px; }
    .meal-row { display:flex; gap:6px; margin-bottom:6px; position:relative; }
    .meal-row input[type=text]   { flex:1; }
    .meal-row input[type=number] { width:80px; }
    .autocomplete-box { position:absolute; top:100%; left:0; right:80px; background:#fff;
                        border:1px solid #ddd; border-radius:8px; z-index:9999;
                        box-shadow:0 4px 12px rgba(0,0,0,.15); max-height:220px; overflow-y:auto; }
    .ac-item { padding:8px 12px; cursor:pointer; font-size:13px; display:flex;
               justify-content:space-between; border-bottom:1px solid #f5f5f0; user-select:none; }
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

  <div class="stats-dashboard">
    <div class="stat-box"><div class="val" id="totalKcal">0</div><div class="lbl">총 칼로리 (kcal)</div></div>
    <div class="stat-box"><div class="val" id="avgKcal">0</div><div class="lbl">일평균 칼로리</div></div>
    <div class="stat-box"><div class="val" id="achieveRate">0%</div><div class="lbl">목표 달성률</div></div>
  </div>
  <div id="statsFeedback" class="alert feedback"></div>

  <form method="post" action="${root}/challenge" id="createForm"
        onkeydown="if(event.key==='Enter'&&event.target.tagName!=='TEXTAREA'){event.preventDefault();}">
    <input type="hidden" name="action" value="create">

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

    <div class="card-section template-section">
      <h6>⚡ 빠른 식단 템플릿 적용</h6>
      <p class="text-muted small mb-3">기간을 먼저 선택한 뒤 버튼을 클릭하면 식단이 자동으로 채워집니다.</p>
      <div class="template-grid">
        <button type="button" class="tpl-btn" onclick="applyTemplate('diet')">
          <span class="tpl-icon">🥗</span>다이어트
          <div class="tpl-kcal">1,400 kcal/일</div>
        </button>
        <button type="button" class="tpl-btn" onclick="applyTemplate('bulk')">
          <span class="tpl-icon">💪</span>벌크업
          <div class="tpl-kcal">3,000 kcal/일</div>
        </button>
        <button type="button" class="tpl-btn" onclick="applyTemplate('healthy')">
          <span class="tpl-icon">🌿</span>건강 유지
          <div class="tpl-kcal">2,000 kcal/일</div>
        </button>
        <button type="button" class="tpl-btn" onclick="applyTemplate('athlete')">
          <span class="tpl-icon">🏃</span>운동 퍼포먼스
          <div class="tpl-kcal">2,500 kcal/일</div>
        </button>
      </div>
    </div>

    <div id="mealPlanContainer"></div>

    <button type="submit" class="btn-create">✅ 챌린지 생성</button>
  </form>
</div>

<script>
var ROOT = '${root}';

/* ── 템플릿 데이터 ── */
var TEMPLATES = {
  diet: {
    targetCalories: 1400,
    B: [{name:'현미밥', kcal:150}, {name:'두부된장국', kcal:80}, {name:'계란찜', kcal:70}],
    L: [{name:'닭가슴살', kcal:165}, {name:'샐러드', kcal:60}, {name:'고구마', kcal:110}],
    D: [{name:'현미밥', kcal:150}, {name:'나물무침', kcal:50}, {name:'미역국', kcal:30}]
  },
  bulk: {
    targetCalories: 3000,
    B: [{name:'흰쌀밥', kcal:300}, {name:'소고기미역국', kcal:120}, {name:'계란후라이', kcal:180}, {name:'우유', kcal:130}],
    L: [{name:'돼지불고기', kcal:350}, {name:'흰쌀밥', kcal:300}, {name:'된장찌개', kcal:100}, {name:'시금치무침', kcal:40}],
    D: [{name:'연어구이', kcal:280}, {name:'흰쌀밥', kcal:300}, {name:'계란탕', kcal:100}, {name:'바나나', kcal:90}]
  },
  healthy: {
    targetCalories: 2000,
    B: [{name:'오트밀', kcal:150}, {name:'블루베리', kcal:50}, {name:'아몬드밀크', kcal:60}],
    L: [{name:'잡곡밥', kcal:220}, {name:'두부조림', kcal:130}, {name:'콩나물국', kcal:40}, {name:'김치', kcal:20}],
    D: [{name:'닭가슴살', kcal:165}, {name:'현미밥', kcal:150}, {name:'브로콜리', kcal:55}, {name:'토마토', kcal:35}]
  },
  athlete: {
    targetCalories: 2500,
    B: [{name:'통밀빵', kcal:200}, {name:'계란프라이', kcal:180}, {name:'오렌지주스', kcal:110}],
    L: [{name:'닭가슴살덮밥', kcal:450}, {name:'된장국', kcal:60}, {name:'사과', kcal:80}],
    D: [{name:'연어스테이크', kcal:300}, {name:'고구마', kcal:110}, {name:'그릭요거트', kcal:100}, {name:'견과류', kcal:180}]
  }
};

/* ── 템플릿 적용 ── */
function applyTemplate(key) {
  var days = parseInt(document.getElementById('durationSelect').value) || 0;
  if (days === 0) { alert('먼저 기간을 선택해주세요.'); return; }
  var tpl = TEMPLATES[key];
  document.getElementById('targetCalories').value = tpl.targetCalories;
  if (document.querySelectorAll('.day-block').length !== days) {
    generateDayBlocks(days);
  }
  for (var d = 1; d <= days; d++) {
    ['B','L','D'].forEach(function(meal) {
      var items = tpl[meal];
      items.forEach(function(item, idx) {
        var slot = idx + 1;
        var fi = document.querySelector('[name="food_d' + d + '_' + meal + '_' + slot + '"]');
        var ki = document.querySelector('[name="kcal_d' + d + '_' + meal + '_' + slot + '"]');
        if (fi) fi.value = item.name;
        if (ki) ki.value = item.kcal;
      });
    });
  }
  updateStats();
}

/* ── 기간 변경 ── */
document.getElementById('durationSelect').addEventListener('change', function() {
  generateDayBlocks(parseInt(this.value) || 0);
  updateStats();
});
document.getElementById('targetCalories').addEventListener('input', updateStats);

/* ── 블록 생성 (DOM API 사용 — JSP EL 충돌 없음) ── */
function generateDayBlocks(days) {
  var container = document.getElementById('mealPlanContainer');
  container.innerHTML = '';
  for (var d = 1; d <= days; d++) {
    var block = buildDayBlock(d);
    container.appendChild(block);
    block.querySelectorAll('.food-input').forEach(attachAutocomplete);
    block.querySelectorAll('.kcal-input').forEach(function(inp) {
      inp.addEventListener('input', updateStats);
    });
  }
}

function buildDayBlock(d) {
  var wrap = document.createElement('div');
  wrap.className = 'day-block';

  var header = document.createElement('div');
  header.className = 'day-label';
  header.innerHTML = '🗓️ ' + d + '일차'
    + ' <span class="small text-muted ms-2">합계: <strong id="day' + d + 'Total">0</strong> kcal</span>';
  wrap.appendChild(header);

  var row = document.createElement('div');
  row.className = 'row g-3';

  [['B','아침'],['L','점심'],['D','저녁']].forEach(function(pair) {
    var m = pair[0], label = pair[1];
    var col = document.createElement('div');
    col.className = 'col-md-4';

    var section = document.createElement('div');
    section.className = 'meal-section';

    var lbl = document.createElement('label');
    lbl.textContent = label;
    section.appendChild(lbl);

    for (var s = 1; s <= 4; s++) {
      var mealRow = document.createElement('div');
      mealRow.className = 'meal-row';

      var fi = document.createElement('input');
      fi.type = 'text';
      fi.className = 'form-control form-control-sm food-input';
      fi.name = 'food_d' + d + '_' + m + '_' + s;
      fi.placeholder = '메뉴 ' + s;
      fi.dataset.day  = String(d);
      fi.dataset.meal = m;
      fi.dataset.slot = String(s);
      fi.autocomplete = 'off';

      var ki = document.createElement('input');
      ki.type = 'number';
      ki.className = 'form-control form-control-sm kcal-input';
      ki.name = 'kcal_d' + d + '_' + m + '_' + s;
      ki.placeholder = 'kcal';
      ki.dataset.day  = String(d);
      ki.dataset.meal = m;
      ki.dataset.slot = String(s);
      ki.min = '0';

      mealRow.appendChild(fi);
      mealRow.appendChild(ki);
      section.appendChild(mealRow);
    }

    col.appendChild(section);
    row.appendChild(col);
  });

  wrap.appendChild(row);
  return wrap;
}

/* ── 자동완성 ── */
function attachAutocomplete(input) {
  var box = null;
  var selecting = false;

  input.addEventListener('input', function() {
    var q = input.value.trim();
    closeBox();
    if (q.length < 1) return;

    fetch(ROOT + '/challenge?action=foodSearch&q=' + encodeURIComponent(q))
      .then(function(res) { return res.json(); })
      .then(function(foods) {
        if (!foods.length) return;
        box = document.createElement('div');
        box.className = 'autocomplete-box';

        foods.forEach(function(f) {
          var item = document.createElement('div');
          item.className = 'ac-item';
          item.innerHTML = '<span>' + f.foodName + '</span>'
            + '<span class="kcal">' + Math.round(f.energy) + 'kcal</span>';

          item.addEventListener('pointerdown', function(e) {
            e.preventDefault();
            selecting = true;
            input.value = f.foodName;
            var ki = document.querySelector(
              '[name="kcal_d' + input.dataset.day + '_' + input.dataset.meal + '_' + input.dataset.slot + '"]'
            );
            if (ki) ki.value = Math.round(f.energy);
            closeBox();
            selecting = false;
            updateStats();
            // 다음 슬롯으로 포커스
            var nextSlot = parseInt(input.dataset.slot) + 1;
            var next = document.querySelector(
              '[name="food_d' + input.dataset.day + '_' + input.dataset.meal + '_' + nextSlot + '"]'
            );
            if (next) next.focus();
          });

          box.appendChild(item);
        });

        input.parentElement.appendChild(box);
      })
      .catch(function() {});
  });

  input.addEventListener('blur', function() {
    if (!selecting) setTimeout(closeBox, 120);
  });

  function closeBox() {
    if (box) { box.remove(); box = null; }
  }
}

/* ── 실시간 통계 ── */
function updateStats() {
  var target = parseInt(document.getElementById('targetCalories').value) || 0;
  var dayBlocks = document.querySelectorAll('.day-block');
  var totalKcal = 0, activeDays = 0;

  dayBlocks.forEach(function(block, i) {
    var daySum = 0;
    block.querySelectorAll('.kcal-input').forEach(function(inp) {
      daySum += parseFloat(inp.value) || 0;
    });
    var el = document.getElementById('day' + (i+1) + 'Total');
    if (el) el.textContent = Math.round(daySum);
    if (daySum > 0) { totalKcal += daySum; activeDays++; }
  });

  var avg = activeDays > 0 ? Math.round(totalKcal / activeDays) : 0;
  document.getElementById('totalKcal').textContent   = Math.round(totalKcal).toLocaleString();
  document.getElementById('avgKcal').textContent     = avg.toLocaleString();
  document.getElementById('achieveRate').textContent = target > 0
    ? Math.round((avg / target) * 100) + '%' : '0%';

  var fb = document.getElementById('statsFeedback');
  if (target > 0 && avg > 0) {
    var rate = (avg / target) * 100;
    fb.style.display = 'block';
    fb.className = 'alert feedback alert-' + (rate >= 100 ? 'success' : rate >= 70 ? 'info' : 'warning');
    fb.textContent = rate >= 100 ? '🎉 목표 달성!' : rate >= 70 ? '👍 거의 다 왔어요!' : '💪 조금 더 채워보세요!';
  } else {
    fb.style.display = 'none';
  }
}
</script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>
