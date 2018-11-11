package example.app.dto;

import java.io.Serializable;

public class SeirenItemDto implements Serializable {
	
	private String[] hyoujiSeiren = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "E", "D", "C", "B", "A", "S"};
	
	private static final long serialVersionUID = 1L;
	
	// 現在レベル
	private Integer iNowLv;
	
	// 表記ネーム
	private String vHyoujiData;
	
	// パラ名
	private String vParamName;
	
	// 使用鉱石
	private String vUseKouseki;
	
	// 使用補助アイテム
	private String vUseHojoItem;
	
	// 鉱石レベル
	private int iKousekiLv = 0;
	
	// 劣化防止割合
	private int rekkaSafe = 0;
	
	// 成功率加算値
	private int seikouAdd = 0;
	
	// 最終成功率加算値
	private int seikouAddFinal = 0;
	
	// 最終成功倍率
	private int ticketBairitu = 1;
	
	// 成功確率
	private double seikouRate;
	
	// 0劣化率
	private double rekka0Rate;
	
	// 1劣化率
	private double rekka1Rate;
	
	// 2劣化率
	private double rekka2Rate;
	
	// 3劣化率
	private double rekka3Rate;
	
	// コンストラクタ
	public SeirenItemDto(int nowLv) {
		this.iNowLv = nowLv;
		this.vHyoujiData = this.hyoujiSeiren[nowLv] + "→" + this.hyoujiSeiren[nowLv + 1];
	}

	public Integer getiNowLv() {
		return iNowLv;
	}

	public void setiNowLv(Integer iNowLv) {
		this.iNowLv = iNowLv;
	}

	public String getvHyoujiData() {
		return vHyoujiData;
	}

	public void setvHyoujiData(String vHyoujiData) {
		this.vHyoujiData = vHyoujiData;
	}

	public String getvParamName() {
		return vParamName;
	}

	public void setvParamName(String vParamName) {
		this.vParamName = vParamName;
	}

	public String getvUseKouseki() {
		return vUseKouseki;
	}

	public void setvUseKouseki(String vUseKouseki) {
		this.vUseKouseki = vUseKouseki;
	}

	public String getvUseHojoItem() {
		return vUseHojoItem;
	}

	public void setvUseHojoItem(String vUseHojoItem) {
		this.vUseHojoItem = vUseHojoItem;
	}

	public double getSeikouRate() {
		return seikouRate;
	}

	public void setSeikouRate(double seikouRate) {
		this.seikouRate = seikouRate;
	}

	public double getRekka0Rate() {
		return rekka0Rate;
	}

	public void setRekka0Rate(double rekka0Rate) {
		this.rekka0Rate = rekka0Rate;
	}

	public double getRekka1Rate() {
		return rekka1Rate;
	}

	public void setRekka1Rate(double rekka1Rate) {
		this.rekka1Rate = rekka1Rate;
	}

	public double getRekka2Rate() {
		return rekka2Rate;
	}

	public void setRekka2Rate(double rekka2Rate) {
		this.rekka2Rate = rekka2Rate;
	}

	public double getRekka3Rate() {
		return rekka3Rate;
	}

	public void setRekka3Rate(double rekka3Rate) {
		this.rekka3Rate = rekka3Rate;
	}

	public int getiKousekiLv() {
		return iKousekiLv;
	}

	public void setiKousekiLv(int iKousekiLv) {
		this.iKousekiLv = iKousekiLv;
	}

	public int getRekkaSafe() {
		return rekkaSafe;
	}

	public void setRekkaSafe(int rekkaSafe) {
		this.rekkaSafe = rekkaSafe;
	}

	public int getSeikouAdd() {
		return seikouAdd;
	}

	public void setSeikouAdd(int seikouAdd) {
		this.seikouAdd = seikouAdd;
	}

	public int getSeikouAddFinal() {
		return seikouAddFinal;
	}

	public void setSeikouAddFinal(int seikouAddFinal) {
		this.seikouAddFinal = seikouAddFinal;
	}

	public int getTicketBairitu() {
		return ticketBairitu;
	}

	public void setTicketBairitu(int ticketBairitu) {
		this.ticketBairitu = ticketBairitu;
	}
	
}
