package com.bookclub_regis_detail.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;




public class BookClub_Regis_DetailService {
	private BookClub_Regis_DetailDAO_interface dao;
	
	public BookClub_Regis_DetailService() {
		dao = new BookClub_Regis_DetailJNDIDAO();
	}
	public BookClub_Regis_DetailVO addBookClub_Regis_Detail(String bc_id,String mem_id) {
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
		System.out.println(mem_id);
		bookClub_Regis_DetailVO.setBc_id(bc_id);
		bookClub_Regis_DetailVO.setMem_id(mem_id);
		bookClub_Regis_DetailVO.setBrd_id(dao.insert(bookClub_Regis_DetailVO));
		
		return bookClub_Regis_DetailVO;
	}
	
	public void delete(String brd_id) {
		dao.delete(brd_id);
	}
	public List<BookClub_Regis_DetailVO> getAll() {
		return dao.getAll();
	}
	public BookClub_Regis_DetailVO getOneBookClub_Regis_Detail(String brd_id) {
		return dao.findByPrimaryKey(brd_id);
	}
	
	public List<BookClub_Regis_DetailVO> getVerify(String bc_id){
		return dao.findByVerify(bc_id);
	}
	public BookClub_Regis_DetailVO update_Status(String bc_id, String mem_id, Integer brd_status) {
		BookClub_Regis_DetailVO bookClub_Regis_DetailVO = new BookClub_Regis_DetailVO();
		bookClub_Regis_DetailVO.setBc_id(bc_id);
		bookClub_Regis_DetailVO.setMem_id(mem_id);
		bookClub_Regis_DetailVO.setBrd_status(brd_status);
		dao.update_Status(bookClub_Regis_DetailVO);
		return bookClub_Regis_DetailVO;
	}
	public List<BookClub_Regis_DetailVO> getByMyself(String mem_id) {
		return dao.findByMyself(mem_id);
	}
//	取得所有同讀書會及狀態為通過的會員
	public List<BookClub_Regis_DetailVO> getMember(String bc_id){
		List<BookClub_Regis_DetailVO> memberList = new ArrayList<BookClub_Regis_DetailVO>();
		List<BookClub_Regis_DetailVO> all = dao.getAll();
		all.stream().filter(bookClub_Regis_DetailVO -> bookClub_Regis_DetailVO.getBrd_status() == 2)
		   .filter(bookClub_Regis_DetailVO -> bookClub_Regis_DetailVO.getBc_id().equals(bc_id))
		   .forEach(bookClub_Regis_DetailVO -> memberList.add(bookClub_Regis_DetailVO));
		return memberList;
	}
	public List<BookClub_Regis_DetailVO> getByBc_id(String bc_id) {
		return dao.findByBc_id(bc_id);
	}
}
