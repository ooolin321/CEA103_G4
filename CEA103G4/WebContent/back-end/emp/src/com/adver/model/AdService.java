package com.adver.model;

import java.sql.Timestamp;
import java.util.List;

public class AdService {

	public AdDAO_Interface adi;

	public AdService() {
		super();
		adi = new AdDAO();
	}

	public AdVO createAd(Integer blo_class, String act_name, String ad_type, Timestamp ad_start, Timestamp ad_end,
			byte[] ad_image, String ad_url, String ad_copy) {

		AdVO adVO = new AdVO();

		adVO.setBlo_class(blo_class);
		adVO.setAct_name(act_name);
		adVO.setAd_type(ad_type);
		adVO.setAd_start(ad_start);
		adVO.setAd_end(ad_end);
		adVO.setAd_image(ad_image);
		adVO.setAd_url(ad_url);
		adVO.setAd_copy(ad_copy);
		adi.doCreate(adVO);

		return adVO;
	}

	public AdVO updateAd(String act_name, String ad_type, Timestamp ad_start, Timestamp ad_end, byte[] ad_image,
			String ad_url, String ad_copy) {
		AdVO adVO = new AdVO();

		adVO.setAct_name(act_name);
		adVO.setAd_type(ad_type);
		adVO.setAd_start(ad_start);
		adVO.setAd_end(ad_end);
		adVO.setAd_image(ad_image);
		adVO.setAd_url(ad_url);
		adVO.setAd_copy(ad_copy);
		adi.doCreate(adVO);

		return adVO;

	}

	public void cancelAd(String ad_id) {
		adi.cancel(ad_id);
	}

	public AdVO getRange(Timestamp ad_start) {
		return adi.findbytime(ad_start);
	}

	public List<AdVO> getAll() {
		return adi.getAll();
	}

	public AdVO getOne(String ad_id) {
		return adi.getOne(ad_id);
	}

}
