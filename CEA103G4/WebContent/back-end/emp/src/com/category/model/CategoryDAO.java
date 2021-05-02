package com.category.model;

import java.util.List;
import java.util.Optional;

public interface CategoryDAO {
	void insert(Category category);
	Optional<Category> findByCategoryID(String categoryID);
	Optional<Category> findByCategoryName(String categoryName);
	List<Category> findByParentCategoryID(String parentCategoryID);
	List<Category> getAll();
	List<Integer> getBookNumByCategoryIDs(List<String> categoryIDs);
	Optional<Category> findCurrentLevelMaxCategoryID(String parentCategoryID, int categoryLevel);
	void update(Category category);
	void delete(String categoryID);
}
