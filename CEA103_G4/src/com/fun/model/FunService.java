package com.fun.model;

import java.util.List;

public class FunService {
		
	private FunDAO_interface dao;
	
	public FunService() {
		dao = new FunDAO();
	}
	
	public FunVO addFun(String funName) {
		FunVO funVO = new FunVO();
		
		funVO.setFunName(funName);	
		dao.insert(funVO);
		
		return funVO;
	}
	
	public FunVO updateFun(Integer funno,String funName) {
		
		FunVO funVO = new FunVO();
		
		funVO.setFunno(funno);
		funVO.setFunName(funName);
		
		dao.update(funVO);
		
		return funVO;
	}
	
	public void deleteFun(Integer funno) {
		dao.delete(funno);
	}
	
	public FunVO getOneFun(Integer funno) {
		return dao.findByPrimaryKey(funno);
	}
	
	public List<FunVO> getAll(){
		return dao.getAll();
	}
}	

