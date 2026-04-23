package com.ssafy.prj.util;

import java.io.IOException;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface ControllerHelper {
	default String getActionParameter(HttpServletRequest request, HttpServletResponse response) throws IOException {
		return getActionParameter(request, response, null);
	}

	default String getActionParameter(HttpServletRequest request, HttpServletResponse response, String _default)
			throws IOException {
		// action 파라미터를 추출하여 반환
		String action = request.getParameter("action");
		if (action == null || action.isBlank()) {
			if (_default != null) {
				action = _default;
			} else {
				action = "index";
			}
		}
		System.out.println("action: " + action);
		return action;
	}

	// TODO: 01. redirect와 forward를 처리할 수 있는 utility method로 redirect, forward를
	// 작성하세요.
	// http로 시작하면 context를 개입시키지 않고 그렇지 않으면 개입시킨다.
	public default void redirect(HttpServletRequest request, HttpServletResponse response, String path)
			throws IOException {
		response.sendRedirect(request.getContextPath() + path);
	}

	public default void forward(HttpServletRequest request, HttpServletResponse response, String path)
			throws ServletException, IOException {
		request.getRequestDispatcher(path).forward(request, response);
	}

	public default void setCookie(String name, String value, int maxAge, String path, HttpServletResponse response) {
		Cookie cookie = new Cookie(name, value);
		
		// 쿠키의 유효시간 설정
		cookie.setMaxAge(maxAge);
		// 패스 설정
		// http://localhost:8888/prj/test/board
		// http://localhost:8888/prj/test/bbb/board
		// http://localhost:8888/prj/aaaa/board
		if (path != null) {
			cookie.setPath(path);
		}
		
		response.addCookie(cookie);
	}
	
	
	// END
}



