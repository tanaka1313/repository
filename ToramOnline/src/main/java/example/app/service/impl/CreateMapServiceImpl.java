package example.app.service.impl;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.stereotype.Service;

import example.app.service.CreateMapService;

@Service
public class CreateMapServiceImpl implements CreateMapService {
	
	private String[] hyoujiSeiren = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "E", "D", "C", "B", "A", "S"};

	@Override
	public Map<Integer, Integer> getCustomMap(int max, int mod, int start) {
		Map<Integer, Integer> map = new LinkedHashMap();
		int mapNum = ( max - start )/ mod + 1;
		for(int i=0; i < mapNum; i++) {
			map.put( start + i * mod, start + i * mod);
		}
		
		return map;
		
	}

	@Override
	public Map<String, String> getCustomAddMaxMap(int max, int mod, int start) {
		Map<String, String> map = new LinkedHashMap();
		int mapNum = ( max - start )/ mod + 1;
		for(int i=0; i < mapNum; i++) {
			String value = String.valueOf(start + i * mod);
			map.put( value, value);
		}
		map.put("MAX","MAX");
		
		return map;
		
	}

	@Override
	public Map<Integer, String> getCustomSeirenMap(int max, int mod, int start) {
		Map<Integer, String> map = new LinkedHashMap();
		int mapNum = ( max - start )/ mod + 1;
		for(int i=0; i < mapNum; i++) {
			int data = start + i * mod;
			map.put( data, hyoujiSeiren[data]);
		}
		
		return map;
		
	}
	
	@Override 
	public Map<String,String>getProperBuiMap(){
		Map<String,String> map = new LinkedHashMap();
		map.put("武器", "武器");
		map.put("体装備", "体装備");
		return map;
	}
	
	@Override
	public Map<String,String>getPermitLogMap(){
		Map<String,String> map = new LinkedHashMap();
		map.put("許可", "許可する");
		map.put("不許可", "許可しない");
		return map;
	}


}
