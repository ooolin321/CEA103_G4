package com.celebrity_book.model;

import java.util.List;


public interface Celebrity_BookDAO_interface {
	public void insert(Celebrity_Book celebrity_Book);
	public void update(Celebrity_Book celebrity_Book);
    public void delete(String book_ID,String mem_ID);
    public List<Celebrity_Book> getAll(String mem);
    public Celebrity_Book findByPrimaryKey(String book_ID,String mem_ID);
}
