package com.estimulo.logistics.sales.to;


import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;
import com.estimulo.common.annotation.Remove;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_contract")
@Table(name="CONTRACT")
@Entity
public class ContractTO extends BaseTO {
	@Transient
	private String checked;
	@Id
	private String contractNo;
	private String estimateNo;
	private String contractType;
	private String customerCode;
	private String contractDate;
	private String contractRequester;
	private String personCodeInCharge;
	private String description;
	@Remove
	@JoinColumn(name="contractNo")
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<ContractDetailTO> contractDetailTOList;
	
	 
}