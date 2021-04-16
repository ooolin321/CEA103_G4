package com.user.model;

import java.util.List;

public interface UserDAO_interface {
	public void insert(UserVO userVO);

	public void update(UserVO userVO);
	
	public void delete(String user_id);
	
	public UserVO findByPrimaryKey(String user_id);

	public List<UserVO> getAll();
	// 萬用複合查詢(傳入參數型態Map)(回傳 List)
//  public List<EmpVO> getAll(Map<String, String[]> map); 

}
