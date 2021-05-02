package com.book.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface BookDAO {
	void insert(Book book);
	Optional<Book> findByBookID(String bookID);
	List<Book> advSearch(Map<String, String> map);
	List<Book> findByBookIDList(List<String> bookIDs, boolean isFront);
	List<Book> findByRandom(int num, boolean isFront);
	List<Book> findNewBooks(int num, boolean isFront);
	void update(Book book);
	void updateSalePricePromo(String bookID, Double salePricePromo);
	void updateSalePricePromoBatch(List<String> bookIDs, List<Double> salePricePromos);
	void updateBPPromoBatch(List<String> bookIDs, List<Double> bookBPPromos);
	void updateEffPromoBatch(List<String> bookIDs, List<String> effectivePromos);
	void updateIsSoldBatch(List<String> bookIDs, Integer isSold);
	void updateEffPromos(String effectivePromos);
	void updateBatch(List<Book> books);
	int findBookNum();
	List<String> findByBookIDLike(String bookID);
	List<String> findByBookNameLike(String bookName);
	List<String> findByAuthorLike(String author);
	List<Book> findByPromoID(String promoID, boolean isFront);
}
