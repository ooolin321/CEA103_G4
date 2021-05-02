package com.publishers.model;

import java.util.*;


public interface PublisherDAO_interface {
	void insert(Publisher publisher);
	void update(Publisher publisher);
	Publisher findByPrimaryKey(String publisher_id);
	List<Publisher> getAll();
	List<Publisher> findByPublisherName(String publisher_name);
	Publisher findOneByPublisherName(String publisher_Name);
	List<String> findByPublisherNameLike(String publisherName);
}
