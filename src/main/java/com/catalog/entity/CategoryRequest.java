package com.catalog.entity;

import javax.validation.constraints.Pattern;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryRequest {
	//@Pattern(regexp="^[a-zA-Z]",message="It should not be blank and contains only alphabets")
	private String levelName;
	private String createdBy;
	private String lastUpdatedBy;
	private String hierarchyLevel;
}
