package com.lecture.model;

import java.util.List;
import java.util.Optional;

public interface LectureDAO_interface {
	public void insert(LectureVO LectureVO);
	public void update(LectureVO LectureVO);
	public void update_state1or2(LectureVO LectureVO);
	public void delete(String lc_id);
	public LectureVO findByPrimaryKey(String lc_id);
	public List<LectureVO> findByState();
	public List<LectureVO> getAll();
	Optional<LectureVO> findLecturePicByLcId(String lc_id);
}
