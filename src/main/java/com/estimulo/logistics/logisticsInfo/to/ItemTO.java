package com.estimulo.logistics.logisticsInfo.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_item")
@Entity
@Table(name="CONTRACT_DETAIL")
public class ItemTO extends BaseTO {
	@Id
	private String itemCode;
	private String itemName;
	private String itemGroupCode;
	private String itemClassification;
	private String unitOfStock;
	private String lossRate;
	private String leadTime;
	private int standardUnitPrice;
	private String description;
}