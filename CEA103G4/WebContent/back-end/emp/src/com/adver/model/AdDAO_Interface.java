package com.adver.model;

import java.sql.Timestamp;
import java.util.List;

public interface AdDAO_Interface {
	void doCreate(AdVO advo);
	void update(AdVO advo);
	void cancel(String ad_id);
	AdVO findbytime(Timestamp ad_start);
	List<AdVO> getAll();
	AdVO getOne(String ad_id);
}
