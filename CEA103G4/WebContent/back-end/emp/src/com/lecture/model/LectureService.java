package com.lecture.model;

import java.util.List;
import java.util.Optional;



public class LectureService {
	
	private LectureDAO_interface dao;

	public LectureService() {
		dao = new LectureDAO();
	}

	public LectureVO addLecture(String lc_class_id, String admin_id, String lc_name, String lc_place, java.sql.Timestamp lc_time, int lc_hr, java.sql.Timestamp lc_deadline, java.sql.Timestamp lc_start_time, int lc_peo_lim, int lc_peo_up, String lc_info, byte[] lc_pic, int lc_state) {
		LectureVO lectureVO = new LectureVO();

		lectureVO.setLc_class_id(lc_class_id);
		lectureVO.setAdmin_id(admin_id);
		lectureVO.setLc_name(lc_name);
		lectureVO.setLc_place(lc_place);
		lectureVO.setLc_time(lc_time);
		lectureVO.setLc_hr(lc_hr);
		lectureVO.setLc_deadline(lc_deadline);
		lectureVO.setLc_start_time(lc_start_time);
		lectureVO.setLc_peo_lim(lc_peo_lim);
		lectureVO.setLc_peo_up(lc_peo_up);
		lectureVO.setLc_info(lc_info);
		lectureVO.setLc_pic(lc_pic);
		lectureVO.setLc_state(lc_state);
		dao.insert(lectureVO);

		return lectureVO;

	}


		public LectureVO update(String lc_place, java.sql.Timestamp lc_time, int lc_hr,java.sql.Timestamp lc_start_time, java.sql.Timestamp lc_deadline, int lc_peo_lim, int lc_peo_up, String lc_info, int lc_state,String lc_id) {
		//System.out.println("lc_price="+lc_price);
		LectureVO lectureVO = new LectureVO();

		lectureVO.setLc_place(lc_place);
		lectureVO.setLc_time(lc_time);
		lectureVO.setLc_hr(lc_hr);
		lectureVO.setLc_deadline(lc_deadline);
		lectureVO.setLc_start_time(lc_start_time);
		lectureVO.setLc_peo_lim(lc_peo_lim);
		lectureVO.setLc_peo_up(lc_peo_up);
		lectureVO.setLc_info(lc_info);
		lectureVO.setLc_state(lc_state);
		lectureVO.setLc_id(lc_id);
		dao.update(lectureVO);

		return lectureVO;

	}
//	public LectureVO update_state1or2(String lc_place, java.sql.Timestamp lc_time, int lc_hr, java.sql.Timestamp lc_deadline, int lc_state) {
//		
//		LectureVO lectureVO = new LectureVO();
//		lectureVO.setLc_place(lc_place);
//		lectureVO.setLc_time(lc_time);
//		lectureVO.setLc_hr(lc_hr);
//		lectureVO.setLc_deadline(lc_deadline);
//		lectureVO.setLc_state(lc_state);
//		
//		dao.update_state1or2(lectureVO);
//		return lectureVO;
//	}
	
	public void deleteLecture(String lc_id) {
		dao.delete(lc_id);
	}
	
	public LectureVO getOneLecture(String lc_id) {
		return dao.findByPrimaryKey(lc_id);
	}
	
	public List<LectureVO> getByState() {
		return dao.findByState();
	}
	
	public List<LectureVO> getAll() {
		return dao.getAll();
	}

	public Optional<LectureVO> getLecturePicByLcId(String lc_id){
		return dao.findLecturePicByLcId(lc_id);
	}
	
}

