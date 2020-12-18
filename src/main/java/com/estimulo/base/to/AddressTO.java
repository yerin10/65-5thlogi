package com.estimulo.base.to;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper=false)
public class AddressTO extends BaseTO {

	private String addressNo;
	private int cnt;
	private String zipCode;
	private String addressType;
	private String address;

}
