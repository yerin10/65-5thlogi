package com.estimulo.logistics.logisticsInfo.to;

import com.estimulo.base.to.BaseTO;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class WarehouseTO extends BaseTO {
	private String warehouseCode;
	private String warehouseName;
	private String warehouseUseOrNot;
	private String description;
}