package example.app.service;

import java.util.List;
import java.util.Map;

import example.app.dto.ProperStatusDto;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;

public interface SkillDataService {
	
	public List<SkillGroupDataForm> getSkillGroupData();
	
	public int calcurateSkillData(List<SkillGroupDataForm> skillGroup, List<SkillDataForm> resultList, Boolean judgeLog);
	
	public Map<String,String> getStatisticsBukiMap();

}
