package com.order.model;

//import java.sql.Date;
import java.util.List;

public class OrderService {

	private OrderDAO_interface dao;

	public OrderService() {
		dao = new OrderJNDIDAO();
	}

	public OrderVO addOrder(Integer order_state, Integer order_shipping,  Integer order_price, Integer pay_method, String rec_name, String rec_addr, String rec_phone, String rec_cellphone, Integer logistics, Integer logisticsstate, Integer discount, String user_id, String seller_id, Integer srating, String srating_content, Integer point) {

		OrderVO orderVO = new OrderVO();

//		orderVO.setOrder_date(order_date);
		orderVO.setOrder_state(order_state);
		orderVO.setOrder_shipping(order_shipping);
		orderVO.setOrder_price(order_price);
		orderVO.setPay_method(pay_method);
//		orderVO.setPay_deadline(pay_deadline);
		orderVO.setRec_name(rec_name);
		orderVO.setRec_addr(rec_addr);
		orderVO.setRec_phone(rec_phone);
		orderVO.setRec_cellphone(rec_cellphone);
		orderVO.setLogistics(logistics);
		orderVO.setLogisticsstate(logisticsstate);
		orderVO.setDiscount(discount);
		orderVO.setUser_id(user_id);
		orderVO.setSeller_id(seller_id);
		orderVO.setSrating(srating);
		orderVO.setSrating_content(srating_content);
		orderVO.setPoint(point);
		
		dao.insert(orderVO);

		return orderVO;
	}

	public OrderVO updateOrder(java.sql.Date order_date, Integer order_state, Integer order_shipping,  Integer order_price, Integer pay_method, java.sql.Date pay_deadline, String rec_name, String rec_addr, String rec_phone, String rec_cellphone, Integer logistics, Integer logisticsstate, Integer discount, String user_id, String seller_id, Integer srating, String srating_content, Integer point, Integer order_no) 
	{
		OrderVO orderVO = new OrderVO();

		orderVO.setOrder_date(order_date);
		orderVO.setOrder_state(order_state);
		orderVO.setOrder_shipping(order_shipping);
		orderVO.setOrder_price(order_price);
		orderVO.setPay_method(pay_method);
		orderVO.setPay_deadline(pay_deadline);
		orderVO.setRec_name(rec_name);
		orderVO.setRec_addr(rec_addr);
		orderVO.setRec_phone(rec_phone);
		orderVO.setRec_cellphone(rec_cellphone);
		orderVO.setLogistics(logistics);
		orderVO.setLogisticsstate(logisticsstate);
		orderVO.setDiscount(discount);
		orderVO.setUser_id(user_id);
		orderVO.setSeller_id(seller_id);
		orderVO.setSrating(srating);
		orderVO.setSrating_content(srating_content);
		orderVO.setPoint(point);
		orderVO.setOrder_no(order_no);
		
		dao.update(orderVO);
		return orderVO;
	}

	public void deleteOrder(Integer order_no) {
		dao.delete(order_no);
	}

	public OrderVO getOneOrder(Integer order_no) {
		return dao.findByPrimaryKey(order_no);
	}

	public List<OrderVO> getAll() {
		return dao.getAll();
	}
}
