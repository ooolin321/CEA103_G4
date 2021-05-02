package com.promo.model;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public class PromoService {
	private final PromoDAO promoDAO;

	public PromoService(PromoDAO promoDAO) {
		this.promoDAO = promoDAO;
	}

	public Optional<Promo> addPromo(String promoName, Timestamp promoStartTime, Timestamp promoEndTime) {
		Promo promo = new Promo(null, promoName, promoStartTime, promoEndTime);
		promoDAO.insert(promo);
		return promoDAO.findByPromoNameUnique(promoName);
	}

	public Optional<Promo> getByPromoID(String promoID) {
		return promoDAO.findByPromoID(promoID);
	}

	public List<Promo> getByPromoName(String promoName) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("promoName", promoName);
		return promoDAO.advSearch(map);
	}

	public Optional<Promo> getByPromoNameUnique(String promoName) {
		return promoDAO.findByPromoNameUnique(promoName);
	}

	public List<Promo> getByPromoTimeRange(Timestamp promoStartTime, Timestamp promoEndTime) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("promoStartTime", promoStartTime.toString());
		map.put("promoEndTime", promoEndTime.toString());
		return promoDAO.advSearch(map);
	}

	public List<Promo> getAll() {
		Map<String, String> map = new HashMap<String, String>();
		return promoDAO.advSearch(map);
	}

	public Optional<Promo> updatePromo(String promoID, String promoName, Timestamp promoStartTime,
			Timestamp promoEndTime) {
		Promo promo = new Promo(promoID, promoName, promoStartTime, promoEndTime);
		promoDAO.update(promo);
		return promoDAO.findByPromoID(promoID);
	}

	public boolean isPromoValid(String promoID) {
		long currentTime = System.currentTimeMillis();
		Optional<Promo> promo = getByPromoID(promoID);
		if (promo.isPresent()) {
			return promo.get().getPromoStartTime().getTime() <= currentTime
					&& promo.get().getPromoEndTime().getTime() >= currentTime;
		} else {
			return false;
		}
	}

	public List<Promo> getByAdvSearch(Map<String, String> map) {
		map.forEach((k, v) -> {
			if (k.contains("Time")) {
				String newVal = v.replace("T", " ");
				System.out.println(newVal);
				map.replace(k, newVal);
			}
		});

		return promoDAO.advSearch(map);
	}

	public Set<Promo> getValidPromos() {
		return promoDAO.findValidPromos();
	}

	public List<String> getPromoNameLike(String promoName) {
		return promoDAO.findByPromoNameLike(promoName);
	}
}
