package example.app.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.app.dao.ProperDataDao;
import example.app.dao.SkillDataDao;
import example.app.dao.SkillLogCalcDao;
import example.app.dao.SkillLogDao;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;
import example.app.dto.SkillLogTypeCalcDto;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;
import example.app.service.ProperDataService;
import example.app.service.SkillDataService;
import example.app.service.SkillLogCalcService;

@Service
public class SkillDataServiceImpl implements SkillDataService {
	
	@Autowired
	SkillDataDao skillDataDao;

	@Autowired
	SkillLogDao skillLogDao;
	
	@Autowired
	SkillLogCalcDao skillLogCalcDao;
	
	@Autowired
	SkillLogCalcService skillLogCalcService;
	
	@Override
	public List<SkillGroupDataForm> getSkillGroupData() {
		List<SkillGroupDataForm>skillGroupList = skillDataDao.getSkillGroupData();
		
		for(SkillGroupDataForm form: skillGroupList) {
			List<SkillDataForm>resultList = new ArrayList();
			
			String groupName = form.getSkillGroupName();
			List<SkillDataForm>skillList = skillDataDao.getSkillData(groupName);
			
			// 取得データをMAPへ変換
			Map<String, SkillDataForm> skillMap = new HashMap();
			for(SkillDataForm data : skillList) {
				data.setSkillDataList(new ArrayList());
				skillMap.put(data.getSkillCode(),data);
			}
			// スキルリストの取得
			for(SkillDataForm data : skillList) {
				// スキルコードの取得
				String skillCode = data.getSkillCode();
				// 桁数の取得
				int length = skillCode.length();
				if(length <= 2) {
					// 起点であるためresultListに直接加える
					resultList.add(data);
				}else {
					String zenteiCode = skillCode.substring(0, length - 1);
					// 起点以外の場合はデータを移動する
					SkillDataForm zentei = skillMap.get(zenteiCode);
					zentei.getSkillDataList().add(data);
				}
			}
			
			form.setSkillDataList(resultList);
		}
		
		// スキル統計情報の設定
		skillLogCalcService.setSkillStatisticsData(skillGroupList, null);
		
		return skillGroupList;
	}
	
	@Override
	public int calcurateSkillData(List<SkillGroupDataForm> skillGroup, List<SkillDataForm> resultList, Boolean judgeLog) {
		
		// 返却用データ
		int resultPt = 0;

		// Mapの作成
		Map<String, SkillDataForm> skillMap = new HashMap();
		// 全スキルデータを一列に変換したもの
		List<SkillDataForm> totalList = new ArrayList();

		
		// totalListの作成
		for(SkillGroupDataForm groupData : skillGroup) {
			// 初期考察データとしてグループ情報からスキル情報を取得
			List<SkillDataForm> skillDataList = groupData.getSkillDataList();
			while(skillDataList.size() > 0) {
				// 次回取得用のリスト格納用
				List<SkillDataForm> tempList = new ArrayList();
				for(SkillDataForm skillData : skillDataList) {
					// totalListに追加するデータを作成
					SkillDataForm newData = new SkillDataForm();
					newData.setSkillCode(skillData.getSkillCode());
					newData.setSkillDataList(new ArrayList());
					newData.setSkillGroupName(groupData.getSkillGroupName());
					newData.setSkillLv(skillData.getSkillLv());
					newData.setSkillName(skillData.getSkillName());
					totalList.add(newData);
					if(skillData.getSkillDataList() != null) {
						// 次のデータがある場合は次回巡回用リストに追加
						tempList.addAll(skillData.getSkillDataList());
					}
				}
				// 次回巡回用リストを設定
				skillDataList = tempList;
			}
		}
		
		// Mapの作成
		for(SkillDataForm data : totalList) {
			skillMap.put(data.getSkillCode(), data);
		}
		
		// 前提スキルのレベルが5未満の場合は5にする
		Collections.reverse(totalList);
		for(SkillDataForm data : totalList) {
			String skillCode = data.getSkillCode();
			int skillLv = (data.getSkillLv() == null ? 0 : data.getSkillLv());
			int length = skillCode.length();
			if(length <= 2 || skillLv == 0) {
				// 起点、もしくはスキルレベル0のため何もしない
			}else {
				String zenteiCode = skillCode.substring(0, length - 1);
				SkillDataForm zentei = skillMap.get(zenteiCode);
				int zenteiLv = (zentei.getSkillLv() == null ? 0 : zentei.getSkillLv());
				if(zenteiLv < 5) {
					zentei.setSkillLv(5);
				}
			}
		}
		Collections.reverse(totalList);
		
		// ログに使用するランダム値
		double randomNumber = Math.random();
		for(int i=0; i < 8; i++) {
			randomNumber *= 10;
		}
		String randNum = String.valueOf((int)Math.floor(randomNumber));
		
		// スキルポイントの計算、および、resultListの作成
		for(SkillDataForm skillData : totalList) {
			Integer pt = skillData.getSkillLv();
			pt = (pt == null ? 0 : pt);
			if(pt > 0) {
				resultPt += pt;
				
				String skillCode = skillData.getSkillCode();
				int length = skillCode.length();
				if(length <= 2) {
					resultList.add(skillData);
				}else {
					String zenteiCode = skillCode.substring(0,  length - 1);
					SkillDataForm zentei = skillMap.get(zenteiCode);
					zentei.getSkillDataList().add(skillData);
				}
				// ログの挿入
				if(judgeLog == true) {
					skillLogDao.insertSkillDetail(skillCode, skillData.getSkillGroupName(), randNum, pt, skillData.getSkillName());
				}

			}
		}
		
		// ログの挿入
		if(judgeLog == true) {
			skillLogDao.insertSkillLog(randNum, resultPt);
		}
		
		return resultPt;

	}
	
	public Map<String,String> getStatisticsBukiMap(){
		Map<String, String> returnMap = new LinkedHashMap();
		
		List<SkillLogTypeCalcDto> logTypeList = skillLogCalcDao.selectLogTypeCalc(null);
		int totalNum = 0;
		
		returnMap.put("NONE", "表示なし");
		for(SkillLogTypeCalcDto dto : logTypeList) {
			totalNum += dto.getiNumber();
		}
		returnMap.put("ALL", "全武器種(" + totalNum + ")");
		
		for(SkillLogTypeCalcDto dto : logTypeList) {
			String hyoji = dto.getvSkillTypeDetailName() + "(" + dto.getiNumber() + ")";
			returnMap.put(dto.getvSkillTypeDetailName(), hyoji);
		}
		
		return returnMap;
	}


	
}
