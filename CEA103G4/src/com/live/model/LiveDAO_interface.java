package com.live.model;

import java.util.List;

public interface LiveDAO_interface {
	public void insert(LiveVO liveVO);

	public void update(LiveVO liveVO);

	public void delete(Integer live_id);

	public LiveVO findByPrimaryKey(Integer live_id);

	public List<LiveVO> getAll();
	
	public List<LiveVO> getAll1();
}
