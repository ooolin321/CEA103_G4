package com.bookpic.model;

import java.util.List;
import java.util.Optional;

public interface BookPicDAO {
	void insert(BookPicture bookPic);
	Optional<BookPicture> findFirstPicByBookID(String bookID);
	Integer findLastPicNameByBookID(String bookID);
	List<BookPicture> findByBookID(String bookID);
	void update(BookPicture bookPic);
	void deleteByBookID(String bookID); // 可能刪除多張
	void deleteByBookIDAndPicName(String bookID, String bookPicName); //刪除單張
	Optional<BookPicture> findByBookIDAndBookPicName(String bookID, String bookPicName);
}
