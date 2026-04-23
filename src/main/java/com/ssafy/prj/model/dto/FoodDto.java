package com.ssafy.prj.model.dto;

import java.io.Serializable;

/**
 * 음식DB.csv 한 행에 대응하는 DTO.
 *
 * CSV 컬럼 순서:
 *   식품코드, 식품명, 식품대분류명, 영양성분함량기준량,
 *   에너지(kcal), 단백질(g), 지방(g), 탄수화물(g),
 *   당류(g), 나트륨(mg), 포화지방산(g), 트랜스지방산(g), 식품중량
 */
public class FoodDto implements Serializable {

    private static final long serialVersionUID = 1L;

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

    public FoodDto() {}

    public FoodDto(String foodCode, String foodName, String category,
                   String referenceAmount, double energy, double protein,
                   double fat, double carbs, double sugar, double sodium,
                   double saturatedFat, double transFat, String foodWeight) {
        this.foodCode = foodCode;
        this.foodName = foodName;
        this.category = category;
        this.referenceAmount = referenceAmount;
        this.energy = energy;
        this.protein = protein;
        this.fat = fat;
        this.carbs = carbs;
        this.sugar = sugar;
        this.sodium = sodium;
        this.saturatedFat = saturatedFat;
        this.transFat = transFat;
        this.foodWeight = foodWeight;
    }

    // ── Getters & Setters ──────────────────────────────────────────

    public String getFoodCode() { return foodCode; }
    public void setFoodCode(String foodCode) { this.foodCode = foodCode; }

    public String getFoodName() { return foodName; }
    public void setFoodName(String foodName) { this.foodName = foodName; }

    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }

    public String getReferenceAmount() { return referenceAmount; }
    public void setReferenceAmount(String referenceAmount) { this.referenceAmount = referenceAmount; }

    public double getEnergy() { return energy; }
    public void setEnergy(double energy) { this.energy = energy; }

    public double getProtein() { return protein; }
    public void setProtein(double protein) { this.protein = protein; }

    public double getFat() { return fat; }
    public void setFat(double fat) { this.fat = fat; }

    public double getCarbs() { return carbs; }
    public void setCarbs(double carbs) { this.carbs = carbs; }

    public double getSugar() { return sugar; }
    public void setSugar(double sugar) { this.sugar = sugar; }

    public double getSodium() { return sodium; }
    public void setSodium(double sodium) { this.sodium = sodium; }

    public double getSaturatedFat() { return saturatedFat; }
    public void setSaturatedFat(double saturatedFat) { this.saturatedFat = saturatedFat; }

    public double getTransFat() { return transFat; }
    public void setTransFat(double transFat) { this.transFat = transFat; }

    public String getFoodWeight() { return foodWeight; }
    public void setFoodWeight(String foodWeight) { this.foodWeight = foodWeight; }

    @Override
    public String toString() {
        return "FoodDto{" + foodCode + ", " + foodName + ", " + energy + "kcal}";
    }
}
