package com.estimulo.logistics.sales.to;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;
import com.estimulo.common.annotation.Remove;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="ESTIMATE")
@Dataset(name="gds_estimate")
public class EstimateTO extends BaseTO {
	
	@Id
	private String estimateNo;
	private String customerCode;
	private String estimateDate;
	private String contractStatus;
	private String estimateRequester;
	private String effectiveDate;
	private String personCodeInCharge;
	private String description;

	//private String personNameCharge;
	@Remove
	@JoinColumn(name="estimateNo")
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<EstimateDetailTO> estimateDetailTOList;

}