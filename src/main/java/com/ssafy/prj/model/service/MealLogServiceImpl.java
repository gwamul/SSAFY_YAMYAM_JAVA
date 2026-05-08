package com.ssafy.prj.model.service;

import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

import com.ssafy.prj.model.dao.MealLogDao;
import com.ssafy.prj.model.dao.MealLogDaoImpl;
import com.ssafy.prj.model.dto.MealLogDto;

public class MealLogServiceImpl implements MealLogService {

	private static MealLogService instance;
	private final MealLogDao mealLogDao;

	private MealLogServiceImpl() {
		mealLogDao = MealLogDaoImpl.getInstance();
	}

	public static MealLogService getInstance() {
		if (instance == null) {
			instance = new MealLogServiceImpl();
		}
		return instance;
	}

	@Override
	public List<MealLogDto> getMealLogsByUserId(String userId) {
		try {
			return mealLogDao.selectByUserId(userId);
		} catch (SQLException e) {
			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	@Override
	public void addMealLog(MealLogDto mealLog) {
		try {
			mealLogDao.insert(mealLog);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}