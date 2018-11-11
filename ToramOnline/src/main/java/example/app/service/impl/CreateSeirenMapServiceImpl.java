package example.app.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.app.dao.ProperDataDao;
import example.app.dao.SeirenDataDao;
import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.service.CreateMapService;
import example.app.service.CreateSeirenMapService;

@Service
public class CreateSeirenMapServiceImpl implements CreateSeirenMapService {

	@Autowired
	SeirenDataDao seirenDataDao;

	@Override
	public Map<String, String> getParamMap() {
		Map<String, String> map = new LinkedHashMap();
		map.put("TEC極", "TEC極");
		map.put("LUK極", "LUK極");
		
		return map;
		
	}

	@Override
	public Map<String, String> getKousekiMap() {
		List<KousekiDataDto> kousekiList = seirenDataDao.getKousekiData();
		Map<String, String> map = new LinkedHashMap();
		for(KousekiDataDto dto : kousekiList) {
			String hyoujiValue = dto.getvKousekiName();
			map.put(dto.getvKousekiName(), hyoujiValue);
		}
		return map;
	}

	@Override
	public Map<String, String> getHojoItemMap() {
		List<HojoItemDataDto> hojoitemList = seirenDataDao.getHojoItemData();
		Map<String, String> map = new LinkedHashMap();
		map.put("なし", "なし");
		for(HojoItemDataDto dto : hojoitemList) {
			String hyoujiValue = dto.getvHojoItemName() 
					+ " (" + dto.getiRekkaSafeRate() 
					+ "/" + dto.getiSeikouRate() 
					+ "/" + dto.getiSmithSeikouRate() 
					+ ")";
			map.put(dto.getvHojoItemName(), hyoujiValue);
		}
		return map;
	}

}
