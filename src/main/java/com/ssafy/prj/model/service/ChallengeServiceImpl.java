package com.ssafy.prj.model.service;



import java.util.List;
import java.util.stream.Collectors;

import com.ssafy.prj.model.dao.ChallengeDao;
import com.ssafy.prj.model.dao.ChallengeDaoFileImpl;
import com.ssafy.prj.model.dao.FoodDao;
import com.ssafy.prj.model.dao.FoodDaoCsvImpl;
import com.ssafy.prj.model.dto.ChallengeDto;
import com.ssafy.prj.model.dto.FoodDto;
import com.ssafy.prj.model.dto.SubscriptionDto;

public class ChallengeServiceImpl implements ChallengeService {

    // ── 싱글톤 ───────────────────────────────────────────────────────
    private static ChallengeService instance;

    public static synchronized ChallengeService getInstance() {
        if (instance == null) instance = new ChallengeServiceImpl();
        return instance;
    }

    // ── DAO 주입 ─────────────────────────────────────────────────────
    private final ChallengeDao challengeDao;
    private final FoodDao foodDao;

    private ChallengeServiceImpl() {
        // DB 전환 시: ChallengeDaoDbImpl.getInstance() 로 교체
        challengeDao = ChallengeDaoFileImpl.getInstance();
        foodDao      = FoodDaoCsvImpl.getInstance();
    }

    // ── ChallengeService 구현 ────────────────────────────────────────

    @Override
    public List<ChallengeDto> getAllChallenges() {
        return challengeDao.selectAll();
    }

    @Override
    public List<ChallengeDto> getFilteredChallenges(String difficulty, Integer duration) {
        return challengeDao.selectAll().stream()
            .filter(c -> difficulty == null || difficulty.isBlank()
                         || c.getDifficulty().equals(difficulty))
            .filter(c -> duration == null
                         || c.getDuration() == duration)
            .collect(Collectors.toList());
    }

    @Override
    public ChallengeDto getChallenge(int id) {
        return challengeDao.selectById(id);
    }

    @Override
    public void createChallenge(ChallengeDto challenge) {
        challengeDao.insert(challenge);
    }

    @Override
    public void deleteChallenge(int id) {
        challengeDao.delete(id);
    }

    @Override
    public boolean isSubscribed(List<SubscriptionDto> myList, int challengeId) {
        if (myList == null) return false;
        return myList.stream().anyMatch(s -> s.getChallengeId() == challengeId);
    }

    @Override
    public SubscriptionDto subscribe(ChallengeDto challenge) {
        return new SubscriptionDto(challenge);
    }

    @Override
    public List<FoodDto> searchFood(String keyword) {
        return foodDao.searchByName(keyword, 10);
    }
}
