package com.pdc.model;

import java.util.*;

public interface PdcDAO_interface {
          public void insert(PdcVO pdcVO);
          public void update(PdcVO pdcVO);
          public void delete(Integer product_id);
          public PdcVO findByPrimaryKey(Integer product_id);
          public List<PdcVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
