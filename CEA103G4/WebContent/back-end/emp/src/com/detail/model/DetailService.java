package com.detail.model;

import java.util.List;

public class DetailService {

	private DetailDAO_Interface ddi;
	
	public DetailService() {
		super();
		ddi = new DetailDAO();
	}

	public DetailVO createDt(String order_id, String book_id, String items_name, 
			Integer comm_qty, Double comm_price, Integer comm_bonus) {
		DetailVO dtVO = new DetailVO();
		
		dtVO.setOrder_id(order_id);
		dtVO.setBook_id(book_id);
		dtVO.setItems_name(items_name);
		dtVO.setComm_qty(comm_qty);
		dtVO.setComm_price(comm_price);
		dtVO.setComm_bonus(comm_bonus);
		ddi.doCreate(dtVO, null);
		
		return dtVO;
	}

	public List<DetailVO> getOneOd(String order_id) {
		return ddi.findbyid(order_id);
	}

}
