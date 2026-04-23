package com.ssafy.prj.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.MemberDto;

public class MemberDaoImpl implements MemberDao {

	private List<MemberDto> members = new ArrayList<>();
	
	private static MemberDao instance;
	private MemberDaoImpl() {
		members.add(new MemberDto("admin", "1234", "관리자"));
		members.add(new MemberDto("ssafy", "1234", "싸피"));
	}
	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDaoImpl();
		}
		return instance;
	}
	
	@Override
	public void insertMember(MemberDto member) {
		members.add(member);
	}
	
	@Override
	public MemberDto selectLogin(MemberDto member) {
		for (MemberDto login : members) {
			if (login.getId().equals(member.getId()) 
					&& login.getPassword().equals(member.getPassword())) {
				return login;
			}
		}
		return null;
	}

}






