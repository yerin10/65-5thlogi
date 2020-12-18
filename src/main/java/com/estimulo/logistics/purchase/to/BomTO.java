package com.estimulo.logistics.purchase.to;

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
@Table(name="BOM")
@Dataset(name="gds_bom")
public class BomTO extends BaseTO {

	@Id
	private String itemCode;
	private String parentItemCode;
	private int no;
	private int netAmount;
	private String description;

}