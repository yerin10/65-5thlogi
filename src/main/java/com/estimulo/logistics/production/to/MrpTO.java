package com.estimulo.logistics.production.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="MRP")
@Dataset(name="gds_mrp")
public class MrpTO extends BaseTO {
	
	@Id
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
	private String mrpGatheringStatus;
}