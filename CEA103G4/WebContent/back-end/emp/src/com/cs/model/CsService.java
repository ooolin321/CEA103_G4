package com.cs.model;

import java.util.List;


public class CsService {
	private CsDAO_interface dao;
	
	public CsService() {
		dao = new CsDAO();
	}
	
	//新增訊息
	public CsVO addCs(String cs_Email,String cs_Tel, String cs_Subject,String cs_Message,Integer cs_isSend) {
		CsVO csVO = new CsVO();
		
		csVO.setCs_Email(cs_Email);
		csVO.setCs_Tel(cs_Tel);
		csVO.setCs_Subject(cs_Subject);
		csVO.setCs_Message(cs_Message);
		csVO.setCs_isSend(cs_isSend);
		dao.insert(csVO);
		
		return csVO;		
	}
	//刪除訊息
	public void deleteCs(String cs_ID) {
		dao.delete(cs_ID);
	}
	//顯示全部
	public List<CsVO> getAll(){
		return dao.getAll();
	}	
	//更新回覆狀態
	public CsVO updateCs(Integer cs_isSend,String cs_ID) {
		
		CsVO csVO =new CsVO();
	
		csVO.setCs_isSend(cs_isSend);
		csVO.setCs_ID(cs_ID);
		
		dao.update(csVO);
		
		return csVO;
	}
	//查詢信箱
	public List<CsVO> getSearch(String cssearch){
		return dao.findBySearch(cssearch);
	}
}
