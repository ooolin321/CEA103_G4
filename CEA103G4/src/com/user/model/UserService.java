package com.user.model;

import java.sql.Date;
import java.util.List;
import java.util.Set;

import com.emp.model.EmpVO;
import com.live_report.model.*;

public class UserService {

	private UserDAO_interface dao;

	public UserService() {
		dao = new UserDAO();
	}

	public UserVO addUser(String user_id, String user_pwd, String user_name, String id_card, String user_gender,
			Date user_dob, String user_mail, String user_phone, String user_mobile, String city, String town, Integer zipcode, String user_addr, Date regdate,
			Integer user_point, Integer violation, Integer user_state, Integer user_comment, Integer comment_total,
			Integer cash) {

		UserVO userVO = new UserVO();

		userVO.setUser_id(user_id);
		userVO.setUser_pwd(user_pwd);
		userVO.setUser_name(user_name);
		userVO.setId_card(id_card);
		userVO.setUser_gender(user_gender);
		userVO.setUser_dob(user_dob);
		userVO.setUser_mail(user_mail);
		userVO.setUser_phone(user_phone);
		userVO.setUser_mobile(user_mobile);
		userVO.setCity(city);
		userVO.setTown(town);
		userVO.setZipcode(zipcode);
		userVO.setUser_addr(user_addr);
		userVO.setRegdate(regdate);
		userVO.setUser_point(user_point);
		userVO.setViolation(violation);
		userVO.setUser_state(user_state);
		userVO.setUser_comment(user_comment);
		userVO.setComment_total(comment_total);
		userVO.setCash(cash);
		
		dao.insert(userVO);

		return userVO;
	}

	public UserVO updateUser(String user_id, String user_pwd, String user_name, String id_card, String user_gender,
			Date user_dob, String user_mail, String user_phone, String user_mobile, String city, String town, Integer zipcode, String user_addr, Date regdate,
			Integer user_point, Integer violation, Integer user_state, Integer user_comment, Integer comment_total,
			Integer cash) {
		
		UserVO userVO = new UserVO();

		userVO.setUser_id(user_id);
		userVO.setUser_pwd(user_pwd);
		userVO.setUser_name(user_name);
		userVO.setId_card(id_card);
		userVO.setUser_gender(user_gender);
		userVO.setUser_dob(user_dob);
		userVO.setUser_mail(user_mail);
		userVO.setUser_phone(user_phone);
		userVO.setUser_mobile(user_mobile);
		userVO.setCity(city);
		userVO.setTown(town);
		userVO.setZipcode(zipcode);
		userVO.setUser_addr(user_addr);
		userVO.setRegdate(regdate);
		userVO.setUser_point(user_point);
		userVO.setViolation(violation);
		userVO.setUser_state(user_state);
		userVO.setUser_comment(user_comment);
		userVO.setComment_total(comment_total);
		userVO.setCash(cash);
		
		dao.update(userVO);
		
		return userVO;
	}
	
	public void deleteUser(String user_id) {
		dao.delete(user_id);
	}

	public UserVO getOneUser(String user_id) {
		return dao.findByPrimaryKey(user_id);
	}

	public List<UserVO> getAll() {
		return dao.getAll();
	}
	
	public Set<Live_reportVO> getLive_reportByUser_id(String user_id) {
		return dao.getLive_reportByUser_id(user_id);
	}
	
	public UserVO selectUser(String user_id, String user_pwd) {

		return dao.login(user_id, user_pwd);
	}
}
