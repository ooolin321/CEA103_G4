package com.bookclub_report.model;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.List;

public class BookClub_ReportService {
	private BookClub_ReportDAO_interface dao;

	public BookClub_ReportService() {
		dao = new BookClub_ReportJNDIDAO();
	}

	public BookClub_ReportVO addBookClub_Report(String mem_id, String bc_id, String bcr_reason, byte[] bcr_proof) {
		BookClub_ReportVO bookClub_ReportVO = new BookClub_ReportVO();

		bookClub_ReportVO.setMem_id(mem_id);
		bookClub_ReportVO.setBc_id(bc_id);
		bookClub_ReportVO.setBcr_reason(bcr_reason);
		bookClub_ReportVO.setBcr_proof(bcr_proof);

		dao.insert(bookClub_ReportVO);
		return bookClub_ReportVO;
	}

	public BookClub_ReportVO update_Status(Integer bcr_status, String bcr_id) {
		BookClub_ReportVO bookClub_ReportVO = new BookClub_ReportVO();
		
		bookClub_ReportVO.setBcr_status(bcr_status);
		bookClub_ReportVO.setBcr_id(bcr_id);

		dao.updateStatus(bookClub_ReportVO);
		return bookClub_ReportVO;
	}

	public List<BookClub_ReportVO> getAll() {
		return dao.getAll();
	}

	public BookClub_ReportVO getOneBookClub_Report(String brd_id) {
		return dao.findByPrimaryKey(brd_id);
	}
}
