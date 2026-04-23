package com.ssafy.prj.model.dao;

import java.util.List;

import com.ssafy.prj.model.dto.ChallengeDto;

/**
 * 챌린지 데이터 접근 인터페이스.
 *
 * 현재 구현체 : ChallengeDaoCsvImpl  (CSV 파일 직렬화 기반)
 * 추후 전환   : ChallengeDaoDbImpl   (JDBC/MyBatis)
 *
 * Service 레이어는 이 인터페이스만 바라보기 때문에
 * 구현체를 교체해도 상위 코드를 변경할 필요가 없습니다.
 */
public interface ChallengeDao {

    /** 전체 챌린지 목록 조회 */
    List<ChallengeDto> selectAll();

    /** 단건 조회 */
    ChallengeDto selectById(int id);

    /** 새 챌린지 저장 */
    void insert(ChallengeDto challenge);

    /** 챌린지 삭제 */
    void delete(int id);
}
