package com.ssafy.prj.model.dao;



import java.sql.SQLException;
import java.util.List;

import com.ssafy.prj.model.dto.FoodDto;

/**
 * 음식 데이터 접근 인터페이스.
 *
 * 현재 구현체: FoodDaoCsvImpl (CSV 파일 기반)
 * 추후 DB 전환 시: FoodDaoDbImpl 을 작성하고 Service 에서 교체만 하면 됩니다.
 */
public interface FoodDao {

    List<FoodDto> selectAll() throws SQLException;
    
    FoodDto selectOne(String food_code) throws SQLException;

    List<FoodDto> searchByName(String keyword, int limit) throws SQLException;

    FoodDto findByCode(String foodCode) throws SQLException;
}
