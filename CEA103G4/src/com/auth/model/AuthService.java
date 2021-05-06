package com.auth.model;

import java.util.List;


public class AuthService {
	private AuthDAO_interface dao;
	
	public AuthService() {
		dao = new AuthDAO();
	}
	
	public AuthVO addAuth(Integer empno,Integer funno,Integer auth_no) {
		AuthVO authVO = new AuthVO();
		
		authVO.setEmpno(empno);
		authVO.setFunno(funno);
		authVO.setAuth_no(auth_no);

				
		dao.insert(authVO);

		return authVO;
	}
	
	public AuthVO updateAuth(Integer empno, Integer funno,  Integer auth_no) {

		AuthVO authVO = new AuthVO();
		authVO.setEmpno(empno);
//System.out.println("AuthSvc empno= " + empno);		
		authVO.setFunno(funno);
//System.out.println("AuthSvc funno= " + funno);		
		authVO.setAuth_no(auth_no);
//System.out.println("AuthSvc auth_no= " + auth_no);
		
		dao.update(authVO);

		return authVO;
	}
	public void deleteAuth(Integer empno,Integer funno) {
		dao.delete(funno,empno);
	}

	public List<AuthVO> getOneAuth(Integer empno) {
		return dao.findAuthByEmpno(empno);
	}

	public List<AuthVO> getAll() {
		return dao.getAll();
	}

	
}
