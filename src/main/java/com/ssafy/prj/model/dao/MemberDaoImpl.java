package com.ssafy.prj.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.MemberDto;
import com.ssafy.prj.util.DBUtil;

public class MemberDaoImpl implements MemberDao {

	
//	static {
//		// 초기 데이터 추가: id, password, name, birthDate, gender, height, weight, disease, image
//		insertMember(new MemberDto("admin", "1234", "관리자", "1990-01-01", "male", 180, 75, "없음", null));
//		members.add(new MemberDto("ssafy", "1234", "싸피", "1995-05-05", "female", 165, 55, "없음", null));
//	}
	
	private static MemberDao instance;
	
	private MemberDaoImpl() {}
	
	public static MemberDao getInstance() {
		if (instance == null) {
			instance = new MemberDaoImpl();
		}
		return instance;
	}
	
	int mem_cnt = 1;

	
	@Override
    public void insertMember(MemberDto member) {
		String sql = "INSERT INTO members (uid, user_id, password, name, birth_date, gender, height, weight, disease, image) "
	               + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";        
		try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
        	pstmt.setString(1, "" + mem_cnt++ );       // 추가된 UID
            pstmt.setString(2, member.getUserId());    // DTO의 userId
            pstmt.setString(3, member.getPassword());
            pstmt.setString(4, member.getName());
            pstmt.setString(5, member.getBirthDate());
            pstmt.setString(6, member.getGender());
            pstmt.setDouble(7, member.getHeight());
            pstmt.setDouble(8, member.getWeight());
            pstmt.setString(9, member.getDisease());
            pstmt.setString(10, member.getImage());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public MemberDto selectLogin(MemberDto member) {
        String sql = "SELECT * FROM members WHERE user_id = ? AND password = ?";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getUserId());
            pstmt.setString(2, member.getPassword());
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return makeDto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public MemberDto selectMember(String id) {
        String sql = "SELECT * FROM members WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) return makeDto(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<MemberDto> selectAll() {
        List<MemberDto> list = new ArrayList<>();
        String sql = "SELECT * FROM members";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            while (rs.next()) {
                list.add(makeDto(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    @Override
    public void updateMember(MemberDto member) {
        String sql = "UPDATE members SET name=?, birth_date=?, gender=?, height=?, weight=?, disease=?, image=? WHERE user_id=?";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, member.getName());
            pstmt.setString(2, member.getBirthDate());
            pstmt.setString(3, member.getGender());
            pstmt.setDouble(4, member.getHeight());
            pstmt.setDouble(5, member.getWeight());
            pstmt.setString(6, member.getDisease());
            pstmt.setString(7, member.getImage());
            pstmt.setString(8, member.getUserId());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deleteMember(String id) {
        String sql = "DELETE FROM members WHERE user_id = ?";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, id);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addFollower(String myId, String targetId) {
        // follower_id가 나를 팔로우하는 사람, following_id가 내가 팔로우하는 대상이라 가정
        String sql = "INSERT INTO follow (follower_id, following_id) VALUES (?, ?)";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, myId);
            pstmt.setString(2, targetId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeFollower(String myId, String targetId) {
        String sql = "DELETE FROM follow WHERE follower_id = ? AND following_id = ?";
        try (Connection conn = DBUtil.getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, myId);
            pstmt.setString(2, targetId);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
	
	
	private MemberDto makeDto(ResultSet rs) throws SQLException {
        MemberDto m = new MemberDto();
        m.setUid(rs.getString("uid"));
        m.setUserId(rs.getString("user_id"));
        m.setPassword(rs.getString("password"));
        m.setName(rs.getString("name"));
        m.setBirthDate(rs.getString("birth_date"));
        m.setGender(rs.getString("gender"));
        m.setHeight(rs.getDouble("height"));
        m.setWeight(rs.getDouble("weight"));
        m.setDisease(rs.getString("disease"));
        m.setImage(rs.getString("image"));
        return m;
    }

}






