package com.promodetail.model;

import java.util.List;
import java.util.Optional;

public interface PromoDetailDAO {
	void insert(PromoDetail promoDetail);
	void insertBatch(List<PromoDetail> promoDetails);
	List<PromoDetail> findByPromoIDAndBookID(String promoID, String bookID);
	void update(PromoDetail promoDetail);
	void updateBatch(List<PromoDetail> promoDetails);
	void deleteByPromoIDAndBookID(String promoID, String bookID);
	void deleteByPromoIDAndBookIDList(String promoID, List<String> bookIDs);
}
