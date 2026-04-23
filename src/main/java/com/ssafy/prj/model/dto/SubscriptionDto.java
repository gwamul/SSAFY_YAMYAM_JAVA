package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.time.LocalDate;

/**
 * 사용자가 구독(참여)한 챌린지 정보 DTO.
 *
 * 기존 JS 구조:
 *   { ...challenge, startDate, successDays, missions }
 *
 * 현재: HttpSession 에 List<SubscriptionDto> 로 저장.
 * 추후 DB 전환 시: 사용자 ID + 챌린지 ID 복합키 테이블로 매핑하면 됩니다.
 */
public class SubscriptionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private int challengeId;
    private String challengeName;
    private String difficulty;
    private int duration;           // 전체 일수
    private int targetCalories;
    private LocalDate startDate;    // 구독 시작일
    private int successDays;        // 연속 달성 일수 (스트릭)

    public SubscriptionDto() {}

    public SubscriptionDto(ChallengeDto challenge) {
        this.challengeId    = challenge.getId();
        this.challengeName  = challenge.getName();
        this.difficulty     = challenge.getDifficulty();
        this.duration       = challenge.getDuration();
        this.targetCalories = challenge.getTargetCalories();
        this.startDate      = LocalDate.now();
        this.successDays    = 0;
    }

    /** 오늘이 챌린지 몇 일차인지 계산 (1-based, 최대 duration) */
    public int getCurrentDay() {
        if (startDate == null) return 1;
        long elapsed = LocalDate.now().toEpochDay() - startDate.toEpochDay();
        int day = (int) elapsed + 1;
        return Math.min(Math.max(day, 1), duration);
    }

    /** 전체 진행률(%) */
    public int getProgressPercent() {
        if (duration <= 0) return 0;
        return (int) Math.round((getCurrentDay() / (double) duration) * 100);
    }

    // ── Getters & Setters ─────────────────────────────────────────

    public int getChallengeId() { return challengeId; }
    public void setChallengeId(int challengeId) { this.challengeId = challengeId; }

    public String getChallengeName() { return challengeName; }
    public void setChallengeName(String challengeName) { this.challengeName = challengeName; }

    public String getDifficulty() { return difficulty; }
    public void setDifficulty(String difficulty) { this.difficulty = difficulty; }

    public int getDuration() { return duration; }
    public void setDuration(int duration) { this.duration = duration; }

    public int getTargetCalories() { return targetCalories; }
    public void setTargetCalories(int targetCalories) { this.targetCalories = targetCalories; }

    public LocalDate getStartDate() { return startDate; }
    public void setStartDate(LocalDate startDate) { this.startDate = startDate; }

    public int getSuccessDays() { return successDays; }
    public void setSuccessDays(int successDays) { this.successDays = successDays; }
}
