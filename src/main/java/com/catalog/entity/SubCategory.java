package com.catalog.entity;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class SubCategory {
	
	Integer levelId;
	String levelName;
	List<SubCategory> levels;
}
