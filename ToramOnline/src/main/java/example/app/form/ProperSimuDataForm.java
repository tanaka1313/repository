package example.app.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProperSimuDataForm implements Serializable {

	private static final long serialVersionUID = 1L;
	
	// 付与プロパ
	private List<ProperDataForm> properList = new ArrayList();
	
	// 付与前のプロパ
	private List<ProperDataForm> properBeforeList = new ArrayList();
	
	// まとめてフラグ
	private String matometeStr;
	
	// 警告メッセージ
	private List<String> warnStrList;
	
	// ペナルティ
	private String vPenalty;
	
	// 消費潜在
	private String vUseSenzai;
	
	// 付与前の潜在値
	private Integer iBeforeSenzai;
	
	// 付与後の潜在値
	private Integer iAfterSenzai;
	
	// 成功率
	private Double dSeikouRate;
	
	// 消費素材ポイント
	private String vSozaiData;
	
	// 素材ポイントマップ
	private Map<String,Integer> sozaiMap = new HashMap();
	
	// コンストラクタ
	public ProperSimuDataForm(Integer senzai) {
		
		matometeStr = "matomete";
		vPenalty = "-";
		vUseSenzai = "-";
		iBeforeSenzai = senzai;
		iAfterSenzai = senzai;
		dSeikouRate = null;
		vSozaiData = "金属：-pt,布地：-pt,獣品：-pt,木材：-pt,薬品：-pt,魔素：-pt";
		properList = new ArrayList();
		properBeforeList = new ArrayList();
		
	}
	
	// コンストラクタ
	public ProperSimuDataForm() {
		properList = new ArrayList();
		properBeforeList = new ArrayList();
	}

	public List<ProperDataForm> getProperList() {
		return properList;
	}

	public void setProperList(List<ProperDataForm> properList) {
		this.properList = properList;
	}

	public List<String> getWarnStrList() {
		return warnStrList;
	}

	public void setWarnStrList(List<String> warnStrList) {
		this.warnStrList = warnStrList;
	}

	public String getvPenalty() {
		return vPenalty;
	}

	public void setvPenalty(String vPenalty) {
		this.vPenalty = vPenalty;
	}

	public String getvUseSenzai() {
		return vUseSenzai;
	}

	public void setvUseSenzai(String vUseSenzai) {
		this.vUseSenzai = vUseSenzai;
	}

	public Integer getiBeforeSenzai() {
		return iBeforeSenzai;
	}

	public void setiBeforeSenzai(Integer iBeforeSenzai) {
		this.iBeforeSenzai = iBeforeSenzai;
	}

	public Integer getiAfterSenzai() {
		return iAfterSenzai;
	}

	public void setiAfterSenzai(Integer iAfterSenzai) {
		this.iAfterSenzai = iAfterSenzai;
	}

	public String getvSozaiData() {
		return vSozaiData;
	}

	public void setvSozaiData(String vSozaiData) {
		this.vSozaiData = vSozaiData;
	}

	public String getMatometeStr() {
		return matometeStr;
	}

	public void setMatometeStr(String matometeStr) {
		this.matometeStr = matometeStr;
	}

	public Double getdSeikouRate() {
		return dSeikouRate;
	}

	public void setdSeikouRate(Double dSeikouRate) {
		this.dSeikouRate = dSeikouRate;
	}

	public List<ProperDataForm> getProperBeforeList() {
		return properBeforeList;
	}

	public void setProperBeforeList(List<ProperDataForm> properBeforeList) {
		this.properBeforeList = properBeforeList;
	}

	public Map<String,Integer> getSozaiMap() {
		return sozaiMap;
	}

	public void setSozaiMap(Map<String,Integer> sozaiMap) {
		this.sozaiMap = sozaiMap;
	}
	
	// toStringのOverRide
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		if("matomete".equals(matometeStr)) {
			builder.append("まとめて");
		}else {
			builder.append("1ずつ付与することで");
		}
		
		for(ProperDataForm proper : properList) {
			if(!"+0".equals(proper.getvProperSabun())) {
				builder.append("," + proper.getProperName() + " Lv." + proper.getProperLv());
			}
		}
		
		builder.append("を付与する。（残潜在：" + iAfterSenzai + "pt)");
		
		return builder.toString();
	}

}
