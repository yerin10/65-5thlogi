package com.estimulo.logistics.production.to;

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
@Dataset(name="gds_mps")
@Entity
@Table(name="MPS")
public class MpsTO extends BaseTO  {
	@Transient
	private String checked;
	@Id
	private String mpsNo;
	private String mpsPlanClassification;
	private String contractDetailNo;
	private String itemCode;
	private String itemName;
	private String unitOfMps;
	private String mpsPlanDate;
	private String mpsPlanAmount;
	private String dueDateOfMps;
	private String scheduledEndDate;
	private String mrpApplyStatus;
	private String description;
	private String salesPlanNo; // column에 뿌리지 않음
}