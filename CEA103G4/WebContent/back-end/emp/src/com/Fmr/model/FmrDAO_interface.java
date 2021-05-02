package com.Fmr.model;

import java.util.*;

public interface FmrDAO_interface {
	public void insert(FmrVO fmrVO);
	public void judge_Fmr(FmrVO fmrVO);
	public List<FmrVO> getAll();
	public List<FmrVO> getAllJundge();
	
}
