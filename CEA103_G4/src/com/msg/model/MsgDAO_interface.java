package com.msg.model;

import java.util.*;

public interface MsgDAO_interface {
          public void insert(MsgVO msgVO);
          public void update(MsgVO msgVO);
          public void delete(Integer message_id);
          public MsgVO findByPrimaryKey(Integer message_id);
          public List<MsgVO> getAll();
          //萬用複合查詢(傳入參數型態Map)(回傳 List)
//        public List<EmpVO> getAll(Map<String, String[]> map); 
}
