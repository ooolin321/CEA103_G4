package com.ad.model;

import java.sql.Date;
import java.util.List;

public class AdService {

	private AdDAO_interface dao;

	public AdService() {
		dao = new AdDAO();
	}

	public AdVO addAd(Integer ad_no, Integer empno, String ad_contact, byte[] ad_photo, Integer ad_state, Date ad_start_date, Date ad_end_date, String ad_url) {

		AdVO adVO = new AdVO();

		adVO.setAd_no(ad_no);
		adVO.setEmpno(empno);
		adVO.setAd_contact(ad_contact);
		adVO.setAd_photo(ad_photo);
		adVO.setAd_state(ad_state);
		adVO.setAd_start_date(ad_start_date);
		adVO.setAd_end_date(ad_end_date);
		adVO.setAd_url(ad_url);
		
		dao.insert(adVO);

		return adVO;
	}

	public AdVO updateAd(Integer ad_no, Integer empno, String ad_contact, byte[] ad_photo, Integer ad_state, Date ad_start_date, Date ad_end_date, String ad_url) {
		
		AdVO adVO = new AdVO();

		adVO.setAd_no(ad_no);
		adVO.setEmpno(empno);
		adVO.setAd_contact(ad_contact);
		adVO.setAd_photo(ad_photo);
		adVO.setAd_state(ad_state);
		adVO.setAd_start_date(ad_start_date);
		adVO.setAd_end_date(ad_end_date);
		adVO.setAd_url(ad_url);
		
		dao.update(adVO);
		
		return adVO;
	}
	
	public void deleteUser(Integer ad_no) {
		dao.delete(ad_no);
	}

	public AdVO getOneUser(Integer ad_no) {
		return dao.findByPrimaryKey(ad_no);
	}

	public List<AdVO> getAll() {
		return dao.getAll();
	}
}
