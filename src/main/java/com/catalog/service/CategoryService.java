package com.catalog.service;

import java.util.List;

import com.catalog.entity.*;

public interface CategoryService {
	public Category saveCatalog(CategoryRequest catgeoryRequest);
	
	public List<Category> fetchCategory();
	
	//public Category updateCatalog(CategoryUpdateRequest catgeoryUpdateRequest);

}
