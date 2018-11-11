package example.app.service;

import java.util.List;
import java.util.Map;

import example.app.dto.ProperStatusDto;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;

public interface SkillLogCalcService {
	
	public void setSkillStatisticsData(List<SkillGroupDataForm> skillGroupList, List<String> statisticsBukiList);
	
}
