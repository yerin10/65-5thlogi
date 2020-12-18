package com.estimulo.basicInfo.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.base.dao.CodeDetailDAO;
import com.estimulo.base.to.CodeDetailTO;
import com.estimulo.basicInfo.dao.WorkplaceDAO;
import com.estimulo.basicInfo.repository.WorkplaceRepository;
import com.estimulo.basicInfo.to.WorkplaceTO;

@Component
public class WorkplaceApplicationServiceImpl implements WorkplaceApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private WorkplaceDAO workplaceDAO;
	@Autowired
	private CodeDetailDAO codeDetailDAO;
	@Autowired
	private WorkplaceRepository workplaceRepository;

	public ArrayList<WorkplaceTO> getWorkplaceList(String companyCode) {

		return workplaceRepository.findByCompanyCode(companyCode);
	}

	public String getNewWorkplaceCode(String companyCode) {

		ArrayList<WorkplaceTO> workplaceList = null;
		String newWorkplaceCode = null;

			workplaceList =  workplaceRepository.findByCompanyCode(companyCode);

			TreeSet<Integer> workplaceCodeNoSet = new TreeSet<>();

			for (WorkplaceTO bean : workplaceList) {

				if (bean.getWorkplaceCode().startsWith("BRC-")) {

					try {

						Integer no = Integer.parseInt(bean.getWorkplaceCode().split("BRC-")[1]);
						workplaceCodeNoSet.add(no);

					} catch (NumberFormatException e) {

						// "BRC-" �떎�쓬 遺�遺꾩쓣 Integer 濡� 蹂��솚�븯吏� 紐삵븯�뒗 寃쎌슦 : 洹몃깷 �떎�쓬 諛섎났臾� �떎�뻾

					}

				}

			}

			if (workplaceCodeNoSet.isEmpty()) {
				newWorkplaceCode = "BRC-" + String.format("%02d", 1);
			} else {
				newWorkplaceCode = "BRC-" + String.format("%02d", workplaceCodeNoSet.pollLast() + 1);
			}

		return newWorkplaceCode;
	}


	public HashMap<String, Object> batchWorkplaceListProcess(ArrayList<WorkplaceTO> workplaceList) {

		HashMap<String, Object> resultMap = new HashMap<>();

			ArrayList<String> insertList = new ArrayList<>();
			ArrayList<String> updateList = new ArrayList<>();
			ArrayList<String> deleteList = new ArrayList<>();

			CodeDetailTO detailCodeBean = new CodeDetailTO();

			for (WorkplaceTO bean : workplaceList) {

				String status = bean.getStatus();

				switch (status) {

				case "INSERT":

					// �깉濡쒖슫 �궗�뾽�옣踰덊샇 �깮�꽦 �썑 bean �뿉 set
					String newWorkplaceCode = getNewWorkplaceCode(bean.getCompanyCode());
					bean.setWorkplaceCode(newWorkplaceCode);

					// �궗�뾽�옣 �뀒�씠釉붿뿉 insert
					workplaceDAO.insertWorkplace(bean);
					insertList.add(bean.getWorkplaceCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Insert
					detailCodeBean.setDivisionCodeNo("CO-02");
					detailCodeBean.setDetailCode(bean.getWorkplaceCode());
					detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

					codeDetailDAO.insertDetailCode(detailCodeBean);

					break;

				case "UPDATE":

					workplaceDAO.updateWorkplace(bean);
					updateList.add(bean.getWorkplaceCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Update
					detailCodeBean.setDivisionCodeNo("CO-02");
					detailCodeBean.setDetailCode(bean.getWorkplaceCode());
					detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

					codeDetailDAO.updateDetailCode(detailCodeBean);

					break;

				case "DELETE":

					workplaceDAO.deleteWorkplace(bean);
					deleteList.add(bean.getWorkplaceCode());

					// CODE_DETAIL �뀒�씠釉붿뿉 Delete
					detailCodeBean.setDivisionCodeNo("CO-02");
					detailCodeBean.setDetailCode(bean.getWorkplaceCode());
					detailCodeBean.setDetailCodeName(bean.getWorkplaceName());

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
