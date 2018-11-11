package example.app.dto;

import java.io.Serializable;
import java.util.List;

import example.app.form.ProperSimuInputForm;

public class ProperHuyoAnswerDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	private String errorMsg;
	
	private String warnMsg;
	
	private int seikouRate;
	
	private List<String> huyoStep;
	
	private String sozaiList;
	
	private String huyoMemberPlus;

	private String huyoMemberMinus;
	
	private ProperSimuInputForm properSimuInputForm;

	public List<String> getHuyoStep() {
		return huyoStep;
	}

	public void setHuyoStep(List<String> huyoTejun) {
		this.huyoStep = huyoTejun;
	}

	public int getSeikouRate() {
		return seikouRate;
	}

	public void setSeikouRate(int seikouRate) {
		this.seikouRate = seikouRate;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public String getWarnMsg() {
		return warnMsg;
	}

	public void setWarnMsg(String warnMsg) {
		this.warnMsg = warnMsg;
	}

	public String getSozaiList() {
		return sozaiList;
	}

	public void setSozaiList(String sozaiList) {
		this.sozaiList = sozaiList;
	}

	public String getHuyoMemberPlus() {
		return huyoMemberPlus;
	}

	public void setHuyoMemberPlus(String huyoMemberPlus) {
		this.huyoMemberPlus = huyoMemberPlus;
	}

	public String getHuyoMemberMinus() {
		return huyoMemberMinus;
	}

	public void setHuyoMemberMinus(String huyoMemberMinus) {
		this.huyoMemberMinus = huyoMemberMinus;
	}

	public ProperSimuInputForm getProperSimuInputForm() {
		return properSimuInputForm;
	}

	public void setProperSimuInputForm(ProperSimuInputForm properSimuInputForm) {
		this.properSimuInputForm = properSimuInputForm;
	}

}
