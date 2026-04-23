package com.ssafy.prj.model.service;

import java.util.List;
import com.ssafy.prj.model.dao.MemberDao;
import com.ssafy.prj.model.dao.MemberDaoImpl;
import com.ssafy.prj.model.dto.MemberDto;

public class MemberServiceImpl implements MemberService {

	private MemberDao memberDao;
	private static MemberService instance;
	private MemberServiceImpl() {
		memberDao = MemberDaoImpl.getInstance();
	}
	public static MemberService getInstance() {
		if (instance == null) {
			instance = new MemberServiceImpl();
		}
		return instance;
	}
	
	@Override
	public void join(MemberDto member) {
		memberDao.insertMember(member);
	}
	
	@Override
	public MemberDto login(MemberDto member) {
		return memberDao.selectLogin(member);
	}

	@Override
	public MemberDto getMember(String id) {
		return memberDao.selectMember(id);
	}

	@Override
	public List<MemberDto> getMemberList() {
		return memberDao.selectAll();
	}

	@Override
	public void modifyMember(MemberDto member) {
		memberDao.updateMember(member);
	}

	@Override
	public void removeMember(String id) {
		memberDao.deleteMember(id);
	}

	@Override
	public void addFollower(String myId, String targetId) {
		memberDao.addFollower(myId, targetId);
	}

	@Override
	public void removeFollower(String myId, String targetId) {
		memberDao.removeFollower(myId, targetId);
	}

}





