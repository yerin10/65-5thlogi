package com.estimulo.logistics.logisticsInfo.to;

import com.estimulo.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class ItemInfoTO extends BaseTO {

	private String itemCode; 
	private String itemName;
	private String itemGroupCode;
	private String itemClassification;
	private String unitOfStock;
	private String lossRate;
	private String leadTime;
	private int standardUnitPrice;
	private String codeUseCheck;
	private String description;	 
}
