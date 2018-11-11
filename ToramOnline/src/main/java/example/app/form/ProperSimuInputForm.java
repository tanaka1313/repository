package example.app.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ProperSimuInputForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// パラメータのレベル
	private int paramLevel=0;
	
	// 初期潜在値
	private int shokiSenzai=0;
	
	// 基礎潜在値
	private int kisoSenzai=0;
	
	// プロパリスト
	private List<ProperDataForm> properList;
	
	// プロパ部位
	private String properBui;
	
	// プロパ付与手順
	private List<ProperSimuDataForm> properStepList;
	
	// コンストラクタ
	public ProperSimuInputForm() {
		properList = new ArrayList();
		for(int i = 0; i < 6; i++) {
			properList.add(new ProperDataForm());
		}
		properStepList = new ArrayList();
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

	public List<ProperDataForm> getProperList() {
		return properList;
	}

	public void setProperList(List<ProperDataForm> properList) {
		this.properList = properList;
	}

	public String getProperBui() {
		return properBui;
	}

	public void setProperBui(String properBui) {
		this.properBui = properBui;
	}

	public List<ProperSimuDataForm> getProperStepList() {
		return properStepList;
	}

	public void setProperStepList(List<ProperSimuDataForm> properStepList) {
		this.properStepList = properStepList;
	}


}
