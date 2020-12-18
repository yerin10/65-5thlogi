package com.estimulo.logistics.production.to;

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

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Table(name="MRP_GATHERING")
@Entity
@Dataset(name="gds_mrpGathering")
public class MrpGatheringTO extends BaseTO {

	@Id
	private String mrpGatheringNo;
	private String orderOrProductionStatus;
	private String itemCode;
	private String itemName;
	private String unitOfMrpGathering;
	private String claimDate;
	private String dueDate;
	private int necessaryAmount;
	
	//1:N «ÿ¡‡æﬂ«‘
	@JoinColumn(name="mrpGatheringNo")
	@OneToMany(fetch=FetchType.LAZY, cascade = CascadeType.ALL)
	private List<MrpTO> mrpTOList;
}