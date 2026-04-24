package com.ssafy.prj.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.MemberDto;

public class MemberDaoImpl implements MemberDao {

	private static List<MemberDto> members = new ArrayList<>();
	
	static {
		// 초기 데이터 추가: id, password, name, birthDate, gender, height, weight, disease, image
		members.add(new MemberDto("admin", "1234", "관리자", "1990-01-01", "male", 180, 75, "없음", null));
		members.add(new MemberDto("ssafy", "1234", "싸피", "1995-05-05", "female", 165, 55, "없음", null));
	}
	
	private static MemberDao instance;
	private MemberDaoImpl() {
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
		MemberDto target = selectMember(targetId);
		
		if (me != null && target != null) {
			// 내가 팔로우하는 사람 목록에 타겟 추가
			if (!me.getFollowing().contains(targetId)) {
				me.getFollowing().add(targetId);
			}
			// 타겟의 팔로워 목록에 나를 추가
			if (!target.getFollowers().contains(myId)) {
				target.getFollowers().add(myId);
			}
		}
	}

	@Override
	public void removeFollower(String myId, String targetId) {
		MemberDto me = selectMember(myId);
		MemberDto target = selectMember(targetId);
		
		if (me != null && target != null) {
			// 내 팔로잉 목록에서 타겟 제거
			me.getFollowing().remove(targetId);
			// 타겟의 팔로워 목록에서 나를 제거
			target.getFollowers().remove(myId);
		}
	}

}






