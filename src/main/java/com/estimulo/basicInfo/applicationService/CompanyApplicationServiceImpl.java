package com.estimulo.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.base.dao.CodeDetailDAO;
import com.estimulo.base.to.CodeDetailTO;
import com.estimulo.basicInfo.dao.CompanyDAO;
import com.estimulo.basicInfo.repository.CompanyRepository;
import com.estimulo.basicInfo.to.CompanyTO;

@Component
public class CompanyApplicationServiceImpl implements CompanyApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private CompanyDAO companyDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private CompanyRepository companyRepository;

	public ArrayList<CompanyTO> getCompanyList() {

		return companyRepository.findAllByOrderByCompanyCodeDesc();
	}

	public String getNewCompanyCode() {

		ArrayList<CompanyTO> companyList = null;
		String newCompanyCode = null;

			companyList = companyRepository.findAllByOrderByCompanyCodeDesc();

			TreeSet<Integer> companyCodeNoSet = new TreeSet<>();

			for (CompanyTO bean : companyList) {

				if (bean.getCompanyCode().startsWith("COM-")) {

					try {

						Integer no = Integer.parseInt(bean.getCompanyCode().split("COM-")[1]);
						companyCodeNoSet.add(no);

					} catch (NumberFormatException e) {

						// "COM-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾

					}

				}

			}

			if (companyCodeNoSet.isEmpty()) {
				newCompanyCode = "COM-" + String.format("%02d", 1);
			} else {
				newCompanyCode = "COM-" + String.format("%02d", companyCodeNoSet.pollLast() + 1);
			}
			
		return newCompanyCode;
	}

	public HashMap<String, Object> batchCompanyListProcess(ArrayList<CompanyTO> companyList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeBean = new CodeDetailTO();

			
			// 2踰덉쓽 諛섎났臾� �떎�뻾 : �빀爾먯꽌 �빐�룄 �긽愿��뾾吏�留�, 異붽�/�닔�젙/�궘�젣媛� 寃뱀튂硫� 以묎컙�뿉 鍮좎��뒗 踰덊샇媛� �깮湲� �닔 �엳�쓬
			for (CompanyTO bean : companyList) {  // 1李� 諛섎났 : INSERT 留� �떎�뻾 => 以묎컙�뿉 鍮좎��뒗 踰덊샇媛� �뾾�룄濡� �븯湲� �쐞�빐�꽌..

				String status = bean.getStatus();

				switch (status) {   

				case "INSERT":

					// �깉濡쒖슫 �쉶�궗踰덊샇 �깮�꽦 �썑 bean �뿉 set
					String newCompanyCode = getNewCompanyCode();
					bean.setCompanyCode(newCompanyCode);

					// �쉶�궗 �뀒�씠釉붿뿉 insert
					companyDAO.insertCompany(bean);
					insertList.add(bean.getCompanyCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeBean.setDivisionCodeNo("CO-01");
					detailCodeBean.setDetailCode(bean.getCompanyCode());
					detailCodeBean.setDetailCodeName(bean.getCompanyName());

					codeDetailDAO.insertDetailCode(detailCodeBean);

					break;

				}

			}

			for (CompanyTO bean : companyList) {    // 2李� 諛섎났 : UPDATE , DELETE 留� �떎�뻾

				String status = bean.getStatus();

				switch (status) {

				case "UPDATE":

					companyDAO.updateCompany(bean);
					updateList.add(bean.getCompanyCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Update
					detailCodeBean.setDivisionCodeNo("CO-01");
					detailCodeBean.setDetailCode(bean.getCompanyCode());
					detailCodeBean.setDetailCodeName(bean.getCompanyName());

					codeDetailDAO.updateDetailCode(detailCodeBean);

					break;

				case "DELETE":

					companyDAO.deleteCompany(bean);
					deleteList.add(bean.getCompanyCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Delete
					detailCodeBean.setDivisionCodeNo("CO-01");
					detailCodeBean.setDetailCode(bean.getCompanyCode());
					detailCodeBean.setDetailCodeName(bean.getCompanyName());

					codeDetailDAO.deleteDetailCode(detailCodeBean);

					break;

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;
	}

}
