package com.live.model;

import java.util.List;

public class LiveService {
	private LiveDAO_interface dao;
	
	public LiveService() {
		dao = new LiveJNDIDAO();
	}
	
	public LiveVO addLive(String live_type,String live_name,java.sql.Date live_time,Integer live_state,String user_id,Integer empno,byte[] live_photo) {
		
		LiveVO liveVO = new LiveVO();
		

		liveVO.setLive_type(live_type);
		liveVO.setLive_name(live_name);
		liveVO.setLive_time(live_time);
		liveVO.setLive_state(live_state);
		liveVO.setUser_id(user_id);
		liveVO.setEmpno(empno);
		liveVO.setLive_photo(live_photo);
		
		dao.insert(liveVO);
		
		return liveVO;
		
	}
	
	public LiveVO updateLive(String live_type,String live_name,java.sql.Date live_time,Integer live_state,String user_id,Integer empno,byte[] live_photo,Integer live_no) {
		
		LiveVO liveVO = new LiveVO();
		
		
		liveVO.setLive_type(live_type);
		liveVO.setLive_name(live_name);
		liveVO.setLive_time(live_time);
		liveVO.setLive_state(live_state);
		liveVO.setUser_id(user_id);
		liveVO.setEmpno(empno);
		liveVO.setLive_photo(live_photo);
		liveVO.setLive_no(live_no);
		
		
		dao.update(liveVO);
		
		return liveVO;
		
	}
	
	public void deleteLive(Integer live_no) {
		dao.delete(live_no);
	}
	
	public LiveVO getOneLive(Integer live_no) {
		return dao.findByPrimaryKey(live_no);
	}
	
	public List<LiveVO> getAll(){
		return dao.getAll();
	}
}
