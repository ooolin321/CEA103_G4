package com.bookclub.model;

import java.util.List;

import com.bookclub.model.BookClubVO;



public interface BookClubDAO_interface {
//	新增
	public String insert(BookClubVO bookclubVO);
//	修改
	public void update(BookClubVO bookclubVO);
//	刪除
	public void delete(String bc_id);
//	根據讀書會編號找一筆
	public BookClubVO findByPrimaryKey(String bc_id);
//	找全部
	public List<BookClubVO> getAll();
//	修改狀態
	public void updateStatus(String bc_id,Integer status);
//	更新報名人數
	public void updateComfirmPeo(Integer comfirm_peo,String bc_id);
//	找狀態不是取消的行程
	public List<BookClubVO> findByStatus();
//	模糊查詢
	public List<BookClubVO> findByBlurry(String selected);
//	找自己辦的讀書會
	public List<BookClubVO> findByMyself(String mem_id);
}
