package com.bookpic.model;

public class BookPicture {
	private String bookID;
	private String bookPicName;
	private byte[] bookPic;

	public BookPicture() {
	}

	public BookPicture(String bookID, String bookPicName, byte[] bookPic) {
		this.bookID = bookID;
		this.bookPicName = bookPicName;
		this.bookPic = bookPic;
	}

	public String getBookID() {
		return bookID;
	}

	public void setBookID(String bookID) {
		this.bookID = bookID;
	}

	public String getBookPicName() {
		return bookPicName;
	}

	public void setBookPicName(String bookPicName) {
		this.bookPicName = bookPicName;
	}

	public byte[] getBookPic() {
		return bookPic;
	}

	public void setBookPic(byte[] bookPic) {
		this.bookPic = bookPic;
	}

	@Override
	public String toString() {
		return bookID + "\t" + bookPicName;
	}

}
