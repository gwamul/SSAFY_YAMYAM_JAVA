package com.ssafy.prj.model.service;

import java.util.List;

import com.ssafy.prj.model.dao.BoardDao;
import com.ssafy.prj.model.dao.BoardDaoFileImpl;
import com.ssafy.prj.model.dto.BoardDto;

public class BoardServiceImpl implements BoardService {

	private static BoardService instance;
	private BoardDao boardDao;
	private BoardServiceImpl() {
		boardDao = BoardDaoFileImpl.getInstance();
	}
	public static BoardService getInstance() {
		if (instance == null) {
			instance = new BoardServiceImpl();
		}
		return instance;
	}
	
	@Override
	public List<BoardDto> list() {
		return boardDao.selectAllBoard();
	}
	
	@Override
	public BoardDto detail(int no) {
		return boardDao.selectOneBoard(no);
	}
	
	@Override
	public void write(BoardDto board) {
		boardDao.insertBoard(board);
	}
	@Override
	public void delete(int no) {
		boardDao.deleteBoard(no);
	}

}











