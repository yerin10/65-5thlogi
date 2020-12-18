package com.estimulo.logistics.purchase.dao;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.purchase.to.OrderDialogTempTO;
import com.estimulo.logistics.purchase.to.OrderInfoTO;
import com.estimulo.logistics.purchase.to.OrderTempTO;

@Mapper
public interface OrderDAO {
	
	 public ArrayList<OrderTempTO> getOrderList(HashMap<String, String> param);
	 
	 public ArrayList<OrderDialogTempTO> getOrderDialogInfo(HashMap<String, String> param);
	 
	 public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery();
	 
	 public ArrayList<OrderInfoTO> getOrderInfoList(HashMap<String, String> param);

	 public HashMap<String,Object> order(HashMap<String, String> param);	 
	 
	 public HashMap<String,Object> optionOrder(HashMap<String, String> param);
	 
}
