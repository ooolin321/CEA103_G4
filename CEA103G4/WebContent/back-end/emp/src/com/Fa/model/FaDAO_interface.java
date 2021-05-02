package com.Fa.model;

import java.util.*;


public interface FaDAO_interface {
	public String insert(FaVO faVO);
	public void update(FaVO faVO);
	public void delete(String faId);
	public void addFaViews(FaVO faVO);
	public FaVO findOneFaByFaId(String faId);
	public List<FaVO> findOneMemFa(String memId);
	public List<FaVO> search(String faTopic);
	public List<FaVO> getAllIndex();
	public List<FaVO> getAllHot();
}
