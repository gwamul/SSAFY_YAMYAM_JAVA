package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MemberDto implements Serializable {

	private static final long serialVersionUID = 3218201200103804386L;

	private String id;
	private String password;
	private String name;
	private String birthDate;
	private String gender;
	private double height;
	private double weight;
	private String disease;
	private String image;
	private List<String> followers = new ArrayList<>();
	
	public MemberDto() {
		super();
	}

	public MemberDto(String id, String password, String name, String birthDate, String gender, double height,
			double weight, String disease, String image) {
		super();
		this.id = id;
		this.password = password;
		this.name = name;
		this.birthDate = birthDate;
		this.gender = gender;
		this.height = height;
		this.weight = weight;
		this.disease = disease;
		this.image = image;
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

	public String getBirthDate() {
		return birthDate;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public double getHeight() {
		return height;
	}

	public void setHeight(double height) {
		this.height = height;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

	public String getDisease() {
		return disease;
	}

	public void setDisease(String disease) {
		this.disease = disease;
	}

	public String getImage() {
		return image;
	}

	public void setImage(String image) {
		this.image = image;
	}

	public List<String> getFollowers() {
		return followers;
	}

	public void setFollowers(List<String> followers) {
		this.followers = followers;
	}

	@Override
	public String toString() {
		return "MemberDto [id=" + id + ", password=" + password + ", name=" + name + ", birthDate=" + birthDate
				+ ", gender=" + gender + ", height=" + height + ", weight=" + weight + ", disease=" + disease
				+ ", image=" + image + ", followers=" + followers + "]";
	}
	
}
