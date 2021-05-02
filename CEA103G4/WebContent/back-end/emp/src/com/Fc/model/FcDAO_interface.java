package com.Fc.model;

import java.util.List;

import com.Fa.model.FaVO;

public interface FcDAO_interface {
	public void insert(String memId,String faId);
	public void delete(String memId,String faId);
	public Integer checkCollect(String memId,String faId);
	public List<FaVO> getOneMemColFa(String memId);
}
