package example.app.dao;

import java.util.List;

import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;

public interface SkillDataDao {
	
	public List<SkillGroupDataForm> getSkillGroupData();
	
	public List<SkillDataForm> getSkillData(String skillGroup);

}
