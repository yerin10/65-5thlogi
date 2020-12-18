package com.estimulo.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.hr.to.EmployeeSecretTO;

@Mapper
public interface EmployeeSecretDAO {

	public ArrayList<EmployeeSecretTO> selectEmployeeSecretList(HashMap<String, String> param);

	public EmployeeSecretTO selectUserPassWord(HashMap<String, String> param);

	public void insertEmployeeSecret(EmployeeSecretTO TO);
	
	public int selectUserPassWordCount(HashMap<String, String> param);
	
	public void deleteEmployeeSecret(EmployeeSecretTO TO);

}
