package com.estimulo.logistics.purchase.to;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="STOCK")
@Dataset(name="gds_stock")
public class StockTO {
	
	private String warehouseCode;
	@Id
	private String itemCode;
	private String itemName;
	private String unitOfStock;
	private String safetyAllowanceAmount;
	private String stockAmount;
	private String orderAmount;
	private String inputAmount;
	private String deliveryAmount;
	
}
