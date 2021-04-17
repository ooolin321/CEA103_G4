package com.ad.model;

import java.util.List;

public interface AdDAO_interface {
	public void insert(AdVO ad_no);

	public void update(AdVO ad_no);
	
	public void delete(Integer ad_no);
	
	public AdVO findByPrimaryKey(Integer ad_no);

	public List<AdVO> getAll();
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
