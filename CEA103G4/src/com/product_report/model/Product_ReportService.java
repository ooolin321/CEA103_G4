package com.product_report.model;
import java.util.List;


public class Product_ReportService {

	private Product_ReportDAO_interface dao;

	public Product_ReportService() {
		dao = new Product_ReportDAO();
	}

	public Product_ReportVO addProduct_Report(String pro_report_content, Integer product_no, String user_id) {

		Product_ReportVO product_reportVO = new Product_ReportVO();

		product_reportVO.setPro_report_content(pro_report_content);
		product_reportVO.setProduct_no(product_no);
		product_reportVO.setUser_id(user_id);
//		product_reportVO.setEmpno(empno);

		dao.insert(product_reportVO);

		return product_reportVO;
	}

	public Product_ReportVO updateProduct_Report(Integer pro_report_no, String pro_report_content, Integer product_no,
			String user_id, Integer empno, Integer proreport_state) {

		Product_ReportVO product_reportVO = new Product_ReportVO();

		product_reportVO.setPro_report_no(pro_report_no);
		product_reportVO.setPro_report_content(pro_report_content);
		product_reportVO.setProduct_no(product_no);
		product_reportVO.setUser_id(user_id);
		//檢舉時間自動預設當下時間
//		product_reportVO.setReport_date(report_date);
		product_reportVO.setEmpno(empno);
		product_reportVO.setProreport_state(proreport_state);
		dao.update(product_reportVO);

		return product_reportVO;
	}

	public void deleteProduct_Report(Integer pro_report_no) {
		dao.delete(pro_report_no);
	}
	
	public Product_ReportVO getOneProduct_Report(Integer pro_report_no) {
		return dao.findByPrimaryKey(pro_report_no);
	}

	public List<Product_ReportVO> getAll_Report_state() {
		return dao.getAll_Report_state();
	}

	public List<Product_ReportVO> getAll() {
		return dao.getAll();
	}
}
