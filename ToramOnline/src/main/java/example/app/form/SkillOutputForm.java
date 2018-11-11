package example.app.form;

import java.io.Serializable;
import java.util.List;

public class SkillOutputForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 消費スキルポイント
	private String skillPt;
	
	// 実行結果文字列
	private List<SkillDataForm> resultString;

	public String getSkillPt() {
		return skillPt;
	}

	public void setSkillPt(String skillPt) {
		this.skillPt = skillPt;
	}

	public List<SkillDataForm> getResultString() {
		return resultString;
	}

	public void setResultString(List<SkillDataForm> resultString) {
		this.resultString = resultString;
	}

}
