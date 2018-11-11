package example.app.form;

import java.io.Serializable;
import java.util.List;

public class ProperOutputForm implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// エラーメッセージ
	private String errMsg;
	
	// 成功率
	private String seikouRate;
	
	// 付与手順
	private List<String> huyoStep;
	
	// 付与プロパ
	private String huyoProperPlus;

	// 付与プロパ
	private String huyoProperMinus;
	
	// 消費素材ポイント
	private String sozaiPt;
	
	// 初期潜在値
	private String syokiSenzai;

	// 成功率
	private Double seikou;
	
	// プラスプロパの数
	private Integer plusNum;
	
	// プラスプロパ成功率
	private Double plusSeikou;
	
	// プロパ手順修正InputForm
	private ProperSimuInputForm properSimuInputForm;
	
	
	public String getErrMsg() {
		return errMsg;
	}

	public void setErrMsg(String errMsg) {
		this.errMsg = errMsg;
	}

	public String getSeikouRate() {
		return seikouRate;
	}

	public void setSeikouRate(String seikouRate) {
		this.seikouRate = seikouRate;
	}

	public List<String> getHuyoStep() {
		return huyoStep;
	}

	public void setHuyoStep(List<String> huyoStep) {
		this.huyoStep = huyoStep;
	}

	public String getHuyoProperPlus() {
		return huyoProperPlus;
	}

	public void setHuyoProperPlus(String huyoProperPlus) {
		this.huyoProperPlus = huyoProperPlus;
	}

	public String getHuyoProperMinus() {
		return huyoProperMinus;
	}

	public void setHuyoProperMinus(String huyoProperMinus) {
		this.huyoProperMinus = huyoProperMinus;
	}

	public String getSozaiPt() {
		return sozaiPt;
	}

	public void setSozaiPt(String sozaiPt) {
		this.sozaiPt = sozaiPt;
	}

	public String getSyokiSenzai() {
		return syokiSenzai;
	}

	public void setSyokiSenzai(String syokiSenzai) {
		this.syokiSenzai = syokiSenzai;
	}

	public Double getSeikou() {
		return seikou;
	}

	public void setSeikou(Double seikou) {
		this.seikou = seikou;
	}

	public Double getPlusSeikou() {
		return plusSeikou;
	}

	public void setPlusSeikou(Double plusSeikou) {
		this.plusSeikou = plusSeikou;
	}

	public Integer getPlusNum() {
		return plusNum;
	}

	public void setPlusNum(Integer plusNum) {
		this.plusNum = plusNum;
	}

	public ProperSimuInputForm getProperSimuInputForm() {
		return properSimuInputForm;
	}

	public void setProperSimuInputForm(ProperSimuInputForm properSimuInputForm) {
		this.properSimuInputForm = properSimuInputForm;
	}

}
