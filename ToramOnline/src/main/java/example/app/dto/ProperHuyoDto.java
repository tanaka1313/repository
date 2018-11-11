package example.app.dto;

import java.io.Serializable;

public class ProperHuyoDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// プロパ名称
	private String vPoroperName;
	
	// ソート区分(同じ値であれば順不同）
	private int iProperSortNum;
	
	// ペナルティ区分(0:ペナルティなし、1:ペナルティあり)
	private int iProperPenaltyFlg;
	
	// プロパ消費素材
	private String vProperSozaiName;
	
	// プロパ素材ポイント
	private int iProperSozaiPt;
	
	// 残潜在値
	private int iProperRestSenzai;
	
	// 現在プロパレベル
	private int iProperNowLevel;

	// コンストラクタ
	public ProperHuyoDto(ProperStatusDto properDto, int iProperSortNum, int iProperPenaltyFlg, int iProperRestSenzai) {
		
		this.vPoroperName = properDto.getvProperName();
		this.vProperSozaiName = properDto.getvProperSozaiName();
		this.iProperSozaiPt = calcurateSozaiPt(properDto.getiProperNowLevel(), properDto.getdProperSozaiTanka());
		this.iProperSortNum = iProperSortNum;
		this.iProperPenaltyFlg = iProperPenaltyFlg;
		this.iProperRestSenzai = iProperRestSenzai;
		this.setiProperNowLevel(properDto.getiProperNowLevel());
		
	}

	public String getvProperName() {
		return vPoroperName;
	}

	public void setvProperName(String vPoroperName) {
		this.vPoroperName = vPoroperName;
	}

	public int getiProperSortNum() {
		return iProperSortNum;
	}

	public void setiProperSortNum(int iProperSortNum) {
		this.iProperSortNum = iProperSortNum;
	}

	public int getiProperPenaltyFlg() {
		return iProperPenaltyFlg;
	}

	public void setiProperPenaltyFlg(int iProperPenaltyFlg) {
		this.iProperPenaltyFlg = iProperPenaltyFlg;
	}
	
	private int calcurateSozaiPt(int nowLv, Double tanka) {
		return (int) Math.floor(0.00000000000009 +  nowLv * nowLv * tanka );
	}

	public String getvProperSozaiName() {
		return vProperSozaiName;
	}

	public void setvProperSozaiName(String vProperSozaiName) {
		this.vProperSozaiName = vProperSozaiName;
	}

	public int getiProperSozaiPt() {
		return iProperSozaiPt;
	}

	public void setiProperSozaiPt(int iProperSozaiPt) {
		this.iProperSozaiPt = iProperSozaiPt;
	}

	public int getiProperRestSenzai() {
		return iProperRestSenzai;
	}

	public void setiProperRestSenzai(int iProperRestSenzai) {
		this.iProperRestSenzai = iProperRestSenzai;
	}

	public int getiProperNowLevel() {
		return iProperNowLevel;
	}

	public void setiProperNowLevel(int iProperNowLevel) {
		this.iProperNowLevel = iProperNowLevel;
	}

}
