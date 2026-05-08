package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.util.List;

/**
 * 챌린지 한 건에 대응하는 DTO.
 *
 * 기존 JS 객체 구조:
 *   { id, name, difficulty, duration, targetCalories, mealPlans: [ DayPlanDto ] }
 */
public class ChallengeDto implements Serializable {

    private static final long serialVersionUID = 1L;

    
    private String creatorId;
    private int id;
    private String name;
    private String difficulty;      // "easy" | "medium" | "hard"
    private int duration;           // 챌린지 기간 (일)
    private int targetCalories;     // 일일 목표 칼로리
    private List<DayPlanDto> mealPlans;

    public ChallengeDto() {}

    public ChallengeDto(int id, String name, String difficulty,
                        int duration, int targetCalories, List<DayPlanDto> mealPlans) {
        this.id = id;
        this.name = name;
        this.difficulty = difficulty;
        this.duration = duration;
        this.targetCalories = targetCalories;
        this.mealPlans = mealPlans;
    }

    // ── Getters & Setters ─────────────────────────────────────────
    
    public String getCreatorId() { return creatorId; }
    public void setCreatorId(String creatorId) { this.creatorId = creatorId; }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getTargetCalories() { return targetCalories; }
    public void setTargetCalories(int targetCalories) { this.targetCalories = targetCalories; }

    public List<DayPlanDto> getMealPlans() { return mealPlans; }
    public void setMealPlans(List<DayPlanDto> mealPlans) { this.mealPlans = mealPlans; }

    @Override
    public String toString() {
        return "ChallengeDto{id=" + id + ", name='" + name + "', difficulty='" + difficulty
                + "', duration=" + duration + ", targetCalories=" + targetCalories + "}";
    }
}
