package com.payme.model;

import java.util.*;

public interface PayDAO_interface {
	public void insert(PayVO payVO);
    public void update(PayVO payVO);
    public void delete(String credit_card_num);
    public List<PayVO> findByMemberId(String mem_id);
    public List<PayVO> getAll();
}
