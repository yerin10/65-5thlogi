package com.estimulo.basicInfo.dao;



import org.apache.ibatis.annotations.Mapper;

import com.estimulo.basicInfo.to.WorkplaceTO;

@Mapper
public interface WorkplaceDAO {
	
	/* public ArrayList<WorkplaceTO> selectWorkplaceList(String companyCode); */

	public void insertWorkplace(WorkplaceTO TO);
	
	public void updateWorkplace(WorkplaceTO TO);
	
	public void deleteWorkplace(WorkplaceTO TO);
}
