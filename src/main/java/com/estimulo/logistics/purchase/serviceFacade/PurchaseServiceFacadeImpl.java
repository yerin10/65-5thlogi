package com.estimulo.logistics.purchase.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.logistics.purchase.applicationService.BomApplicationService;
import com.estimulo.logistics.purchase.applicationService.OrderApplicationService;
import com.estimulo.logistics.purchase.applicationService.StockApplicationService;
import com.estimulo.logistics.purchase.to.BomDeployTO;
import com.estimulo.logistics.purchase.to.BomInfoTO;
import com.estimulo.logistics.purchase.to.BomTO;
import com.estimulo.logistics.purchase.to.OrderInfoTO;
import com.estimulo.logistics.purchase.to.StockLogTO;
import com.estimulo.logistics.purchase.to.StockTO;

@Service
public class PurchaseServiceFacadeImpl implements PurchaseServiceFacade {

	@Autowired
	private BomApplicationService bomAS;
	@Autowired
	private OrderApplicationService orderAS;
	@Autowired
	private StockApplicationService stockAS;	

	@Override
	public ArrayList<BomDeployTO> getBomDeployList(String deployCondition, String itemCode,
			String itemClassificationCondition) {

		return bomAS.getBomDeployList(deployCondition, itemCode, itemClassificationCondition);
	}

	@Override
	public ArrayList<BomInfoTO> getBomInfoList(String parentItemCode) {

		return bomAS.getBomInfoList(parentItemCode);
	}

	@Override
	public HashMap<String,Object> getOrderList(String startDate, String endDate) {

		return orderAS.getOrderList(startDate, endDate);
	}

	@Override
	public ArrayList<BomInfoTO> getAllItemWithBomRegisterAvailable() {

		return bomAS.getAllItemWithBomRegisterAvailable();
	}

	@Override
	public HashMap<String, Object> batchBomListProcess(ArrayList<BomTO> batchBomList) {

		return bomAS.batchBomListProcess(batchBomList);

	}

	@Override
	public HashMap<String,Object> getOrderDialogInfo(ArrayList<String> mrpNoArr) {

		return orderAS.getOrderDialogInfo(mrpNoArr);

	}

	@Override
	public HashMap<String,Object> order(ArrayList<String> mrpGaNoArr) {

        HashMap<String,Object> resultMap = null;

			resultMap = orderAS.order(mrpGaNoArr);

    	return resultMap;
		
	}

	@Override
	public HashMap<String,Object> optionOrder(String itemCode, String itemAmount) {

    	return orderAS.optionOrder(itemCode, itemAmount);
		
	}

	@Override
	public ArrayList<StockTO> getStockList() {

		return stockAS.getStockList();

	}

	@Override
	public ArrayList<StockLogTO> getStockLogList(String startDate, String endDate) {

		return stockAS.getStockLogList(startDate, endDate);
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoListOnDelivery() {

		return orderAS.getOrderInfoListOnDelivery();

	}

	@Override
	public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {

		/* return stockAS.warehousing(orderNoArr); */
        HashMap<String,Object> resultMap = null;


  			resultMap = stockAS.warehousing(orderNoArr);

      	return resultMap;
	}

	@Override
	public ArrayList<OrderInfoTO> getOrderInfoList(String startDate, String endDate) {

		return orderAS.getOrderInfoList(startDate,endDate);

	}

}
