package com.estimulo.hr.to;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;


@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_employeeSecret")
public class EmployeeSecretTO extends BaseTO {
	
	 private String companyCode;	// pk1
	 private String empCode;		// pk2
	 private int seq;				// pk3
	 private String userPassword;

}