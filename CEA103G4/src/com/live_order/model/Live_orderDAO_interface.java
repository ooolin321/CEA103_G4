package com.live_order.model;

import java.util.*;

import com.live_order_detail.model.Live_order_detailVO;

public interface Live_orderDAO_interface {
	public void insert(Live_orderVO live_orderVO);

	public void update(Live_orderVO live_orderVO);

	public void delete(Integer live_order_no);

	public Live_orderVO findByPrimaryKey(Integer live_order_no);

	public List<Live_orderVO> getAll();
	
	public Set<Live_order_detailVO> getDetailsByNo(Integer live_order_no);
}
