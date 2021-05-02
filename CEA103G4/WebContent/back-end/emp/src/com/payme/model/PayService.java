package com.payme.model;

import java.util.List;

public class PayService {
	private PayDAO_interface dao;
	
	public PayService() {
		dao = new PayDAO();
	}
	
	public PayVO addPay(String credit_card_num,
			Integer credit_card_year, Integer credit_card_mon,String mem_id) {
		
		PayVO payVO = new PayVO();
		payVO.setCredit_card_num(credit_card_num);
		payVO.setCredit_card_year(credit_card_year);
		payVO.setCredit_card_mon(credit_card_mon);
		payVO.setMem_id(mem_id);
		dao.insert(payVO);
		return payVO;
	}
	
	public void addPay(PayVO payVO) {
		dao.insert(payVO);
	}
	
	public void deletePay(String credit_card_num) {
		dao.delete(credit_card_num);
	}
	
	public List<PayVO> getOneMemPay(String mem_id){
		return dao.findByMemberId(mem_id);	
	}
	
	public List<PayVO> getAll(){
		return dao.getAll();
		
	}
}
