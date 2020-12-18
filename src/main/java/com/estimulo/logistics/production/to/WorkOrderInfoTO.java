package com.estimulo.logistics.production.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_workOrderInfo")
@Table(name="WORK_ORDER_INFO")
@Entity
public class WorkOrderInfoTO {
	
	private String checked;
	@Id
	private String workOrderNo;
	private String mrpNo;
	private String mpsNo;
	private String mrpGatheringNo;
	private String itemClassification;
	private String itemCode;
	private String itemName;
	private String unitOfMrp;
	private String requiredAmount;
	private String workSiteCode;
	private String workStieName;
	private String productionProcessCode;
	private String productionProcessName;
	private String inspectionStatus;
	private String productionStatus;
	private String completionStatus;
	private String actualCompletionAmount;
	
}
