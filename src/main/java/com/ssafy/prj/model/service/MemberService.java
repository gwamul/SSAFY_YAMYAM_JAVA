package com.ssafy.prj.model.service;

import java.util.List;
import com.ssafy.prj.model.dto.MemberDto;

public interface MemberService {
	void join(MemberDto member);

	MemberDto login(MemberDto member);

	MemberDto getMember(String id);

	List<MemberDto> getMemberList();

	void modifyMember(MemberDto member);

	void removeMember(String id);

	void addFollower(String myId, String targetId);

	void removeFollower(String myId, String targetId);
}
