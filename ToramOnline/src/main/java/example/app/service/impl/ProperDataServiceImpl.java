package example.app.service.impl;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.app.dao.ProperDataDao;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;
import example.app.service.ProperDataService;

@Service
public class ProperDataServiceImpl implements ProperDataService {
	
	@Autowired
	ProperDataDao properDataDao;

	@Override
	public Map<String, String> getMinusProperNameMap(Integer lv, String properBui) {
		List<ProperDataDto>properList = properDataDao.getMinusProperData(lv, properBui);
		Map<String, String>map = new LinkedHashMap();
		map.put("", "");
		String keyValue;
		for(ProperDataDto properDto: properList) {
			keyValue = "【" + properDto.getvProperGroupName() + "】"
					+ properDto.getvProperName() + " lv-"
					+ properDto.getiProperMaxLv() + "(戻り値："
					+ (int) (Math.floor(0.00000000000009 + properDto.getiProperLvPoint() * properDto.getiProperMaxLv() * 0.3))
					+ "pt)";
			map.put(properDto.getvProperName(), keyValue);
		}
		
		return map;
	}
	
	@Override
	public Map<String, String> getPlusProperNameMap(Integer lv, String properBui) {
		List<ProperDataDto>properList = properDataDao.getPlusProperData(lv, properBui);
		Map<String, String>map = new LinkedHashMap();
		map.put("", "");
		String keyValue;
		for(ProperDataDto properDto: properList) {
			keyValue = "【" + properDto.getvProperGroupName() + "】"
					+ properDto.getvProperName()
					+ "(単価：" + properDto.getiProperLvPoint()
					+ "pt、MaxLv:" + properDto.getiProperMaxLv() + ")";
			map.put(properDto.getvProperName(), keyValue);
		}
		
		return map;
	}

	@Override
	public ProperStatusDto getProperStatusDto(String properName, Integer paramLv, String properBui) {
		ProperStatusDto properDto = properDataDao.getProperStatusDto(properName, paramLv, properBui);
		if(properDto != null) {
			properDto.setiProperNowLevel(0);
		}
		
		return properDto;
	}
	
}
