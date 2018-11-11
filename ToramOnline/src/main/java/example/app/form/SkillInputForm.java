package example.app.form;

import java.io.Serializable;
import java.util.List;

public class SkillInputForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// スキルグループデータ
	private List<SkillGroupDataForm> skillGroup;
	
	// 統計情報　武器種
	private String statisticsBuki = "NONE";
	
	// 統計情報　取得の許可
	private String permitLog = "許可";

	public List<SkillGroupDataForm> getSkillGroup() {
		return skillGroup;
	}

	public void setSkillGroup(List<SkillGroupDataForm> skillGroup) {
		this.skillGroup = skillGroup;
	}

	public String getStatisticsBuki() {
		return statisticsBuki;
	}

	public void setStatisticsBuki(String statisticsBuki) {
		this.statisticsBuki = statisticsBuki;
	}

	public String getPermitLog() {
		return permitLog;
	}

	public void setPermitLog(String permitLog) {
		this.permitLog = permitLog;
	}

}
