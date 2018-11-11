package example.app.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProperInputForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// パラメータのレベル
	private int paramLevel=0;
	
	// 初期潜在値
	private int shokiSenzai=0;
	
	// 基礎潜在値
	private int kisoSenzai=0;
	
	// マイナスプロパリスト
	private List<ProperDataForm> minusProperList;
	
	// プラスプロパリスト
	private List<ProperDataForm> plusProperList;
	
	// プロパ部位
	private String properBui;
	
	// コンストラクタ
	public ProperInputForm() {
		minusProperList = new ArrayList();
		minusProperList.add(new ProperDataForm());
		minusProperList.add(new ProperDataForm());
		minusProperList.add(new ProperDataForm());
		minusProperList.add(new ProperDataForm());
		minusProperList.add(new ProperDataForm());
		plusProperList = new ArrayList();
		plusProperList.add(new ProperDataForm());
		plusProperList.add(new ProperDataForm());
		plusProperList.add(new ProperDataForm());
		plusProperList.add(new ProperDataForm());
		plusProperList.add(new ProperDataForm());
	}

	public int getParamLevel() {
		return paramLevel;
	}

	public void setParamLevel(int paramLevel) {
		this.paramLevel = paramLevel;
	}

	public int getShokiSenzai() {
		return shokiSenzai;
	}

	public void setShokiSenzai(int shokiSenzai) {
		this.shokiSenzai = shokiSenzai;
	}

	public int getKisoSenzai() {
		return kisoSenzai;
	}

	public void setKisoSenzai(int kisoSenzai) {
		this.kisoSenzai = kisoSenzai;
	}

	public List<ProperDataForm> getMinusProperList() {
		return minusProperList;
	}

	public void setMinusProperList(List<ProperDataForm> minusProperList) {
		this.minusProperList = minusProperList;
	}

	public List<ProperDataForm> getPlusProperList() {
		return plusProperList;
	}

	public void setPlusProperList(List<ProperDataForm> plusProperList) {
		this.plusProperList = plusProperList;
	}

	public String getProperBui() {
		return properBui;
	}

	public void setProperBui(String properBui) {
		this.properBui = properBui;
	}
	
}
