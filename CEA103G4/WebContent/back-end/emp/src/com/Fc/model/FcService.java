package com.Fc.model;

import java.util.List;

import com.Fa.model.FaVO;

public class FcService {
	private FcDAO_interface dao;
	
	public FcService() {
		dao = new FcDAO();
	}
	
	//收藏文章
	public void collectFa(String memId,String faId) {
		dao.insert(memId, faId);
	}
	
	//取消收藏 
	public void cancelCollectFa(String memId,String faId) {
		dao.delete(memId, faId);
	}
	
	public List<FaVO> getOneMemCollection(String memId){
		return dao.getOneMemColFa(memId);
	}
	
	public Integer checkCollection(String memId,String faId) {
		return dao.checkCollect(memId, faId);
	}
	
	
}
