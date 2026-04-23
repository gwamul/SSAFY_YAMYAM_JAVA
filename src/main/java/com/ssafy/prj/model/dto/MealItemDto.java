package com.ssafy.prj.model.dto;

import java.io.Serializable;

/**
 * 식단(아침/점심/저녁) 안의 음식 한 항목 DTO.
 *
 * 기존 JS 구조: { name: "국밥", kcal: 137 }
 */
public class MealItemDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private double kcal;

    public MealItemDto() {}

    public MealItemDto(String name, double kcal) {
        this.name = name;
        this.kcal = kcal;
    }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getKcal() { return kcal; }
    public void setKcal(double kcal) { this.kcal = kcal; }

    @Override
    public String toString() {
        return "MealItemDto{name='" + name + "', kcal=" + kcal + "}";
    }
}
