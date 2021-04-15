package com.live_order.model;

import java.util.*;

public interface Live_orderDAO_interface {
	public void insert(Live_orderVO live_orderVO);

	public void update(Live_orderVO live_orderVO);

	public void delete(Integer live_order_no);

	public Live_orderVO findByPrimaryKey(Integer live_order_no);

	public List<Live_orderVO> getAll();
}
