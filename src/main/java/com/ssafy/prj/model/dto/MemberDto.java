package com.ssafy.prj.model.dto;

import java.io.Serializable;

public class MemberDto implements Serializable {

	private static final long serialVersionUID = 3218201200103804386L;

	private String id;
	private String password;
	private String name;
	
	public MemberDto() {
		super();
	}

	public MemberDto(String id, String password, String name) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", password=" + password + ", name=" + name + "]";
	}
	
}
