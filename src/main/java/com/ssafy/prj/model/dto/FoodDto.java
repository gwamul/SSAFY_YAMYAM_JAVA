package com.ssafy.prj.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 음식DB.csv 한 행에 대응하는 DTO.
 *
 * CSV 컬럼 순서:
 *   식품코드, 식품명, 식품대분류명, 영양성분함량기준량,
 *   에너지(kcal), 단백질(g), 지방(g), 탄수화물(g),
 *   당류(g), 나트륨(mg), 포화지방산(g), 트랜스지방산(g), 식품중량
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class FoodDto{

//	private String u
    private String foodCode;       // 식품코드
    private String foodName;       // 식품명
    private String category;       // 식품대분류명
    private String referenceAmount;// 영양성분함량기준량 (예: "100g")
    private double energy;         // 에너지(kcal)
    private double protein;        // 단백질(g)
    private double fat;            // 지방(g)
    private double carbs;          // 탄수화물(g)
    private double sugar;          // 당류(g)
    private double sodium;         // 나트륨(mg)
    private double saturatedFat;   // 포화지방산(g)
    private double transFat;       // 트랜스지방산(g)
    private String foodWeight;     // 식품중량 (예: "900g") — 문자열 그대로 보존
  
}
