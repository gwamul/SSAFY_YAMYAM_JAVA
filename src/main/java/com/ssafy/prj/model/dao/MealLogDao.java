package com.ssafy.prj.model.dao;

import java.sql.SQLException;
import java.util.List;

import com.ssafy.prj.model.dto.MealLogDto;

public interface MealLogDao {

	List<MealLogDto> selectByUserId(String userId) throws SQLException;

	void insert(MealLogDto mealLog) throws SQLException;
}