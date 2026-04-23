package com.ssafy.prj.model.dao;

import java.util.List;
import com.ssafy.prj.model.dto.MemberDto;

public interface MemberDao {
	void insertMember(MemberDto member);

	MemberDto selectLogin(MemberDto member);

	MemberDto selectMember(String id);

	List<MemberDto> selectAll();

	void updateMember(MemberDto member);

	void deleteMember(String id);

	void addFollower(String myId, String targetId);

	void removeFollower(String myId, String targetId);
}
