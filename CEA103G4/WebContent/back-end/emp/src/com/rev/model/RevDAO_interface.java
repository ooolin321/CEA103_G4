package com.rev.model;

import java.util.List;

public interface RevDAO_interface {
	public RevVO insert(RevVO revVO);
	public void updateStatus(RevVO revVO);
    public void delete(String mem_id);
    public RevVO findByPrimaryKey(String rev_id);
    public List<RevVO> findByMemId(String mem_id);
    public List<RevVO> getAll();
    public List<RevVO> getByBookId(String book_id);
    public Double getRatingAvg(String book_id);
}
