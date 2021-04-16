package com.bid.model;

public class BidService {
	private BidDAO_interface dao;
	
	public BidService() {
		dao = new BidJNDIDAO();
	}
	
	
}
