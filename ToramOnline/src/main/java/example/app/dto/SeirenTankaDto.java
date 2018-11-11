package example.app.dto;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

public class SeirenTankaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// アイテム名
	private String vItemName;
	
	// 価格
	@Pattern(regexp = "[0-9]*")
	@Min(0)
	private Integer iTanka;
	
	// コンストラクタ
	public SeirenTankaDto(String vItemName, int tanka) {
		this.setvItemName(vItemName);
		this.iTanka = tanka;
	}
	
	public SeirenTankaDto() {
		
	}


	public Integer getiTanka() {
		return iTanka;
	}

	public void setiTanka(Integer ikakaku) {
		this.iTanka = ikakaku;
	}

	public String getvItemName() {
		return vItemName;
	}

	public void setvItemName(String vItemName) {
		this.vItemName = vItemName;
	}
	

}