package com.ssafy.prj.model.dto;

import java.io.Serializable;

public class BoardDto implements Serializable {

	private static final long serialVersionUID = 429013509225028514L;
	private int no;
	private String title;
	private String writer;
	private String content;
	private int viewCnt;
	public BoardDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public BoardDto(int no, String title, String writer, String content, int viewCnt) {
		super();
		this.no = no;
		this.title = title;
		this.writer = writer;
		this.content = content;
		this.viewCnt = viewCnt;
	}
	public int getNo() {
		return no;
	}
	public void setNo(int no) {
		this.no = no;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public int getViewCnt() {
		return viewCnt;
	}
	public void setViewCnt(int viewCnt) {
		this.viewCnt = viewCnt;
	}
	@Override
	public String toString() {
		return "BoardDto [no=" + no + ", title=" + title + ", writer=" + writer + ", content=" + content + ", viewCnt="
				+ viewCnt + "]";
	}
}
