package com.signup.model;

import java.util.List;

public interface SignupDAO_interface {
	public void insert(SignupVO signupVO);
//	public void update(SignupVO signupVO);
	public void delete(String signup_id);
	public SignupVO findByPrimaryKey(String signup_id);
	public List<SignupVO> findByLecture(String lc_id);
	public List<SignupVO> findByMember(String mem_id);
	public List<SignupVO> getAll();
	public boolean checkSignUp(String mem_id, String lc_id);
}
