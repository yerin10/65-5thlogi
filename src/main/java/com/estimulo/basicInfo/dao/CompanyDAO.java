package com.estimulo.basicInfo.dao;



import org.apache.ibatis.annotations.Mapper;

import com.estimulo.basicInfo.to.CompanyTO;

@Mapper
public interface CompanyDAO {
	
	/* public ArrayList<CompanyTO> selectCompanyList(); */
	
	public void insertCompany(CompanyTO TO);
	
	public void updateCompany(CompanyTO TO);

	public void deleteCompany(CompanyTO TO);
	
}
