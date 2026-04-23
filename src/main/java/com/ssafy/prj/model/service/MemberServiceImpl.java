package com.ssafy.prj.model.service;

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

}





