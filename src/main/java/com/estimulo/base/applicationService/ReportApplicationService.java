package com.estimulo.base.applicationService;

import java.util.ArrayList;

import com.estimulo.base.to.ContractReportTO;
import com.estimulo.base.to.EstimateReportTO;

public interface ReportApplicationService {

	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo);

	public ArrayList<ContractReportTO> getContractReport(String contractNo);
	
}
