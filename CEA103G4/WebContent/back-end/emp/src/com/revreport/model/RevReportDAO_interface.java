package com.revreport.model;

import java.util.List;
import com.rev.model.RevVO;

public interface RevReportDAO_interface {
	public void insert(RevReportVO revReportVO);
	public void updateStatus(RevReportVO revReportVO);
    public void delete(String rev_rep_id);
    public RevReportVO findByPrimaryKey(String rev_rep_id);
    public List<RevReportVO> findByMemId(String mem_id);
    public List<RevReportVO> getAll();
}
