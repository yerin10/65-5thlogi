package com.estimulo.logistics.sales.applicationService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estimulo.logistics.sales.dao.EstimateDAO;
import com.estimulo.logistics.sales.dao.EstimateDetailDAO;
import com.estimulo.logistics.sales.repository.EstimateDetailRepository;
import com.estimulo.logistics.sales.repository.EstimateRepository;
import com.estimulo.logistics.sales.to.EstimateDetailTO;
import com.estimulo.logistics.sales.to.EstimateTO;

@Component
public class EstimateApplicationServiceImpl implements EstimateApplicationService {

	// 李몄“蹂��닔 �꽑�뼵
	@Autowired
	private EstimateDAO estimateDAO;
	@Autowired 
	private EstimateDetailDAO estimateDetailDAO;
	@Autowired
	private EstimateRepository estimateRepository;
	@Autowired
	private EstimateDetailRepository estimateDetailRepository;

	public ArrayList<EstimateTO> getEstimateList(String dateSearchCondition, String startDate, String endDate) {

		ArrayList<EstimateTO> estimateTOList = null;
		HashMap<String, String> param = new HashMap<>();
			param.put("dateSearchCondition", dateSearchCondition);
			param.put("startDate", startDate);
			param.put("endDate", endDate);
			
			estimateTOList = estimateDAO.selectEstimateList(param);

			/*
			 * for (EstimateTO bean : estimateTOList) {
			 * 
			 * bean.setEstimateDetailTOList(estimateDetailDAO.selectEstimateDetailList(bean)
			 * );
			 * 
			 * }
			 */
			
		return estimateTOList;
	}

	/*
	 * public ArrayList<EstimateDetailTO> getEstimateDetailList(String estimateNo) {
	 * 
	 * return estimateDetailDAO.selectEstimateDetailList(estimateNo);
	 * 
	 * }
	 */

	public String getNewEstimateNo(String estimateDate) {

		StringBuffer newEstimateNo = null;
				System.out.println("estimateDate:::"+estimateDate);
			int i = estimateDAO.selectEstimateCount(estimateDate);

			newEstimateNo = new StringBuffer();
			newEstimateNo.append("ES");
			newEstimateNo.append(estimateDate.replace("-", ""));
			newEstimateNo.append(String.format("%02d", i));		//  ES + estimateDate(-뺀거) + 날짜같은거 카운팅숫자

		return newEstimateNo.toString();
	}

	public HashMap<String, Object> addNewEstimate(String estimateDate, EstimateTO newEstimateTO) {

		HashMap<String, Object> resultMap = null;

		System.out.println("@@@?#@#@#?@#?@#?s");
		//새로운견적 생성
		String newEstimateNo = getNewEstimateNo(estimateDate);		
			
			//견적일련번호 TO 담는다
			newEstimateTO.setEstimateNo(newEstimateNo);
			// 酉곕떒�뿉�꽌 蹂대궡�삩 寃ъ쟻 Bean �뿉 �깉濡쒖슫 寃ъ쟻�씪�젴踰덊샇 set
			/*
			 * estimateDAO.insertEstimate(newEstimateBean);
			 * 
			 */
			
			//견적상세 리스트 담음
			List<EstimateDetailTO> estimateDetailTOList = newEstimateTO.getEstimateDetailTOList();
			// 寃ъ쟻�긽�꽭 List
			StringBuffer newEstimateDetailNo = null;

			int i = 1;

			for (EstimateDetailTO bean : estimateDetailTOList) {

				//새로운 견적번호 담음
				bean.setEstimateNo(newEstimateNo);
				
				// 견적일련번호-01, -02, -03... 방식으로 견적상세일련번호 생성
				newEstimateDetailNo = new StringBuffer();

				newEstimateDetailNo.append(newEstimateNo);
				newEstimateDetailNo.append("-");
				newEstimateDetailNo.append(String.format("%02d", i++)); // .format("%02d", i++): 두자리 숫자, INSERT 될 때마다 하나씩 증가; ES2020010401-01, ES20200104-02...
				// �깮�꽦�맂 寃ъ쟻�긽�꽭 �씪�젴踰덊샇 set
				bean.setEstimateDetailNo(newEstimateDetailNo.toString());

			}
			System.out.println("?????@@@?#@#@#?@#?@#?s");
			//견적 인설트함
			estimateRepository.save(newEstimateTO);
			System.out.println("??");
			// 견적상세List 를 batchListProcess 로 Insert, 결과 맵 반환
			resultMap = batchEstimateDetailListProcess(estimateDetailTOList);

			// 寃곌낵 留듭뿉 "estimateNo" �궎媛믪쑝濡� �깉濡� �깮�꽦�맂 寃ъ쟻�씪�젴踰덊샇 ���옣
			resultMap.put("newEstimateNo", newEstimateNo);

		return resultMap;
		// �깉濡� �깮�꽦�맂 寃ъ쟻�씪�젴踰덊샇瑜� ���옣
	}

	public HashMap<String, Object> batchEstimateDetailListProcess(List<EstimateDetailTO> estimateDetailTOList) {

		HashMap<String, Object> resultMap = new HashMap<>();

		// 異붽�/�닔�젙/�궘�젣�맂 寃ъ쟻�긽�꽭�씪�젴踰덊샇瑜� �떞�쓣 ArrayList
		ArrayList<String> insertList = new ArrayList<>();
		ArrayList<String> updateList = new ArrayList<>();
		ArrayList<String> deleteList = new ArrayList<>();

			// 첫 번째 반복문 : INSERT 만 먼저 처리 => DELETE 를 먼저 하면 새로운 번호가 기존에 매겨졌던 번호로 매겨질 수 있음; UPDATE 는 상관없음;
			for (EstimateDetailTO bean : estimateDetailTOList) {

				String status = bean.getStatus();

				// �깉濡쒖슫 寃ъ쟻�긽�꽭�씪�젴踰덊샇瑜� �떞�쓣 StringBuffer
				StringBuffer newEstimateDetailNo = new StringBuffer();

				switch (status) {

				case "insert":

					// 견적상세 일련번호 양식 : "ES20180101-01"
					// 견적 조회/수정의 새로운 견적상세를 일괄저장하는 경우 => 견적상세일련번호가 '저장시 지정됨' => "ES" 로 시작하지 않음
					if (!bean.getEstimateDetailNo().startsWith("ES")) {
							System.out.println("dqdqdqdqdqdqdqdqdqdq");
							// 견적상세TO에 있는 견적번호로 이전에 등록됐던 견적상세번호를 Mapper에서 가져옴;
							// registeredNoList = 기존에 등록된 견적상세일련번호들
							//ArrayList<EstimateDetailTO> registeredNoList = estimateDetailMapper.selectEstimateDetailCount(estimateDetailTO.getEstimateNo());
						int newNo;
						List<EstimateDetailTO> list = estimateDetailRepository.findAllByEstimateNo(bean.getEstimateNo());

						TreeSet<Integer> intSet = new TreeSet<>();

						for(EstimateDetailTO listBean: list) {
							String estimateDetailNo = listBean.getEstimateDetailNo();
							int no = Integer.parseInt(estimateDetailNo.split("-")[1]);
							System.out.println("no::::::"+no);
							intSet.add(no);
						}

						if (intSet.isEmpty()) {
							newNo = 1;
						} else {
							newNo = intSet.pollLast() + 1;
							System.out.println("newNo::::::"+newNo);
						}

						// �깉濡쒖슫 寃ъ쟻�긽�꽭�씪�젴踰덊샇 �깮�꽦
						newEstimateDetailNo.append(bean.getEstimateNo());
						newEstimateDetailNo.append("-");
						newEstimateDetailNo.append(String.format("%02d", newNo));
						System.out.println("newEstimateDetailNo:::::"+newEstimateDetailNo);

						// 寃ъ쟻�긽�꽭�씪�젴踰덊샇 set
						bean.setEstimateDetailNo(newEstimateDetailNo.toString());
					}

					//estimateDetailDAO.insertEstimateDetail(bean);
					estimateDetailRepository.save(bean);
					insertList.add(bean.getEstimateDetailNo());		//ES2020010301-01 <= �씠�떎 -01 �� 泥ル쾲吏� 寃ъ쟻�씠�씪�꽌 -01 �씠�떎.
					break;

				}

			}

			// �몢 踰덉㎏ 諛섎났臾� : UPDATE, DELETE 泥섎━

			for (EstimateDetailTO bean : estimateDetailTOList) {

				String status = bean.getStatus();

				switch (status) {

				case "update":

					estimateDetailDAO.updateEstimateDetail(bean);
					updateList.add(bean.getEstimateDetailNo());
					break;

				case "delete":

					estimateDetailDAO.deleteEstimateDetail(bean);
					deleteList.add(bean.getEstimateDetailNo());

					break;

				default:

				}

			}

			resultMap.put("INSERT", insertList);
			resultMap.put("UPDATE", updateList);
			resultMap.put("DELETE", deleteList);

		return resultMap;

	}

}
