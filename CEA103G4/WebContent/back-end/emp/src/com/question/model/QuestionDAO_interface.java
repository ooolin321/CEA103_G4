package com.question.model;

import java.util.List;

public interface QuestionDAO_interface {
	//新增
	public void insert(QuestionVO questionVO);
	//修改
	public void update(QuestionVO questionVO);
	//刪除
	public void delete(String bcq_id);
	//根據問卷編號找一筆
	public QuestionVO findByPrimaryKey(String bcq_id);
	//找全部
	public List<QuestionVO> getAll();
	//根據讀書會編號找多筆
	public List<QuestionVO> findByBcid(String bc_id);
}
