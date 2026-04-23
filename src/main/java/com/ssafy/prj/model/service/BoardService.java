package com.ssafy.prj.model.service;

import java.util.List;

import com.ssafy.prj.model.dto.BoardDto;

public interface BoardService {
	List<BoardDto> list();

	BoardDto detail(int no);

	void write(BoardDto board);

	void delete(int no);
}
