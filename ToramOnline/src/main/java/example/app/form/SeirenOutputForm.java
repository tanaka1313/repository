package example.app.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SeirenOutputForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// 0からSにするのに必要な期待値スピナ
	private double kitaitiSpina0toS;
	
	// 精錬値
	private List<String> hyoujiDataList;
	
	// 表示用アイテムリスト
	private List<List<String>> hyoujiItemList;
	
	// 成功率
	private List<Integer> seikouRateList;
	
	// 期待値用マップ
	private Map<String,List<Double>> kitaitiMap;
	
	// カスタマイズ部分の初期精錬表示
	private String syokiSeirenHyoji;
	
	// カスタマイズ部分の目標精錬表示
	private String mokuhyoSeirenHyoji;
	
	// カスタマイズ部分の精錬表示
	private String seirenStr;
	
	// カスタマイズ部分のMap
	private Map<String,Double> customUseMap;

	// 試行回数
	private Integer doNum;
	
	// 消費スピナのリスト
	private List<Integer> spnList;
	
	// 平均値
	private Double average;
	
	// 分散
	private Double dispersion;
	
	// 標準偏差
	private Double standardDev;
	
	// エラーメッセージ
	private String errMessage;
	
	public double getKitaitiSpina0toS() {
		return kitaitiSpina0toS;
	}

	public void setKitaitiSpina0toS(double kitaitiSpina0toS) {
		this.kitaitiSpina0toS = kitaitiSpina0toS;
	}

	public List<String> getHyoujiDataList() {
		return hyoujiDataList;
	}

	public void setHyoujiDataList(List<String> hyoujiDataList) {
		this.hyoujiDataList = hyoujiDataList;
	}

	public List<List<String>> getHyoujiItemList() {
		return hyoujiItemList;
	}

	public void setHyoujiItemList(List<List<String>> hyoujiItemList) {
		this.hyoujiItemList = hyoujiItemList;
	}

	public List<Integer> getSeikouRateList() {
		return seikouRateList;
	}

	public void setSeikouRateList(List<Integer> seikouRateList) {
		this.seikouRateList = seikouRateList;
	}

	public Map<String, List<Double>> getKitaitiMap() {
		return kitaitiMap;
	}

	public void setKitaitiMap(Map<String, List<Double>> kitaitiMap) {
		this.kitaitiMap = kitaitiMap;
	}

	public Map<String,Double> getCustomUseMap() {
		return customUseMap;
	}

	public void setCustomUseMap(Map<String,Double> customUseMap) {
		this.customUseMap = customUseMap;
	}

	public String getSeirenStr() {
		return seirenStr;
	}

	public void setSeirenStr(String seirenStr) {
		this.seirenStr = seirenStr;
	}

	public String getMokuhyoSeirenHyoji() {
		return mokuhyoSeirenHyoji;
	}

	public void setMokuhyoSeirenHyoji(String mokuhyoSeirenHyoji) {
		this.mokuhyoSeirenHyoji = mokuhyoSeirenHyoji;
	}

	public String getSyokiSeirenHyoji() {
		return syokiSeirenHyoji;
	}

	public void setSyokiSeirenHyoji(String syokiSeirenHyoji) {
		this.syokiSeirenHyoji = syokiSeirenHyoji;
	}

	public Integer getDoNum() {
		return doNum;
	}

	public void setDoNum(Integer doNum) {
		this.doNum = doNum;
	}

	public List<Integer> getSpnList() {
		return spnList;
	}

	public void setSpnList(List<Integer> spnList) {
		this.spnList = spnList;
	}

	public Double getAverage() {
		return average;
	}

	public void setAverage(Double average) {
		this.average = average;
	}

	public Double getDispersion() {
		return dispersion;
	}

	public void setDispersion(Double dispersion) {
		this.dispersion = dispersion;
	}

	public Double getStandardDev() {
		return standardDev;
	}

	public void setStandardDev(Double standardDev) {
		this.standardDev = standardDev;
	}

	public String getErrMessage() {
		return errMessage;
	}

	public void setErrMessage(String errMessage) {
		this.errMessage = errMessage;
	}
	
	


}
