package com.categorie.model;

import java.util.List;
import java.util.Set;

import com.categorie.model.CategorieVO;

public interface CategorieDAO_interface {
	public void insert(CategorieVO categorieVO);
	public void update(CategorieVO categorieVO);
	public void delete(String lc_class_id);
	public CategorieVO findByPrimaryKey(String lc_class_id);
	public List<CategorieVO> getAll();
	//�d�߬Y���O�����y(�@��h)(�^�� Set)
//	public Set<LecturesVO> getLecturesByCategorieId(String lc_class_id);

}
