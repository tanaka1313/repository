package example.app.dao;

import java.util.List;

import example.app.dto.SkillLogCalcDto;
import example.app.dto.SkillLogTypeCalcDto;

public interface SkillLogCalcDao {
	
	public List<SkillLogCalcDto> selectLogCalc(List<String> paramList);
	
	public List<SkillLogCalcDto> selectLogCalcPerLv(List<String> paramList, String skillCode);
	
	public List<SkillLogTypeCalcDto> selectLogTypeCalc(List<String> paramList);

}
