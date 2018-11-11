package example.app.dto;

import java.io.Serializable;

public class ProperStatusDto implements Serializable, Cloneable {
	
	private static final long serialVersionUID = 1L;
	
	// プロパ名称
	private String vProperName;
	
	// プロパグループ名称
	private String vProperGroupName;
	
	// プロパ最大レベル
	private Integer iProperMaxLevel;
	
	// プロパ目標レベル
	private Integer iProperMokuhyoLevel;
	
	// プロパ今レベル
	private Integer iProperNowLevel;
	
	// プロパ潜在単価
	private Integer iProperSenzaiTanka;
	
	// プロパ消費素材
	private String vProperSozaiName;
	
	// プロパ素材ポイント単価
	private Double dProperSozaiTanka;

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

	public Integer getiProperMokuhyoLevel() {
		return iProperMokuhyoLevel;
	}

	public void setiProperMokuhyoLevel(Integer iProperMokuhyoLevel) {
		this.iProperMokuhyoLevel = iProperMokuhyoLevel;
	}

	public Integer getiProperNowLevel() {
		return iProperNowLevel;
	}

	public void setiProperNowLevel(Integer iProperNowLevel) {
		this.iProperNowLevel = iProperNowLevel;
	}

	public Integer getiProperSenzaiTanka() {
		return iProperSenzaiTanka;
	}

	public void setiProperSenzaiTanka(Integer iProperSenzaiTanka) {
		this.iProperSenzaiTanka = iProperSenzaiTanka;
	}

	public String getvProperSozaiName() {
		return vProperSozaiName;
	}

	public void setvProperSozaiName(String vProperSozaiName) {
		this.vProperSozaiName = vProperSozaiName;
	}

	public Double getdProperSozaiTanka() {
		return dProperSozaiTanka;
	}

	public void setdProperSozaiTanka(Double dProperSozaiTanka) {
		this.dProperSozaiTanka = dProperSozaiTanka;
	}

	public Integer getiProperMaxLevel() {
		return iProperMaxLevel;
	}

	public void setiProperMaxLevel(Integer iProperMaxLevel) {
		this.iProperMaxLevel = iProperMaxLevel;
	}
	
	@Override
	public ProperStatusDto clone() {
		ProperStatusDto cloneDto = null;
		try {
			cloneDto = (ProperStatusDto) super.clone();
			cloneDto.vProperName = new String(this.vProperName);
			cloneDto.vProperGroupName = new String(this.vProperGroupName);
			cloneDto.iProperMaxLevel = new Integer(this.iProperMaxLevel);
			cloneDto.iProperMokuhyoLevel = new Integer(this.iProperMokuhyoLevel);
			cloneDto.iProperNowLevel = new Integer(this.iProperNowLevel);
			cloneDto.iProperSenzaiTanka = new Integer(this.iProperSenzaiTanka);
			cloneDto.vProperSozaiName = new String(this.vProperSozaiName);
			cloneDto.dProperSozaiTanka = new Double(this.dProperSozaiTanka);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return cloneDto;
	}
	
	
}
