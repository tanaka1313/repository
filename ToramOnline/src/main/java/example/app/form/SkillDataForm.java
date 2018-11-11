package example.app.form;

import java.io.Serializable;
import java.util.List;

public class SkillDataForm  implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// スキル名称
	private String skillName;
	
	// スキルレベル
	private Integer skillLv;
	
	// スキルコード
	private String skillCode;
	
	// 統計情報 取得率
	private Double acquisitionRate;
	
	// 統計情報 レベル頻度
	private String lvFrequency;
	
	// スキルリスト
	private List<SkillDataForm> skillDataList;
	
	// スキルグループ名
	private String skillGroupName;

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Integer getSkillLv() {
		return skillLv;
	}

	public void setSkillLv(Integer skillLv) {
		this.skillLv = skillLv;
	}

	public String getSkillCode() {
		return skillCode;
	}

	public void setSkillCode(String skillCode) {
		this.skillCode = skillCode;
	}

	public List<SkillDataForm> getSkillDataList() {
		return skillDataList;
	}

	public void setSkillDataList(List<SkillDataForm> skillDataList) {
		this.skillDataList = skillDataList;
	}

	public String getSkillGroupName() {
		return skillGroupName;
	}

	public void setSkillGroupName(String skillGroupName) {
		this.skillGroupName = skillGroupName;
	}
	
	@Override
	public String toString() {
		String resultStr = "【" + this.getSkillGroupName() + "】 " ;
		resultStr = resultStr		+ this.getSkillName() 
				+ " Lv." + this.getSkillLv();
		
		return resultStr;
	}

	public Double getAcquisitionRate() {
		return acquisitionRate;
	}

	public void setAcquisitionRate(Double getRate) {
		this.acquisitionRate = getRate;
	}

	public String getLvFrequency() {
		return lvFrequency;
	}

	public void setLvFrequency(String lvFrequency) {
		this.lvFrequency = lvFrequency;
	}
	
}
