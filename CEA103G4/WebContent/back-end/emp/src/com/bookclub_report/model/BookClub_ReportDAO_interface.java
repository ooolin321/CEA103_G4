package com.bookclub_report.model;

import java.util.List;

import com.bookclub.model.BookClubVO;

public interface BookClub_ReportDAO_interface {
	public void insert(BookClub_ReportVO bookClub_ReportVO);
    public void update(BookClub_ReportVO bookClub_ReportVO);
    public void updateStatus(BookClub_ReportVO bookClub_ReportVO);
    public void delete(String bcr_id);
    public BookClub_ReportVO findByPrimaryKey(String bcr_id);
    public List<BookClub_ReportVO> getAll();
}
