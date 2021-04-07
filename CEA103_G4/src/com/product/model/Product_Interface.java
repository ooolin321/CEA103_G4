package com.product.model;
import java.util.*;

public interface Product_Interface {
          public void insert(ProductVO productVO);
          public void update(ProductVO productVO);
          public void delete(Integer product_id);
          public ProductVO findByPrimaryKey(Integer product_id);
          public List<ProductVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 

}
