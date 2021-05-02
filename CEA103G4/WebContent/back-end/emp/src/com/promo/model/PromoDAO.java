package com.promo.model;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

public interface PromoDAO {
	void insert(Promo promo);
	Optional<Promo> findByPromoID(String promoID);
	Optional<Promo> findByPromoNameUnique(String promoName);
	void update(Promo promo);
	List<Promo> advSearch(Map<String, String> map);
	Set<Promo> findValidPromos();
	List<String> findByPromoNameLike(String promoName);
}
