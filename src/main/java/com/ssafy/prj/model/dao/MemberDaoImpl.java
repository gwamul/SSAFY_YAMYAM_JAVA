package com.ssafy.prj.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.MemberDto;

public class MemberDaoImpl implements MemberDao {

	private List<MemberDto> members = new ArrayList<>();
	
	private static MemberDao instance;
	private MemberDaoImpl() {
		// 바뀐 생성자에 맞춰 초기 데이터 추가: id, password, name, birthDate, gender, height, weight, disease, image
		members.add(new MemberDto("admin", "1234", "관리자", "1990-01-01", "male", 180, 75, "없음", null));
		members.add(new MemberDto("ssafy", "1234", "싸피", "1995-05-05", "female", 165, 55, "없음", null));
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

	@Override
	public MemberDto selectMember(String id) {
		for (MemberDto m : members) {
			if (m.getId().equals(id)) return m;
		}
		return null;
	}

	@Override
	public List<MemberDto> selectAll() {
		return members;
	}

	@Override
	public void updateMember(MemberDto member) {
		for (MemberDto m : members) {
			if (m.getId().equals(member.getId())) {
				if (member.getPassword() != null && !member.getPassword().isEmpty()) {
					m.setPassword(member.getPassword());
				}
				m.setName(member.getName());
				m.setBirthDate(member.getBirthDate());
				m.setGender(member.getGender());
				m.setHeight(member.getHeight());
				m.setWeight(member.getWeight());
				m.setDisease(member.getDisease());
				if (member.getImage() != null) {
					m.setImage(member.getImage());
				}
				break;
			}
		}
	}

	@Override
	public void deleteMember(String id) {
		for (int i = 0; i < members.size(); i++) {
			if (members.get(i).getId().equals(id)) {
				members.remove(i);
				break;
			}
		}
	}

	@Override
	public void addFollower(String myId, String targetId) {
		MemberDto me = selectMember(myId);
		if (me != null && !me.getFollowers().contains(targetId)) {
			me.getFollowers().add(targetId);
		}
	}

	@Override
	public void removeFollower(String myId, String targetId) {
		MemberDto me = selectMember(myId);
		if (me != null) {
			me.getFollowers().remove(targetId);
		}
	}

}






