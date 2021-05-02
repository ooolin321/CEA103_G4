package com.detail.model;

import java.sql.Connection;
import java.util.List;

public interface DetailDAO_Interface {

	void doCreate(DetailVO dtVO , Connection con);
	List<DetailVO> findbyid(String order_id);
	
}
