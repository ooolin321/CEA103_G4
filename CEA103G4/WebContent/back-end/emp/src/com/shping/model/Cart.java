package com.shping.model;

import java.io.Serializable;

public class Cart implements Serializable {
	private static final long serialVersionUID = 06L;
	
	private String mem_Id;
	private Double mem_Bonus;
	
	private String isbn;
	private String book_Id;
	private String book_Name;
	private String publisher_Id;
	private Integer order_Qty;
	private Double price;
	private Double book_BP;
	private String rec_Name;
	private String rec_Tel;
	private String rec_Add;
	private Integer order_Total;
	private Integer order_Pay;
	private Integer delivery;
	private Integer get_Bonus;
	private Integer use_Bonus; 
	private Integer comm_Qty;
	private String mem_Note;
	
	


	public Cart() {
		super();
		mem_Id="";
		mem_Bonus=0.0;
		isbn = "";
		book_Id="";
		book_Name ="";
		publisher_Id ="";
		order_Qty = 0;
		price = 0.0;
		book_BP = 0.0;
		rec_Name ="";
		rec_Tel="";
		rec_Add="";
		order_Total=0;
		order_Pay=0;
		delivery=0;
		get_Bonus=0;
		use_Bonus=0;
		comm_Qty= 0;
		mem_Note="";;
	}



	public Integer getComm_Qty() {
		return comm_Qty;
	}



	public void setComm_Qty(Integer comm_Qty) {
		this.comm_Qty = comm_Qty;
	}



	public String getBook_Id() {
		return book_Id;
	}



	public void setBook_Id(String book_Id) {
		this.book_Id = book_Id;
	}



	public String getMem_Id() {
		return mem_Id;
	}




	public void setMem_Id(String mem_Id) {
		this.mem_Id = mem_Id;
	}




	public Double getMem_Bonus() {
		return mem_Bonus;
	}




	public void setMem_Bonus(Double mem_Bonus) {
		this.mem_Bonus = mem_Bonus;
	}




	public String getIsbn() {
		return isbn;
	}




	public void setIsbn(String isbn) {
		this.isbn = isbn;
	}




	public String getBook_Name() {
		return book_Name;
	}




	public void setBook_Name(String book_Name) {
		this.book_Name = book_Name;
	}




	public String getPublisher_Id() {
		return publisher_Id;
	}




	public void setPublisher_Id(String publisher_Id) {
		this.publisher_Id = publisher_Id;
	}




	public Integer getOrder_Qty() {
		return order_Qty;
	}




	public void setOrder_Qty(Integer order_Qty) {
		this.order_Qty = order_Qty;
	}




	public Double getPrice() {
		return price;
	}




	public void setPrice(Double price) {
		this.price = price;
	}




	public Double getBook_BP() {
		return book_BP;
	}




	public void setBook_BP(Double book_BP) {
		this.book_BP = book_BP;
	}




	public String getRec_Name() {
		return rec_Name;
	}




	public void setRec_Name(String rec_Name) {
		this.rec_Name = rec_Name;
	}




	public String getRec_Tel() {
		return rec_Tel;
	}




	public void setRec_Tel(String rec_Tel) {
		this.rec_Tel = rec_Tel;
	}




	public String getRec_Add() {
		return rec_Add;
	}




	public void setRec_Add(String rec_Add) {
		this.rec_Add = rec_Add;
	}




	public Integer getOrder_Total() {
		return order_Total;
	}




	public void setOrder_Total(Integer order_Total) {
		this.order_Total = order_Total;
	}




	public Integer getOrder_Pay() {
		return order_Pay;
	}




	public void setOrder_Pay(Integer order_Pay) {
		this.order_Pay = order_Pay;
	}




	public Integer getDelivery() {
		return delivery;
	}




	public void setDelivery(Integer delivery) {
		this.delivery = delivery;
	}




	public Integer getGet_Bonus() {
		return get_Bonus;
	}




	public void setGet_Bonus(Integer get_Bonus) {
		this.get_Bonus = get_Bonus;
	}




	public Integer getUse_Bonus() {
		return use_Bonus;
	}




	public void setUse_Bonus(Integer use_Bonus) {
		this.use_Bonus = use_Bonus;
	}




	public String getMem_Note() {
		return mem_Note;
	}




	public void setMem_Note(String mem_Note) {
		this.mem_Note = mem_Note;
	}




	public static long getSerialversionuid() {
		return serialVersionUID;
	}



	




	@Override
	public String toString() {
		return "Cart [mem_Id=" + mem_Id + ", mem_Bonus=" + mem_Bonus + ", isbn=" + isbn + ", book_Id=" + book_Id
				+ ", book_Name=" + book_Name + ", publisher_Id=" + publisher_Id + ", order_Qty=" + order_Qty
				+ ", price=" + price + ", book_BP=" + book_BP + ", rec_Name=" + rec_Name + ", rec_Tel=" + rec_Tel
				+ ", rec_Add=" + rec_Add + ", order_Total=" + order_Total + ", order_Pay=" + order_Pay + ", delivery="
				+ delivery + ", get_Bonus=" + get_Bonus + ", use_Bonus=" + use_Bonus + ", mem_Note=" + mem_Note + "]";
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((book_Name == null) ? 0 : book_Name.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cart other = (Cart) obj;
		if (book_Name == null) {
			if (other.book_Name != null)
				return false;
		} else if (!book_Name.equals(other.book_Name))
			return false;
		return true;
	}
	
	

}
