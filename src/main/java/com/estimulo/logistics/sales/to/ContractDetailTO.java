package com.estimulo.logistics.sales.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;
@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_contractDetail")
@Entity
@Table(name="CONTRACT_DETAIL")
public class ContractDetailTO extends BaseTO {
	@Transient
	private String checked;
	@Id
	private String contractDetailNo;
	private String contractNo;
	private String itemCode;
	private String itemName;
	private String unitOfContract;
	private String dueDateOfContract;
	private String estimateAmount;// 수주수량
	private String stockAmountUse;
	private String productionRequirement;    
	private String unitPriceOfContract;
	private String sumPriceOfContract;
	private String processingStatus;
	private String operationCompletedStatus;
	private String deliveryCompletionStatus;	
	private String description;
	
}