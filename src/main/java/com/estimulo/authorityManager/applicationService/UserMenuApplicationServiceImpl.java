package com.estimulo.authorityManager.applicationService;

import java.util.HashMap;
import java.util.List;
import java.util.TreeMap;

import javax.servlet.ServletContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.estimulo.authorityManager.dao.UserMenuDAO;
import com.estimulo.authorityManager.to.UserMenuTO;

@Service
public class UserMenuApplicationServiceImpl implements UserMenuApplicationService {

	// DAO 참조변수 선언
	@Autowired
	private UserMenuDAO userMenuDAO;	
	
	
	public String getUserMenuCode(String workplaceCode, String deptCode, String positionCode,
			ServletContext application) {

		StringBuffer menuCode = null;
		HashMap<String, UserMenuTO> map = new HashMap<String, UserMenuTO>();
		TreeMap<Integer, UserMenuTO> treeMap = null;		
		HashMap<String, String> param = new HashMap<>();
			param.put("workplaceCode" , workplaceCode);
			param.put("deptCode" , deptCode);
			param.put("positionCode" , positionCode);

			// dao 파트 시작
			List<UserMenuTO> list = userMenuDAO.selectUserMenuCodeList(param);
			// dao 파트 끝
			for(UserMenuTO bean : list) {
				map.put(bean.getNo() + "", bean);
			}

			menuCode = new StringBuffer();
			treeMap = new TreeMap<>();

			for (String key : map.keySet()) {
				treeMap.put(Integer.parseInt(key.toString()), map.get(key));
			}

			Integer key = treeMap.firstKey();
			Integer nextKey = treeMap.higherKey(key);
			Integer previousKey = null;

			Integer maxKey = treeMap.lastKey();

			menuCode.append("<ul>\r\n\r\n");

			while (nextKey != null || key == maxKey) { // while 반복문 시작
				UserMenuTO bean = treeMap.get(key);
				Integer menuLevel = bean.getMenuLevel();
				Integer menuOrder = bean.getMenuOrder();
				String menuName = bean.getMenuName();
				String url = null;
				String isAccessDenied = bean.getIsAccessDenied();
				String contextPath = application.getContextPath();

				if (bean.getUrl() == null) {
					url = "#";
				} else if (isAccessDenied != null) {
					url = contextPath + "/accessDenied.html";
				} else if (isAccessDenied == null) {
					url = contextPath + bean.getUrl();
				}

				UserMenuTO previouesTO = null;
				UserMenuTO nextTO = null;

				Integer nextMenuLevel = null;
				Integer previousMenuLevel = null;

				if (treeMap.lowerKey(key) != null) {
					previousKey = treeMap.lowerKey(key);
					previouesTO = treeMap.get(previousKey);
					previousMenuLevel = previouesTO.getMenuLevel();
				}

				if (nextKey != null) {
					nextTO = treeMap.get(nextKey);
					nextMenuLevel = nextTO.getMenuLevel();
				} else {
					nextMenuLevel = 0;
				}

				// "Home" 메뉴
				if (menuLevel == 1 && menuOrder == 0) {
					menuCode.append("\t<li><a class='aTag' href ='" + url + "'>" + menuName + "</a></li>\r\n\r\n");
				}

				if (menuLevel == 1 && menuOrder != 0) {
					menuCode.append("\t<li class='active'><a href='" + url + "'>" + menuName + "</a>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 1 && nextMenuLevel == 3) {
					menuCode.append(
							"\t\t<ul>\r\n" + "\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 1 && nextMenuLevel == 2) {
					menuCode.append("\t\t<ul>\r\n" + "\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName
							+ "</a></li>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 1 && (nextMenuLevel == 1 || nextMenuLevel == 0)) {
					menuCode.append("\t\t<ul>\r\n" + "\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName
							+ "</a></li>\r\n" + "\t\t</ul>\r\n" + "\t</li>\r\n\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 2 && nextMenuLevel == 3) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 2 && nextMenuLevel == 2) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 2 && (nextMenuLevel == 1 || nextMenuLevel == 0)) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n"
							+ "\t\t</ul>\r\n" + "\t</li>\r\n\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 3 && nextMenuLevel == 3) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 3 && nextMenuLevel == 2) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n");
				}

				if (menuLevel == 2 && previousMenuLevel == 3 && (nextMenuLevel == 1 || nextMenuLevel == 0)) {
					menuCode.append("\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n"
							+ "\t\t</ul>\r\n" + "\t</li>\r\n\r\n");
				}

				if (menuLevel == 3 && previousMenuLevel == 2 && nextMenuLevel != 2) {
					menuCode.append("\t\t\t\t<ul>\r\n" + "\t\t\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName
							+ "</a></li>\r\n");
				} else if (menuLevel == 3 && previousMenuLevel == 2 && nextMenuLevel == 2) {
					menuCode.append("\t\t\t\t<ul>\r\n" + "\t\t\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName
							+ "</a></li>\r\n" + "\t\t\t\t</ul>\r\n");
				}

				if (menuLevel == 3 && previousMenuLevel == 3 && nextMenuLevel == 3) {
					menuCode.append("\t\t\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n");
				} else if (menuLevel == 3 && previousMenuLevel == 3 && nextMenuLevel == 2) {
					menuCode.append("\t\t\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n"
							+ "\t\t\t\t</ul>\r\n\t\t\t</li>\r\n");
				} else if (menuLevel == 3 && previousMenuLevel == 3 && (nextMenuLevel == 1 || nextMenuLevel == 0)) {
					menuCode.append("\t\t\t\t\t<li><a class='aTag' href='" + url + "'>" + menuName + "</a></li>\r\n"
							+ "\t\t\t\t</ul>\r\n" + "\t\t\t</li>\r\n" + "\t\t</ul>\r\n" + "\t</li>\r\n\r\n");
				}

				key = nextKey;

				if (key != null) {
					nextKey = treeMap.higherKey(key);
				}

			} // while 반복문 끝

			menuCode.append("</ul>");

		return menuCode.toString();

	}
}
