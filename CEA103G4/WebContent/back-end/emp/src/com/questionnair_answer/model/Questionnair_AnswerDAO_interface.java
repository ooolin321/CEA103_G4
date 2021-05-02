package com.questionnair_answer.model;

import java.util.List;

public interface Questionnair_AnswerDAO_interface {
	public void insert(Questionnair_AnswerVO questionnair_AnswerVO);
	public void update(Questionnair_AnswerVO Questionnair_AnswerVO);
	public void delete(String brd_id, String bcq_id);
	public Questionnair_AnswerVO findByPrimaryKey(String brd_id, String bcq_id);
	public List<Questionnair_AnswerVO> findByBrdid(String brd_id);
	public List<Questionnair_AnswerVO> getAll();
}
