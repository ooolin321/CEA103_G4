package com.order.model;

import java.util.*;

public interface OrderDAO_interface {
	public Object insert(OrderVO orderVO);
	public void update(OrderVO orderVO);
	public void delete(Integer order_no);
	public OrderVO findByPrimaryKey(Integer order_no);
	public List<OrderVO> getAll();
	public List<OrderVO> getAll(Map<String, String[]> map);
}