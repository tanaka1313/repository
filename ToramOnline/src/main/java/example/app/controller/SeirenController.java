package example.app.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.util.SystemPropertyUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import example.app.EchoForm;
import example.app.dao.SeirenDataDao;
import example.app.dao.SeirenSeigenDao;
import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.dto.SeirenItemDto;
import example.app.dto.SeirenRekkaDto;
import example.app.dto.SeirenTankaDto;
import example.app.form.ProperInputForm;
import example.app.form.SeirenInputForm;
import example.app.form.SeirenOutputForm;
import example.app.service.CreateMapService;
import example.app.service.CreateSeirenMapService;
import example.app.service.SeirenHelpService;

@Controller
public class SeirenController {
	
	@Autowired
	CreateMapService createMapService;
	
	@Autowired
	CreateSeirenMapService seirenMapService;
	
	@Autowired
	SeirenHelpService seirenHelpService;
	
	@Autowired
	SeirenSeigenDao seirenSeigenDao;
	
	@RequestMapping("/SeirenLogic")
	public String viewLogicInput(Model model) {
		return "seirenLogic";
	}
	
	@RequestMapping("/Seiren")
	public String viewInput(Model model) {
		SeirenInputForm seirenInputForm = new SeirenInputForm();
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);
		
    	// 確率の設定
		this.setNashiRate(seirenInputForm);
		// 使用アイテム設定
		this.setNashiItem(seirenInputForm);
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);
		
		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);
		
		return "seirenKitaiti";
	}
	
	// スロなしデフォルト適用押下時の処理
    @RequestMapping(path = "/Seiren" , params="setDefaultNashi", method = RequestMethod.POST)
    public String defaultNashiPost(@ModelAttribute SeirenInputForm seirenInputForm, Model model) {
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);
		
    	// 確率の設定
		this.setNashiRate(seirenInputForm);
		// 使用アイテム設定
		this.setNashiItem(seirenInputForm);
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);
		
    	
		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);

		return "seirenKitaiti";
    }

	// スロありデフォルト適用押下時の処理
    @RequestMapping(path = "/Seiren" , params="setDefaultAri", method = RequestMethod.POST)
    public String defaultAriPost(@ModelAttribute SeirenInputForm seirenInputForm, Model model) {
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);
		
    	// 確率の設定
		this.setAriRate(seirenInputForm);
		// 使用アイテム設定
		this.setNashiItem(seirenInputForm);
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);
		
    	
		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);

		return "seirenKitaiti";
    }
    
    // 単価情報更新ボタン押下時
    @RequestMapping(path = "/Seiren" , params="tankaData", method = RequestMethod.POST)
    public String tankaDataPost(@ModelAttribute SeirenInputForm seirenInputForm, Model model) {
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);
		
		
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);
		
		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);
		
		return "seirenKitaiti";

    }
    
	// 送信ボタン押下
    @RequestMapping(path = "/Seiren" , params="sendData", method = RequestMethod.POST)
    public String dataPost(@ModelAttribute SeirenInputForm seirenInputForm, Model model) {
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);
		
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);

		
		SeirenOutputForm seirenOutputForm = seirenHelpService.getSeirenAve(seirenInputForm);

		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("outputForm", seirenOutputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);
		
		return "seirenKitaiti";

    }
    
	// シミュレート送信ボタン押下
    @RequestMapping(path = "/Seiren" , params="sendSimu", method = RequestMethod.POST)
    public String dataSimuPost(@ModelAttribute SeirenInputForm seirenInputForm, Model model) {
		seirenInputForm.setParamItems(seirenMapService.getParamMap());
		seirenInputForm.setKousekiItems(seirenMapService.getKousekiMap());
		seirenInputForm.setHojoitemItems(seirenMapService.getHojoItemMap());
		Map<Integer, String> seirenItemsSyoki = createMapService.getCustomSeirenMap(14, 1, 0);
		Map<Integer, String> seirenItemsMokuhyo = createMapService.getCustomSeirenMap(15, 1, 1);

		
		// 単価情報設定
		this.setTankaInfo(seirenInputForm);

		SeirenOutputForm seirenOutputForm = seirenHelpService.getSeirenAve(seirenInputForm);
		int kitaiti = (int)Math.floor(0.00000000000009 + seirenOutputForm.getCustomUseMap().get("試行回数"));
		int maxNum = 999999;
		int total = 0;
		if(kitaiti > 2000_0000) {
			// 試行回数の期待値が2000万回を超える場合
			total = seirenSeigenDao.countSeigen(20000);
			maxNum = 0;
			seirenSeigenDao.insertSeigen(20000, kitaiti);
		}else if(kitaiti > 1000_0000) {
			// 試行回数の期待値が1000万回を超える場合
			total = seirenSeigenDao.countSeigen(10000);
			maxNum = 1;
			seirenSeigenDao.insertSeigen(10000, kitaiti);
		}else if(kitaiti > 500_0000) {
			// 試行回数の期待値が500万回を超える場合
			total = seirenSeigenDao.countSeigen(5000);
			maxNum = 2;
			seirenSeigenDao.insertSeigen(5000, kitaiti);
		}else if(kitaiti > 100_0000) {
			// 試行回数の期待値が100万回を超える場合
			total = seirenSeigenDao.countSeigen(1000);
			maxNum = 3;
			seirenSeigenDao.insertSeigen(1000, kitaiti);
		}else if(kitaiti > 10_0000) {
			// 試行回数の期待値が10万回を超える場合
			total = seirenSeigenDao.countSeigen(100);
			maxNum = 30;
			seirenSeigenDao.insertSeigen(100, kitaiti);
		}else if(kitaiti > 5_0000) {
			// 試行回数の期待値が5万回を超える場合
			total = seirenSeigenDao.countSeigen(50);
			maxNum = 60;
			seirenSeigenDao.insertSeigen(50, kitaiti);
		}else if(kitaiti > 1_0000) {
			// 試行回数の期待値が1万回を超える場合
			total = seirenSeigenDao.countSeigen(10);
			maxNum = 300;
			seirenSeigenDao.insertSeigen(10, kitaiti);
		}
		if(total <= maxNum) {
			seirenOutputForm = seirenHelpService.getSeirenSimu(seirenInputForm, seirenOutputForm);
		}else if(maxNum != 0){
			// 失敗時はエラーメッセージを出力
			seirenOutputForm.setErrMessage("負荷の高い処理は１日に行える回数を制限しております。お手数をおかけしますが、より負荷の少ない手順に変更するか、後ほどお試しください。");
		}else {
			seirenOutputForm.setErrMessage("負荷が高すぎるため、実行できないように制限しております。");
		}

		model.addAttribute("inputForm", seirenInputForm);
		model.addAttribute("outputForm", seirenOutputForm);
		model.addAttribute("seirenItemsSyoki", seirenItemsSyoki);
		model.addAttribute("seirenItemsMokuhyo", seirenItemsMokuhyo);
		
		return "seirenSimulator";

    }
    
	// スロなしの確率を設定
	private void setNashiRate(SeirenInputForm seirenInputForm) {
		seirenInputForm.getTecRekka().setvRekkaNashi(String.valueOf((double) 600 / 12));
		seirenInputForm.getTecRekka().setvRekka1(String.valueOf((double) 600 / 12));
		seirenInputForm.getTecRekka().setvRekka2(String.valueOf((double) 0));
		seirenInputForm.getTecRekka().setvRekka3(String.valueOf((double) 0));
		seirenInputForm.getLucRekka().setvRekkaNashi(String.valueOf((double) (600 + 7500) / (12 + 75)));
		seirenInputForm.getLucRekka().setvRekka1(String.valueOf((double) 600 / (12 + 75)));
		seirenInputForm.getLucRekka().setvRekka2(String.valueOf((double) 0));
		seirenInputForm.getLucRekka().setvRekka3(String.valueOf((double) 0));
	}

	// スロありの確率を設定
	private void setAriRate(SeirenInputForm seirenInputForm) {
		seirenInputForm.getTecRekka().setvRekkaNashi(String.valueOf((double) 400 / 12));
		seirenInputForm.getTecRekka().setvRekka1(String.valueOf((double) 400 / 12));
		seirenInputForm.getTecRekka().setvRekka2(String.valueOf((double) 400 / 12));
		seirenInputForm.getTecRekka().setvRekka3(String.valueOf((double) 0));
		seirenInputForm.getLucRekka().setvRekkaNashi(String.valueOf((double) (400 + 7500) / (12 + 75)));
		seirenInputForm.getLucRekka().setvRekka1(String.valueOf((double) 400 / (12 + 75)));
		seirenInputForm.getLucRekka().setvRekka2(String.valueOf((double) 400 / (12 + 75)));
		seirenInputForm.getLucRekka().setvRekka3(String.valueOf((double) 0));
	}
	
	// 使用アイテムを設定
	private void setNashiItem(SeirenInputForm seirenInputForm) {
		for(int i = 0; i < 15; i++) {
	    	// パラ設定
			if(i > 12) {
				seirenInputForm.getSeirenItems().get(i).setvParamName("LUK極");
			}else {
				seirenInputForm.getSeirenItems().get(i).setvParamName("TEC極");
			}
			// 鉱石設定
			if(i > 12) {
				seirenInputForm.getSeirenItems().get(i).setvUseKouseki("赤鉄鋼");
			}else if(i > 9){
				seirenInputForm.getSeirenItems().get(i).setvUseKouseki("オリハルコン");
			}else if(i > 7){
				seirenInputForm.getSeirenItems().get(i).setvUseKouseki("ミスリル鉱石");
			}else if(i > -1){
				seirenInputForm.getSeirenItems().get(i).setvUseKouseki("ダマスカ鉱石");
			}else {
				seirenInputForm.getSeirenItems().get(i).setvUseKouseki("ダマスカ鉱石");
			}
			// 補助アイテム設定
			if(i > 8) {
				seirenInputForm.getSeirenItems().get(i).setvUseHojoItem("劣化防止剤");
			}else {
				seirenInputForm.getSeirenItems().get(i).setvUseHojoItem("なし");
			}
			
		}
	}
	
	// 単価情報の設定
	private void setTankaInfo(SeirenInputForm seirenInputForm) {
		
		List<SeirenItemDto> itemList = seirenInputForm.getSeirenItems();
		// 保存用データ
		Map<Integer,Integer> hozonTemp = seirenInputForm.getHozonTankaItems();
		// 単価情報の更新
		for(SeirenTankaDto dto : seirenInputForm.getTankaItems()) {
			hozonTemp.put(dto.getvItemName().hashCode(), dto.getiTanka());
		}
		seirenInputForm.setTankaItems(new ArrayList());
		List<SeirenTankaDto> tankaList = seirenInputForm.getTankaItems();
		List<String> tempCheckList = new ArrayList();
		
		// 鉱石の情報取得
		for(SeirenItemDto dto : itemList) {
			if(!tempCheckList.contains(dto.getvUseKouseki())) {
				String itemName = dto.getvUseKouseki();
				Integer tanka = hozonTemp.get(itemName.hashCode());
				if(tanka == null) {
					// なぜか初回のみハッシュ値139の差が出る
					tanka = hozonTemp.get(itemName.hashCode() + 139);
				}
				tanka = (tanka == null ? 0 : tanka);
				tankaList.add(new SeirenTankaDto(itemName, tanka));
				tempCheckList.add(dto.getvUseKouseki());
			}
		}
		
		// 補助アイテムの情報取得
		for(SeirenItemDto dto : itemList) {
			if(!(tempCheckList.contains(dto.getvUseHojoItem()) || "なし".equals(dto.getvUseHojoItem()))) {
				String itemName = dto.getvUseHojoItem();
				Integer tanka = hozonTemp.get(itemName.hashCode());
				if(tanka == null) {
					// なぜか初回のみハッシュ値139の差が出る
					tanka = hozonTemp.get(itemName.hashCode() + 139);
				}
				tanka = (tanka == null ? 0 : tanka);
				tankaList.add(new SeirenTankaDto(itemName, tanka));
				tempCheckList.add(dto.getvUseHojoItem());
			}
		}
		
	}


}
