package com.book.model;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.Objects;

import com.promo.model.Promo;

public class Book implements Serializable {
	private String bookID;
	private String publisherID;
	private String languageID;
	private String categoryID;
	private String bookName;
	private String isbn;
	private String author;
	private Double listPrice;
	private Double salePrice;
	private Double bookBP;
	private Integer isSold;
	private Date publicationDate;
	private Integer stock;
	private Integer safetyStock;
	private String bookIntro;
	private String bookNameOriginal;
	private Double salePricePromo;
	private Double bookBPPromo;
	private String effectivePromos;

	public Double getBookBPPromo() {
		return bookBPPromo;
	}

	public void setBookBPPromo(Double bookBPPromo) {
		this.bookBPPromo = bookBPPromo;
	}

	public String getEffectivePromos() {
		return effectivePromos;
	}

	public void setEffectivePromos(String effectivePromos) {
		this.effectivePromos = effectivePromos;
	}

	public void setSalePricePromo(Double salePricePromo) {
		this.salePricePromo = salePricePromo;
	}

	public Book(String bookID, String publisherID, String languageID, String categoryID, String bookName, String isbn,
			String author, Double listPrice, Double salePrice, Double bookBP, Integer isSold, Date publicationDate,
			Integer stock, Integer safetyStock, String bookIntro, String bookNameOriginal, Double salePricePromo,
			Double bookBPPromo, String effectivePromos) {
		super();
		this.bookID = bookID;
		this.publisherID = publisherID;
		this.languageID = languageID;
		this.categoryID = categoryID;
		this.bookName = bookName;
		this.isbn = isbn;
		this.author = author;
		this.listPrice = listPrice;
		this.salePrice = salePrice;
		this.bookBP = bookBP;
		this.isSold = isSold;
		this.publicationDate = publicationDate;
		this.stock = stock;
		this.safetyStock = safetyStock;
		this.bookIntro = bookIntro;
		this.bookNameOriginal = bookNameOriginal;
		this.salePricePromo = salePricePromo;
		this.bookBPPromo = bookBPPromo;
		this.effectivePromos = effectivePromos;
	}

	public Book() {
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getPublisherID() {
		return publisherID;
	}

	public void setPublisherID(String publisherID) {
		this.publisherID = publisherID;
	}

	public String getLanguageID() {
		return languageID;
	}

	public void setLanguageID(String languageID) {
		this.languageID = languageID;
	}

	public String getCategoryID() {
		return categoryID;
	}

	public void setCategoryID(String categoryID) {
		this.categoryID = categoryID;
	}

	public String getBookName() {
		return bookName;
	}

	public void setBookName(String bookName) {
		this.bookName = bookName;
	}

	public String getIsbn() {
		return isbn;
	}

	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public Double getListPrice() {
		return listPrice;
	}

	public void setListPrice(Double listPrice) {
		this.listPrice = listPrice;
	}

	public Double getSalePrice() {
		return salePrice;
	}

	public void setSalePrice(Double salePrice) {
		this.salePrice = salePrice;
	}

	public Double getBookBP() {
		return bookBP;
	}

	public void setBookBP(Double bookBP) {
		this.bookBP = bookBP;
	}

	public Integer getIsSold() {
		return isSold;
	}

	public void setIsSold(Integer isSold) {
		this.isSold = isSold;
	}

	public Date getPublicationDate() {
		return publicationDate;
	}

	public void setPublicationDate(Date publicationDate) {
		this.publicationDate = publicationDate;
	}

	public Integer getStock() {
		return stock;
	}

	public void setStock(Integer stock) {
		this.stock = stock;
	}

	public Integer getSafetyStock() {
		return safetyStock;
	}

	public void setSafetyStock(Integer safetyStock) {
		this.safetyStock = safetyStock;
	}

	public String getBookIntro() {
		return bookIntro;
	}

	public void setBookIntro(String bookIntro) {
		this.bookIntro = bookIntro;
	}

	public String getBookNameOriginal() {
		return bookNameOriginal;
	}

	public void setBookNameOriginal(String bookNameOriginal) {
		this.bookNameOriginal = bookNameOriginal;
	}

	public Double getSalePricePromo() {
		return salePricePromo;
	}

	@Override
	public String toString() {
		return bookID + "\t" + publisherID + "\t" + languageID + "\t" + categoryID + "\t" + bookName + "\t" + isbn
				+ "\t" + author + "\t" + listPrice + "\t" + salePrice + "\t" + bookBP + "\t" + isSold + "\t"
				+ publicationDate + "\t" + stock + "\t" + safetyStock + "\t" + bookIntro + "\t" + bookNameOriginal
				+ "\t" + salePricePromo + "\t" + bookBPPromo + "\t" + effectivePromos;
	}

	public Double getBookRealPrice() {
		// 促銷時優惠價salePricePromo為空，回傳定價、預設售價最小的那個
		if (Double.isNaN(salePricePromo)) {
			return Math.min(listPrice, salePrice);
			// 促銷時優惠價salePricePromo非空，回傳定價、預設售價、促銷售價最小的那個
		} else {
			double temp = Math.min(listPrice, salePrice);
			return Math.min(temp, salePricePromo);
		}
	}

	public Double getBookRealBP() {
		// 促銷時優惠價bookBPPromo為空，回傳預設bookBP的那個
		if (Double.isNaN(bookBPPromo)) {
			return bookBP;
			// 促銷時優惠價bookBPPromo非空，回傳bookBP與bookBPPromo最大的那個
		} else {
			return Math.max(bookBP, bookBPPromo);
		}
	}

	// 複寫hashCode和equals，定義bookID相同的Book物件為相同
	@Override
	public int hashCode() {
		// Objects 有 hash() 方法可以使用
		// 以下可以簡化為 return Objects.hash(name, number);
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.bookID);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final Book other = (Book) obj;

		if (!Objects.equals(this.bookID, other.bookID)) {
			return false;
		}

		return true;
	}

}
