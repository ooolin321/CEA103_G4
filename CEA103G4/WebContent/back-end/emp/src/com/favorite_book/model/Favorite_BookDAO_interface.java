package com.favorite_book.model;

import java.util.List;

import com.celebrity_book.model.Celebrity_Book;
import com.mem.model.MemVO;


public interface Favorite_BookDAO_interface {
	public void insert(Favorite_Book favorite_Books);
    public void delete(String book_ID,String mem_ID);
    public List<Favorite_Book> getAll(String mem);
}
