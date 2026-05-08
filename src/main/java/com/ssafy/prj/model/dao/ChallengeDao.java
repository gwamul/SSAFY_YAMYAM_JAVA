package com.ssafy.prj.model.dao;

import java.util.List;

import com.ssafy.prj.model.dto.ChallengeDto;


public interface ChallengeDao {

    List<ChallengeDto> selectAll();
    
    ChallengeDto selectById(int id);
  
    void insert(ChallengeDto challenge);

    void delete(int id);
}
