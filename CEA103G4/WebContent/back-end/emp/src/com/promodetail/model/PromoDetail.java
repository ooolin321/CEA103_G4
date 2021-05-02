package com.promodetail.model;

public class PromoDetail {
	private String promoID;
	private String bookID;
	private int discount;
	private int bpPercent;

	public PromoDetail(String promoID, String bookID, int discount, int bpPercent) {
		this.promoID = promoID;
		this.bookID = bookID;
		this.discount = discount;
		this.bpPercent = bpPercent;
	}

	public PromoDetail() {
	}

	public String getPromoID() {
		return promoID;
	}

	public void setPromoID(String promoID) {
		this.promoID = promoID;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public int getDiscount() {
		return discount;
	}

	public void setDiscount(int discount) {
		this.discount = discount;
	}

	public int getBpPercent() {
		return bpPercent;
	}

	public void setBpPercent(int bpPercent) {
		this.bpPercent = bpPercent;
	}

	@Override
	public String toString() {
		return promoID + "\t" + bookID + "\t" + discount + "\t" + bpPercent;
	}
}
