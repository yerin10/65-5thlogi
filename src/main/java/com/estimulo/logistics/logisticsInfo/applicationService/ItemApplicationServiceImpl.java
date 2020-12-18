package com.estimulo.logistics.logisticsInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.base.dao.CodeDetailDAO;
import com.estimulo.base.to.CodeDetailTO;
import com.estimulo.logistics.logisticsInfo.dao.ItemDAO;
import com.estimulo.logistics.logisticsInfo.to.ItemInfoTO;
import com.estimulo.logistics.logisticsInfo.to.ItemTO;
import com.estimulo.logistics.purchase.dao.BomDAO;
import com.estimulo.logistics.purchase.to.BomTO;

@Component
public class ItemApplicationServiceImpl implements ItemApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private ItemDAO itemDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private BomDAO bomDAO;
	
	public ArrayList<ItemInfoTO> getItemInfoList(String searchCondition, String[] paramArray) {

		ArrayList<ItemInfoTO> itemInfoList = null;
		
		HashMap<String, String> param = new HashMap<>();
		

		if (paramArray != null) {
			for (int i = 0; i < paramArray.length; i++) {
				
				switch (i + "") {
				
				case "0":
					if (searchCondition.equals("ITEM_CLASSIFICATION")) {
						param.put("itemClassification", paramArray[0]);
					} else if (searchCondition.equals("ITEM_GROUP_CODE")) {
						param.put("itemGroupCode", paramArray[0]);
					} else if (searchCondition.equals("STANDARD_UNIT_PRICE")) {
						param.put("minPrice", paramArray[0]);
					}
					break;
				case "1":
					param.put("maxPrice", paramArray[1]);
					break;
				}
			}
		}

			switch (searchCondition) {

			case "ALL":

				itemInfoList = itemDAO.selectAllItemList();

				break;

			case "ITEM_CLASSIFICATION":
				
				param.put("searchCondition", "ITEM_CLASSIFICATION");
				
				itemInfoList = itemDAO.selectItemList(param);

				break;

			case "ITEM_GROUP_CODE":

				param.put("searchCondition", "ITEM_GROUP_CODE");
				
				itemInfoList = itemDAO.selectItemList(param);

				break;

			case "STANDARD_UNIT_PRICE":
				
				param.put("searchCondition", "STANDARD_UNIT_PRICE");

				itemInfoList = itemDAO.selectItemList(param);

				break;

			}

		return itemInfoList;
	}

	public HashMap<String, Object> batchItemListProcess(ArrayList<ItemTO> itemTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeTO = new CodeDetailTO();
			BomTO bomTO = new BomTO();
			
			for (ItemTO TO : itemTOList) {

				String status = TO.getStatus();

				switch (status) {

				case "INSERT":

					itemDAO.insertItem(TO);
					insertList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Insert
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.insertDetailCode(detailCodeTO);

					
					// 새로운 품목이 완제품 (ItemClassification : "IT-CI") , 반제품 (ItemClassification : "IT-SI") 일 경우 BOM 테이블에 Insert
					if( TO.getItemClassification().equals("IT-CI") || TO.getItemClassification().equals("IT-SI") ) {
						
						bomTO.setNo(1);
						bomTO.setParentItemCode("NULL");
						bomTO.setItemCode( TO.getItemCode() );
						bomTO.setNetAmount(1);
						
						bomDAO.insertBom(bomTO);
					}
					
					
					break;

				case "UPDATE":

					itemDAO.updateItem(TO);

					updateList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Update
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.updateDetailCode(detailCodeTO);

					break;

				case "DELETE":

					itemDAO.deleteItem(TO);

					deleteList.add(TO.getItemCode());

					// CODE_DETAIL 테이블에 Delete
					detailCodeTO.setDivisionCodeNo(TO.getItemClassification());
					detailCodeTO.setDetailCode(TO.getItemCode());
					detailCodeTO.setDetailCodeName(TO.getItemName());
					detailCodeTO.setDescription(TO.getDescription());

					codeDetailDAO.deleteDetailCode(detailCodeTO);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);
		
		return resultMap;

	}

	@Override
	public int getStandardUnitPrice(String itemCode) {

		return itemDAO.getStandardUnitPrice(itemCode);
	}
	
	@Override
	public int getStandardUnitPriceBox(String itemCode) {

		return itemDAO.getStandardUnitPriceBox(itemCode);
	}
	

}
