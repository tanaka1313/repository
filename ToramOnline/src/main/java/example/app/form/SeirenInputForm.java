package example.app.form;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import example.app.dto.SeirenItemDto;
import example.app.dto.SeirenRekkaDto;
import example.app.dto.SeirenTankaDto;

public class SeirenInputForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// TEC極 情報の設定
	private SeirenRekkaDto tecRekka;

	// LUC極 情報の設定
	private SeirenRekkaDto lucRekka;
	
	// 精錬データ
	private List<SeirenItemDto> seirenItems = new ArrayList();
	
	// 単価情報
	private List<SeirenTankaDto> tankaItems = new ArrayList();
	
	// パラメータMAP
	private Map<String,String> paramItems;
	
	// 鉱石MAP
	private Map<String,String> kousekiItems;
	
	// 補助アイテムMAP
	private Map<String,String> hojoitemItems;
	
	// 情報保存用単価マップ
	private Map<Integer,Integer> hozonTankaItems;
	
	// 精錬初期値
	private Integer syokiSeiren = 0;
	
	// 精錬目標値
	private Integer mokuhyoSeiren = 15;
	
	// コンストラクタ
	public SeirenInputForm() {
		this.lucRekka = new SeirenRekkaDto("LUK極");
		this.tecRekka = new SeirenRekkaDto("TEC極");
		
		for(int i = 0; i < 15; i++) {
			seirenItems.add(new SeirenItemDto(i));
		}
		this.setHozonTankaItems(new HashMap());
		tankaItems = new ArrayList();
	}

	public List<SeirenItemDto> getSeirenItems() {
		return seirenItems;
	}

	public void setSeirenItems(List<SeirenItemDto> seirenItems) {
		this.seirenItems = seirenItems;
	}

	public SeirenRekkaDto getTecRekka() {
		return tecRekka;
	}

	public void setTecRekka(SeirenRekkaDto tecRekka) {
		this.tecRekka = tecRekka;
	}

	public SeirenRekkaDto getLucRekka() {
		return lucRekka;
	}

	public void setLucRekka(SeirenRekkaDto lucRekka) {
		this.lucRekka = lucRekka;
	}

	public Map<String,String> getParamItems() {
		return paramItems;
	}

	public void setParamItems(Map<String,String> paramItems) {
		this.paramItems = paramItems;
	}

	public Map<String,String> getKousekiItems() {
		return kousekiItems;
	}

	public void setKousekiItems(Map<String,String> kousekiItems) {
		this.kousekiItems = kousekiItems;
	}

	public Map<String,String> getHojoitemItems() {
		return hojoitemItems;
	}

	public void setHojoitemItems(Map<String,String> hojoitemItems) {
		this.hojoitemItems = hojoitemItems;
	}

	public List<SeirenTankaDto> getTankaItems() {
		return tankaItems;
	}

	public void setTankaItems(List<SeirenTankaDto> kakakuItems) {
		this.tankaItems = kakakuItems;
	}

	public Map<Integer,Integer> getHozonTankaItems() {
		return hozonTankaItems;
	}

	public void setHozonTankaItems(Map<Integer,Integer> hozonTankaItems) {
		this.hozonTankaItems = hozonTankaItems;
	}

	public Integer getSyokiSeiren() {
		return syokiSeiren;
	}

	public void setSyokiSeiren(Integer syokiSeiren) {
		this.syokiSeiren = syokiSeiren;
	}

	public Integer getMokuhyoSeiren() {
		return mokuhyoSeiren;
	}

	public void setMokuhyoSeiren(Integer mokuhyoSeiren) {
		this.mokuhyoSeiren = mokuhyoSeiren;
	}


}
