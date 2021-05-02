package com.questionnair_answer.model;

import java.util.List;

public class Questionnair_AnswerService {
	private Questionnair_AnswerDAO_interface dao;
	
	public Questionnair_AnswerService() {
		dao = new Questionnair_AnswerJNDIDAO();
	}
	public Questionnair_AnswerVO addQuestionnair_Answer(String brd_id,String bcq_id,String qa_data) {
		Questionnair_AnswerVO questionnair_AnswerVO = new Questionnair_AnswerVO();
		questionnair_AnswerVO.setBrd_id(brd_id);
		questionnair_AnswerVO.setBcq_id(bcq_id);
		questionnair_AnswerVO.setQa_data(qa_data);
		
		dao.insert(questionnair_AnswerVO);
		return questionnair_AnswerVO; 
	}
	public Questionnair_AnswerVO update(String brd_id,String bcq_id,String qa_data) {
		Questionnair_AnswerVO questionnair_AnswerVO = new Questionnair_AnswerVO();
		questionnair_AnswerVO.setBrd_id(brd_id);
		questionnair_AnswerVO.setBcq_id(bcq_id);
		questionnair_AnswerVO.setQa_data(qa_data);
		
		dao.update(questionnair_AnswerVO);
		return questionnair_AnswerVO; 
	}
	public void deleteQuestionnair_Answer(String brd_id,String bcq_id) {
		dao.delete(brd_id, bcq_id);
	}
	public Questionnair_AnswerVO getQuestionnair_Answer(String brd_id,String bcq_id) {
		return dao.findByPrimaryKey(brd_id,bcq_id);
	}
	public List<Questionnair_AnswerVO> getByBrdid(String brd_id) {
		return dao.findByBrdid(brd_id);
	}
}	
	