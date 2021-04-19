package com.auth.model;

import java.util.List;


public class AuthService {
	private AuthDAO_interface dao;
	
	public AuthService() {
		dao = new AuthDAO();
	}
	
	public AuthVO addAuth(Integer empno,Integer funno,Integer state) {
		AuthVO authVO = new AuthVO();
		
		authVO.setEmpno(empno);
		authVO.setFunno(funno);
		authVO.setState(state);
				
		dao.insert(authVO);

		return authVO;
	}
	
	public AuthVO updateAuth(Integer empno, Integer funno, Integer state) {

		AuthVO authVO = new AuthVO();

		authVO.setEmpno(empno);
		authVO.setFunno(funno);
		authVO.setState(state);
		
		dao.update(authVO);

		return authVO;
	}
	public void deleteAuthority(Integer empno,Integer funno) {
		dao.delete(empno,funno);
	}

	public AuthVO getOneAuthorityByEmpNo(Integer empno) {
		return dao.findByEmpNo(empno);
	}

	public List<AuthVO> getAll() {
		return dao.getAll();
	}

	
}
