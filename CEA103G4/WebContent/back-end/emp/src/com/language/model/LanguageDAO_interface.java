package com.language.model;

import java.util.List;

import com.publishers.model.Publisher;

public interface LanguageDAO_interface {
	public void update(Language languages);
	public Language findByPrimaryKey(String languages_id);
	public List<Language> getAll();
}
