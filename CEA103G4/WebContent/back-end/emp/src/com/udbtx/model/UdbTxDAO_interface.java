package com.udbtx.model;

import java.util.List;
import java.util.Optional;

public interface UdbTxDAO_interface {
	public void insert(UdbTxVO udbtxVO);
	public void update(UdbTxVO udbtxVO);
	public void delete(String po_no);
	public List<UdbTxVO> findBySellerId(String seller_mem_id);
	public List<UdbTxVO> findByBuyerId(String buyer_mem_id);
	public UdbTxVO findByPoNo(String po_no);
	public List<UdbTxVO> getAll();
	Optional<UdbTxVO> findUdbPicByPoNo(String po_no);//show圖片

}
