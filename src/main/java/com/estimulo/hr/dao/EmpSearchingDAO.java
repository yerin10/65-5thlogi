package com.estimulo.hr.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.hr.to.EmpInfoTO;

@Mapper
public interface EmpSearchingDAO {

	public ArrayList<EmpInfoTO> selectAllEmpList(HashMap<String, String> param);

	public ArrayList<EmpInfoTO> getTotalEmpInfo(HashMap<String, String> param);
	
	
}
