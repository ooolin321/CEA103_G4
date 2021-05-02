package com.Follow.model;

import java.util.List;

public interface FollowDAO_interface {
	public void insert(FollowVO followVO);
	public void delete(FollowVO followVO);
	public Integer checkSubscribe(String memId,String aMemId);
	public List<FollowVO> followList(String aMemId);
}
