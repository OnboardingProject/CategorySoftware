package com.catalog.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.catalog.entity.Category;

public interface CategoryRepository extends MongoRepository<Category, String> {
	Category findByLevelId(int levelId);

}
