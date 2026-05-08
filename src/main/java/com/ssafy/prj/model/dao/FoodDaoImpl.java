package com.ssafy.prj.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Repository;

import com.ssafy.prj.model.dto.FoodDto;
import com.ssafy.prj.util.DBUtil;


@Repository
public class FoodDaoImpl implements FoodDao{

	private static FoodDaoImpl instance = new FoodDaoImpl();
	
	private FoodDaoImpl() {}
	
	public static FoodDaoImpl getInstance() {
		return instance;
	}

	@Override
	public List<FoodDto> selectAll() throws SQLException {
		List<FoodDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try { 
			conn = DBUtil.getConnection();
			String sql = "SELECT food_code, food_name, category, reference_amount, energy, protein, fat, carbs, sugar, sodium, saturated_fat, trans_fat, food_weight FROM foods";
			pstmt = conn.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()) {
				list.add(makeDto(rs));
			}
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		return list;
	}
	
	@Override
	public FoodDto selectOne(String food_code) throws SQLException {
		FoodDto food = null;
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		
		try { 
			conn = DBUtil.getConnection();
			String sql = "SELECT food_code, food_name, category, reference_amount, energy, protein, fat, carbs, sugar, sodium, saturated_fat, trans_fat, food_weight FROM foods where food_code = ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, food_code);
			rs = pstmt.executeQuery();
			
			if(rs.next()) {
				food = makeDto(rs);
			}
		} finally {
			DBUtil.close(rs, pstmt, conn);
		}
		
		return food;
	}

	@Override
	public List<FoodDto> searchByName(String keyword, int limit) throws SQLException {
		List<FoodDto> list = new ArrayList<>();
		Connection conn = null;
		PreparedStatement pstmt = null;
		ResultSet rs = null;

		try {
			conn = DBUtil.getConnection();
			String sql = "SELECT food_code, food_name, category, reference_amount, energy, protein, fat, carbs, sugar, sodium, saturated_fat, trans_fat, food_weight "
					+ "FROM foods WHERE food_name LIKE ? ORDER BY food_name LIMIT ?";
			pstmt = conn.prepareStatement(sql);
			pstmt.setString(1, "%" + keyword + "%");
			pstmt.setInt(2, limit);
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
	public FoodDto findByCode(String foodCode) throws SQLException {
		return selectOne(foodCode);
	}
	
	
	
	
	
	
	
	
	

	private FoodDto makeDto(ResultSet rs) throws SQLException {
		// TODO Auto-generated method stub
		FoodDto food = new FoodDto();
        food.setFoodCode(rs.getString("food_code"));
        food.setFoodName(rs.getString("food_name"));
        food.setCategory(rs.getString("category"));
        food.setReferenceAmount(rs.getString("reference_amount"));
        food.setEnergy(rs.getDouble("energy"));
        food.setProtein(rs.getDouble("protein"));
        food.setFat(rs.getDouble("fat"));
        food.setCarbs(rs.getDouble("carbs"));
        food.setSugar(rs.getDouble("sugar"));
        food.setSodium(rs.getDouble("sodium"));
        food.setSaturatedFat(rs.getDouble("saturated_fat"));
        food.setTransFat(rs.getDouble("trans_fat"));
        food.setFoodWeight(rs.getString("food_weight"));
        return food;
	}

	
	
	
	
	
	
}
