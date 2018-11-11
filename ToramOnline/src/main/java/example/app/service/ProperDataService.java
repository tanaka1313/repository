package example.app.service;

import java.util.Map;

import example.app.dto.ProperStatusDto;

public interface ProperDataService {
	
	public Map<String,String>getMinusProperNameMap(Integer lv, String properBui);
	
	public Map<String,String>getPlusProperNameMap(Integer lv, String properBui);
	
	public ProperStatusDto getProperStatusDto(String properName, Integer paramLv, String properBui);

}
