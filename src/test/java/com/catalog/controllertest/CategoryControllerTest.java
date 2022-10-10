package com.catalog.controllertest;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.catalog.controller.CategoryController;
import com.catalog.entity.Category;
import com.catalog.entity.CategoryRequest;
import com.catalog.service.CategoryServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This is the Catalog controller test class where we test add catalog and fetch
 * category api {@link CategoryController }
 * 
 * @author
 *
 */

@SpringBootTest
@AutoConfigureMockMvc
public class CategoryControllerTest {

	@InjectMocks
	private CategoryController categoryController;

	@Mock
	CategoryServiceImpl categoryService;

	@Autowired
	MockMvc mockMvc;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(categoryController).build();
	}

	@Test
	public void saveCategoryTestSuccess() throws Exception {

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);
		Category category2 = new Category("id2", 2, "external", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);
		CategoryRequest categoryRequest = new CategoryRequest("abc", "u1212", "u1212", null);
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(category1);
		categoryList.add(category2);
		when(categoryService.saveCatalog(Mockito.any(CategoryRequest.class))).thenReturn(category1);
		mockMvc.perform(MockMvcRequestBuilders.post("http://localhost:8080/api/catalog/add-category")
				.contentType(MediaType.APPLICATION_JSON)
				.content(new ObjectMapper().writeValueAsString(categoryRequest)))
				.andExpect(MockMvcResultMatchers.status().is(201));

	}

	/**
	 * {@link CategoryController#fetchCategory()}
	 * 
	 * @throws Exception
	 */
	@Test
	public void fetchAllCategoryTestSuccess() throws Exception {

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);
		Category category2 = new Category("id2", 2, "external", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);
		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(category1);
		categoryList.add(category2);
		when(categoryService.fetchCategory()).thenReturn(categoryList);
		mockMvc.perform(get("/api/catalog/fetch-category").contentType(MediaType.APPLICATION_JSON))
				.andExpect(MockMvcResultMatchers.status().is(200));

	}

}