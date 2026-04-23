package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 챌린지 1일치 식단 계획 DTO.
 *
 * 기존 JS 구조:
 *   { day: 1, breakfast: [ MealItemDto ], lunch: [...], dinner: [...] }
 */
public class DayPlanDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int day;
    private List<MealItemDto> breakfast;
    private List<MealItemDto> lunch;
    private List<MealItemDto> dinner;

    public DayPlanDto() {}

    public DayPlanDto(int day, List<MealItemDto> breakfast,
                      List<MealItemDto> lunch, List<MealItemDto> dinner) {
        this.day = day;
        this.breakfast = breakfast;
        this.lunch = lunch;
        this.dinner = dinner;
    }

    /** 해당 일의 총 칼로리 합산 */
    public double getTotalKcal() {
        double total = 0;
        if (breakfast != null) for (MealItemDto m : breakfast) total += m.getKcal();
        if (lunch    != null) for (MealItemDto m : lunch)      total += m.getKcal();
        if (dinner   != null) for (MealItemDto m : dinner)     total += m.getKcal();
        return total;
    }

    // ── Getters & Setters ─────────────────────────────────────────

    public int getDay() { return day; }
    public void setDay(int day) { this.day = day; }

    public List<MealItemDto> getBreakfast() { return breakfast; }
    public void setBreakfast(List<MealItemDto> breakfast) { this.breakfast = breakfast; }

    public List<MealItemDto> getLunch() { return lunch; }
    public void setLunch(List<MealItemDto> lunch) { this.lunch = lunch; }

    public List<MealItemDto> getDinner() { return dinner; }
    public void setDinner(List<MealItemDto> dinner) { this.dinner = dinner; }
}
