package com.estimulo.basicInfo.dao;

import java.util.ArrayList;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.basicInfo.to.CustomerTO;

@Mapper
public interface CustomerDAO {

	public ArrayList<CustomerTO> selectCustomerListByCompany();

	public ArrayList<CustomerTO> selectCustomerListByWorkplace(String workplaceCode);
	
	public void insertCustomer(CustomerTO TO);

	public void updateCustomer(CustomerTO TO);

	public void deleteCustomer(CustomerTO TO);
}
