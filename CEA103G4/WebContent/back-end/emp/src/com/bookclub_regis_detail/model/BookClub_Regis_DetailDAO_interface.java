package com.bookclub_regis_detail.model;

import java.util.List;

import com.bookclub.model.BookClubVO;


public interface BookClub_Regis_DetailDAO_interface {
	public String insert(BookClub_Regis_DetailVO bookClub_Regis_DetailVO);
    public void update_Status(BookClub_Regis_DetailVO bookClub_Regis_DetailVO);
    public void delete(String brd_id);
    public BookClub_Regis_DetailVO findByPrimaryKey(String brd_id);
    public List<BookClub_Regis_DetailVO> getAll();
    public List<BookClub_Regis_DetailVO> findByVerify(String bc_id);
    public List<BookClub_Regis_DetailVO> findByMyself(String mem_id);
    public List<BookClub_Regis_DetailVO> findByBc_id(String bc_id);
}
