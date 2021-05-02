package com.order.model;

import java.util.List;
import java.util.Map;

import com.detail.model.DetailVO;

public interface OrderDAO_Interface {
	
	void doCreate(OrderVO odvo);
	void doCreateODDT(OrderVO odvo, List<DetailVO> cartlist, Double newBonus);
	void update(OrderVO odvo);
	void shipment(OrderVO odvo);
	void cancel(String order_id);
	OrderVO findbyid(String order_id);
	List<OrderVO> getAll();
	public List<OrderVO> allSelect(Map<String, String[]> map);
	
}
