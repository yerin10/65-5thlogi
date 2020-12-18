package com.estimulo.hr.to;

import com.estimulo.base.to.BaseTO;
import com.estimulo.common.annotation.Dataset;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
@Dataset(name="gds_employeeDetail")
public class EmployeeDetailTO extends BaseTO {
	 private String companyCode;		// pk1
	 private String empCode;			// pk2
	 private int seq;					// pk3
	 private String updateHistory;
	 private String updateDate;
	 private String workplaceCode;
	 private String deptCode;
	 private String positionCode;
	 private String userId;
	 private String phoneNumber;
	 private String email;
	 private String zipCode;
	 private String basicAddress;
	 private String detailAddress;
	 private String levelOfEducation;
	 private String image;				//image file!

}