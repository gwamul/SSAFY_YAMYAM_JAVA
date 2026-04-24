package com.ssafy.prj.controller;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.ChallengeDto;
import com.ssafy.prj.model.dto.DayPlanDto;
import com.ssafy.prj.model.dto.DifficultyMeta;
import com.ssafy.prj.model.dto.FoodDto;
import com.ssafy.prj.model.dto.MealItemDto;
import com.ssafy.prj.model.dto.SubscriptionDto;
import com.ssafy.prj.model.service.ChallengeService;
import com.ssafy.prj.model.service.ChallengeServiceImpl;
import com.ssafy.prj.util.ControllerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/challenge")
public class ChallengeController extends HttpServlet implements ControllerHelper{

	private static final long serialVersionUID = 1L;
	private ChallengeService challengeService;

	public ChallengeController() {
		challengeService = ChallengeServiceImpl.getInstance();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		String action = getActionParameter(request, response, "list");
		switch (action) {
		case "list"       -> list(request, response);
		case "detail"     -> detail(request, response);
		case "myList"     -> myList(request, response);
		case "createForm" -> createForm(request, response);
		case "delete"     -> delete(request, response);
		case "foodSearch" -> foodSearch(request, response);
		default           -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}





	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		req.setCharacterEncoding("UTF-8");

		String action = getActionParameter(req, res, "list");
		switch (action) {
		case "create"    -> create(req, res);
		case "subscribe" -> subscribe(req, res);
		default          -> doGet(req, res);
		}
	}

	/*
	 * 현재 가지고 있는 challenge 리스트 보여줌.
	 */
	private void list(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		
		String difficulty = req.getParameter("difficulty");
		String durationStr = req.getParameter("duration");
		Integer duration = null;
		try { 
			if (durationStr != null) duration = Integer.parseInt(durationStr); 
		}catch (NumberFormatException ignored) {
			ignored.printStackTrace();
		}

		List<ChallengeDto> challenges = challengeService.getFilteredChallenges(difficulty, duration);
		req.setAttribute("challenges", challenges);
		req.setAttribute("difficulty", difficulty);
		req.setAttribute("duration", durationStr);

		List<DifficultyMeta> diffMetas = List.of(
				new DifficultyMeta("easy",   "#BCCA8C", "🌱 초급: 가벼운 시작", "누구나 부담 없이 시작할 수 있는 기초 식단"),
				new DifficultyMeta("medium", "#68723D", "🔥 중급: 꾸준한 관리", "본격적인 체중 조절과 건강 관리"),
				new DifficultyMeta("hard",   "#4f572e", "🏆 상급: 극한의 도전", "강력한 의지가 필요한 고강도 식단")
				);
		req.setAttribute("diffMetas", diffMetas);
		forward(req, res, "/WEB-INF/challenge/list.jsp");
	}

	/*
	 * 특정 챌린지 상세 출력 
	 */
	private void detail(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		int id = parseInt(req.getParameter("id"), -1);
		ChallengeDto challenge = challengeService.getChallenge(id);
		if (challenge == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}
		req.setAttribute("challenge", challenge);
		forward(req, res, "/WEB-INF/challenge/detail.jsp");
	}

	/*
	 * 내 챌린지 목록
	 */
	@SuppressWarnings("unchecked")
	private void myList(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		HttpSession session = req.getSession();
		List<SubscriptionDto> myList =
				(List<SubscriptionDto>) session.getAttribute("mySubscribed");
		if (myList == null) myList = new ArrayList<>();
		req.setAttribute("myList", myList);
		forward(req, res, "/WEB-INF/challenge/myList.jsp");
	}

	/** 챌린지 만들기 폼 */
	private void createForm(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		forward(req, res, "/WEB-INF/challenge/create.jsp");
	}

	/** 챌린지 생성 처리 */
	private void create(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		ChallengeDto challenge = buildChallengeFromRequest(req);
		challengeService.createChallenge(challenge);
		redirect(req, res, "/challenge?action=list");
	}

	/** 챌린지 삭제 */
	private void delete(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		int id = parseInt(req.getParameter("id"), -1);
		challengeService.deleteChallenge(id);
		redirect(req, res, "/challenge?action=list");
	}

	/** 챌린지 구독 (세션에 추가) */
	@SuppressWarnings("unchecked")
	private void subscribe(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		int id = parseInt(req.getParameter("id"), -1);
		ChallengeDto challenge = challengeService.getChallenge(id);
		if (challenge == null) {
			res.sendError(HttpServletResponse.SC_NOT_FOUND);
			return;
		}

		HttpSession session = req.getSession();
		List<SubscriptionDto> myList =
				(List<SubscriptionDto>) session.getAttribute("mySubscribed");
		if (myList == null) myList = new ArrayList<>();

		if (!challengeService.isSubscribed(myList, id)) {
			myList.add(challengeService.subscribe(challenge));
			session.setAttribute("mySubscribed", myList);
		}
		redirect(req, res, "/challenge?action=myList");
	}

	/**
	 * 음식 자동완성 검색 (AJAX → JSON 응답).
	 *
	 * 응답 형식:
	 * [{"foodName":"국밥","energy":137.0},{"foodName":"김치찌개","energy":45.0}, ...]
	 */
	private void foodSearch(HttpServletRequest req, HttpServletResponse res)
			throws IOException {
		String keyword = req.getParameter("q");
		List<FoodDto> foods = challengeService.searchFood(keyword);

		StringBuilder json = new StringBuilder("[");
		for (int i = 0; i < foods.size(); i++) {
			FoodDto f = foods.get(i);
			if (i > 0) json.append(',');
			json.append("{")
			.append("\"foodCode\":\"").append(escapeJson(f.getFoodCode())).append("\",")
			.append("\"foodName\":\"").append(escapeJson(f.getFoodName())).append("\",")
			.append("\"energy\":").append(f.getEnergy()).append(",")
			.append("\"protein\":").append(f.getProtein()).append(",")
			.append("\"fat\":").append(f.getFat()).append(",")
			.append("\"carbs\":").append(f.getCarbs())
			.append("}");
		}
		json.append("]");
		writeJson(res, json.toString());
	}

	// ── 폼 파싱 헬퍼 ────────────────────────────────────────────────

	/**
	 * POST 파라미터로부터 ChallengeDto 를 조립합니다.
	 *
	 * 폼 파라미터 설계:
	 *   name, difficulty, duration, targetCalories
	 *   food_d{day}_{meal}_{slot}   (음식 이름)
	 *   kcal_d{day}_{meal}_{slot}   (칼로리)
	 *   day: 1..duration, meal: B|L|D, slot: 1..4
	 */
	private ChallengeDto buildChallengeFromRequest(HttpServletRequest req) {
		String name       = req.getParameter("name");
		String difficulty = req.getParameter("difficulty");
		int duration      = parseInt(req.getParameter("duration"), 7);
		int targetCal     = parseInt(req.getParameter("targetCalories"), 2000);

		List<DayPlanDto> plans = new ArrayList<>();
		String[] meals = {"B", "L", "D"};

		for (int d = 1; d <= duration; d++) {
			DayPlanDto day = new DayPlanDto();
			day.setDay(d);
			day.setBreakfast(extractMealItems(req, d, "B"));
			day.setLunch    (extractMealItems(req, d, "L"));
			day.setDinner   (extractMealItems(req, d, "D"));
			plans.add(day);
		}

		ChallengeDto dto = new ChallengeDto();
		dto.setName(name);
		dto.setDifficulty(difficulty);
		dto.setDuration(duration);
		dto.setTargetCalories(targetCal);
		dto.setMealPlans(plans);
		return dto;
	}

	private List<MealItemDto> extractMealItems(HttpServletRequest req, int day, String meal) {
		List<MealItemDto> items = new ArrayList<>();
		for (int slot = 1; slot <= 4; slot++) {
			String foodName = req.getParameter("food_d" + day + "_" + meal + "_" + slot);
			String kcalStr  = req.getParameter("kcal_d" + day + "_" + meal + "_" + slot);
			if (foodName != null && !foodName.isBlank()) {
				items.add(new MealItemDto(foodName.trim(), parseDouble(kcalStr)));
			}
		}
		return items;
	}

	
	private int parseInt(String s, int defaultVal) {
		try { return Integer.parseInt(s); } catch (Exception e) { return defaultVal; }
	}

	private double parseDouble(String s) {
		try { return Double.parseDouble(s); } catch (Exception e) { return 0.0; }
	}

	private String escapeJson(String s) {
		return s == null ? "" : s.replace("\\", "\\\\").replace("\"", "\\\"");
	}

}
