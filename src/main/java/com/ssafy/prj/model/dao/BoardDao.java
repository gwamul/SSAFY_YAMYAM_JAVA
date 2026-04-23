package com.ssafy.prj.model.dao;

import java.util.List;

import com.ssafy.prj.model.dto.BoardDto;

public interface BoardDao {
	List<BoardDto> selectAllBoard();

	BoardDto selectOneBoard(int no);

	void insertBoard(BoardDto board);

	void deleteBoard(int no);
	
	default void load() {}
	
	default void save() {}
}





