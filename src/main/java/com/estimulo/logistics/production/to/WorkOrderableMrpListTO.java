package com.estimulo.logistics.production.to;

import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_workOrderableMrpList")
public class WorkOrderableMrpListTO {
	private String checked;
	private String mrpNo;
	private String mpsNo;	
	private String mrpGatheringNo;	
	private String itemClassification;	
	private String itemCode;
	private String itemName;	
	private String unitOfMrp;	
	private int requiredAmount;	
	private String orderDate;
	private String requiredDate;
}
