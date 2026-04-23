package com.ssafy.prj.model.dao;



import java.util.List;

import com.ssafy.prj.model.dto.FoodDto;

/**
 * 음식 데이터 접근 인터페이스.
 *
 * 현재 구현체: FoodDaoCsvImpl (CSV 파일 기반)
 * 추후 DB 전환 시: FoodDaoDbImpl 을 작성하고 Service 에서 교체만 하면 됩니다.
 */
public interface FoodDao {

    /**
     * 이름에 keyword 가 포함된 음식 목록을 반환합니다.
     * @param keyword 검색어 (대소문자 무관)
     * @param limit   최대 반환 개수
     */
    List<FoodDto> searchByName(String keyword, int limit);

    /**
     * 음식 코드로 단일 항목을 조회합니다.
     */
    FoodDto findByCode(String foodCode);
}
