package com.ssafy.prj.filter;

import java.io.IOException;

import com.ssafy.prj.model.dto.MemberDto;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

@WebFilter({"/board", "/diet"})
public class SessionFilter extends HttpFilter implements Filter {

	@Override
	public void doFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto)session.getAttribute("loginUser");
		if (member == null) {
			session.setAttribute("alertMsg", "로그인 후 사용 가능합니다.");
			response.sendRedirect(request.getContextPath() + "/member?action=loginForm");
			return;
		}
		chain.doFilter(request, response);
	}
}






