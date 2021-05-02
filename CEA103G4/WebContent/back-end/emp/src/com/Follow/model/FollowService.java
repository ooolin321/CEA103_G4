package com.Follow.model;

import java.util.List;

public class FollowService {
	private FollowDAO dao;
	
	public FollowService() {
		dao = new FollowDAO();
	}
	
	//追蹤
	public void subscribe(String memId,String aMemId) {
		FollowVO followVO = new FollowVO();
		followVO.setMemId(memId);
		followVO.setaMemId(aMemId);
		
		dao.insert(followVO);
	}
	
	//退追蹤
	public void unSubscribe(String memId,String aMemId) {
		FollowVO followVO = new FollowVO();
		followVO.setMemId(memId);
		followVO.setaMemId(aMemId);
		
		dao.delete(followVO);
	}
	//通知
	public List<FollowVO> getFollowList(String aMemId){
		return dao.followList(aMemId);
	}
	
	public Integer checkSubscribe(String memId,String aMemId) {
		return dao.checkSubscribe(memId, aMemId);
	}
	
}
