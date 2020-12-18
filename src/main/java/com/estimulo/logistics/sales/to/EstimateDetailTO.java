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
@Entity
@Table(name="ESTIMATE_DETAIL")
@Dataset(name="gds_estimateDetail")
public class EstimateDetailTO extends BaseTO {
	@Transient
	private String checked;
	@Id
	private String estimateDetailNo;
	private String estimateNo;
	private String itemCode;
	private String itemName;
	private String unitOfEstimate;
	private String dueDateOfEstimate;
	private String estimateAmount;
	private String unitPriceOfEstimate;
	private String sumPriceOfEstimate;
	private String description;

}