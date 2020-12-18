package com.estimulo.base.to;

import lombok.Data;

@Data
public class EstimateReportTO {

	private String estimateNo;
	private String estimateDate;
	private String unitOfEstimate;
	private String estimateAmount;
	private String unitPriceOfEstimate;
	private String sumPriceOfEstimate;
	private String itemCode;
	private String itemName;
	private String customerName;
	private String customerTelNumber;
	private String customerFaxNumber;
	private String tax;
	private String totalAmount;
	private String totalEstimatePrice;
	private String totalEstimatePriceWithTax;
	
}
