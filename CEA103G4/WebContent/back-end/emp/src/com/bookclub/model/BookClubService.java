package com.bookclub.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;



public class BookClubService {
	private BookClubDAO_interface dao;
	
	public BookClubService() {
		dao = new BookClubJNDIDAO();
	}
	public BookClubVO addBookClub(String mem_id,String bc_name,String bc_place, Timestamp bc_time_start, Timestamp bc_time_end,Integer bc_peo_upper_limit,Integer bc_peo_lower_limit, String bc_intro,byte[] bc_cover_pic,Date bc_init, Date bc_deadline) {
		BookClubVO bookClubVO = new BookClubVO();
		bookClubVO.setMem_id(mem_id);
		bookClubVO.setBc_name(bc_name);
		bookClubVO.setBc_place(bc_place);
		bookClubVO.setBc_time_start(bc_time_start);
		bookClubVO.setBc_time_end(bc_time_end);
		bookClubVO.setBc_peo_upper_limit(bc_peo_upper_limit);
		bookClubVO.setBc_peo_lower_limit(bc_peo_lower_limit);
		bookClubVO.setBc_intro(bc_intro);
		bookClubVO.setBc_cover_pic(bc_cover_pic);
		bookClubVO.setBc_init(bc_init);
		bookClubVO.setBc_deadline(bc_deadline);
		bookClubVO.setBc_id(dao.insert(bookClubVO));;
		
		return bookClubVO;
	}
	public BookClubVO update(String bc_id,String bc_name,String bc_place, Timestamp bc_time_start, Timestamp bc_time_end,Integer bc_peo_upper_limit,Integer bc_peo_lower_limit, String bc_intro,byte[] bc_cover_pic,Date bc_init, Date bc_deadline) {
		BookClubVO bookClubVO = new BookClubVO();
		bookClubVO.setBc_id(bc_id);
		bookClubVO.setBc_name(bc_name);
		bookClubVO.setBc_place(bc_place);
		bookClubVO.setBc_time_start(bc_time_start);
		bookClubVO.setBc_time_end(bc_time_end);
		bookClubVO.setBc_peo_upper_limit(bc_peo_upper_limit);
		bookClubVO.setBc_peo_lower_limit(bc_peo_lower_limit);
		bookClubVO.setBc_intro(bc_intro);
		bookClubVO.setBc_cover_pic(bc_cover_pic);
		bookClubVO.setBc_init(bc_init);
		bookClubVO.setBc_deadline(bc_deadline);
		dao.update(bookClubVO);
		return bookClubVO;
	}
	public BookClubVO getOneBookClub(String bc_id) {
		return dao.findByPrimaryKey(bc_id);
	}
	public void deleteBookClub(String bc_id) {
		dao.delete(bc_id);
	}
	public void updateStatus(String bc_id, Integer status) {
		dao.updateStatus(bc_id, status);
	}
	public void updateComfirmPeo(Integer peo, String gro_no) {
		dao.updateComfirmPeo(peo, gro_no);
	}
	public List<BookClubVO> getAll() {
		return dao.getAll();
	}
	public List<BookClubVO> getByStatus(){
		return dao.findByStatus();
	}
	public List<BookClubVO> getBlurry(String bc_name){
		return dao.findByBlurry(bc_name);
	}
	public List<BookClubVO> getByMyself(String mem_id){
		return dao.findByMyself(mem_id);
	}
}
