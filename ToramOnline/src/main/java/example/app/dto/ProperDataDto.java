package example.app.dto;

import java.io.Serializable;

public class ProperDataDto implements Serializable{
	
	private static final long serialVersionUID = 1L;

	// プロパ名
	private String vProperName;
	
	// プロパグループ名
	private String vProperGroupName;
	
	// プロパ最大レベル
	private Integer iProperMaxLv;
	
	// プロパ消費潜在値
	private Integer iProperLvPoint;

	// プロパ消費素材
	private String vProperSozai;
	
	// プロパ素材消費量
	private Double dProperSozaiPoint;
	
	// プロパ備考
	private String vProperBikou;

	public String getvProperName() {
		return vProperName;
	}

	public void setvProperName(String vProperName) {
		this.vProperName = vProperName;
	}

	public String getvProperGroupName() {
		return vProperGroupName;
	}

	public void setvProperGroupName(String vProperGroupName) {
		this.vProperGroupName = vProperGroupName;
	}

	public Integer getiProperMaxLv() {
		return iProperMaxLv;
	}

	public void setiProperMaxLv(Integer iProperMaxLv) {
		this.iProperMaxLv = iProperMaxLv;
	}

	public Integer getiProperLvPoint() {
		return iProperLvPoint;
	}

	public void setiProperLvPoint(Integer iProperLvPoint) {
		this.iProperLvPoint = iProperLvPoint;
	}

	public String getvProperSozai() {
		return vProperSozai;
	}

	public void setvProperSozai(String vProperSozai) {
		this.vProperSozai = vProperSozai;
	}

	public Double getdProperSozaiPoint() {
		return dProperSozaiPoint;
	}

	public void setdProperSozaiPoint(Double dProperSozaiPoint) {
		this.dProperSozaiPoint = dProperSozaiPoint;
	}

	public String getvProperBikou() {
		return vProperBikou;
	}

	public void setvProperBikou(String vProperBikou) {
		this.vProperBikou = vProperBikou;
	}
	

}

