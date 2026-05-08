package com.ssafy.prj.model.service;

import java.util.List;

import com.ssafy.prj.model.dto.MealLogDto;

public interface MealLogService {

	List<MealLogDto> getMealLogsByUserId(String userId);

	void addMealLog(MealLogDto mealLog);
}