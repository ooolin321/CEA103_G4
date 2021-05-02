package com.publishers.model;

import java.util.List;

public class PublisherService {

	private PublisherDAO_interface dao;

	public PublisherService(PublisherDAO_interface dao) {
		this.dao = dao;
	}

	public Publisher updatePublisher(String publisher_ID, String publisher_Name, String publisher_Phone,
			String publisher_Address, String publisher_Email) {
		Publisher publisher = new Publisher();

		publisher.setPublisher_ID(publisher_ID);
		publisher.setPublisher_Name(publisher_Name);
		publisher.setPublisher_Phone(publisher_Phone);
		publisher.setPublisher_Address(publisher_Address);
		publisher.setPublisher_Email(publisher_Email);
		dao.update(publisher);

		return dao.findByPrimaryKey(publisher_ID);
	}

	public Publisher getOnePublisher(String publisher_id) {
		return dao.findByPrimaryKey(publisher_id);
	}

	public List<Publisher> getAll() {
		return dao.getAll();
	}

	public Publisher insertPublisher(String publisher_ID, String publisher_Name, String publisher_Phone,
			String publisher_Address, String publisher_Email) {
		Publisher publisher = new Publisher();
		publisher.setPublisher_Name(publisher_Name);
		publisher.setPublisher_Phone(publisher_Phone);
		publisher.setPublisher_Address(publisher_Address);
		publisher.setPublisher_Email(publisher_Email);
		dao.insert(publisher);

		return publisher;
	}

	public List<Publisher> getByPublisherName(String publisher_Name) {
		return dao.findByPublisherName(publisher_Name);
	}
	
	public Publisher getOneByPublisherName(String publisher_Name) {
		return dao.findOneByPublisherName(publisher_Name);
	}

	public List<String> getByPublisherNameLike(String publisherName) {
		return dao.findByPublisherNameLike(publisherName);
	}
}
