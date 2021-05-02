package com.favorite_book.model;

import java.util.List;

public class Favorite_BookService {
	private Favorite_BookDAO_interface dao ;
	
	public Favorite_BookService() {
		dao = new Favorite_BookDAO();
	}
	
	public Favorite_Book addFavBook(String book_ID, String mem_ID) {
		Favorite_Book favorite_Book = new Favorite_Book();
		
		favorite_Book.setBook_ID(book_ID);
		favorite_Book.setMem_ID(mem_ID);
		dao.insert(favorite_Book);
		
		return favorite_Book;
	}
	public List<Favorite_Book> getAll(String memID){
		return dao.getAll(memID);
	}
	public void deleteFavBook(String book_ID,String mem_ID) {
		dao.delete(book_ID, mem_ID);
	}
}
