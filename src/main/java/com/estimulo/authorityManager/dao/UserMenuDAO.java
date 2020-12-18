package com.estimulo.authorityManager.dao;

import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.authorityManager.to.UserMenuTO;

@Mapper
public interface UserMenuDAO {

	public List<UserMenuTO> selectUserMenuCodeList(HashMap<String, String> param);

	
}
