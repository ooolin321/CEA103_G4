package com.Fm.model;

import java.util.ArrayList;
import java.util.List;

import com.Fa.model.FaVO;

public class FmService {
	private FmDAO_interface dao;
	
	public FmService() {
		dao = new FmDAO();
	}
	//取得所有留言
	public List<FmVO> getAll(){
		return dao.getAll();
	}
	
	//新增留言
	public FmVO addFm(String faId,String memId,String fmContent) {
		FmVO fmVO = new FmVO();
		fmVO.setFaId(faId);
		fmVO.setMemId(memId);
		fmVO.setFmContent(fmContent);
		dao.insert(fmVO);
		return fmVO;
	}
	//下架留言
	public void logout_Fm(String fmId) {
		dao.delete(fmId);
	}
	//取得文章下的所有留言
	public List<FmVO> getOneFAFm(String faId) {
		return dao.getOneFaFm(faId);
	}
	//取得一篇文章來進行留言
	public FmVO addFmByGetFa(String faId) {
		return dao.addFmByGetFa(faId);
	}
	//修改留言
	public FmVO updateFm(String fmId,String fmContent) {
		FmVO fmVO = new FmVO();
		fmVO.setFmId(fmId);
		fmVO.setFmContent(fmContent);
		dao.update(fmVO);
		return fmVO;
	}
	//取得一個文章的回應數
	public Integer getFmResponsesByFaId(String faId) {
		return dao.getFmResponsesByFaId(faId);
	}
	//取得一個留言來檢舉或修改
	public FmVO getOneFm(String fmId) {
		return dao.getOneFmByFmId(fmId);
	}
	//取得一個會員的所有留言(還沒做)
	public List<FmVO> getOneMemFm(String memId){
		return dao.getOneMemFm(memId);
	}
	
	
}
