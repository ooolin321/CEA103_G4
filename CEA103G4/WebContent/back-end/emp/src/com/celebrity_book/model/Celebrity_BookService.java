package com.celebrity_book.model;

import java.util.List;


public class Celebrity_BookService {
	private Celebrity_BookDAO_interface dao;
	
	public Celebrity_BookService() {
		dao = new Celebrity_BookDAO();
	}
	
	public Celebrity_Book addCelBook(String book_ID, String mem_ID,Integer share_State) {
		Celebrity_Book celebrity_Book = new Celebrity_Book();
			
		celebrity_Book.setBook_ID(book_ID);
		celebrity_Book.setMem_ID(mem_ID);
		celebrity_Book.setShare_State(share_State);
		dao.insert(celebrity_Book);
		
		return celebrity_Book;
	}
	public Celebrity_Book updateCelBook(Integer share_State,String book_ID, String mem_ID) {
		Celebrity_Book celebrity_Book = new Celebrity_Book();
		
		
		celebrity_Book.setShare_State(share_State);
		celebrity_Book.setBook_ID(book_ID);
		celebrity_Book.setMem_ID(mem_ID);
		
		dao.update(celebrity_Book);
		
		return celebrity_Book;
	}
	public List<Celebrity_Book> getAll(String mem_ID){
		return dao.getAll(mem_ID);
	}
	public void deleteCelBook(String book_ID,String mem_ID) {
		dao.delete(book_ID, mem_ID);
	}
	public Celebrity_Book getOneCelBook(String book_ID, String mem_ID) {
		return dao.findByPrimaryKey(book_ID, mem_ID);
	}
	
	
}
