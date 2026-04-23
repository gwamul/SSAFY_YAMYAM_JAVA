package com.ssafy.prj.model.service;


import java.util.List;

import com.ssafy.prj.model.dto.ChallengeDto;
import com.ssafy.prj.model.dto.FoodDto;
import com.ssafy.prj.model.dto.SubscriptionDto;

/**
 * 챌린지 비즈니스 로직 인터페이스.
 */
public interface ChallengeService {

    // ── 챌린지 목록 / 탐색 ──────────────────────────────────────────

    /** 전체 챌린지 목록 */
    List<ChallengeDto> getAllChallenges();

    /** 난이도·기간 필터 조회 */
    List<ChallengeDto> getFilteredChallenges(String difficulty, Integer duration);

    /** 단건 조회 */
    ChallengeDto getChallenge(int id);

    // ── 챌린지 생성 ──────────────────────────────────────────────────

    /** 새 챌린지 등록 */
    void createChallenge(ChallengeDto challenge);

    /** 챌린지 삭제 */
    void deleteChallenge(int id);

    // ── 구독 ─────────────────────────────────────────────────────────

    /** 이미 구독 중인지 확인 */
    boolean isSubscribed(List<SubscriptionDto> myList, int challengeId);

    /** 챌린지 구독(참여) */
    SubscriptionDto subscribe(ChallengeDto challenge);

    // ── 음식 검색 (챌린지 생성 폼에서 자동완성) ─────────────────────

    /** 음식 이름으로 검색 (최대 10건) */
    List<FoodDto> searchFood(String keyword);
}
