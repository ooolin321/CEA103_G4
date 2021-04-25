package com.emp.model;

import java.util.*;



public interface EmpDAO_interface {
	public void insert(EmpVO empVO);
	public void update(EmpVO empVO);
	public void delete(Integer empno);
    public EmpVO findByPrimaryKey(Integer empno);
    public List<EmpVO> getAll();
    
    public EmpVO login(Integer empno,String empPwd);
	public List<EmpVO> sendMail(EmpVO empVO);
	public String genAuthCode();
	
	

    
    //萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 
	
}
