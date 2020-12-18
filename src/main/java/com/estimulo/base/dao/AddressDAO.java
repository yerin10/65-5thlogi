package com.estimulo.base.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.base.to.AddressTO;

@Mapper
public interface AddressDAO {

	public String selectSidoCode(String sidoName);
	
	public ArrayList<AddressTO> selectRoadNameAddressList(HashMap<String, String> param);
	
	public ArrayList<AddressTO> selectJibunAddressList(HashMap<String, String> param);

	
}
