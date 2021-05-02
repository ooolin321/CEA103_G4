package com.language.model;

public class Language {
	private String Language_ID;
	private String Language_Name;

	public String getLanguage_ID() {
		return Language_ID;
	}

	public void setLanguage_ID(String language_ID) {
		this.Language_ID = language_ID;
	}

	public String getLanguage_Name() {
		return Language_Name;
	}

	public void setLanguage_Name(String language_Name) {
		this.Language_Name = language_Name;
	}

	public Language(String language_ID, String language_Name) {
		super();
		Language_ID = language_ID;
		Language_Name = language_Name;
	}

	public Language() {

	}

}
