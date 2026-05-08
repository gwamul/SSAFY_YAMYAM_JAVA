package com.ssafy.prj.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.prj.model.dto.MealLogDto;
import com.ssafy.prj.util.DBUtil;

@Repository
public class MealLogDaoImpl implements MealLogDao {

	private static MealLogDaoImpl instance = new MealLogDaoImpl();

	private MealLogDaoImpl() {}

	public static MealLogDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<MealLogDto> selectByUserId(String userId) throws SQLException {
		List<MealLogDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT ml.meal_id, ml.user_uid, ml.food_name, ml.food_code, ml.energy, ml.protein, ml.fat, ml.carbs, ml.sugar, ml.sodium, ml.saturated_fat, ml.trans_fat, ml.created_date "
					+ "FROM meal_logs ml "
					+ "JOIN members m ON ml.user_uid = m.uid "
					+ "WHERE m.user_id = ? "
					+ "ORDER BY ml.created_date DESC, ml.meal_id DESC";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, userId);
			rs = pstmt.executeQuery();

			while (rs.next()) {
				list.add(makeDto(rs));
			}
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}

		return list;
	}

	@Override
	public void insert(MealLogDto mealLog) throws SQLException {
		Connection conn = null;
		PreparedStatement pstmt = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "INSERT INTO meal_logs (user_uid, food_name, food_code, energy, protein, fat, carbs, sugar, sodium, saturated_fat, trans_fat) "
					+ "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, mealLog.getUserUid());
			pstmt.setString(2, mealLog.getFoodName());
			pstmt.setString(3, mealLog.getFoodCode());
			pstmt.setDouble(4, mealLog.getEnergy());
			pstmt.setDouble(5, mealLog.getProtein());
			pstmt.setDouble(6, mealLog.getFat());
			pstmt.setDouble(7, mealLog.getCarbs());
			pstmt.setDouble(8, mealLog.getSugar());
			pstmt.setDouble(9, mealLog.getSodium());
			pstmt.setDouble(10, mealLog.getSaturatedFat());
			pstmt.setDouble(11, mealLog.getTransFat());
			pstmt.executeUpdate();
		} finally {
			DBUtil.close(pstmt, conn);
		}
	}

	private MealLogDto makeDto(ResultSet rs) throws SQLException {
		MealLogDto mealLog = new MealLogDto();
		mealLog.setMealId(rs.getLong("meal_id"));
		mealLog.setUserUid(rs.getString("user_uid"));
		mealLog.setFoodName(rs.getString("food_name"));
		mealLog.setFoodCode(rs.getString("food_code"));
		mealLog.setEnergy(rs.getDouble("energy"));
		mealLog.setProtein(rs.getDouble("protein"));
		mealLog.setFat(rs.getDouble("fat"));
		mealLog.setCarbs(rs.getDouble("carbs"));
		mealLog.setSugar(rs.getDouble("sugar"));
		mealLog.setSodium(rs.getDouble("sodium"));
		mealLog.setSaturatedFat(rs.getDouble("saturated_fat"));
		mealLog.setTransFat(rs.getDouble("trans_fat"));
		mealLog.setCreatedDate(rs.getTimestamp("created_date"));
		return mealLog;
	}
}