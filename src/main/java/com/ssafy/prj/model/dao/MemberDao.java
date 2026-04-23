package com.ssafy.prj.model.dao;

import com.ssafy.prj.model.dto.MemberDto;

public interface MemberDao {
	void insertMember(MemberDto member);

	MemberDto selectLogin(MemberDto member);
}
