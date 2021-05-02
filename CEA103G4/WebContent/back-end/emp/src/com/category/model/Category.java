package com.category.model;

import java.io.Serializable;

public class Category implements Serializable {
	private String categoryID;
	private String categoryName;
	private String parentCategoryID;

	public Category() {
	}

	public Category(String categoryID, String categoryName, String parentCategoryID) {
		super();
		this.categoryID = categoryID;
		this.categoryName = categoryName;
		this.parentCategoryID = parentCategoryID;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getCategoryName() {
		return categoryName;
	}

	public void setCategoryName(String categoryName) {
		this.categoryName = categoryName;
	}

	public String getParentCategoryID() {
		return parentCategoryID;
	}

	public void setParentCategoryID(String parentCategoryID) {
		this.parentCategoryID = parentCategoryID;
	}
	
	@Override
	public String toString() {
		return categoryID + "\t" + categoryName + "\t" + parentCategoryID;
	}

}
