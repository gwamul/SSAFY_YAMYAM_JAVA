package com.ssafy.prj.controller;

import java.io.IOException;

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
		default -> response.sendError(HttpServletResponse.SC_NOT_FOUND);
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		HttpSession session = request.getSession();
		session.invalidate();
//		response.sendRedirect(request.getContextPath() + "/main");
		redirect(request, response, "/main");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String saveId = request.getParameter("saveId");		
		
		MemberDto member = new MemberDto();
		member.setId(id);
		member.setPassword(password);
		MemberDto loginUser = memberService.login(member);
		if (loginUser == null) {
			request.getSession().setAttribute("alertMsg", "아이디 또는 패스워드가 잘못되었습니다.");
//			response.sendRedirect(request.getContextPath() + "/member?action=loginForm");
			redirect(request, response, "/member?action=loginForm");
		} else {
			if (saveId != null) {
				setCookie("saveId", id, 60 * 60 * 24, null, response);
			} else {
				setCookie("saveId", id, 0, null, response);
			}
			
			HttpSession session = request.getSession();
			session.setAttribute("loginUser", loginUser);
//			response.sendRedirect(request.getContextPath() + "/main");
			redirect(request, response,  "/main");
		}
	}

	private void loginForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher("/member/login.jsp");
//		rd.forward(request, response);
		forward(request, response, "/WEB-INF/member/login.jsp");		
	}

	private void join(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String name = request.getParameter("name");
		MemberDto member = new MemberDto(id, password, name);
		memberService.join(member);
//		response.sendRedirect(request.getContextPath() + "/member?action=loginForm");
		redirect(request, response, "/member?action=loginForm");
	}

	private void joinForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher("/member/join.jsp");
//		rd.forward(request, response);
		forward(request, response, "/WEB-INF/member/join.jsp");
	}

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
}






