package com.estimulo.logistics.purchase.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.purchase.dao.StockDAO;
import com.estimulo.logistics.purchase.repository.StockRepository;
import com.estimulo.logistics.purchase.to.StockLogTO;
import com.estimulo.logistics.purchase.to.StockTO;

@Component
public class StockApplicationServiceImpl implements StockApplicationService{

		// DAO 참조변수 선언
		@Autowired
		private StockDAO stockDAO;
		@Autowired
		private StockRepository stockRepository;

		@Override
		public ArrayList<StockTO> getStockList() {
			//ArrayList<StockTO> stockList = stockMapper.selectStockList();
			
			ArrayList<StockTO> stockList = stockRepository.findAllByOrderByItemCode();
			return stockList;
		}
	
		@Override
		public ArrayList<StockLogTO> getStockLogList(String startDate,String endDate) {

			HashMap<String, String> param = new HashMap<>();
				param.put("startDate", startDate);
				param.put("endDate", endDate);
			
			return stockDAO.selectStockLogList(param);
		}

		@Override
		public HashMap<String,Object> warehousing(ArrayList<String> orderNoArr) {
			
			String orderNoList = orderNoArr.toString().replace("[", "").replace("]", "");

			HashMap<String, String> param = new HashMap<>();
				param.put("orderNoList", orderNoList);
			
			stockDAO.warehousing(param);
			 
			HashMap<String,Object> resultMap = new HashMap<>();
            	resultMap.put("errorCode",param.get("ERROR_CODE"));
            	resultMap.put("errorMsg", param.get("ERROR_MSG"));

        	return resultMap;
			
		}
		
}
