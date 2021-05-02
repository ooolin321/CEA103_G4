package com.language.model;

import java.util.List;

public class LanguageService {
	private LanguageDAO_interface dao;

	public LanguageService(LanguageDAO_interface dao) {
		this.dao = dao;
	}

	public Language updateLanguage(String language_ID, String language_Name) {
		Language language = new Language();

		language.setLanguage_ID(language_ID);
		language.setLanguage_Name(language_Name);
		dao.update(language);

		return language;
	}

	public Language getOneLanguage(String language_id) {
		return dao.findByPrimaryKey(language_id);
	}

	public List<Language> getAll() {
		return dao.getAll();
	}
}
