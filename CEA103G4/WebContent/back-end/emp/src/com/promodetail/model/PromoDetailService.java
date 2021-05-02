package com.promodetail.model;

import java.util.List;
import java.util.Optional;

public class PromoDetailService {
	private final PromoDetailDAO promoDetailDAO;

	public PromoDetailService(PromoDetailDAO promoDetailDAO) {
		this.promoDetailDAO = promoDetailDAO;
	}

	public Optional<PromoDetail> addPromoDetail(String promoID, String bookID, int discount, int bpPercent) {
		PromoDetail promoDetail = new PromoDetail(promoID, bookID, discount, bpPercent);
		promoDetailDAO.insert(promoDetail);
		return getByPromoIDAndBookID(promoID, bookID);
	}

	public Optional<PromoDetail> getByPromoIDAndBookID(String promoID, String bookID) {
		List<PromoDetail> promoDetails = promoDetailDAO.findByPromoIDAndBookID(promoID, bookID);
		if (promoDetails.size() != 0) {
			return Optional.ofNullable(promoDetailDAO.findByPromoIDAndBookID(promoID, bookID).get(0));
		} else {
			return Optional.empty();
		}
	}

	public List<PromoDetail> getByPromoID(String promoID) {
		return promoDetailDAO.findByPromoIDAndBookID(promoID, null);
	}

	public List<PromoDetail> getByBookID(String bookID) {
		return promoDetailDAO.findByPromoIDAndBookID(null, bookID);
	}

	public Optional<PromoDetail> updatePromoDetail(String promoID, String bookID, int discount, int bpPercent) {
		PromoDetail promoDetail = new PromoDetail(promoID, bookID, discount, bpPercent);
		promoDetailDAO.update(promoDetail);
		return getByPromoIDAndBookID(promoID, bookID);
	}

	public void deletePDByPromoIDAndBookID(String promoID, String bookID) {
		promoDetailDAO.deleteByPromoIDAndBookID(promoID, bookID);
	}

	public void deletePDByPromoID(String promoID) {
		promoDetailDAO.deleteByPromoIDAndBookID(promoID, null);
	}

	public void deletePDByBookID(String bookID) {
		promoDetailDAO.deleteByPromoIDAndBookID(null, bookID);
	}
	
	public void deleteByPromoIDAndBookIDList(String promoID, List<String> bookIDs) {
		promoDetailDAO.deleteByPromoIDAndBookIDList(promoID, bookIDs);
	}

	public List<PromoDetail> addPromoDetailBatch(List<PromoDetail> promoDetails) {
		promoDetailDAO.insertBatch(promoDetails);
		return promoDetails;
	}

	public List<PromoDetail> updatePromoDetailBatch(List<PromoDetail> promoDetails) {
		promoDetailDAO.updateBatch(promoDetails);
		return promoDetails;
	}
}
