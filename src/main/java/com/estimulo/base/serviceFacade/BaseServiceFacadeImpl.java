package com.estimulo.base.serviceFacade;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.base.applicationService.AddressApplicationService;
import com.estimulo.base.applicationService.CodeApplicationService;
import com.estimulo.base.applicationService.ReportApplicationService;
import com.estimulo.base.to.AddressTO;
import com.estimulo.base.to.CodeDetailTO;
import com.estimulo.base.to.CodeTO;
import com.estimulo.base.to.ContractReportTO;
import com.estimulo.base.to.EstimateReportTO;

@Service
public class BaseServiceFacadeImpl implements BaseServiceFacade {
	
	@Autowired
	private CodeApplicationService codeAS;
	@Autowired
	private AddressApplicationService addressAS;
	@Autowired
	private ReportApplicationService reportAS;

	@Override
	public ArrayList<CodeDetailTO> getDetailCodeList(String divisionCode) {
		
		return codeAS.getDetailCodeList(divisionCode);
	}

	@Override
	public ArrayList<CodeTO> getCodeList() {

		return codeAS.getCodeList();
	}

	@Override
	public Boolean checkCodeDuplication(String divisionCode, String newDetailCode) {

		return codeAS.checkCodeDuplication(divisionCode, newDetailCode);
	}

	@Override
	public HashMap<String, Object> batchCodeListProcess(ArrayList<CodeTO> codeList) {

		return codeAS.batchCodeListProcess(codeList);
	}

	@Override
	public HashMap<String, Object> batchDetailCodeListProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeAS.batchDetailCodeListProcess(detailCodeList);
	}

	@Override
	public HashMap<String, Object> changeCodeUseCheckProcess(ArrayList<CodeDetailTO> detailCodeList) {

		return codeAS.changeCodeUseCheckProcess(detailCodeList);
	}

	@Override
	public ArrayList<AddressTO> getAddressList(String sidoName, String searchAddressType, String searchValue, String mainNumber) {

		return addressAS.getAddressList(sidoName, searchAddressType, searchValue, mainNumber);
	}
	
	@Override
	public ArrayList<EstimateReportTO> getEstimateReport(String estimateNo) {

		return reportAS.getEstimateReport(estimateNo);

	}

	@Override
	public ArrayList<ContractReportTO> getContractReport(String contractNo) {

		return reportAS.getContractReport(contractNo);

	}

}
