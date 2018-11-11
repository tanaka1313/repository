package example.app.dto;

import java.io.Serializable;

public class SkillLogTypeCalcDto implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// スキルタイプ詳細コード
	private String vSkillTypeDetailCode;
	
	// スキルタイプ詳細名
	private String vSkillTypeDetailName;
	
	// カウント
	private Integer iNumber;


	public Integer getiNumber() {
		return iNumber;
	}

	public void setiNumber(Integer iNumber) {
		this.iNumber = iNumber;
	}

	public String getvSkillTypeDetailCode() {
		return vSkillTypeDetailCode;
	}

	public void setvSkillTypeDetailCode(String vSkillTypeDetailCode) {
		this.vSkillTypeDetailCode = vSkillTypeDetailCode;
	}

	public String getvSkillTypeDetailName() {
		return vSkillTypeDetailName;
	}

	public void setvSkillTypeDetailName(String vSkillTypeDetailName) {
		this.vSkillTypeDetailName = vSkillTypeDetailName;
	}
	
}
