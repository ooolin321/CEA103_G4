package com.Fmr.model;

import java.util.List;

public class FmrService {
	private FmrDAO_interface dao;
	
	public FmrService() {
		dao = new FmrDAO();
	}
	
	//新增留言檢舉
	public FmrVO addFmr(String fmId,String memId,String fmrContent) {
		FmrVO fmrVO = new FmrVO();
		fmrVO.setFmId(fmId);
		fmrVO.setMemId(memId);
		fmrVO.setFmrContent(fmrContent);
		dao.insert(fmrVO);
		return fmrVO;
	}
	//審查留言檢舉是否通過
	public void judgeFmr(Integer fmrStatus,String adminId,String fmrId) {
		FmrVO fmrVO = new FmrVO();
		fmrVO.setFmrStatus(fmrStatus);
		fmrVO.setAdminId(adminId);
		fmrVO.setFmrId(fmrId);
		dao.judge_Fmr(fmrVO);
	}
	
	//顯示所有留言檢舉資料
	public List<FmrVO> getAll(){
		return dao.getAll();
	}
	
	//顯示待審核留言檢舉
	public List<FmrVO> getAllJundge(){
		return dao.getAllJundge();
	}
	
}
