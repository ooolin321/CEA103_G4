package com.emp.model;

import java.util.List;

public class EmpService {

	private EmpDAO_interface dao;

	public EmpService() {
		dao = new EmpDAO();
	}

	public EmpVO addEmp(String ename, String job, String id, String gender, java.sql.Date dob, String addr, Double sal,
			Integer state, java.sql.Date hiredate, String empPwd) {

		EmpVO empVO = new EmpVO();

		empVO.setEname(ename);
		empVO.setJob(job);
		empVO.setId(id);
		empVO.setGender(gender);
		empVO.setDob(dob);
		empVO.setAddr(addr);
		empVO.setSal(sal);
		empVO.setState(state);
		empVO.setHiredate(hiredate);
		empVO.setEmp_pwd(empPwd);
		
		dao.insert(empVO);
		
		return empVO;
	}

	public EmpVO updateEmp(Integer empno, String ename, String job, String id, String gender, java.sql.Date dob,
			String addr, Double sal, Integer state, java.sql.Date hiredate, String empPwd) {

		EmpVO empVO = new EmpVO();

		empVO.setEmpno(empno);
		empVO.setEname(ename);
		empVO.setJob(job);
		empVO.setId(id);
		empVO.setGender(gender);
		empVO.setDob(dob);
		empVO.setAddr(addr);
		empVO.setSal(sal);
		empVO.setState(state);
		empVO.setHiredate(hiredate);
		empVO.setEmp_pwd(empPwd);

		dao.update(empVO);

		return empVO;
	}

	public void deleteEmp(Integer empno) {
		dao.delete(empno);
	}

	public EmpVO getOneEmp(Integer empno) {
		return dao.findByPrimaryKey(empno);
	}

	public List<EmpVO> getAll() {
		return dao.getAll();
	}
}
