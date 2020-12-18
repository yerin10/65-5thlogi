package com.estimulo.authorityManager.to;

import lombok.Data;

@Data
public class UserMenuTO {
	private int no;
	private int menuLevel;
	private int menuOrder;
	private String menuName;
	private int leaf;
	private String url;
	private String isAccessDenied;	
}
