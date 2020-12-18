package com.estimulo.base.to;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

import com.estimulo.common.annotation.Dataset;
import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name="CODE_DETAIL")
@Dataset(name="gds_codeDetail")
@IdClass(CodeDetailTO.class)
@SuppressWarnings("serial")
public class CodeDetailTO extends BaseTO implements Serializable{
	@Id
	private String divisionCodeNo;
	@Id
	private String detailCode;
	private String detailCodeName;
	private String codeUseCheck;
	private String description;

}