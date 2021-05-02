package com.categorie.model;

import java.util.List;
import java.util.Set;

import com.categorie.model.*;

public class CategorieService {
	
	private CategorieDAO_interface dao;
	
	public CategorieService() {
		dao = new  CategorieDAO();
	}
	
	public CategorieVO addCategorie(String lc_class_name) {

		CategorieVO categorieVO = new CategorieVO();

		categorieVO.setLc_class_name(lc_class_name);
		dao.insert(categorieVO);

		return categorieVO;
	}
	
	public CategorieVO updateCategorie(String lc_class_id, String lc_class_name) {

		CategorieVO categorieVO = new CategorieVO();

		categorieVO.setLc_class_id(lc_class_id);
		categorieVO.setLc_class_name(lc_class_name);
		dao.update(categorieVO);

		return categorieVO;
	}
	
	
	
	public List<CategorieVO> getAll() {
		return dao.getAll();
	}

	public CategorieVO getOneCategorie(String lc_class_id) {
		return dao.findByPrimaryKey(lc_class_id);
	}

//	public Set<LecturesVO> getLecturesByCategorieId(String lc_class_id) {
//		return dao.getEmpsByDeptno(lc_class_id);
//	}

	public void deleteCategorie(String lc_class_id) {
		dao.delete(lc_class_id);
	}

}
