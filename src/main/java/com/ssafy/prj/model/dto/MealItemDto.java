package com.ssafy.prj.model.dto;

import java.io.Serializable;

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
