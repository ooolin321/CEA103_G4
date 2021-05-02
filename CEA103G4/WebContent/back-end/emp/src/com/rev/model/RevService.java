package com.rev.model;

import java.util.List;

public class RevService {
	
	private RevDAO_interface dao;
	
	public RevService() {
		dao = new RevDAO();
	}
	
	public RevVO addRev(String rev_content, String mem_id, String book_id, Integer rating) {
		RevVO revVO = new RevVO();
		
		revVO.setRev_content(rev_content);
		revVO.setMem_id(mem_id);
		revVO.setBook_id(book_id);
		revVO.setRating(rating);
		revVO = dao.insert(revVO);
		revVO = getOneRev(revVO.getRev_id());
		
		return revVO;
	}

	public RevVO updateStatus(Integer rev_status, String rev_id) {
		RevVO revVO = new RevVO();
		
		revVO.setRev_status(rev_status);
		revVO.setRev_id(rev_id);
		dao.updateStatus(revVO);
	
		return revVO;
	}
	
	public void deleteRev(String rev_id) {
		dao.delete(rev_id);
	}
	
	public RevVO getOneRev(String rev_id) {
		return dao.findByPrimaryKey(rev_id);
	}
	
	public List<RevVO> getRevByMem(String mem_id) {
		return dao.findByMemId(mem_id);
	}
	
	public List<RevVO> getAll() {
		return dao.getAll();
	}
	
	public List<RevVO> getByBookId(String book_id) {
		return dao.getByBookId(book_id);
	}
	public Double getRatingAvg(String book_id) {
		return dao.getRatingAvg(book_id);
	}
}
