package com.udbtx.model;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;

public class UdbTxService {
	private UdbTxDAO_interface dao;

	public UdbTxService() {
		dao = new UdbTxDAO();
	}

	public UdbTxVO addUsedbookTransaction(String book_id, String seller_mem_id,
			String buyer_mem_id, String book_state, byte[] book_state_pic, 
			Integer udb_sale_price, Timestamp release_date, Integer udb_order_state_no, 
			Timestamp udb_order_date, Integer prod_state, Integer payment_state) {

		UdbTxVO udbtxVO = new UdbTxVO();

		udbtxVO.setBook_id(book_id);
		udbtxVO.setSeller_mem_id(seller_mem_id);
		udbtxVO.setBuyer_mem_id(buyer_mem_id);
		udbtxVO.setBook_state(book_state);
		udbtxVO.setBook_state_pic(book_state_pic);
		udbtxVO.setUdb_sale_price(udb_sale_price);
		udbtxVO.setRelease_date(release_date);
		udbtxVO.setUdb_order_state_no(udb_order_state_no);
		udbtxVO.setUdb_order_date(udb_order_date);
		udbtxVO.setProd_state(prod_state);
		udbtxVO.setPayment_state(payment_state);

		dao.insert(udbtxVO);

		return udbtxVO;
	}

	public UdbTxVO updateUsedbookTransaction(String po_no, String book_state, byte[] book_state_pic, 
			Integer udb_sale_price, Integer udb_order_state_no, Integer prod_state, Integer payment_state) {

		UdbTxVO udbtxVO = new UdbTxVO();

		udbtxVO.setPo_no(po_no);
		udbtxVO.setBook_state(book_state);
		udbtxVO.setBook_state_pic(book_state_pic);
		udbtxVO.setUdb_sale_price(udb_sale_price);
		udbtxVO.setUdb_order_state_no(udb_order_state_no);
		udbtxVO.setProd_state(prod_state);
		udbtxVO.setPayment_state(payment_state);		
		dao.update(udbtxVO);

		return udbtxVO;
	}

	public void deleteUdbTx(String po_no) {
		dao.delete(po_no);
	}

	public UdbTxVO getOnePoNo(String po_no) {
		return dao.findByPoNo(po_no);
	}
	public List<UdbTxVO> getOneSeller(String seller_mem_id) {
		return dao.findBySellerId(seller_mem_id);
	}
	public List<UdbTxVO> getOneBuyer(String buyer_mem_id) {
		return dao.findByBuyerId(buyer_mem_id);
	}

	public List<UdbTxVO> getAll() {
		return dao.getAll();
	}
	public Optional<UdbTxVO> getOnePoNoPic(String po_no) {
		return dao.findUdbPicByPoNo(po_no);
	}

}
