package example.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import example.app.EchoForm;
import example.app.dao.SkillLogDao;
import example.app.form.ProperInputForm;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;
import example.app.form.SkillInputForm;
import example.app.form.SkillOutputForm;
import example.app.service.CreateMapService;
import example.app.service.SkillDataService;
import example.app.service.SkillLogCalcService;

@Controller
public class SkillController {
	
	@Autowired
	SkillDataService skillDataService;
	
	@Autowired
	CreateMapService createMapService;
	
	@Autowired
	SkillLogCalcService skillLogCalcService;
	
	@RequestMapping("/Skill")
	public String viewSkillInput(Model model) {
		// 選択肢の作成
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(10, 1, 0);
		Map<String, String> statisticsBukiMap = skillDataService.getStatisticsBukiMap();
		Map<String, String> permitLogMap = createMapService.getPermitLogMap();
		// スキル情報の取得
		SkillInputForm skillInputForm = new SkillInputForm();
		skillInputForm.setSkillGroup(skillDataService.getSkillGroupData());
		
		model.addAttribute("inputForm", skillInputForm);
		model.addAttribute("lvItems", levelMap);
		model.addAttribute("statisticsBukiItems", statisticsBukiMap);
		model.addAttribute("permitLogItems", permitLogMap);
		
		return "skillCalc";
	}
	
	@RequestMapping("/SkillCode")
	public String viewSkillInputCode(Model model) {
		return "skillCode";
	}

	
	@RequestMapping(path = "/Skill", params= "sendData" , method = RequestMethod.POST)
	public String skillDataCalc(@ModelAttribute SkillInputForm skillInputForm, Model model) {
		
		// 選択肢の作成
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(10, 1, 0);
		Map<String, String> statisticsBukiMap = skillDataService.getStatisticsBukiMap();
		Map<String, String> permitLogMap = createMapService.getPermitLogMap();
		
		List<SkillGroupDataForm> skillGroup = skillInputForm.getSkillGroup();
		// 結果の取得（合計ポイント）
		int resultPt = 0;
		// 結果文字列の取得
		List<SkillDataForm> resultList = new ArrayList();
		
		// ログ取得の有無を選択
		Boolean isPermitLog = false;
		if(!"不許可".equals(skillInputForm.getPermitLog())) {
			isPermitLog = true;
		}
		
		// 計算の実行
		resultPt = skillDataService.calcurateSkillData(skillGroup, resultList, isPermitLog);
		
		// 実行結果の計算
		SkillOutputForm skillOutputForm = new SkillOutputForm();
		skillOutputForm.setSkillPt("必要スキルポイント： " + resultPt + " pt");
		skillOutputForm.setResultString(resultList);
		
		model.addAttribute("inputForm", skillInputForm);
		model.addAttribute("outputForm", skillOutputForm);
		model.addAttribute("lvItems", levelMap);
		model.addAttribute("statisticsBukiItems", statisticsBukiMap);
		model.addAttribute("permitLogItems", permitLogMap);
		
		
		return "skillCalc";
		
	}

	@RequestMapping(path = "/Skill", params= "statisticsSet" , method = RequestMethod.POST)
	public String statisticsCalc(@ModelAttribute SkillInputForm skillInputForm, Model model) {
		
		// 選択肢の作成
		Map<Integer, Integer> levelMap = createMapService.getCustomMap(10, 1, 0);
		Map<String, String> statisticsBukiMap = skillDataService.getStatisticsBukiMap();
		Map<String, String> permitLogMap = createMapService.getPermitLogMap();
		
		// 武器種の決定
		String statisticBukiStr = this.determineBuki(skillInputForm.getStatisticsBuki());
		
		List<String> statisticsBukiList = new ArrayList();
		
		String temp = statisticBukiStr;
		if("NONE".equals(statisticBukiStr)) {
			statisticsBukiList = null;
		}else if(!"ALL".equals(statisticBukiStr)){
			statisticsBukiList.add(statisticBukiStr);
		}
		
		
		List<SkillGroupDataForm> skillGroup = skillInputForm.getSkillGroup();
		// 結果の取得（合計ポイント）
		int resultPt = 0;
		// 結果文字列の取得
		List<SkillDataForm> resultList = new ArrayList();
		
		// 統計情報の更新
		skillLogCalcService.setSkillStatisticsData(skillGroup, statisticsBukiList);
		
		skillInputForm.setStatisticsBuki(statisticBukiStr);
		
		model.addAttribute("inputForm", skillInputForm);
		model.addAttribute("lvItems", levelMap);
		model.addAttribute("statisticsBukiItems", statisticsBukiMap);
		model.addAttribute("permitLogItems", permitLogMap);
		
		
		return "skillCalc";
		
	}
	
	private String determineBuki(String bukiMulti) {
		String returnStr = "";
		
		
		if(bukiMulti != null) {
			String[] splitStr = bukiMulti.split(",");
			Map<String, Integer> calcMap = new HashMap();
			for(String str : splitStr) {
				Integer num = calcMap.get(str);
				calcMap.put(str, num == null ? 1 : num + 1);
			}
			String tempStr = "";
			int num = 999;
			for(String str : calcMap.keySet()) {
				if(num > calcMap.get(str)) {
					tempStr = str;
					num = calcMap.get(str);
				}
			}
			returnStr = tempStr;
		}
		return returnStr;
	}


}
