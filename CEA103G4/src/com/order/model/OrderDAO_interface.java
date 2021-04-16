package com.order.model;

import java.util.*;

public interface OrderDAO_interface {
	public void insert(OrderVO orderVO);
	public void update(OrderVO orderVO);
	public void delete(Integer order_no);
	public OrderVO findByPrimaryKey(Integer order_no);
	public List<OrderVO> getAll();
	
}