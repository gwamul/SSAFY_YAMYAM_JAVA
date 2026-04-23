package com.ssafy.prj.model.dao;

import java.util.ArrayList;
import java.util.List;

import com.ssafy.prj.model.dto.BoardDto;

public class BoardDaoImpl implements BoardDao {

	private List<BoardDto> boards = new ArrayList<>();
	private static int boardNo = 0;
	
	private static BoardDao instance;
	private BoardDaoImpl() {
		boards.add(new BoardDto(++boardNo, "title1", "writer1", "content1", 0));
		boards.add(new BoardDto(++boardNo, "title2", "writer2", "content2", 0));
		boards.add(new BoardDto(++boardNo, "title3", "writer3", "content3", 0));
		boards.add(new BoardDto(++boardNo, "title4", "writer4", "content4", 0));
		boards.add(new BoardDto(++boardNo, "title5", "writer5", "content5", 0));
	}
	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDaoImpl();
		}
		return instance;
	}
	
	@Override
	public List<BoardDto> selectAllBoard() {
		return boards;
	}
	
	@Override
	public BoardDto selectOneBoard(int no) {
		for (BoardDto board : boards) {
			if (board.getNo() == no) {
				return board;
			}
		}
		return null;
	}
	
	@Override
	public void insertBoard(BoardDto board) {
		board.setNo(++boardNo);
		boards.add(board);
	}
	@Override
	public void deleteBoard(int no) {
		for (BoardDto board : boards) {
			if (board.getNo() == no) {
				boards.remove(board);
				return;
			}
		}
	}

}







