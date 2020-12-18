package com.estimulo.logistics.production.to;

import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_workSiteSimulation")
public class WorkSiteSimulationTO {
	
	private String workOrderNo;
	private String mrpNo;
	private String mpsNo;
	private String workSieteName;
	private String wdItem;
	private String parentItemCode;
	private String parentItemName;
	private String itemClassIfication;
	private String itemCode;
	private String itemName;
	private String requiredAmount;
	
}