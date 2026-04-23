package com.ssafy.prj.model.dao;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.management.RuntimeErrorException;

import com.ssafy.prj.model.dto.BoardDto;

public class BoardDaoFileImpl implements BoardDao {

	private List<BoardDto> boards = new ArrayList<>();
	private static int boardNo = 0;
	private static BoardDao instance;
	
	private final File boardDataFile;
	
	private BoardDaoFileImpl() {
		System.out.println(BoardDaoFileImpl.class.getResource("/").getPath());
		String path = BoardDaoFileImpl.class.getResource("/").getPath() + "board.dat";
		boardDataFile = new File(path);
		
		try {
			if (!boardDataFile.exists()) {
				boardDataFile.createNewFile();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public static BoardDao getInstance() {
		if (instance == null) {
			instance = new BoardDaoFileImpl();
		}
		return instance;
	}
	
	public void load() {
		try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(boardDataFile))) {
			boards = (List)ois.readObject();
		} catch (Exception e) {
			System.out.println("저장된 정보가 없습니다.");
			boards = Collections.synchronizedList(new ArrayList<>());
		} finally {
			if (boards.isEmpty()) {
				boards.add(new BoardDto(++boardNo, "title1", "writer1", "content1", 0));
				boards.add(new BoardDto(++boardNo, "title2", "writer2", "content2", 0));
				boards.add(new BoardDto(++boardNo, "title3", "writer3", "content3", 0));
				boards.add(new BoardDto(++boardNo, "title4", "writer4", "content4", 0));
				boards.add(new BoardDto(++boardNo, "title5", "writer5", "content5", 0));
			}
			System.out.println("게시판 로딩 완료 : " + boards.size());
		}
	}
	
	public void save() {
		try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(boardDataFile))) {
			oos.writeObject(boards);
			System.out.println("게시판 정보 닫히기 전 저장 완료");
		} catch (Exception e) {
			throw new RuntimeException("정보 저장 실패", e);
		}
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







