package com.catalog.servicetest;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.management.ListenerNotFoundException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import com.catalog.entity.Category;
import com.catalog.entity.CategoryRequest;
import com.catalog.entity.SubCategory;
import com.catalog.exception.HierarchyNotFoundException;
import com.catalog.exception.ListEmptyException;
import com.catalog.repository.CategoryRepository;
import com.catalog.service.CategoryServiceImpl;

/**
 * This is the service test class of Catalog management {@link CategoryService}
 * 
 * @author
 *
 */
public class CategoryServiceTest {

	@InjectMocks
	private CategoryServiceImpl categoryServiceImpl;

	@Mock
	CategoryRepository categoryRepository;

	@BeforeEach
	public void setUp() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * 
	 * {@link CategoryServiceImpl#saveCatalog(CategoryRequest)#saveCatalogIfServiceTest()
	 * } This is to test to add catalog with a parent category request
	 */
	@Test
	public void saveCatalogIfParentServiceTest() {

		CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", null);
		Category category = categoryServiceImpl.saveCatalog(categoryRequest);
		when(categoryRequest.getHierarchyLevel()).thenReturn(null);
		when(categoryRepository.save(category)).thenReturn(category);
	}

	@Test
	public void saveCatalogIfParentElseServiceTest() {

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);

		CategoryRequest categoryRequest2 = new CategoryRequest("external", "u1212", "u1212", null);
		categoryServiceImpl.saveCatalog(categoryRequest2);
		when(categoryRequest2.getHierarchyLevel()).thenReturn(null);
		List<Category> listCategory = new ArrayList<Category>();
		listCategory.add(category1);
		when(categoryRepository.findAll()).thenReturn(listCategory);

		int nextLevelId = listCategory.get(listCategory.size() - 1).getLevelId() + 1;
		Category category = categoryServiceImpl.saveCatalog(categoryRequest2);
		category.setLevelId(nextLevelId);
		when(categoryRepository.save(category)).thenReturn(category);
	}

	@Test
	public void saveCatalogelseServiceHierarchyTest() {
		try {
			List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

			SubCategory subCategory = new SubCategory(1, "internal 1.1", null);
			subCategoryList.add(subCategory);

			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), subCategoryList);
			CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "2");

			// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.any());
			String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();

			for (String a : levelsHierarchy) {

				levels.add(Integer.parseInt(a));
			}
			when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(null);

			categoryServiceImpl.saveCatalog(categoryRequest);

		} catch (Exception e) {
			assertTrue(e instanceof HierarchyNotFoundException);
		}
	}

	@Test
	public void saveCatalogelseIfChildServicesecondexceptionHierarchyTest() {

		try {
			List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

			SubCategory subCategory = new SubCategory(1, "internal 1.1", null);
			subCategoryList.add(subCategory);

			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), null);
			CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-1");

			// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.anyString());
			String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();

			for (String a : levelsHierarchy) {

				levels.add(Integer.parseInt(a));
			}
			when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);

			when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);

		} catch (Exception e) {
			assertTrue(e instanceof HierarchyNotFoundException);
		}
	}

	@Test
	public void saveCatalogelseIfChildServiceHierarchyTest() {

		try {
			List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

			SubCategory subCategory = new SubCategory(1, "internal 1.1", null);
			subCategoryList.add(subCategory);

			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), subCategoryList);
			CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-1");

			// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.anyString());
			String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();

			for (String a : levelsHierarchy) {

				levels.add(Integer.parseInt(a));
			}
			when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
			when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
			Category category2 = categoryServiceImpl.saveCatalog(categoryRequest);
			when(categoryRepository.save(category1)).thenReturn(category2);

		} catch (Exception e) {
			assertTrue(e instanceof HierarchyNotFoundException);
		}

	}

	@Test
	public void saveCatalogelseIfChildServiceFunctionHierarchyTest() {

		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		List<SubCategory> subCategoryInnerList = new ArrayList<SubCategory>();

		SubCategory subCategory = new SubCategory(1, "internal 1.1", subCategoryInnerList);
		SubCategory subCategoryInner = new SubCategory(1, "internal 1.1", null);
		subCategoryList.add(subCategory);
		subCategoryInnerList.add(subCategoryInner);

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), subCategoryList);
		CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-1");

		// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.anyString());
		String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
		List<Integer> levels = new ArrayList<Integer>();

		for (String a : levelsHierarchy) {

			levels.add(Integer.parseInt(a));
		}
		when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
		when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
		Category category2 = categoryServiceImpl.saveCatalog(categoryRequest);
		when(categoryRepository.save(category1)).thenReturn(category2);

	}

	@Test
	public void saveCatalogelseIfChildServiceFunctionHierarchyExceptionTest() {

		try {
			List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
			List<SubCategory> subCategoryInnerList = new ArrayList<SubCategory>();

			SubCategory subCategory = new SubCategory(1, "internal 1.1", subCategoryInnerList);
			SubCategory subCategoryInner = new SubCategory(1, "internal 1.1", null);
			subCategoryList.add(subCategory);
			subCategoryInnerList.add(subCategoryInner);

			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), subCategoryList);
			CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-3");

			String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();

			for (String a : levelsHierarchy) {

				levels.add(Integer.parseInt(a));
			}
			when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
			when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
		} catch (Exception e) {
			assertTrue(e instanceof HierarchyNotFoundException);
		}

	}

	@Test
	public void saveCatalogelseIfChildServiceFunctionElseHierarchyTest() {

		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
		List<SubCategory> subCategoryInnerList = new ArrayList<SubCategory>();
		List<SubCategory> subCategoryInnerInnerList = new ArrayList<SubCategory>();
		SubCategory subCategory = new SubCategory(1, "internal 1.1", subCategoryInnerList);
		SubCategory subCategoryInner = new SubCategory(1, "internal 1.1", subCategoryInnerInnerList);
		subCategoryList.add(subCategory);
		subCategoryInnerList.add(subCategoryInner);
		SubCategory subCategoryInnerInner = new SubCategory(1, "internal 1.1.1", null);
		subCategoryInnerInnerList.add(subCategoryInnerInner);
		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), subCategoryList);
		CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-1-1");

		// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.anyString());
		String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
		List<Integer> levels = new ArrayList<Integer>();

		for (String a : levelsHierarchy) {

			levels.add(Integer.parseInt(a));
		}
		when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
		when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
		Category category2 = categoryServiceImpl.saveCatalog(categoryRequest);
		when(categoryRepository.save(category1)).thenReturn(category2);

	}

	@Test
	public void saveCatalogelseServiceTest() {
		List<SubCategory> subCategoryList = new ArrayList<SubCategory>();

		SubCategory subCategory = new SubCategory(1, "internal 1.1", null);
		subCategoryList.add(subCategory);

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), subCategoryList);
		CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1");

		when(categoryRequest.getHierarchyLevel()).thenReturn((String) Mockito.any());
		String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
		List<Integer> levels = new ArrayList<Integer>();

		for (String a : levelsHierarchy) {

			levels.add(Integer.parseInt(a));
		}
		when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
		when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
		Category category2 = categoryServiceImpl.saveCatalog(categoryRequest);
		when(categoryRepository.save(category1)).thenReturn(category2);
	}

	@Test
	public void saveCatalogelseIfChildServiceFunctionElseHierarchyExceptionTest() {

		try {
			List<SubCategory> subCategoryList = new ArrayList<SubCategory>();
			List<SubCategory> subCategoryInnerList = new ArrayList<SubCategory>();
			List<SubCategory> subCategoryInnerInnerList = new ArrayList<SubCategory>();
			SubCategory subCategory = new SubCategory(1, "internal 1.1", subCategoryInnerList);
			SubCategory subCategoryInner = new SubCategory(1, "internal 1.1", subCategoryInnerInnerList);
			subCategoryList.add(subCategory);
			subCategoryInnerList.add(subCategoryInner);
			SubCategory subCategoryInnerInner = new SubCategory(1, "internal 1.1.1", null);
			subCategoryInnerInnerList.add(subCategoryInnerInner);
			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), subCategoryList);
			CategoryRequest categoryRequest = new CategoryRequest("internal", "u1212", "u1212", "1-1-3");

			// when(categoryRequest.getHigherhierarchyLevel()).thenReturn(Mockito.anyString());
			String[] levelsHierarchy = categoryRequest.getHierarchyLevel().split("-");
			List<Integer> levels = new ArrayList<Integer>();

			for (String a : levelsHierarchy) {

				levels.add(Integer.parseInt(a));
			}
			when(categoryRepository.findByLevelId(levels.get(0))).thenReturn(category1);
			when(categoryServiceImpl.saveCatalog(categoryRequest)).thenReturn(category1);
			Category category2 = categoryServiceImpl.saveCatalog(categoryRequest);
			when(categoryRepository.save(category1)).thenReturn(category2);
		} catch (Exception e) {
			assertTrue(e instanceof HierarchyNotFoundException);
		}

	}

	/**
	 * 
	 * {@link CategoryServiceImpl#fetchCategory()#fetchCategoryTest()} This is the
	 * method to test whether the categories are displayed
	 */
	@Test
	public void fetchCategoryServiceTest() {

		Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
				LocalDateTime.now(), null);

		List<Category> categoryList = new ArrayList<Category>();
		categoryList.add(category1);

		when(categoryRepository.findAll()).thenReturn(categoryList);
		categoryServiceImpl.fetchCategory();
	}

	/**
	 * {@link CategoryServiceImpl#fetchCategory()#fetchCategoryServiceTestException()}
	 * This is the test case to check when the catalog is empty
	 * 
	 * 
	 */
	@Test
	public void fetchCategoryServiceTestException() {

		try {
			Category category1 = new Category("id1", 1, "internal", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), null);
			Category category2 = new Category("id2", 2, "external", "u1212", LocalDateTime.now(), "u1212",
					LocalDateTime.now(), null);
			List<Category> categoryList = new ArrayList<Category>();
			categoryList.add(category1);
			categoryList.add(category2);
			when(categoryRepository.findAll()).thenReturn(null);

			categoryServiceImpl.fetchCategory();
		} catch (Exception e) {
			assertTrue(e instanceof ListEmptyException);
		}
	}

}