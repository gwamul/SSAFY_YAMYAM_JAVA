package com.ssafy.prj.model.service;

import com.ssafy.prj.model.dto.MemberDto;

public interface MemberService {
	void join(MemberDto member);

	MemberDto login(MemberDto member);
}
