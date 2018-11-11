package example.app.controller;

import example.app.common.ProperHuyoComparator;
import example.app.common.ProperStatusComparator;
import example.app.dao.ProperBaseDataDao;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperHuyoAnswerDto;
import example.app.dto.ProperHuyoDto;
import example.app.dto.ProperStatusDto;
import example.app.form.ProperDataForm;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuDataForm;
import example.app.form.ProperSimuInputForm;
import example.app.service.CreateMapService;
import example.app.service.ProperDataService;
import example.app.service.ProperSimuHelpService;
import example.app.service.CreateProperHuyoLogicService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ProperSimuController {
	
	int targetLv = 170;
	
	int maxLv = 180;
	
	int senzaiMax = 80;
	
	@Autowired
	ProperDataService properDataService;

	@Autowired
	CreateMapService createMapService;
	
	@Autowired
	ProperSimuHelpService properSimuHelpService;
	
	@RequestMapping(path = "/ProperSimulator", method = RequestMethod.GET)
	public String properSimurator(Model model) {
		ProperSimuInputForm properInputForm = new ProperSimuInputForm();
		// フォーム初期値の設定
		// パラメータレベル
		properInputForm.setParamLevel(targetLv);
		properInputForm.setShokiSenzai(60);
		properInputForm.setKisoSenzai(10);
		properInputForm.setProperBui("武器");
		// 選択肢の取得
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// ステップリストを初期化
		int i = 1;
		for(ProperDataForm proper : properInputForm.getProperList()) {
			if(i == 1) {
				proper.setProperName("最初に");
			}
			if(i == 2) {
				proper.setProperName("プロパを選んで");
			}
			if(i == 3) {
				proper.setProperName("プロパ決定ボタンを");
			}
			if(i == 4) {
				proper.setProperName("押下してください");
			}
			i++;
		}
		properInputForm.setProperStepList(new ArrayList());
		this.firstStepCreate(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		
		return "properSimulator";
	}

	// レベル更新押下時の処理
    @RequestMapping(path = {"/ProperSimulator"} , params="lebelUpdate", method = RequestMethod.POST)
    public String levelUpdatePost(@ModelAttribute ProperSimuInputForm properInputForm, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);

        return "properSimulator";
    }

    @RequestMapping(path = {"/ProperSimulator"} , params="setProper", method = RequestMethod.POST)
    public String calcurateData(@ModelAttribute ProperSimuInputForm properInputForm, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// エラーの確認
		ProperOutputForm properOutputForm = new ProperOutputForm();
		Map<String,Integer> countMap = new HashMap();
		for(ProperDataForm proper : properInputForm.getProperList()) {
			String properName = proper.getProperName();
			Integer num = countMap.get(properName);
			countMap.put(properName, num == null ? 1 : num + 1);
		}
		for(String properName : countMap.keySet()) {
			if(countMap.get(properName) > 1 && !"".equals(properName)) {
				properOutputForm.setErrMsg("【エラー】重複しているプロパがあります。");
			}
		}
		// データが存在することの確認
		for(ProperDataForm proper : properInputForm.getProperList()) {
			String properName = proper.getProperName();
			ProperStatusDto dto = properDataService.getProperStatusDto(properName, properInputForm.getParamLevel(), properInputForm.getProperBui());
			if(dto == null && !"".equals(properName)) {
				properOutputForm.setErrMsg("【エラー】" + properName + "は存在しないプロパです。");
			}
		}
		
		// ステップリストを初期化
		properInputForm.setProperStepList(new ArrayList());
		if(StringUtils.isEmpty(properOutputForm.getErrMsg())) {
			this.firstStepCreate(properInputForm);
		}
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		

        return "properSimulator";
    }

	// 後に処理追加押下時の処理
    @RequestMapping(path = {"/ProperSimulator"} , params="insertAfter", method = RequestMethod.POST)
    public String insertAfterPost(
    		@ModelAttribute ProperSimuInputForm properInputForm
    		, @RequestParam("insertAfter") Integer num
    		, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// 処理手順の追加
		ProperSimuDataForm simuForm = new ProperSimuDataForm(properInputForm.getProperStepList().get(num).getiAfterSenzai());
		List<ProperDataForm> initList = new ArrayList();
		for(ProperDataForm tempForm : properInputForm.getProperStepList().get(num).getProperList()) {
			ProperDataForm form = new ProperDataForm();
			form.setProperName(tempForm.getProperName());
			form.setProperLv(tempForm.getProperLv());
			initList.add(form);
		}
		
		simuForm.setProperList(initList);
		// まとめてフラグを設定
		simuForm.setMatometeStr(properInputForm.getProperStepList().get(num).getMatometeStr());
		
		// ステップリストに設定
		properInputForm.getProperStepList().add(num + 1, simuForm);
		
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properInputForm);
		// Outputを作成
		ProperOutputForm properOutputForm = properSimuHelpService.createOutput(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);

        return "properSimulator";
    }

	// 前に処理追加押下時の処理
    @RequestMapping(path = {"/ProperSimulator"} , params="insertBefore", method = RequestMethod.POST)
    public String insertBeforePost(
    		@ModelAttribute ProperSimuInputForm properInputForm
    		, @RequestParam("insertBefore") Integer num
    		, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// 処理手順の追加
		ProperSimuDataForm simuForm = new ProperSimuDataForm(properInputForm.getProperStepList().get(num).getiAfterSenzai());
		List<ProperDataForm> initList = new ArrayList();
		for(ProperDataForm tempForm : properInputForm.getProperStepList().get(num).getProperList()) {
			ProperDataForm form = new ProperDataForm();
			form.setProperName(tempForm.getProperName());
			form.setProperLv(tempForm.getProperLv());
			initList.add(form);
		}
		
		simuForm.setProperList(initList);
		// まとめてフラグを設定
		simuForm.setMatometeStr(properInputForm.getProperStepList().get(num).getMatometeStr());
		// ステップリストに設定
		properInputForm.getProperStepList().add(num, simuForm);
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properInputForm);
		// Outputを作成
		ProperOutputForm properOutputForm = properSimuHelpService.createOutput(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);

        return "properSimulator";
    }

	// この処理を削除押下時の処理
    @RequestMapping(path = {"/ProperSimulator"} , params="delete", method = RequestMethod.POST)
    public String deletePost(
    		@ModelAttribute ProperSimuInputForm properInputForm
    		, @RequestParam("delete") Integer num
    		, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// 処理手順の追加
		properInputForm.getProperStepList().remove((int)num);
		
		if(properInputForm.getProperStepList().size() == 0) {
			// ステップリストを初期化
			properInputForm.setProperStepList(new ArrayList());
			// プロパ手順画面を初期値として１つ追加
			ProperSimuDataForm simuForm = new ProperSimuDataForm(properInputForm.getShokiSenzai());
			List<ProperDataForm> initList = new ArrayList();
			List<ProperDataForm> properList = properInputForm.getProperList();
			for(ProperDataForm proper : properList) {
				if(!StringUtils.isEmpty(proper.getProperName())) {
					ProperDataForm properData = new ProperDataForm();
					properData.setProperName(proper.getProperName());
					properData.setProperLv(0);
					properData.setvProperSabun("+0");
					initList.add(properData);
				}
			}
			
			simuForm.setProperList(initList);
			// ステップリストに設定
			properInputForm.getProperStepList().add(simuForm);
		}
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properInputForm);
		// Outputを作成
		ProperOutputForm properOutputForm = properSimuHelpService.createOutput(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);

        return "properSimulator";
    }
    
	// 計算押下時の処理
    @RequestMapping(path = {"/ProperSimulator"} , params="calcurate", method = RequestMethod.POST)
    public String calcuratePost(
    		@ModelAttribute ProperSimuInputForm properInputForm
    		, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<Integer, Integer> properLvMap = createMapService.getCustomMap(properInputForm.getParamLevel() / 10, 1, -1 * properInputForm.getParamLevel() / 10);
		Map<String,String>properBuiMap = createMapService.getProperBuiMap();
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properInputForm);
		// Outputを作成
		ProperOutputForm properOutputForm = properSimuHelpService.createOutput(properInputForm);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("buiItems", properBuiMap);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);

        return "properSimulator";
    }
    
	// プロパ手順画面を初期値として１つ追加
    private void firstStepCreate(ProperSimuInputForm properInputForm) {
    	ProperSimuDataForm simuForm = new ProperSimuDataForm(properInputForm.getShokiSenzai());
    	List<ProperDataForm> initList = new ArrayList();
    	List<ProperDataForm> properList = properInputForm.getProperList();
    	for(ProperDataForm proper : properList) {
    		if(!StringUtils.isEmpty(proper.getProperName())) {
    			ProperDataForm properData = new ProperDataForm();
    			properData.setProperName(proper.getProperName());
    			properData.setProperLv(0);
    			properData.setvProperSabun("+0");
    			initList.add(properData);
    		}
    	}
		
		simuForm.setProperList(initList);
		// ステップリストに設定
		properInputForm.getProperStepList().add(simuForm);
    }

    
}
