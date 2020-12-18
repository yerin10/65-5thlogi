package com.estimulo.logistics.production.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.estimulo.logistics.production.to.MrpTO;
import com.estimulo.logistics.production.to.OpenMrpTO;

@Mapper
public interface MrpDAO {

	public ArrayList<MrpTO> selectMrpListAll(HashMap<String, String> param);
	
	public ArrayList<MrpTO> selectMrpList(HashMap<String, String> param); 

	public ArrayList<MrpTO> selectMrpListAsMrpGatheringNo(String mrpGatheringNo);
	
	public ArrayList<OpenMrpTO> openMrp(HashMap<String, String> param);
	
	public List<MrpTO> selectMrpCount(String mrpRegisterDate);

	public void insertMrp(MrpTO TO);
	
	public void updateMrp(MrpTO TO);
	
	public void  changeMrpGatheringStatus(HashMap<String, String> param);
	
	public void deleteMrp(MrpTO TO);
	
}
