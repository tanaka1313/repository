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
import example.app.form.ProperSimuInputForm;
import example.app.service.CreateMapService;
import example.app.service.CreateProperAnswerLogicService;
import example.app.service.ProperDataService;
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
import java.util.concurrent.TimeUnit;

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
import org.springframework.web.bind.annotation.RestController;

@Controller
public class ProperController {
	
	int targetLv = 170;
	
	int maxLv = 180;
	
	int senzaiMax = 80;
	
	@Autowired
	ProperDataService properDataService;
	
	@Autowired
	CreateMapService createMapService;
	
	@Autowired
	CreateProperAnswerLogicService createProperAnswerLogicService;

	@RequestMapping("/ProperLogic")
	public String viewInput(Model model) {
		return "properLogic";
	}
	
	@RequestMapping(path = "/BukiProper", method = RequestMethod.GET)
	public String bukiProper(Model model) {
		ProperInputForm properInputForm = new ProperInputForm();
		// フォーム初期値の設定
		// パラメータレベル
		properInputForm.setParamLevel(targetLv);
		properInputForm.setShokiSenzai(60);
		properInputForm.setKisoSenzai(15);
		properInputForm.setProperBui("武器");
		// 選択肢の取得
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String, String> minusProperNameMap = properDataService.getMinusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String, String> properLvMap = createMapService.getCustomAddMaxMap(properInputForm.getParamLevel() / 10, 1, 0);
		
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("minusItems", minusProperNameMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		ProperSimuInputForm simulator = new ProperSimuInputForm();
		model.addAttribute("simulator", simulator);
		
		return "properCalc";
	}
	
	@RequestMapping(path = "/BouguProper", method = RequestMethod.GET)
	public String bougProper(Model model) {
		ProperInputForm properInputForm = new ProperInputForm();
		// フォーム初期値の設定
		// パラメータレベル
		properInputForm.setParamLevel(targetLv);
		properInputForm.setShokiSenzai(60);
		properInputForm.setKisoSenzai(15);
		properInputForm.setProperBui("体装備");
		// 選択肢の取得
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String, String> minusProperNameMap = properDataService.getMinusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String, String> properLvMap = createMapService.getCustomAddMaxMap(properInputForm.getParamLevel() / 10, 1, 0);
		
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("minusItems", minusProperNameMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		ProperSimuInputForm simulator = new ProperSimuInputForm();
		model.addAttribute("simulator", simulator);
		
		return "properCalc";
	}
	
	// レベル更新押下時の処理
    @RequestMapping(path = {"/BukiProper" , "/BouguProper"} , params="lebelUpdate", method = RequestMethod.POST)
    public String levelUpdatePost(@ModelAttribute ProperInputForm properInputForm, Model model) {
    	
		// パラメータレベルのみ保持
		ProperInputForm properInputFormNew = new ProperInputForm();
		// パラメータレベル、初期潜在値、基礎潜在値のみ保持
		properInputFormNew.setParamLevel(properInputForm.getParamLevel());
		properInputFormNew.setShokiSenzai(properInputForm.getShokiSenzai());
		properInputFormNew.setKisoSenzai(properInputForm.getKisoSenzai());
		properInputFormNew.setProperBui(properInputForm.getProperBui());
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String, String> minusProperNameMap = properDataService.getMinusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String, String> properLvMap = createMapService.getCustomAddMaxMap(properInputForm.getParamLevel() / 10, 1, 0);
    	
		model.addAttribute("inputForm", properInputFormNew);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("minusItems", minusProperNameMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		ProperSimuInputForm simulator = new ProperSimuInputForm();
		model.addAttribute("simulator", simulator);

        return "properCalc";
    }

    @RequestMapping(path = {"/BukiProper" , "/BouguProper"} , params="sendData", method = RequestMethod.POST)
    public String calcurateData(@ModelAttribute ProperInputForm properInputForm, Model model) {
    	
		// 選択肢の更新
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(maxLv, 10, 100);
		Map<Integer, Integer> senzaiMap = createMapService.getCustomMap(senzaiMax, 1, 1);
		Map<String, String> minusProperNameMap = properDataService.getMinusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String,String> plusProperNameMap = properDataService.getPlusProperNameMap(properInputForm.getParamLevel(), properInputForm.getProperBui());
		Map<String, String> properLvMap = createMapService.getCustomAddMaxMap(properInputForm.getParamLevel() / 10, 1, 0);
    	
		model.addAttribute("inputForm", properInputForm);
		model.addAttribute("levelItems", levelMap);
		model.addAttribute("senzaiItems", senzaiMap);
		model.addAttribute("minusItems", minusProperNameMap);
		model.addAttribute("plusItems", plusProperNameMap);
		model.addAttribute("properLvItems", properLvMap);
		
    	// 処理負荷軽減処理
    	try {
    		TimeUnit.MILLISECONDS.sleep(500);
    	}catch(Exception e) {
    		
    	}
    	
		// 使用するロジック
    	ProperOutputForm properOutputForm = createProperAnswerLogicService.getProperAnswer(properInputForm);
		
    	// エラー発生時のフォームの作成
    	if(!"".equals(properOutputForm.getErrMsg())) {
    		model.addAttribute("outputForm", properOutputForm);
    		ProperSimuInputForm simulator = new ProperSimuInputForm();
    		model.addAttribute("simulator", simulator);
    		return "properCalc";
    	}
    	
    	// シミュレーター連結用の情報を分離
		ProperSimuInputForm simulator = properOutputForm.getProperSimuInputForm();
    	
		model.addAttribute("outputForm", properOutputForm);
		model.addAttribute("simulator", simulator);

        return "properCalc";
    }
    
}
