package com.promo.model;

import java.sql.Timestamp;
import java.util.Objects;

public class Promo {
	private String promoID;
	private String promoName;
	// 輸入字串格式2020-09-01T00:02 -> 2020-09-01 00:02:00
	private Timestamp promoStartTime;
	private Timestamp promoEndTime;

	public Promo(String promoID, String promoName, Timestamp promoStartTime, Timestamp promoEndTime) {
		this.promoID = promoID;
		this.promoName = promoName;
		this.promoStartTime = promoStartTime;
		this.promoEndTime = promoEndTime;
	}

	public Promo() {
	}

	public String getPromoID() {
		return promoID;
	}

	public void setPromoID(String promoID) {
		this.promoID = promoID;
	}

	public String getPromoName() {
		return promoName;
	}

	public void setPromoName(String promoName) {
		this.promoName = promoName;
	}

	public Timestamp getPromoStartTime() {
		return promoStartTime;
	}

	public void setPromoStartTime(Timestamp promoStartTime) {
		this.promoStartTime = promoStartTime;
	}

	public Timestamp getPromoEndTime() {
		return promoEndTime;
	}

	public void setPromoEndTime(Timestamp promoEndTime) {
		this.promoEndTime = promoEndTime;
	}

	public boolean isValid() {
		Timestamp cur = new Timestamp(System.currentTimeMillis());
		return (cur.after(promoStartTime) && cur.before(promoEndTime));
	}

	@Override
	public String toString() {
		return promoID + "\t" + promoName + "\t" + promoStartTime + "\t" + promoEndTime;
	}

	// 複寫hashCode和equals，定義promoID相同的Promo物件為相同
	@Override
	public int hashCode() {
		// Objects 有 hash() 方法可以使用
		// 以下可以簡化為 return Objects.hash(name, number);
		int hash = 7;
		hash = 47 * hash + Objects.hashCode(this.promoID);
		return hash;
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}

		if (getClass() != obj.getClass()) {
			return false;
		}

		final Promo other = (Promo) obj;

		if (!Objects.equals(this.promoID, other.promoID)) {
			return false;
		}

		return true;
	}

}
