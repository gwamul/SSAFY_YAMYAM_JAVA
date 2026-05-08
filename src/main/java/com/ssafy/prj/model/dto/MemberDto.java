package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;


@Data
@AllArgsConstructor
public class MemberDto implements Serializable {

	private static final long serialVersionUID = 3218201200103804386L;
	
	private String uid;
	private String userId;
	private String password;
	private String name;
	private String birthDate;
	private String gender;
	private double height;
	private double weight;
	private String disease;
	private String image;
	private List<String> followers = new ArrayList<>();
	private List<String> following = new ArrayList<>();
	
	public MemberDto() {
		super();
	}

	public MemberDto(String user_id, String password, String name, String birthDate, String gender, double height,
			double weight, String disease, String image) {
		super();
		this.userId = user_id;
		this.password = password;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.disease = disease;
		this.image = image;
	}

	
	
}
