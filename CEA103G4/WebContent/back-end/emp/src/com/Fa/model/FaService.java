package com.Fa.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class FaService {
	private FaDAO_interface dao;
	
	public FaService() {
		dao = new FaDAO();
		
	}
	//新增留言
	public FaVO addFa(String memId,String faTopic,String faContent) {
		
		FaVO faVO = new FaVO();
		
		faVO.setMemId(memId);
		faVO.setFaTopic(faTopic);
		faVO.setFaContent(faContent);
		faVO.setFaId(dao.insert(faVO));
		
		return faVO;
		
	}
	//修改留言
	public FaVO updateFa(String faTopic,String faContent,String faId) {
		
		FaVO faVO = new FaVO();
		
		faVO.setFaTopic(faTopic);
		faVO.setFaContent(faContent);
		faVO.setFaId(faId);

		
		dao.update(faVO);
		return faVO;
		
	}
	//增加瀏覽數
	public FaVO addFaViews(String faId,Integer faViews) {
		FaVO faVO = new FaVO();
		faVO.setFaId(faId);
		faVO.setFaViews(faViews);
		dao.addFaViews(faVO);
		return faVO;
	}
	//取得一篇文章
	public FaVO getOneFa(String faId) {
		return dao.findOneFaByFaId(faId);
	}
	//取得一個會員的所有文章
	public List<FaVO> getOneMemFa(String memId) {
		return dao.findOneMemFa(memId);
	}
	//下架文章
	public void deleteFa(String faId) {
		dao.delete(faId);
	}
	//最新文章
	public List<FaVO> getAll_Index(){
		return dao.getAllIndex();
	}
	//熱門文章
	public List<FaVO> getAllHot(){
		return dao.getAllHot();
	}
	//模糊搜尋
	public List<FaVO> search(String faTopic){
		return dao.search(faTopic);
	}
	
}
