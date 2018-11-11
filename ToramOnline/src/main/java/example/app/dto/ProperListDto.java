package example.app.dto;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ProperListDto implements Serializable , Cloneable{
	
	private static final long serialVersionUID = 1L;
	
	// プラスプロパリスト
	private List<ProperStatusDto> plusProperList = new ArrayList();
	
	// 同項目プロパリストFIRST
	private List<ProperStatusDto> reptPlusProperListFirst = new ArrayList();
	
	// 同項目プロパリストAfter
	private List<ProperStatusDto> reptPlusProperListAfter = new ArrayList();
	
	// マイナスプロパリスト
	private List<ProperStatusDto> minusProperList = new ArrayList();
	
	// 戻り潜在
	private double modoriSenzai = 0;
	
	// 禁止プロパリスト
	private List<ProperStatusDto>kinsiProperList = new ArrayList();
	
	// 禁止プロパリストマイナス
	private List<ProperStatusDto>kinsiMinusProperList = new ArrayList();

	// 付与禁止リストに必要な潜在値
	private int kinsiSenzai = 0;

	// 重複度MAP
	private Map<String, Integer> reptNumMap = new HashMap<>();
	
	// プロパ名→同じグループに属するプロパのリスト
	private Map<String, List<ProperStatusDto>> properGroupToDtoMap = new HashMap();
	
	// 最終ペナルティ
	private int finalPenalty = 0;
	
	// プロパ付与上限
	private int settiJougen = 0;
	
	// マイナス付与前プロパ設置可能数
	private int setti1 = 0;
	
	// マイナス付与後プロパ設置可能数
	private int setti2 = 0;
	
	// 警告メッセージ
	private String warnStr = "";
	
	// 最大単価
	private int maxTanka = 0;
	
	// マイナス最大単価
	private int minusMaxTanka = 0;

	public List<ProperStatusDto> getPlusProperList() {
		return plusProperList;
	}

	public void setPlusProperList(List<ProperStatusDto> plusProperList) {
		this.plusProperList = plusProperList;
	}

	public List<ProperStatusDto> getReptPlusProperListFirst() {
		return reptPlusProperListFirst;
	}

	public void setReptPlusProperListFirst(List<ProperStatusDto> reptPlusProperListFirst) {
		this.reptPlusProperListFirst = reptPlusProperListFirst;
	}

	public List<ProperStatusDto> getReptPlusProperListAfter() {
		return reptPlusProperListAfter;
	}

	public void setReptPlusProperListAfter(List<ProperStatusDto> reptPlusProperListAfter) {
		this.reptPlusProperListAfter = reptPlusProperListAfter;
	}

	public List<ProperStatusDto> getMinusProperList() {
		return minusProperList;
	}

	public void setMinusProperList(List<ProperStatusDto> minusProperList) {
		this.minusProperList = minusProperList;
	}

	public double getModoriSenzai() {
		return modoriSenzai;
	}

	public void setModoriSenzai(double modoriSenzai) {
		this.modoriSenzai = modoriSenzai;
	}

	public List<ProperStatusDto> getKinsiProperList() {
		return kinsiProperList;
	}

	public void setKinsiProperList(List<ProperStatusDto> kinsiProperList) {
		this.kinsiProperList = kinsiProperList;
	}

	public List<ProperStatusDto> getKinsiMinusProperList() {
		return kinsiMinusProperList;
	}

	public void setKinsiMinusProperList(List<ProperStatusDto> kinsiMinusProperList) {
		this.kinsiMinusProperList = kinsiMinusProperList;
	}

	public int getKinsiSenzai() {
		return kinsiSenzai;
	}

	public void setKinsiSenzai(int kinsiSenzai) {
		this.kinsiSenzai = kinsiSenzai;
	}

	public Map<String, Integer> getReptNumMap() {
		return reptNumMap;
	}

	public void setReptNumMap(Map<String, Integer> reptNumMap) {
		this.reptNumMap = reptNumMap;
	}

	public Map<String, List<ProperStatusDto>> getProperGroupToDtoMap() {
		return properGroupToDtoMap;
	}

	public void setProperGroupToDtoMap(Map<String, List<ProperStatusDto>> properGroupToDtoMap) {
		this.properGroupToDtoMap = properGroupToDtoMap;
	}

	public int getFinalPenalty() {
		return finalPenalty;
	}

	public void setFinalPenalty(int finalPenalty) {
		this.finalPenalty = finalPenalty;
	}

	public int getSettiJougen() {
		return settiJougen;
	}

	public void setSettiJougen(int settiJougen) {
		this.settiJougen = settiJougen;
	}

	public int getSetti1() {
		return setti1;
	}

	public void setSetti1(int setti1) {
		this.setti1 = setti1;
	}

	public int getSetti2() {
		return setti2;
	}

	public void setSetti2(int setti2) {
		this.setti2 = setti2;
	}

	public String getWarnStr() {
		return warnStr;
	}

	public void setWarnStr(String warnStr) {
		this.warnStr = warnStr;
	}

	public int getMaxTanka() {
		return maxTanka;
	}

	public void setMaxTanka(int maxTanka) {
		this.maxTanka = maxTanka;
	}

	public int getMinusMaxTanka() {
		return minusMaxTanka;
	}

	public void setMinusMaxTanka(int minusMaxTanka) {
		this.minusMaxTanka = minusMaxTanka;
	}
	
	@Override
	public ProperListDto clone(){
		ProperListDto cloneDto = null;
		try {
			// modoriSenzai,kinsiSenzaiは基本型のためコピー済
			// finalPenalty,settiJougen,setti1,setti2は基本型のためコピー済
			// maxTanka,minusMaxTankaは基本型のためコピー済
			cloneDto = (ProperListDto) super.clone();
			cloneDto.plusProperList = new ArrayList<>(this.plusProperList);
			cloneDto.reptPlusProperListFirst = new ArrayList<>(this.reptPlusProperListFirst);
			cloneDto.reptPlusProperListAfter = new ArrayList<>(this.reptPlusProperListAfter);
			cloneDto.minusProperList = new ArrayList<>(this.minusProperList);
			cloneDto.kinsiProperList = new ArrayList<>(this.kinsiProperList);
			cloneDto.kinsiMinusProperList = new ArrayList<>(this.kinsiMinusProperList);
			cloneDto.reptNumMap = new HashMap<>(this.reptNumMap);
			// cloneDto.properGroupToDtoMap = new HashMap<>(this.properGroupToDtoMap);
			Map<String, List<ProperStatusDto>> tempMap = new HashMap();
			for(String s : this.properGroupToDtoMap.keySet()) {
				List<ProperStatusDto> tempList = this.properGroupToDtoMap.get(s);
				tempMap.put(s, new ArrayList<>(tempList));
			}
			cloneDto.properGroupToDtoMap = tempMap;
			cloneDto.warnStr = new String(this.warnStr);
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cloneDto;
	}

}
