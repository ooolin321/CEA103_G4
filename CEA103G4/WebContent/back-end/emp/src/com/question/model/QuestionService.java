package com.question.model;

import java.util.List;

public class QuestionService {
	private QuestionDAO_interface dao;
	
	public QuestionService() {
		dao = new QuestionJNDIDAO();
	}
	public QuestionVO addQuestion(String bc_id,String q_data) {
		QuestionVO questionVO = new QuestionVO();
		questionVO.setBc_id(bc_id);
		questionVO.setQ_data(q_data);
		dao.insert(questionVO);
		return questionVO; 
	}
	public QuestionVO update(String q_data) {
		QuestionVO questionVO = new QuestionVO();
		questionVO.setQ_data(q_data);
		dao.update(questionVO);
		return questionVO; 
	}
	public void deleteQuestion(String bcq_id) {
		dao.delete(bcq_id);
	}
	public QuestionVO getOneQuestion(String bcq_id) {
		return dao.findByPrimaryKey(bcq_id);
	}
	public List<QuestionVO> getAllQuestion(){
		return dao.getAll();
	}
	public List<QuestionVO> getByBcid(String bc_id){
		return dao.findByBcid(bc_id);
	}
}	
	