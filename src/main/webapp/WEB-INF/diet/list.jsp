<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<c:set var="root" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>YamYam - 식단 기록</title>
<%@ include file="/WEB-INF/common/header.jsp" %>
<style>
.diet-wrap {
	max-width: 1180px;
	margin: 32px auto 56px;
	padding: 0 20px;
}
.hero-card {
	background: linear-gradient(135deg, #3d5220 0%, #68723D 100%);
	color: #fff;
	border-radius: 28px;
	padding: 28px 30px;
	box-shadow: 0 18px 40px rgba(61, 82, 32, 0.22);
	margin-bottom: 20px;
}
.hero-card h1 {
	font-size: 30px;
	font-weight: 800;
	margin: 0 0 8px;
}
.hero-card p {
	margin: 0;
	color: rgba(255,255,255,0.82);
	font-size: 14px;
}
.summary-grid {
	display: grid;
	grid-template-columns: repeat(4, minmax(0, 1fr));
	gap: 14px;
	margin-bottom: 22px;
}
.summary-card {
	background: #fff;
	border-radius: 22px;
	padding: 18px 20px;
	box-shadow: 0 8px 24px rgba(0,0,0,0.06);
	border: 1px solid rgba(61, 82, 32, 0.08);
}
.summary-label {
	display: block;
	font-size: 12px;
	font-weight: 700;
	color: #8a8a80;
	margin-bottom: 8px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
}
.summary-value {
	font-size: 26px;
	font-weight: 800;
	color: #2f3e18;
}
.composer-grid {
	display: grid;
	grid-template-columns: 1.1fr 0.9fr;
	gap: 18px;
	margin-bottom: 22px;
}
.composer-card {
	background: #fff;
	border-radius: 28px;
	padding: 20px;
	box-shadow: 0 8px 24px rgba(0,0,0,0.06);
	border: 1px solid rgba(61, 82, 32, 0.08);
}
.composer-title {
	font-size: 18px;
	font-weight: 800;
	color: #2f3e18;
	margin: 0 0 6px;
}
.composer-sub {
	color: #7a7a71;
	font-size: 13px;
	margin: 0 0 16px;
}
.search-bar {
	display: flex;
	gap: 10px;
	margin-bottom: 14px;
}
.search-bar input,
.search-bar select,
.search-bar button,
.composer-card input {
	border-radius: 14px;
	border: 1px solid #dde2d4;
	padding: 11px 12px;
	font-size: 14px;
}
.search-bar input {
	flex: 1;
}
.search-bar button,
.record-btn {
	background: #3d5220;
	color: #fff;
	font-weight: 800;
	border: none;
	cursor: pointer;
}
.search-results {
	max-height: 420px;
	overflow: auto;
	border-top: 1px solid #f0f0ea;
	padding-top: 10px;
}
.food-result {
	width: 100%;
	text-align: left;
	background: #f9faf6;
	border: 1px solid #edf1e3;
	border-radius: 16px;
	padding: 14px;
	margin-bottom: 10px;
	cursor: pointer;
	transition: transform .15s ease, box-shadow .15s ease;
}
.food-result:hover {
	transform: translateY(-1px);
	box-shadow: 0 8px 18px rgba(61, 82, 32, 0.08);
}
.food-result.active {
	border-color: #3d5220;
	background: #eef4df;
}
.food-result-name {
	font-weight: 800;
	color: #2f3e18;
	margin-bottom: 6px;
}
.food-result-meta {
	font-size: 12px;
	color: #7a7a71;
}
.selected-food {
	background: linear-gradient(135deg, #f7faf0, #eef4df);
	border-radius: 20px;
	padding: 18px;
	border: 1px solid #dde8c2;
}
.selected-food .name {
	font-size: 20px;
	font-weight: 900;
	color: #2f3e18;
}
.selected-food .meta {
	font-size: 12px;
	color: #7a7a71;
	margin-top: 4px;
}
.nutri-grid {
	display: grid;
	grid-template-columns: repeat(3, minmax(0, 1fr));
	gap: 10px;
	margin-top: 16px;
}
.nutri-pill {
	background: rgba(255,255,255,0.7);
	border-radius: 16px;
	padding: 12px;
	text-align: center;
	border: 1px solid rgba(61, 82, 32, 0.08);
}
.nutri-pill .label {
	display: block;
	font-size: 11px;
	font-weight: 800;
	color: #7a7a71;
	text-transform: uppercase;
	letter-spacing: 0.06em;
}
.nutri-pill .value {
	display: block;
	font-size: 18px;
	font-weight: 900;
	color: #2f3e18;
	margin-top: 4px;
}
.record-form {
	display: grid;
	grid-template-columns: 1fr 120px;
	gap: 10px;
	margin-top: 16px;
}
.record-form .full {
	grid-column: 1 / -1;
}
.record-form label {
	display: block;
	font-size: 12px;
	font-weight: 800;
	color: #7a7a71;
	margin-bottom: 6px;
}
.record-form input[readonly] {
	background: #f8f8f4;
}
.record-btn {
	padding: 12px 14px;
	border-radius: 14px;
	align-self: end;
}
.hint-box {
	background: #fff7d9;
	border: 1px solid #f1dda3;
	color: #7d6420;
	border-radius: 18px;
	padding: 14px 16px;
	font-size: 13px;
	margin-bottom: 18px;
}
.table-card {
	background: #fff;
	border-radius: 28px;
	padding: 18px;
	box-shadow: 0 8px 24px rgba(0,0,0,0.06);
	overflow: hidden;
}
.table-card table {
	width: 100%;
	border-collapse: collapse;
}
.table-card th,
.table-card td {
	padding: 14px 12px;
	border-bottom: 1px solid #f0f0ea;
	text-align: left;
	font-size: 14px;
}
.table-card th {
	font-size: 12px;
	text-transform: uppercase;
	letter-spacing: 0.08em;
	color: #7a7a71;
	background: #fafaf7;
}
.food-name {
	font-weight: 800;
	color: #2f3e18;
}
.food-code {
	display: block;
	font-size: 12px;
	color: #8f8f85;
	margin-top: 4px;
}
.nutrient {
	white-space: nowrap;
}
.empty-state {
	text-align: center;
	padding: 64px 20px;
	color: #9a9a90;
}
.empty-state h3 {
	margin: 0 0 10px;
	font-size: 24px;
	color: #4a5f28;
}
@media (max-width: 992px) {
	.composer-grid { grid-template-columns: 1fr; }
	.summary-grid { grid-template-columns: repeat(2, minmax(0, 1fr)); }
	.table-card { overflow-x: auto; }
}
@media (max-width: 576px) {
	.diet-wrap { padding: 0 14px; }
	.summary-grid { grid-template-columns: 1fr; }
	.hero-card h1 { font-size: 24px; }
}
</style>
</head>
<body>
<div class="diet-wrap">
	<div class="hero-card">
		<h1>${sessionScope.loginUser.name}님의 식단 기록</h1>
		<p>음식 DB에서 검색해 선택하고, 섭취량에 맞춰 바로 기록하세요.</p>
	</div>

	<div class="hint-box">
		음식을 검색하면 DB에 저장된 영양값이 표시됩니다. 음식을 선택한 뒤 섭취량을 입력하고 기록을 저장하세요.
	</div>

	<div class="composer-grid">
		<div class="composer-card">
			<p class="composer-title">음식 검색</p>
			<p class="composer-sub">`foods` 테이블에서 실제 음식 데이터를 불러옵니다.</p>
			<div class="search-bar">
				<input id="foodKeyword" type="text" placeholder="예: 미역국, 닭가슴살, 불고기덮밥">
				<button type="button" id="searchBtn">검색</button>
			</div>
			<div id="searchResults" class="search-results">
				<c:if test="${empty searchKeyword}">
					<div class="empty-state" style="padding: 36px 12px;">
						<p style="margin:0;">검색어를 입력하면 음식 목록이 나타납니다.</p>
					</div>
				</c:if>
			</div>
		</div>

		<div class="composer-card">
			<p class="composer-title">식단 기록하기</p>
			<p class="composer-sub">검색 결과로 채운 뒤 직접 수정해서 저장할 수 있습니다.</p>
			<div class="selected-food">
				<div class="name" id="selectedFoodName">음식을 선택하세요</div>
				<div class="meta" id="selectedFoodMeta">검색 결과를 클릭하면 기본값이 채워집니다.</div>
				<div class="nutri-grid">
					<div class="nutri-pill"><span class="label">kcal</span><span class="value" id="selectedEnergy">0</span></div>
					<div class="nutri-pill"><span class="label">protein</span><span class="value" id="selectedProtein">0</span></div>
					<div class="nutri-pill"><span class="label">carbs</span><span class="value" id="selectedCarbs">0</span></div>
				</div>
		</div>

			<form method="post" action="${root}/diet?action=save" class="record-form" id="recordForm">
				<input type="hidden" name="foodCode" id="foodCode">
				<div class="full">
					<label>음식명</label>
					<input type="text" name="foodName" id="foodNameInput" placeholder="음식명을 직접 입력하거나 검색 결과를 선택하세요">
				</div>
				<div>
					<label>칼로리(kcal)</label>
					<input type="number" name="energy" id="energyInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>단백질(g)</label>
					<input type="number" name="protein" id="proteinInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>지방(g)</label>
					<input type="number" name="fat" id="fatInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>탄수화물(g)</label>
					<input type="number" name="carbs" id="carbsInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>당류(g)</label>
					<input type="number" name="sugar" id="sugarInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>나트륨(mg)</label>
					<input type="number" name="sodium" id="sodiumInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>포화지방(g)</label>
					<input type="number" name="saturatedFat" id="saturatedFatInput" step="0.1" min="0" placeholder="0">
				</div>
				<div>
					<label>트랜스지방(g)</label>
					<input type="number" name="transFat" id="transFatInput" step="0.1" min="0" placeholder="0">
				</div>
				<div class="full" style="display:flex; gap:10px; align-items:end; justify-content:flex-end;">
					<button type="button" class="btn btn-outline-secondary" id="clearSelectionBtn" style="border-radius:14px; padding:12px 14px; font-weight:800;">직접 입력</button>
					<button type="submit" class="record-btn">기록 추가</button>
				</div>
			</form>
		</div>
	</div>

	<div class="summary-grid">
		<div class="summary-card">
			<span class="summary-label">기록 수</span>
			<div class="summary-value">${totalCount}</div>
		</div>
		<div class="summary-card">
			<span class="summary-label">총 칼로리</span>
			<div class="summary-value"><fmt:formatNumber value="${totalEnergy}" maxFractionDigits="0" /> <small style="font-size:16px; color:#7a7a71;">kcal</small></div>
		</div>
		<div class="summary-card">
			<span class="summary-label">총 단백질</span>
			<div class="summary-value"><fmt:formatNumber value="${totalProtein}" maxFractionDigits="1" /> <small style="font-size:16px; color:#7a7a71;">g</small></div>
		</div>
		<div class="summary-card">
			<span class="summary-label">총 탄수화물</span>
			<div class="summary-value"><fmt:formatNumber value="${totalCarbs}" maxFractionDigits="1" /> <small style="font-size:16px; color:#7a7a71;">g</small></div>
		</div>
	</div>

	<div class="table-card">
		<c:choose>
			<c:when test="${empty mealLogs}">
				<div class="empty-state">
					<h3>아직 등록된 식단 기록이 없습니다.</h3>
					<p>식단을 추가한 뒤 이 화면에서 시간순으로 확인할 수 있습니다.</p>
				</div>
			</c:when>
			<c:otherwise>
				<table>
					<thead>
						<tr>
							<th>기록 시간</th>
							<th>음식</th>
							<th>칼로리</th>
							<th>탄수화물</th>
							<th>단백질</th>
							<th>지방</th>
							<th>나트륨</th>
						</tr>
					</thead>
					<tbody>
						<c:forEach var="mealLog" items="${mealLogs}">
							<tr>
								<td><fmt:formatDate value="${mealLog.createdDate}" pattern="yyyy-MM-dd HH:mm" /></td>
								<td>
									<span class="food-name">${mealLog.foodName}</span>
									<c:if test="${not empty mealLog.foodCode}">
										<span class="food-code">#${mealLog.foodCode}</span>
									</c:if>
								</td>
								<td class="nutrient"><fmt:formatNumber value="${mealLog.energy}" maxFractionDigits="0" /> kcal</td>
								<td class="nutrient"><fmt:formatNumber value="${mealLog.carbs}" maxFractionDigits="1" /> g</td>
								<td class="nutrient"><fmt:formatNumber value="${mealLog.protein}" maxFractionDigits="1" /> g</td>
								<td class="nutrient"><fmt:formatNumber value="${mealLog.fat}" maxFractionDigits="1" /> g</td>
								<td class="nutrient"><fmt:formatNumber value="${mealLog.sodium}" maxFractionDigits="1" /> mg</td>
							</tr>
						</c:forEach>
					</tbody>
				</table>
			</c:otherwise>
		</c:choose>
	</div>
</div>

<script>
const root = '${root}';
const foodKeyword = document.getElementById('foodKeyword');
const searchBtn = document.getElementById('searchBtn');
const searchResults = document.getElementById('searchResults');
const foodCodeInput = document.getElementById('foodCode');
const foodNameInput = document.getElementById('foodNameInput');
const selectedFoodName = document.getElementById('selectedFoodName');
const selectedFoodMeta = document.getElementById('selectedFoodMeta');
const selectedEnergy = document.getElementById('selectedEnergy');
const selectedProtein = document.getElementById('selectedProtein');
const selectedCarbs = document.getElementById('selectedCarbs');
const energyInput = document.getElementById('energyInput');
const proteinInput = document.getElementById('proteinInput');
const fatInput = document.getElementById('fatInput');
const carbsInput = document.getElementById('carbsInput');
const sugarInput = document.getElementById('sugarInput');
const sodiumInput = document.getElementById('sodiumInput');
const saturatedFatInput = document.getElementById('saturatedFatInput');
const transFatInput = document.getElementById('transFatInput');
const clearSelectionBtn = document.getElementById('clearSelectionBtn');

let selectedFood = null;

function renderSelected(food) {
	selectedFood = food;
	foodCodeInput.value = food.foodCode;
	foodNameInput.value = food.foodName;
	selectedFoodName.textContent = food.foodName;
	selectedFoodMeta.textContent = (food.category || '기타') + ' · 기준량 ' + (food.referenceAmount || '-');
	energyInput.value = food.energy ?? 0;
	proteinInput.value = food.protein ?? 0;
	fatInput.value = food.fat ?? 0;
	carbsInput.value = food.carbs ?? 0;
	sugarInput.value = food.sugar ?? 0;
	sodiumInput.value = food.sodium ?? 0;
	saturatedFatInput.value = food.saturatedFat ?? 0;
	transFatInput.value = food.transFat ?? 0;
	updatePreview();
}

function updatePreview() {
	selectedEnergy.textContent = Number(energyInput.value || 0).toFixed(0);
	selectedProtein.textContent = Number(proteinInput.value || 0).toFixed(1);
	selectedCarbs.textContent = Number(carbsInput.value || 0).toFixed(1);
}

function renderResults(items) {
	if (!items.length) {
		searchResults.innerHTML = '<div class="empty-state" style="padding: 36px 12px;"><p style="margin:0;">검색 결과가 없습니다.</p></div>';
		return;
	}

	searchResults.innerHTML = items.map((item, index) => {
		var activeClass = index === 0 ? 'active' : '';
		return '<button type="button" class="food-result ' + activeClass + '" data-index="' + index + '">' +
			'<div class="food-result-name">' + item.foodName + '</div>' +
			'<div class="food-result-meta">' +
				(item.category || '기타') + ' · ' +
				(item.referenceAmount || '-') + ' · ' +
				Number(item.energy).toFixed(0) + 'kcal · P ' +
				Number(item.protein).toFixed(1) + 'g · C ' +
				Number(item.carbs).toFixed(1) + 'g' +
			'</div>' +
		'</button>';
	}).join('');

	Array.from(searchResults.querySelectorAll('.food-result')).forEach((button) => {
		button.addEventListener('click', () => {
			Array.from(searchResults.querySelectorAll('.food-result')).forEach((node) => node.classList.remove('active'));
			button.classList.add('active');
			const item = items[Number(button.dataset.index)];
			renderSelected(item);
		});
	});

	renderSelected(items[0]);
}

async function searchFoods() {
	const keyword = foodKeyword.value.trim();
	if (!keyword) {
		searchResults.innerHTML = '<div class="empty-state" style="padding: 36px 12px;"><p style="margin:0;">검색어를 입력하면 음식 목록이 나타납니다.</p></div>';
		return;
	}

	searchResults.innerHTML = '<div class="empty-state" style="padding: 36px 12px;"><p style="margin:0;">검색 중...</p></div>';
	const response = await fetch(root + '/diet?action=search&q=' + encodeURIComponent(keyword));
	const items = await response.json();
	renderResults(items);
}

function clearSelection() {
	selectedFood = null;
	foodCodeInput.value = '';
	foodNameInput.value = '';
	selectedFoodName.textContent = '직접 입력';
	selectedFoodMeta.textContent = '음식명과 영양값을 직접 입력할 수 있습니다.';
	energyInput.value = '';
	proteinInput.value = '';
	fatInput.value = '';
	carbsInput.value = '';
	sugarInput.value = '';
	sodiumInput.value = '';
	saturatedFatInput.value = '';
	transFatInput.value = '';
	selectedEnergy.textContent = '0';
	selectedProtein.textContent = '0';
	selectedCarbs.textContent = '0';
}

searchBtn.addEventListener('click', searchFoods);
foodKeyword.addEventListener('keydown', (event) => {
	if (event.key === 'Enter') {
		event.preventDefault();
		searchFoods();
	}
});
energyInput.addEventListener('input', updatePreview);
proteinInput.addEventListener('input', updatePreview);
carbsInput.addEventListener('input', updatePreview);
clearSelectionBtn.addEventListener('click', clearSelection);

document.getElementById('recordForm').addEventListener('submit', (event) => {
	if (!foodNameInput.value.trim()) {
		event.preventDefault();
		alert('음식명을 입력하세요.');
	}
});
</script>
</body>
</html>