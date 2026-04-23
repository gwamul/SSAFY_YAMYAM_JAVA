package com.ssafy.prj.controller;

import java.io.IOException;
import java.util.List;

import com.ssafy.prj.model.dto.BoardDto;
import com.ssafy.prj.model.service.BoardService;
import com.ssafy.prj.model.service.BoardServiceImpl;
import com.ssafy.prj.util.ControllerHelper;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/board")
public class BoardController extends HttpServlet implements ControllerHelper {
	private static final long serialVersionUID = 1L;
    private BoardService boardService;
	
    public BoardController() {
    	boardService = BoardServiceImpl.getInstance();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// /board -> list
		// /board?action=list
		// /board?action=writeForm
		// /board?action=write
		String action = getActionParameter(request, response, "list");
		switch (action) {
		case "list" -> list(request, response);
		case "detail" -> detail(request, response);
		case "writeForm" -> writeForm(request, response);
		case "write" -> write(request, response);
		case "delete" -> delete(request, response);
		}
	}

	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		int no = Integer.parseInt(request.getParameter("no"));
		boardService.delete(no);
//		response.sendRedirect(request.getContextPath() + "/board");
		redirect(request, response, "/board");
	}

	private void write(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// 사용자 보내준 정보 추출
		String title = request.getParameter("title");
		String writer = request.getParameter("writer");
		String content = request.getParameter("content");
		BoardDto board = new BoardDto();
		board.setTitle(title);
		board.setWriter(writer);
		board.setContent(content);
		
		// 일처리 부탁
		boardService.write(board);
		
		// 페이지 이동
//		response.sendRedirect(request.getContextPath() +  "/board?action=list");
		redirect(request, response, "/board?action=list");
		
//		RequestDispatcher rd = request.getRequestDispatcher("/board?action=list");
//		rd.forward(request, response);
		
	}

	private void writeForm(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		RequestDispatcher rd = request.getRequestDispatcher("/board/write.jsp");
//		rd.forward(request, response);
		forward(request, response, "/WEB-INF/board/write.jsp");
	}

	private void detail(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("detail");
		// 필요한 파라미터 꺼내오기
		int no = Integer.parseInt(request.getParameter("no"));
		// 데이터 준비하기
		BoardDto board = boardService.detail(no);
		// 데이터 공유하기
		request.setAttribute("board", board);
		// 페이지 이동하기
//		RequestDispatcher rd = request.getRequestDispatcher("/board/detail.jsp");
//		rd.forward(request, response);
		forward(request, response, "/WEB-INF/board/detail.jsp");
	}

	private void list(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		/*
		HttpSession session = request.getSession();
		MemberDto member = (MemberDto)session.getAttribute("loginUser");
		if (member == null) {
			response.sendRedirect(request.getContextPath() + "/member?action=loginForm");
			return;
		}
		*/
		
		List<BoardDto> boards = boardService.list();
		request.setAttribute("boards", boards);
//		RequestDispatcher rd = request.getRequestDispatcher("/board/list.jsp");
//		rd.forward(request, response);
		
		forward(request, response, "/WEB-INF/board/list.jsp");
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}








