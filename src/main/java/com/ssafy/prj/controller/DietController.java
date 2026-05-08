package com.ssafy.prj.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dao.FoodDao;
import com.ssafy.prj.model.dao.FoodDaoImpl;
import com.ssafy.prj.model.dto.MealLogDto;
import com.ssafy.prj.model.dto.FoodDto;
import com.ssafy.prj.model.dto.MemberDto;
import com.ssafy.prj.model.service.MealLogService;
import com.ssafy.prj.model.service.MealLogServiceImpl;
import com.ssafy.prj.util.ControllerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/diet")
public class DietController extends HttpServlet implements ControllerHelper {

	private static final long serialVersionUID = 1L;
	private final MealLogService mealLogService;
	private final FoodDao foodDao;

	public DietController() {
		mealLogService = MealLogServiceImpl.getInstance();
		foodDao = FoodDaoImpl.getInstance();
	}

	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response, "list");
		switch (action) {
		case "search" -> searchFoods(request, response);
		default -> list(request, response);
		}
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response, "save");
		switch (action) {
		case "save" -> saveMealLog(request, response);
		default -> doGet(request, response);
		}
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			redirect(request, response, "/member?action=loginForm");
			return;
		}

		List<MealLogDto> mealLogs = mealLogService.getMealLogsByUserId(loginUser.getUserId());
		double totalEnergy = 0.0;
		double totalProtein = 0.0;
		double totalFat = 0.0;
		double totalCarbs = 0.0;

		for (MealLogDto mealLog : mealLogs) {
			totalEnergy += mealLog.getEnergy();
			totalProtein += mealLog.getProtein();
			totalFat += mealLog.getFat();
			totalCarbs += mealLog.getCarbs();
		}

		request.setAttribute("mealLogs", mealLogs);
		request.setAttribute("totalCount", mealLogs.size());
		request.setAttribute("totalEnergy", totalEnergy);
		request.setAttribute("totalProtein", totalProtein);
		request.setAttribute("totalFat", totalFat);
		request.setAttribute("totalCarbs", totalCarbs);
		request.setAttribute("searchKeyword", request.getParameter("q"));
		forward(request, response, "/WEB-INF/diet/list.jsp");
	}

	private void searchFoods(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String keyword = request.getParameter("q");
		if (keyword == null) keyword = "";
		StringBuilder json = new StringBuilder("[");
		try {
			List<FoodDto> foods = keyword.isBlank()
					? new ArrayList<>()
					: foodDao.searchByName(keyword, 12);
			for (int i = 0; i < foods.size(); i++) {
				FoodDto food = foods.get(i);
				if (i > 0) json.append(',');
				json.append('{')
					.append("\"foodCode\":\"").append(escapeJson(food.getFoodCode())).append("\",")
					.append("\"foodName\":\"").append(escapeJson(food.getFoodName())).append("\",")
					.append("\"category\":\"").append(escapeJson(food.getCategory())).append("\",")
					.append("\"referenceAmount\":\"").append(escapeJson(food.getReferenceAmount())).append("\",")
					.append("\"energy\":").append(food.getEnergy()).append(',')
					.append("\"protein\":").append(food.getProtein()).append(',')
					.append("\"fat\":").append(food.getFat()).append(',')
					.append("\"carbs\":").append(food.getCarbs()).append(',')
					.append("\"sugar\":").append(food.getSugar()).append(',')
					.append("\"sodium\":").append(food.getSodium()).append(',')
					.append("\"saturatedFat\":").append(food.getSaturatedFat()).append(',')
					.append("\"transFat\":").append(food.getTransFat())
					.append('}');
			}
		} catch (SQLException e) {
			response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		json.append(']');
		writeJson(response, json.toString());
	}

	private void saveMealLog(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			redirect(request, response, "/member?action=loginForm");
			return;
		}

		String foodCode = request.getParameter("foodCode");
		String foodName = request.getParameter("foodName");

		try {
			FoodDto food = null;
			if (foodCode != null && !foodCode.isBlank()) {
				food = foodDao.findByCode(foodCode);
			}

			MealLogDto mealLog = new MealLogDto();
			mealLog.setUserUid(loginUser.getUid());
			mealLog.setFoodCode(foodCode == null || foodCode.isBlank() ? null : foodCode);
			mealLog.setFoodName(resolveFoodName(foodName, food));
			mealLog.setEnergy(parseDouble(request.getParameter("energy"), food == null ? 0.0 : food.getEnergy()));
			mealLog.setProtein(parseDouble(request.getParameter("protein"), food == null ? 0.0 : food.getProtein()));
			mealLog.setFat(parseDouble(request.getParameter("fat"), food == null ? 0.0 : food.getFat()));
			mealLog.setCarbs(parseDouble(request.getParameter("carbs"), food == null ? 0.0 : food.getCarbs()));
			mealLog.setSugar(parseDouble(request.getParameter("sugar"), food == null ? 0.0 : food.getSugar()));
			mealLog.setSodium(parseDouble(request.getParameter("sodium"), food == null ? 0.0 : food.getSodium()));
			mealLog.setSaturatedFat(parseDouble(request.getParameter("saturatedFat"), food == null ? 0.0 : food.getSaturatedFat()));
			mealLog.setTransFat(parseDouble(request.getParameter("transFat"), food == null ? 0.0 : food.getTransFat()));
			mealLogService.addMealLog(mealLog);
			session.setAttribute("alertMsg", mealLog.getFoodName() + "이 기록되었습니다.");
		} catch (SQLException e) {
			e.printStackTrace();
			session.setAttribute("alertMsg", "식단 저장 중 오류가 발생했습니다.");
		}

		redirect(request, response, "/diet");
	}

	private String escapeJson(String value) {
		return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
	}

	private double parseDouble(String value, double defaultValue) {
		try {
			return Double.parseDouble(value);
		} catch (Exception e) {
			return defaultValue;
		}
	}

	private String resolveFoodName(String foodName, FoodDto food) {
		if (foodName != null && !foodName.isBlank()) {
			return foodName.trim();
		}
		return food == null ? "직접 입력 식단" : food.getFoodName();
	}
}