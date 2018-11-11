package example.app.form;

import java.io.Serializable;

public class ProperDataForm implements Serializable {

	private static final long serialVersionUID = 1L;

	// プロパ名
	private String properName;
	
	// プロパレベル
	private Integer properLv;
	
	// プロパレベル表示用
	private String properLvHyoji;
	
	// プロパ差分
	private String vProperSabun;
	
	// 潜在単価
	private Integer iTanka;
	
	// 消費潜在
	private Double dSyohiSenzai;
	
	// コンストラクタ
	public ProperDataForm() {
		properLvHyoji = "MAX";
	}

	// コンストラクタ
	public ProperDataForm(String properName) {
		this.properName = properName;
		properLvHyoji = "MAX";
	}

	public String getProperName() {
		return properName;
	}

	public void setProperName(String properName) {
		this.properName = properName;
	}

	public Integer getProperLv() {
		return properLv;
	}

	public void setProperLv(Integer properLv) {
		this.properLv = properLv;
	}

	public Double getdSyohiSenzai() {
		return dSyohiSenzai;
	}

	public void setdSyohiSenzai(Double dSyohiSenzai) {
		this.dSyohiSenzai = dSyohiSenzai;
	}

	public Integer getiTanka() {
		return iTanka;
	}

	public void setiTanka(Integer iTanka) {
		this.iTanka = iTanka;
	}

	public String getiProperSabun() {
		return getvProperSabun();
	}

	public void setiProperSabun(String vProperSabun) {
		this.setvProperSabun(vProperSabun);
	}

	public String getProperLvHyoji() {
		return properLvHyoji;
	}

	public void setProperLvHyoji(String properLvHyoji) {
		this.properLvHyoji = properLvHyoji;
	}

	public String getvProperSabun() {
		return vProperSabun;
	}

	public void setvProperSabun(String vProperSabun) {
		this.vProperSabun = vProperSabun;
	}
}
