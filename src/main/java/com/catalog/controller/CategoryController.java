package com.catalog.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.catalog.entity.Category;
import com.catalog.entity.CategoryRequest;
import com.catalog.entity.CategoryUpdateRequest;
import com.catalog.service.CategoryService;

import io.swagger.v3.oas.annotations.Operation;
import lombok.extern.slf4j.Slf4j;

/**
 * This is the controller class of catalog management
 * 
 * @author
 *
 */
@RestController
@RequestMapping("api/catalog")
@Slf4j
public class CategoryController {
	@Autowired
	CategoryService categoryService;

	/**
	 * This is the method to add categories
	 * 
	 * @param categoryRequest
	 * @return category
	 */
	@PostMapping("/addCategory")
	@Operation(summary = "Adding category", description = "API to add categories", tags="Save Category")
	public ResponseEntity<Category> saveCatalog(@Valid @RequestBody CategoryRequest categoryRequest) {
		log.info("Save Controller Entry");
		Category categories = categoryService.saveCatalog(categoryRequest);
		log.info("Save Controller Exit");
		return new ResponseEntity<Category>(categories, HttpStatus.CREATED);
	}

	/**
	 * This is the method to fetch all categories available
	 * 
	 * @return category list
	 *
	 */
	@GetMapping("/fetchCategory")
	@Operation(summary = "Fetching category", description = "API for displaying details of fetching categories", tags="Get Category")
	public ResponseEntity<List<Category>> fetchCatalog() {
		log.info("Controller fetch category starts");
		List<Category> categoryList = categoryService.fetchCategory();
		log.info("Controller fetch category ends with response: {}", categoryList);
		return new ResponseEntity<List<Category>>(categoryList, HttpStatus.OK);
	}
	
	/*
	 * @PutMapping("/update") public ResponseEntity<Category>
	 * upcateCatalog(@RequestBody CategoryUpdateRequest categoryUpdateRequest) {
	 * log.info("Save Controller Entry"); Category categories =
	 * categoryService.updateCatalog(categoryUpdateRequest);
	 * log.info("Save Controller Exit"); return new
	 * ResponseEntity<Category>(categories, HttpStatus.CREATED); }
	 */

}
