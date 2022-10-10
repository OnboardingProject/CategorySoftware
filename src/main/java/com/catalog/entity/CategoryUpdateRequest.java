package com.catalog.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CategoryUpdateRequest {
	private Integer levelId;
	private String levelName;
	private String lastUpdatedBy;
	private String hierarchyLevel;
}
