package com.category.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeMap;

public class CategoryService {
	private final CategoryDAO categoryDAO;

	public CategoryService(CategoryDAO categoryDAO) {
		this.categoryDAO = categoryDAO;
	}

	public Optional<Category> addCategory(String categoryName) {
		categoryName = categoryName.replaceAll("\\s+", "");
		categoryName = categoryName.replace(".", ",");
		Optional<Category> parentCategory = getParentCategoryByChildCategoryName(categoryName);
		// 自動判斷故無需使用
		String parentCategoryID = null;
		StringBuffer categoryID = null;
		String categoryIDNum = null;
		int categoryLevel = 0;
		int temp;
		// 判斷本類別的層級並取得parentCategoryID
		if (parentCategory.isPresent()) {
			parentCategoryID = parentCategory.get().getCategoryID();
			if (Integer.parseInt(parentCategoryID.substring(5, 7)) > 0) {
				categoryLevel = 2;
			} else if (Integer.parseInt(parentCategoryID.substring(3, 5)) > 0) {
				categoryLevel = 1;
			}
		}

		// 計算本類別編碼值
		Optional<Category> currentMaxIDCategory = categoryDAO.findCurrentLevelMaxCategoryID(parentCategoryID,
				categoryLevel);
		// 同層級有其他類別存在
		if (currentMaxIDCategory.isPresent()) {
			String maxCategoryID = currentMaxIDCategory.get().getCategoryID();
			if (categoryLevel == 0) {
				temp = Integer.parseInt(maxCategoryID.substring(3, 5)) + 1;
				categoryIDNum = String.format("%02d", temp);
				categoryID = new StringBuffer(maxCategoryID).replace(3, 5, categoryIDNum);
			} else if (categoryLevel == 1) {
				temp = Integer.parseInt(maxCategoryID.substring(5, 7)) + 1;
				categoryIDNum = String.format("%02d", temp);
				categoryID = new StringBuffer(maxCategoryID).replace(5, 7, categoryIDNum);
			} else {
				temp = Integer.parseInt(maxCategoryID.substring(7)) + 1;
				categoryIDNum = String.format("%03d", temp);
				categoryID = new StringBuffer(maxCategoryID).replace(7, 10, categoryIDNum);
			}
			// 同層級無其他類別存在
		} else {
			if (categoryLevel == 0) {
				categoryID = new StringBuffer("CAT0100000");
			} else if (categoryLevel == 1) {
				categoryID = new StringBuffer(parentCategoryID).replace(5, 7, "01");
			} else {
				categoryID = new StringBuffer(parentCategoryID).replace(7, 10, "001");
			}
		}

		Category category = new Category(categoryID.toString(), categoryName, parentCategoryID);
		categoryDAO.insert(category);
		return Optional.of(category);
	}

	public Optional<Category> getByCategoryID(String categoryID) {
		return categoryDAO.findByCategoryID(categoryID);
	}

	public Optional<Category> getByCategoryName(String categoryName) {
		return categoryDAO.findByCategoryName(categoryName);
	}

	public Optional<Category> getParentCategoryByChildCategoryName(String categoryName) {
		categoryName = categoryName.replace(" ", "");
		int lastIdxOf = categoryName.lastIndexOf(',');
		if (lastIdxOf == -1) {
			return Optional.empty();
		}
		String parentCategoryName = categoryName.substring(0, lastIdxOf);
		return categoryDAO.findByCategoryName(parentCategoryName);
	}

	public List<Category> getByParentCategoryID(String parentCategoryID) {
		return categoryDAO.findByParentCategoryID(parentCategoryID);
	}

	public List<Category> getAll() {
		return categoryDAO.getAll();
	}

	public Optional<Category> getCurrentLevelMaxCategoryID(String parentCategoryID, int categoryLevel) {
		return categoryDAO.findCurrentLevelMaxCategoryID(parentCategoryID, categoryLevel);
	}

	public Optional<Category> updateCategory(String categoryID, String categoryName, String parentCategoryID) {
		Optional<Category> originalCat = getByCategoryID(categoryID);

		if (originalCat.isPresent()) {
			Category originalCategory = originalCat.get();
			String originalParentCategoryID = originalCategory.getParentCategoryID();
			// 更新後分類不變，只變最後的名稱
			if (originalParentCategoryID != null && originalParentCategoryID.equals(parentCategoryID)) {
				originalCategory.setCategoryName(categoryName);
				categoryDAO.update(originalCategory);
				return Optional.of(originalCategory);
			} else {
				// 編輯後類別有變，需刪掉重加
				deleteCategory(categoryID);
				return addCategory(categoryName);
			}
		} else {
			return Optional.ofNullable(null);
		}
	}

	public void deleteCategory(String categoryID) {
		categoryDAO.delete(categoryID);
	}

	public Map<String, List<Category>> getCategoryTree() {
		List<Category> allCats = getAll();
		Map<String, List<Category>> catMap = new LinkedHashMap<String, List<Category>>(); // parentCatID :
																							// [childCats...]
		allCats.forEach(cat -> {
			String parentCatID = cat.getParentCategoryID();
			String catName = cat.getCategoryName();
			List<Category> childCats = catMap.get(parentCatID);

			// 類別名稱只留最後一個,後的字串
			int lastIdxOfCamma = catName.lastIndexOf(',');
			if (lastIdxOfCamma != -1) {
				cat.setCategoryName(catName.substring(lastIdxOfCamma + 1));
			}

			if (childCats == null) {
				childCats = new ArrayList<>(Arrays.asList(cat));
				catMap.put(parentCatID, childCats);
			} else {
				childCats.add(cat);
			}

		});

//		catMap.forEach((k, v) -> {
//			System.out.println(k);
//			v.forEach(cat -> System.out.println(cat));
//		});

		return catMap;
	}

	public Map<String, String> getCatLevelMap(String categoryID) {
		Map<String, String> catLevelMap = new TreeMap<String, String>(); // ID : Name
		Optional<Category> cat = getByCategoryID(categoryID);
		if (cat.isPresent()) {
			String categoryName = cat.get().getCategoryName();
			String[] catagoryNameLevel = categoryName.split(",");

			for (int i = catagoryNameLevel.length - 1; i > -1; i--) {
				catLevelMap.put(categoryID, catagoryNameLevel[i]);
				categoryID = cat.get().getParentCategoryID();
				cat = getByCategoryID(categoryID);

				if (!cat.isPresent()) {
					break;
				}
			}
		}
//		catLevelMap.forEach((k, v) -> System.out.println(k + " - " + v));
		return catLevelMap;
	}

	public Map<String, Integer> getBookNumByCategoryIDs(List<String> categoryIDs) {
		Map<String, Integer> categoryCountMap = new HashMap<String, Integer>();
		List<String> categoryIDsLocal = new ArrayList<String>();

		for (String categoryID : categoryIDs) {
			if (Integer.parseInt(categoryID.substring(7)) > 0) {
				categoryIDsLocal.add(categoryID);
			} else if (Integer.parseInt(categoryID.substring(5, 7)) > 0) {
				categoryIDsLocal.add(categoryID.substring(0, 7));
			} else {
				categoryIDsLocal.add(categoryID.substring(0, 5));
			}
		}

		List<Integer> categoryCount = categoryDAO.getBookNumByCategoryIDs(categoryIDsLocal);
		for (int i = 0; i < categoryIDsLocal.size(); i++) {
			categoryCountMap.put(categoryIDs.get(i), categoryCount.get(i));
		}

		return categoryCountMap;
	}

	public Set<String> getAllChildCatIDs(String categoryID) {
		Set<String> categoryIDs = new LinkedHashSet<String>();

		// 若有第一層子類別，加入
		getByParentCategoryID(categoryID).forEach(firstChildCat -> {
			String fisrtChildCatID = firstChildCat.getCategoryID();
			categoryIDs.add(fisrtChildCatID);
			
			// 若有第二層子類別，加入
			getByParentCategoryID(fisrtChildCatID)
					.forEach(secondChildCat -> categoryIDs.add(secondChildCat.getCategoryID()));

		});

		return categoryIDs;
	}
}
