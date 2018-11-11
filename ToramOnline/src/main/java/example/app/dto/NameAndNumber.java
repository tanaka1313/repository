package example.app.dto;

public class NameAndNumber {
	private String vName;
	private int iNumber;
	private int iTanka;
	
	public NameAndNumber(String vName, int iNumber, int iTanka) {
		this.vName = vName;
		this.iNumber = iNumber;
		this.iTanka = iTanka;
	}
	
	public String getvName() {
		return vName;
	}
	public void setvName(String vName) {
		this.vName = vName;
	}
	public int getiNumber() {
		return iNumber;
	}
	public void setiNumber(int iNumber) {
		this.iNumber = iNumber;
	}

	public int getiTanka() {
		return iTanka;
	}

	public void setiTanka(int iTanka) {
		this.iTanka = iTanka;
	}

}
