package example.app.dto;

import java.io.Serializable;

public class KousekiDataDto implements Serializable {
	
	private static final long serialVersionUID = 1L;

	// 鉱石名
	private String vKousekiName;
	
	// 鉱石性能
	private int iKouseki;

	public String getvKousekiName() {
		return vKousekiName;
	}

	public void setvKousekiName(String vKousekiName) {
		this.vKousekiName = vKousekiName;
	}

	public int getiKouseki() {
		return iKouseki;
	}

	public void setiKouseki(int iKouseki) {
		this.iKouseki = iKouseki;
	}

}
