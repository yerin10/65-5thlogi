package com.estimulo.logistics.logisticsInfo.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.logistics.logisticsInfo.applicationService.ItemApplicationService;
import com.estimulo.logistics.logisticsInfo.applicationService.WarehouseApplicationService;
import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;
import com.estimulo.logistics.logisticsInfo.to.WarehouseTO;

@Service
public class LogisticsInfoServiceFacadeImpl implements LogisticsInfoServiceFacade {
	
	@Autowired
	private ItemApplicationService itemAS;
	@Autowired
	private WarehouseApplicationService warehouseAS;
		

	@Override
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

		return itemAS.getItemInfoList(searchCondition, paramArray);
	}

	@Override
	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {
		
		return itemAS.batchItemListProcess(itemTOList);
	}

	@Override
	public ArrayList<WarehouseTO> getWarehouseInfoList() {

		return warehouseAS.getWarehouseInfoList();
	}

	@Override
	public void modifyWarehouseInfo(WarehouseTO warehouseTO) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String findLastWarehouseCode() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getStandardUnitPrice(String itemCode) {

		return itemAS.getStandardUnitPrice(itemCode);
		
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {

		return itemAS.getStandardUnitPriceBox(itemCode);
		
	}

}
