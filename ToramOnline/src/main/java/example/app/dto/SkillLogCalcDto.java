package example.app.dto;

import java.io.Serializable;

public class SkillLogCalcDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// スキルコード
	private String vSkillCode;
	
	// スキルレベル
	private Integer iSkillLv;
	
	// カウント
	private Integer iNumber;

	public String getvSkillCode() {
		return vSkillCode;
	}

	public void setvSkillCode(String vSkillCode) {
		this.vSkillCode = vSkillCode;
	}

	public Integer getiSkillLv() {
		return iSkillLv;
	}

	public void setiSkillLv(Integer iSkillLv) {
		this.iSkillLv = iSkillLv;
	}

	public Integer getiNumber() {
		return iNumber;
	}

	public void setiNumber(Integer iNumber) {
		this.iNumber = iNumber;
	}
	
}
