package com.Far.model;

import java.util.*;

import com.Fa.model.*;

public interface FarDAO_interface {
	
	public void insert(FarVO farVO);
	public void judge(FarVO farVO); 
	public List<FarVO> getAll();
	public List<FarVO> getAllJundge();
}
