package com.redis.model;

public class RedisKey {
	private String keyName;
	private long ttl;
	private long size;

	public RedisKey() {
	}

	public String getKeyName() {
		return keyName;
	}

	public RedisKey(String keyName, long ttl, long size) {
		super();
		this.keyName = keyName;
		this.ttl = ttl;
		this.size = size;
	}

	public void setKeyName(String keyName) {
		this.keyName = keyName;
	}

	public long getTtl() {
		return ttl;
	}

	public void setTtl(long ttl) {
		this.ttl = ttl;
	}

	public long getSize() {
		return size;
	}

	public void setSize(long size) {
		this.size = size;
	}

	@Override
	public String toString() {
		return "RedisKey [keyName=" + keyName + ", ttl=" + ttl + ", size=" + size + "]";
	}

	// 同樣keyName視為相同物件
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((keyName == null) ? 0 : keyName.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RedisKey other = (RedisKey) obj;
		if (keyName == null) {
			if (other.keyName != null)
				return false;
		} else if (!keyName.equals(other.keyName))
			return false;
		return true;
	}

}
