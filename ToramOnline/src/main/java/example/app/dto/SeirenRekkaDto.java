package example.app.dto;

import java.io.Serializable;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.validation.constraints.Min;

import org.springframework.util.StringUtils;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

public class SeirenRekkaDto implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// パラ情報
	private String vParamName;
	
	// 劣化なし確率
	@Min(0)
	private Double dRekkaNashi;
	
	// 1劣化確率
	@Min(0)
	private Double dRekka1;
	
	// 2劣化確率
	@Min(0)
	private Double dRekka2;
	
	// 3劣化確率
	@Min(0)
	private Double dRekka3;
	
	// 劣化なし確率Str
	private String vRekkaNashi;
	
	// 1劣化確率Str
	private String vRekka1;
	
	// 2劣化確率Str
	private String vRekka2;
	
	// 3劣化確率Str
	private String vRekka3;
	
	public SeirenRekkaDto(String paramName) {
		this.setvParamName(paramName);
	}
	
	public void refresh() {
		
		// 総和
		double total = 0;
		
		// 値の設定
		this.dRekkaNashi = this.returnValue(this.vRekkaNashi);
		this.dRekka1 = this.returnValue(this.vRekka1);
		this.dRekka2 = this.returnValue(this.vRekka2);
		this.dRekka3 = this.returnValue(this.vRekka3);
		
		
		// 不正データの処理
		if(this.getdRekkaNashi() == null || this.getdRekkaNashi() < 0) {
			this.setdRekkaNashi((double) 0);
		}
		if(this.getdRekka1() == null || this.getdRekka1() < 0) {
			this.setdRekka1((double) 0);
		}
		if(this.getdRekka2() == null || this.getdRekka2() < 0) {
			this.setdRekka2((double) 0);
		}
		if(this.getdRekka3() == null || this.getdRekka3() < 0) {
			this.setdRekka3((double) 0);
		}
		
		total += this.getdRekkaNashi();
		total += this.getdRekka1();
		total += this.getdRekka2();
		total += this.getdRekka3();
		
		// totalが0の場合の対応
		if(total == 0) {
			this.setdRekkaNashi((double)1);
			this.setdRekka1((double)1);
			total = 2;
		}
		
		this.setdRekkaNashi(this.getdRekkaNashi() / total * 100);
		this.setdRekka1(this.getdRekka1() / total * 100);
		this.setdRekka2(this.getdRekka2() / total * 100);
		this.setdRekka3(this.getdRekka3() / total * 100);
		
		// 値の反映
		this.vRekkaNashi = String.valueOf(this.dRekkaNashi);
		this.vRekka1 = String.valueOf(this.dRekka1);
		this.vRekka2 = String.valueOf(this.dRekka2);
		this.vRekka3 = String.valueOf(this.dRekka3);
		
	}

	public String getvParamName() {
		return vParamName;
	}

	public void setvParamName(String vParamName) {
		this.vParamName = vParamName;
	}

	public Double getdRekkaNashi() {
		return dRekkaNashi;
	}

	public void setdRekkaNashi(Double dRekkaNashi) {
		this.dRekkaNashi = dRekkaNashi;
	}

	public Double getdRekka1() {
		return dRekka1;
	}

	public void setdRekka1(Double dRekka1) {
		this.dRekka1 = dRekka1;
	}

	public Double getdRekka2() {
		return dRekka2;
	}

	public void setdRekka2(Double dRekka2) {
		this.dRekka2 = dRekka2;
	}

	public Double getdRekka3() {
		return dRekka3;
	}

	public void setdRekka3(Double dRekka3) {
		this.dRekka3 = dRekka3;
	}

	public String getvRekkaNashi() {
		return vRekkaNashi;
	}

	public void setvRekkaNashi(String vRekkaNashi) {
		this.vRekkaNashi = vRekkaNashi;
	}

	public String getvRekka1() {
		return vRekka1;
	}

	public void setvRekka1(String vRekka1) {
		this.vRekka1 = vRekka1;
	}

	public String getvRekka2() {
		return vRekka2;
	}

	public void setvRekka2(String vRekka2) {
		this.vRekka2 = vRekka2;
	}

	public String getvRekka3() {
		return vRekka3;
	}

	public void setvRekka3(String vRekka3) {
		this.vRekka3 = vRekka3;
	}
	
	private double returnValue(String str){
		double result = (double)-1;
		if(str == null || "".equals(str)) {
			str = "0.0";
		}
		// サニタイズ
		str = StringUtils.delete(str, "<");
		str = StringUtils.delete(str, ">");
		str = StringUtils.delete(str, "&");
		str = StringUtils.delete(str, "\"");
		str = StringUtils.delete(str, "'");
		
		str = str + "/ 1.0";
		try {
			ScriptEngineManager factory = new ScriptEngineManager();
			ScriptEngine engine = factory.getEngineByName("JavaScript");
			result = (Double)engine.eval(str);
		} catch (ScriptException e) {
			result = 0;
		}
		
		return result;
	}

}