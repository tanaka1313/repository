package example.app.dao;

public interface SkillLogDao {
	
	public void insertSkillLog(String randNum, int totalSp);
	
	public void insertSkillDetail(String skillCode, String skillGroupName, String randNum, int skillLv, String skillName);

}
