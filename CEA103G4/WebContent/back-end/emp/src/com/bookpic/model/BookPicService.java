package com.bookpic.model;

import java.util.List;
import java.util.Optional;

public class BookPicService {
	private final BookPicDAO bookPicDAO;

	public BookPicService(BookPicDAO bookPicDAO) {
		this.bookPicDAO = bookPicDAO;
	}

	public Optional<BookPicture> addBookPic(String bookID, String bookPicName, byte[] bookPic) {
		// 只取.副檔名
		bookPicName = bookPicName.substring(bookPicName.lastIndexOf('.'));
		// 決定檔名編號
		int bookPicNameNum = bookPicDAO.findLastPicNameByBookID(bookID) + 1;
		bookPicName = bookPicNameNum + bookPicName;

		BookPicture bookPicture = new BookPicture(bookID, bookPicName, bookPic);
		bookPicDAO.insert(bookPicture);
		return Optional.of(bookPicture);
	}

	public Optional<BookPicture> getFirstPicByBookID(String bookID) {
		Optional<BookPicture> bookPicture = bookPicDAO.findFirstPicByBookID(bookID);
		if (bookPicture.isPresent()) {
			return bookPicture;
		} else {
			// 查無圖片回傳此張(尚無圖片)
			return bookPicDAO.findFirstPicByBookID("B00000000001");
		}
	}

	public Integer getLastPicNameByBookID(String bookID) {
		return bookPicDAO.findLastPicNameByBookID(bookID);
	}

	public List<BookPicture> getByBookID(String bookID) {
		List<BookPicture> listBookPicture = bookPicDAO.findByBookID(bookID);
		if (listBookPicture.isEmpty()) {
			// 查無圖片回傳此張(尚無圖片)
			listBookPicture.add(bookPicDAO.findFirstPicByBookID("B00000000001").get());
		}
		return listBookPicture;
	}

	public Optional<BookPicture> updateBookPicture(String bookID, String bookPicName, byte[] bookPic) {
		BookPicture bookPicture = new BookPicture(bookID, bookPicName, bookPic);
		bookPicDAO.update(bookPicture);
		return Optional.of(bookPicture);
	}

	public void deletePicsByBookID(String bookID) {
		bookPicDAO.deleteByBookID(bookID);
	}

	public void deletePicByBookIDAndPicName(String bookID, String bookPicName) {
		bookPicDAO.deleteByBookIDAndPicName(bookID, bookPicName);
	}
	
	public Optional<BookPicture> getByBookIDAndBookPicName(String bookID, String bookPicName){
		return bookPicDAO.findByBookIDAndBookPicName(bookID, bookPicName);
	}

}
