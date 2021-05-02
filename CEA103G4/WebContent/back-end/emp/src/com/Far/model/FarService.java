package com.Far.model;

import java.util.List;

public class FarService {
	private FarDAO_interface dao;
	
	public FarService() {
		dao = new FarDAO();
	}

	//新增文章檢舉
	public FarVO addFar(String faId,String memId,String farContent) {
		FarVO farVO = new FarVO();
		farVO.setFaId(faId);
		farVO.setMemId(memId);
		farVO.setFarContent(farContent);
		dao.insert(farVO);
		return farVO; 
	}
	
	//審查文章檢舉是否通過
	public void judgeFar(Integer farStatus,String adminId,String farId) {
		FarVO farVO = new FarVO();
		farVO.setFarId(farId);
		farVO.setFarStatus(farStatus);
		farVO.setAdminId(adminId);
		dao.judge(farVO);
	}

	
	//顯示所有文章檢舉資料
	public List<FarVO> getAll(){
		return dao.getAll();
	}
	
	//顯示狀態為0的 文章檢舉資料
	public List<FarVO> getAllJundge(){
		return dao.getAllJundge();
	}
	
}
