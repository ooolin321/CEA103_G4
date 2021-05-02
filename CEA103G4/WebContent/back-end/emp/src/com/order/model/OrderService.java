package com.order.model;

import java.util.List;
import java.util.Map;

import com.detail.model.DetailVO;


public class OrderService {
	
	private OrderDAO_Interface odi;

	public OrderService() {
		super();
		odi = new OrderDAO();
	}
	
	public OrderVO createOd(String mem_id, String rec_name, String rec_tel, 
			String rec_add, Integer order_qty, Integer order_total, 
			Integer order_pay, Integer delivery, Integer get_bonus, Integer use_bonus, 
			String mem_note) {
				
		OrderVO odVO = new OrderVO();
	
		odVO.setMem_id(mem_id);
		odVO.setRec_name(rec_name);
		odVO.setRec_tel(rec_tel);
		odVO.setRec_add(rec_add);
		odVO.setOrder_qty(order_qty);
		odVO.setOrder_total(order_total);
		odVO.setOrder_pay(order_pay);
		odVO.setDelivery(delivery);
		odVO.setGet_bonus(get_bonus);
		odVO.setUse_bonus(use_bonus);
		odVO.setMem_note(mem_note);
		odi.doCreate(odVO);
		
		return odVO; 
		
	}
	
	public void createODDT(OrderVO odCartVO , List<DetailVO> cartlist, Double newBonus) {

		odi.doCreateODDT(odCartVO, cartlist, newBonus);
		
	}
	
	
	public OrderVO updateOd(String order_id, String rec_name, String rec_tel, 
			String rec_add,Integer order_pay, Integer delivery,Integer order_status, 
			 String mem_note) {
		
		OrderVO odVO = new OrderVO();
		
		odVO.setOrder_id(order_id);
		odVO.setRec_name(rec_name);
		odVO.setRec_tel(rec_tel);
		odVO.setRec_add(rec_add);
		odVO.setOrder_pay(order_pay);
		odVO.setDelivery(delivery);
		odVO.setOrder_status(order_status);
		odVO.setMem_note(mem_note);
		odi.update(odVO);
		return odVO;
	}
	
	public void cancel(String order_id) {
		odi.cancel(order_id);
	}
	
	public void shipment(OrderVO odvo) {
		odi.shipment(odvo);
	}
	
	public OrderVO getOneOd(String order_id) {
		return odi.findbyid(order_id);
	}
	
	public List<OrderVO> getAll(){
		return odi.getAll();
		
	}
	public List<OrderVO> allSelect(Map<String, String[]> map){
		return odi.allSelect(map);
	}
	

}
