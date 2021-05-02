package com.cs.model;

import java.util.List;

public interface CsDAO_interface {
	public void insert(CsVO csVO);
    public void delete(String cs_ID);
    public CsVO findByPrimaryKey(String cs_ID);
    public List<CsVO> getAll();
    public void update(CsVO csVO);
    public List<CsVO> findBySearch(String cssearch);
}
