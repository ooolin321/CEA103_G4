package com.live_order_detail.model;

import java.util.List;

import com.live_order.model.Live_orderVO;

public class Live_order_detailDAO_interface {
    public void insert(Live_order_detailVO live_order_detailVO);
    public void update(Live_order_detailVO live_order_detailVO);
    public void delete(Integer live_order_no);
    public Live_orderVO findByPrimaryKey(Integer live_order_no);
    public List<Live_orderVO> getAll();
}
