package com.Fm.model;

import java.util.List;

public interface FmDAO_interface {
	public void insert(FmVO fmVO);
	public void update(FmVO fmVO);
	public void delete(String fmId);
	public List<FmVO> getAll();
	public List<FmVO> getOneFaFm(String fmId);
	public List<FmVO> getOneMemFm(String memId);
	public FmVO addFmByGetFa(String faId);
	public FmVO getOneFmByFmId(String fmId);
	public Integer getFmResponsesByFaId(String faId);
}
