package com.ssafy.prj.controller;

import java.io.IOException;
import java.util.List;

import com.ssafy.prj.model.dto.MemberDto;
import com.ssafy.prj.model.service.MemberService;
import com.ssafy.prj.model.service.MemberServiceImpl;
import com.ssafy.prj.util.ControllerHelper;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebServlet("/member")
public class MemberController extends HttpServlet implements ControllerHelper {

	private MemberService memberService;
	
	public MemberController () {
		memberService = MemberServiceImpl.getInstance();
	}
	
	@Override
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String action = getActionParameter(request, response);
		switch (action) {
		case "joinForm" -> joinForm(request, response);
		case "join" -> join(request, response);
		case "loginForm" -> loginForm(request, response);
		case "login" -> login(request, response);
		case "logout" -> logout(request, response);
		case "mypage" -> mypage(request, response);
		case "editForm" -> editForm(request, response);
		case "update" -> update(request, response);
		case "delete" -> delete(request, response);
		case "addFollower" -> addFollower(request, response);
		case "removeFollower" -> removeFollower(request, response);
		default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void mypage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String targetId = request.getParameter("id");
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		
		if (targetId == null && loginUser != null) {
			targetId = loginUser.getUserId();
		}
		
		if (targetId == null) {
			redirect(request, response, "/member?action=loginForm");
			return;
		}
		
		MemberDto targetUser = memberService.getMember(targetId);
		request.setAttribute("targetUser", targetUser);
		
		// 팔로워 목록을 보여주기 위해 전체 사용자 목록도 함께 전달 (학습용 단순화)
		List<MemberDto> allUsers = memberService.getMemberList();
		request.setAttribute("allUsers", allUsers);
		
		forward(request, response, "/WEB-INF/member/mypage.jsp");
	}

	private void editForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		if (loginUser == null) {
			redirect(request, response, "/member?action=loginForm");
			return;
		}
		forward(request, response, "/WEB-INF/member/edit.jsp");
	}

	private void update(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String birthDate = request.getParameter("birthDate");
		String gender = request.getParameter("gender");
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));
		String disease = request.getParameter("disease");
		
		MemberDto member = new MemberDto(id, password, name, birthDate, gender, height, weight, disease, null);
		memberService.modifyMember(member);
		
		// 세션 정보 갱신 (중요: 세션 유지 및 최신 데이터 보장)
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", memberService.getMember(id));
		
		redirect(request, response, "/member?action=mypage");
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		if (loginUser != null) {
			memberService.removeMember(loginUser.getUserId());
			session.invalidate();
		}
		redirect(request, response, "/main");
	}

	private void addFollower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetId = request.getParameter("targetId");
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		
		if (loginUser != null && targetId != null && !loginUser.getUserId().equals(targetId)) {
			memberService.addFollower(loginUser.getUserId(), targetId);
			// 내 세션 정보도 최신화 (내가 팔로잉한 목록이 반영되도록)
			session.setAttribute("loginUser", memberService.getMember(loginUser.getUserId()));
		}
		redirect(request, response, "/member?action=mypage&id=" + targetId);
	}

	private void removeFollower(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String targetId = request.getParameter("targetId");
		HttpSession session = request.getSession();
		MemberDto loginUser = (MemberDto) session.getAttribute("loginUser");
		
		if (loginUser != null && targetId != null) {
			memberService.removeFollower(loginUser.getUserId(), targetId);
			// 내 세션 정보도 최신화
			session.setAttribute("loginUser", memberService.getMember(loginUser.getUserId()));
		}
		redirect(request, response, "/member?action=mypage&id=" + targetId);
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
		redirect(request, response, "/main");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String saveId = request.getParameter("saveId");		
		
		MemberDto member = new MemberDto();
		member.setUserId(id);
		member.setPassword(password);
		MemberDto loginUser = memberService.login(member);
		if (loginUser == null) {
			request.getSession().setAttribute("alertMsg", "아이디 또는 패스워드가 잘못되었습니다.");
			redirect(request, response, "/member?action=loginForm");
		} else {
			if (saveId != null) {
				setCookie("saveId", id, 60 * 60 * 24, null, response);
			} else {
				setCookie("saveId", id, 0, null, response);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
			redirect(request, response, "/member?action=mypage");
		}
	}

	private void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward(request, response, "/WEB-INF/member/login.jsp");		
	}

	private void join(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		String birthDate = request.getParameter("birthDate");
		String gender = request.getParameter("gender");
		double height = Double.parseDouble(request.getParameter("height"));
		double weight = Double.parseDouble(request.getParameter("weight"));
		String disease = request.getParameter("disease");
		
		MemberDto member = new MemberDto(id, password, name, birthDate, gender, height, weight, disease, null);
		memberService.join(member);
		redirect(request, response, "/member?action=loginForm");
	}

	private void joinForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		forward(request, response, "/WEB-INF/member/join.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}






