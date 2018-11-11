package example.app.dto;

import java.io.Serializable;

public class HojoItemDataDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// 補助アイテム名
	private String vHojoItemName;
	
	// 劣化防止率
	private int iRekkaSafeRate;
	
	// 成功率
	private int iSeikouRate;
	
	// 最終成功率加算値
	private int iSmithSeikouRate;
	
	// 最終成功倍率
	private int iTicketBairitu;

	public String getvHojoItemName() {
		return vHojoItemName;
	}

	public void setvHojoItemName(String vHojoItemName) {
		this.vHojoItemName = vHojoItemName;
	}

	public int getiRekkaSafeRate() {
		return iRekkaSafeRate;
	}

	public void setiRekkaSafeRate(int iRekkaSafeRate) {
		this.iRekkaSafeRate = iRekkaSafeRate;
	}

	public int getiSeikouRate() {
		return iSeikouRate;
	}

	public void setiSeikouRate(int iSeikouRate) {
		this.iSeikouRate = iSeikouRate;
	}

	public int getiSmithSeikouRate() {
		return iSmithSeikouRate;
	}

	public void setiSmithSeikouRate(int iSmithSeikouRate) {
		this.iSmithSeikouRate = iSmithSeikouRate;
	}

	public int getiTicketBairitu() {
		return iTicketBairitu;
	}

	public void setiTicketBairitu(int iTicketBairitu) {
		this.iTicketBairitu = iTicketBairitu;
	}

}
