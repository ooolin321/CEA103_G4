package com.auth.model;

import java.util.List;


public class AuthService {
	private AuthDAO_interface dao;
	
	public AuthService() {
		dao = new AuthDAO();
	}
	
	public AuthVO addAuth(Integer empno,Integer funno,Integer auth_no) {
		AuthVO authVO = new AuthVO();
		
		authVO.setFunno(funno);
		authVO.setEmpno(empno);
		authVO.setAuth_no(auth_no);

				
		dao.insert(authVO);

		return authVO;
	}
	
	public AuthVO updateAuth(Integer funno,Integer empno , Integer auth_no) {

		AuthVO authVO = new AuthVO();

		authVO.setEmpno(empno);
		authVO.setFunno(funno);
		authVO.setAuth_no(auth_no);
		
		dao.update(authVO);

		return authVO;
	}
	public void deleteAuth(Integer empno,Integer funno) {
		dao.delete(funno,empno);
	}

	public AuthVO getOneAuth(Integer empno) {
		return dao.findAuthByEmpno(empno);
	}

	public List<AuthVO> getAll() {
		return dao.getAll();
	}

	
}
