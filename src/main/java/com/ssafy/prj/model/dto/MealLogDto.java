package com.ssafy.prj.model.dto;

import java.io.Serializable;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MealLogDto implements Serializable {

	private static final long serialVersionUID = 1L;

	private long mealId;
	private String userUid;
	private String foodName;
	private String foodCode;
	private double energy;
	private double protein;
	private double fat;
	private double carbs;
	private double sugar;
	private double sodium;
	private double saturatedFat;
	private double transFat;
	private Timestamp createdDate;
}