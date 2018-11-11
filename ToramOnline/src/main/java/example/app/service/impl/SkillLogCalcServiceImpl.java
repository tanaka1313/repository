package example.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import example.app.dao.SkillLogCalcDao;
import example.app.dto.SkillLogCalcDto;
import example.app.dto.SkillLogTypeCalcDto;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;
import example.app.service.SkillLogCalcService;

@Service
public class SkillLogCalcServiceImpl implements SkillLogCalcService {
	
	@Autowired
	SkillLogCalcDao skillLogCalcDao;

	@Override
	public void setSkillStatisticsData(List<SkillGroupDataForm> skillGroupList, List<String> statisticsBukiList) {
		
		// 全スキルデータを一列に変換したもの
		List<SkillDataForm> totalList = new ArrayList();
		
		// 各スキルの取得数 null→全武器種
		List<SkillLogCalcDto> skillCountList = skillLogCalcDao.selectLogCalc(statisticsBukiList);
		
		// 分母の取得
		List<SkillLogTypeCalcDto> skillTotalCountList = skillLogCalcDao.selectLogTypeCalc(statisticsBukiList);
		
		// totalListの作成
		for(SkillGroupDataForm groupData : skillGroupList) {
			// 初期考察データとしてグループ情報からスキル情報を取得
			List<SkillDataForm> skillDataList = groupData.getSkillDataList();
			while(skillDataList.size() > 0) {
				// 次回取得用のリスト格納用
				List<SkillDataForm> tempList = new ArrayList();
				for(SkillDataForm skillData : skillDataList) {
					totalList.add(skillData);
					if(skillData.getSkillDataList() != null) {
						// 次のデータがある場合は次回巡回用リストに追加
						tempList.addAll(skillData.getSkillDataList());
					}
				}
				// 次回巡回用リストを設定
				skillDataList = tempList;
			}
		}
		
		
		// 分母の設定
		double totalSkillData = 0;
		for(SkillLogTypeCalcDto dto : skillTotalCountList) {
			totalSkillData += dto.getiNumber();
		}
		if(totalSkillData == 0) {
			totalSkillData = 1000;
		}
		
		// Mapの作成
		Map<String, Integer> skillCountRateMap = new HashMap();
		for(SkillLogCalcDto dto : skillCountList) {
			int getRate = dto.getiNumber();
			skillCountRateMap.put(dto.getvSkillCode(), getRate);
		}
		
		// 取得率の設定
		for(SkillDataForm form : totalList) {
			Integer skillNum = skillCountRateMap.get(form.getSkillCode());
			Double getRate = null;
			if(statisticsBukiList != null) {
				if(skillNum == null) {skillNum = 0; }
				getRate = ((double) skillNum) / totalSkillData * 100;
			}
			form.setAcquisitionRate(getRate);
		}
		
		// レベル頻度の設定
		for(SkillDataForm form : totalList) {
			List<SkillLogCalcDto> skillCountPerLv = skillLogCalcDao.selectLogCalcPerLv(statisticsBukiList, form.getSkillCode());
			
			StringBuffer frequency = new StringBuffer(); 
			for(int i = 0; i < 2 ; i++ ) {
				if(skillCountPerLv.size() <= i || (int)(skillCountPerLv.get(i).getiNumber()) == 0) {
					break;
				}
				frequency.append(skillCountPerLv.get(i).getiSkillLv());
				frequency.append("(");
				Integer skillCount = skillCountRateMap.get(skillCountPerLv.get(i).getvSkillCode());
				if(skillCount == null || skillCount == 0) {
					skillCount = 1;
				}
				frequency.append((int)(Math.floor((double)skillCountPerLv.get(i).getiNumber() / (double)skillCount  * 100)));
				frequency.append("%)");
				
				if(frequency.length() <= 5) {
					frequency.append("　　");
				}else if(frequency.length() <= 6) {
					frequency.append("　 ");
				}else {
					frequency.append("　");
				}
				
			}
//			if(frequency.length() > 0) {
//				frequency.delete(frequency.length()-1, 1);
//			}
			
			form.setLvFrequency(frequency.toString());
		}

	}

}
