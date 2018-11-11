package example.app.dao;

import java.util.List;

import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;

public interface ProperDataDao {
	
	public List<ProperDataDto> getMinusProperData(Integer lv, String properBui);
	
	public List<ProperDataDto> getPlusProperData(Integer lv, String properBui);
	
	public ProperStatusDto getProperStatusDto(String properName, int lv, String properBui);

}
