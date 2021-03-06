package example.app.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import example.app.dto.ProperStatusDto;
import example.app.form.ProperDataForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuDataForm;
import example.app.form.ProperSimuInputForm;
import example.app.service.ProperDataService;
import example.app.service.ProperSimuHelpService;

@Service
public class ProperSimuHelpServiceImpl implements ProperSimuHelpService {
	
	// 素材リスト
	String sozaiList[] = {"金属", "布地", "獣品", "木材", "薬品", "魔素"};
	
	@Autowired
	ProperDataService properDataService;

	@Override
	public void calcurateStep(ProperSimuInputForm properInputForm) {
    	// 手順のリスト
    	List<ProperSimuDataForm> stepList = properInputForm.getProperStepList();
    	// 単価情報の取得
    	Map<String, ProperStatusDto> tankaMap = new HashMap();
    	if(stepList.size() > 0) {
        	for(ProperDataForm form : stepList.get(0).getProperList()) {
        		// データの取得
        		ProperStatusDto data = properDataService.getProperStatusDto(
        				form.getProperName() , properInputForm.getParamLevel()
        				, properInputForm.getProperBui());
        		// Mapへ設定
        		if(data != null) {
            		tankaMap.put(form.getProperName(), data);
        		}
        	}
        	// データの精査
        	for(ProperSimuDataForm stepForm : stepList) {
            	// 削除用リスト
            	List<ProperDataForm> sakujoList = new ArrayList();
        		for(ProperDataForm proper : stepForm.getProperList()) {
        			if(!tankaMap.keySet().contains(proper.getProperName())) {
        				sakujoList.add(proper);
        			}
        		}
        		for(ProperDataForm proper : sakujoList) {
        			stepForm.getProperList().remove(proper);
        		}
        	}
        	
        	// 処理負荷軽減処理
        	try {
        		TimeUnit.MILLISECONDS.sleep(350);
        	}catch(Exception e) {
        		
        	}
        	
        	// 付与前データの設定(初回分)
        	List<ProperDataForm> shokiProper = new ArrayList();
        	for(ProperDataForm form : stepList.get(0).getProperList()) {
        		ProperDataForm temp = new ProperDataForm();
        		temp.setProperName(form.getProperName());
        		temp.setProperLv(0);
        		shokiProper.add(temp);
        	}
        	stepList.get(0).setProperBeforeList(shokiProper);
        	// 付与前データの設定（初回以外）
        	int length = stepList.size();
        	for(int i = 1; i < length; i++) {
        		stepList.get(i).setProperBeforeList(stepList.get(i-1).getProperList());
        	}
        	
        	// 消費潜在の計算、および、付与前の潜在の設定、付与後の潜在の設定、ペナルティの計算
        	int beforeSenzai = properInputForm.getShokiSenzai();
        	Map<String, Integer> beforeCountMap = new HashMap();
        	int beforePenalty = 0;
        	for(ProperSimuDataForm stepForm : stepList) {
            	// まとめてフラグ（"matomete"の場合にまとめて付与、それ以外は１ずつ付与）
            	String matometeStr = stepForm.getMatometeStr();
            	// ペナルティ
            	int penalty = 0;
            	// 使用有無のマップ
            	Map<String,Integer> nowCountMap = new HashMap();
            	// エラーリストの初期化
            	stepForm.setWarnStrList(new ArrayList());
            	// ペナルティの計算
            	Map<String,Integer> reptMap = new HashMap();
            	for(ProperDataForm form : stepForm.getProperList()) {
            		if(form.getProperLv() != 0 || beforeCountMap.get(form.getProperName()) != null) {
            			String groupName = tankaMap.get(form.getProperName()).getvProperGroupName();
            			Integer count = reptMap.get(groupName);
            			reptMap.put(groupName, count == null ? 1 : count + 1);
            			nowCountMap.put(form.getProperName(), 1);
            		}
            	}
            	// ペナルティの計算
            	for(String gourpName : reptMap.keySet()) {
            		int reptNum = reptMap.get(gourpName);
            		if(reptNum > 1) {
            			penalty += reptNum * reptNum * 5;
            		}
            	}
            	// ペナルティを設定
            	stepForm.setvPenalty(String.valueOf(penalty));
            	// 素材ポイントの計算
            	stepForm.setSozaiMap(new HashMap());
            	for(int i = 0; i < stepForm.getProperList().size(); i++) {
            		String sozaiName = tankaMap.get(stepForm.getProperList().get(i).getProperName()).getvProperSozaiName();
            		double sozaiTanka = tankaMap.get(stepForm.getProperList().get(i).getProperName()).getdProperSozaiTanka();
            		Integer nowSozai = stepForm.getSozaiMap().get(sozaiName);
            		int start = stepForm.getProperBeforeList().get(i).getProperLv();
            		int end = stepForm.getProperList().get(i).getProperLv();
            		int needSozai = this.calcurateSozaiPt(start, end, sozaiTanka);
            		int afterSozai = (nowSozai == null ? needSozai : nowSozai + needSozai);
            		stepForm.getSozaiMap().put(sozaiName, afterSozai);
            	}
            	// 素材ポイントの設定
            	StringBuilder sozaiBuilder = new StringBuilder();
            	for(String sozaiName : sozaiList) {
            		Integer sozaiPt = stepForm.getSozaiMap().get(sozaiName);
            		sozaiPt = (sozaiPt == null ? 0 : sozaiPt);
            		sozaiBuilder.append("," + sozaiName + ":");
            		sozaiBuilder.append( sozaiPt + "pt");
            	}
            	sozaiBuilder.delete(0, 1);
            	stepForm.setvSozaiData(sozaiBuilder.toString());
            	
        		// 付与前の潜在の設定
        		stepForm.setiBeforeSenzai(beforeSenzai);
        		// 消費潜在の計算、および、差分の計算
        		double shohiSenzai = 0;
        		double modoriSenzai = 0;
        		Boolean errorFlg = false;
        		int countSabun = 0;
        		for(int i = 0; i < stepForm.getProperList().size(); i++) {
        			// 差分の計算
        			int sabun = stepForm.getProperList().get(i).getProperLv() 
        					- stepForm.getProperBeforeList().get(i).getProperLv();
        			// 単価の取得
    				int tanka = tankaMap.get(stepForm.getProperList().get(i).getProperName()).getiProperSenzaiTanka();
    				double dTanka = tanka * (((double) 100 + penalty) / (double)100);
        			// 消費潜在の加算、および、差分の設定
        			if(sabun >= 0) {
        				// まとめてフラグにより潜在の計算を処理
        				if("matomete".equals(matometeStr)) {
        					// まとめて付与時
            				shohiSenzai += sabun * dTanka;
        				}else {
        					// 1ずつ付与時
            				shohiSenzai += sabun * Math.floor(0.00000000000009 + dTanka);
        				}
        				stepForm.getProperList().get(i).setvProperSabun("+" + sabun);
        			}else {
        				// まとめてフラグにより潜在の計算を処理
        				if("matomete".equals(matometeStr)) {
        					// まとめて付与時
            				modoriSenzai -= ((double)sabun) * dTanka * 0.3;
        				}else {
        					// 1ずつ付与時
            				modoriSenzai -= ((double)sabun) * Math.floor(0.00000000000009 + dTanka * 0.3);
        				}
        				stepForm.getProperList().get(i).setvProperSabun(String.valueOf(sabun));
        			}
        			// 以下の場合はエラーとする。
        			// 1.1ずつ付与である
        			// 2.ペナルティが前回分と今回分で等しくない
        			// 3.前回分に含まれないものが2個以上ある
        			if(!"matomete".equals(matometeStr)) {
        				if(sabun != 0) {
            					countSabun++;
        				}
        			}
        		}
        		if(countSabun >= 2 && penalty != beforePenalty) {
        			errorFlg = true;
        		}
        		// エラーフラグが立っている場合はエラーメッセージを出力
        		if(errorFlg == true) {
        			String errorStr = "【エラー】1ずつ付与するため付与する順番により消費潜在が変わり、正しく計算が行えません。";
        			stepForm.getWarnStrList().add(errorStr);
        		}
    			// すでに6個付与している場合はエラーを発生
    			int properNum = 0;
    			for(ProperDataForm proper :stepForm.getProperBeforeList()) {
    				if(beforeCountMap.get(proper.getProperName()) != null) {
    					properNum++;
    				}
    			}
    			if(properNum >= 6) {
    				String errorStr = "【エラー】すでに付与済プロパが上限の6個を超えています。";
    				stepForm.getWarnStrList().add(errorStr);
    			}

        		// 消費潜在、付与後潜在の設定
    			if(shohiSenzai >= modoriSenzai) {
    				shohiSenzai = Math.floor(0.00000000000009 + shohiSenzai - modoriSenzai);
    			}else {
    				shohiSenzai = Math.floor(0.00000000000009 + modoriSenzai - shohiSenzai) * -1;
    			}
        		int iShohiSenzai = (int)Math.floor(0.00000000000009 + shohiSenzai);
        		
        		stepForm.setvUseSenzai(String.valueOf(iShohiSenzai));
        		stepForm.setiAfterSenzai(stepForm.getiBeforeSenzai() - iShohiSenzai);
        		// 残潜在を保存
            	beforeSenzai = stepForm.getiAfterSenzai();
            	// 使用有無のマップを次のものに更新
            	beforeCountMap = nowCountMap;
            	beforePenalty = penalty;
        	}
        	
        	// 処理負荷軽減処理
        	try {
        		TimeUnit.MILLISECONDS.sleep(350);
        	}catch(Exception e) {
        		
        	}
        	
    		int i = stepList.size();
        	// 成功率の計算
        	for(ProperSimuDataForm stepForm : stepList) {
        		// 成功率
        		Double seikouRate = 0.0;
            	// まとめてフラグ（"matomete"の場合にまとめて付与、それ以外は１ずつ付与）
            	String matometeStr = stepForm.getMatometeStr();
            	// 成功率の計算
        		if(stepForm.getiAfterSenzai() > 0 && i != 1) {
        			seikouRate = 100.0;
        		}else {
        			if("matomete".equals(matometeStr) && stepForm.getiBeforeSenzai() > 0) {
        				int bunbo = (stepForm.getiBeforeSenzai() > properInputForm.getKisoSenzai() ? 
        						stepForm.getiBeforeSenzai() : properInputForm.getKisoSenzai());
        				seikouRate = 130 + 230 * ((double) stepForm.getiAfterSenzai() / (double)bunbo);
        			}else {
        				seikouRate = null;
        			}
        		}
    			stepForm.setdSeikouRate(seikouRate);
    			// 成功率nullの場合はエラーなのでエラーメッセージを出力
    			if(stepForm.getdSeikouRate() == null) {
    				if(stepForm.getiBeforeSenzai() <= 0) {
    					String errorStr = "【エラー】すでに潜在値が0以下のため、これ以上強化できません。";
    					stepForm.getWarnStrList().add(errorStr);
    				}
    				if(!"matomete".equals(matometeStr) && stepForm.getiAfterSenzai() < 0) {
    					String errorStr = "【エラー】付与後潜在がマイナスとなる強化はまとめて付与を選択してください。";
    					stepForm.getWarnStrList().add(errorStr);
    				}
    			}
    			// レベル上限を超えて付与している場合はエラーを出力
    			for(ProperDataForm proper : stepForm.getProperList()) {
    				int maxLv = tankaMap.get(proper.getProperName()).getiProperMaxLevel();
    				if(proper.getProperLv() > maxLv || proper.getProperLv() < maxLv * -1) {
    					String errorStr = "【エラー】プロパ " + proper.getProperName() + "のレベルが範囲外です。";
    					stepForm.getWarnStrList().add(errorStr);
    				}
    			}
    			i --;
        	}
    	}
    	
	}

	@Override
	public int calcurateSozaiPt(int start, int end, double tanka) {
    	int point = 0;
    	if(start <= end) {
        	for(int i = start + 1; i <= end; i++) {
        		point += Math.floor(0.00000000000009 +  i * i * tanka );
        	}
    	}else {
        	for(int i = end ; i < start; i++) {
        		point += Math.floor(0.00000000000009 +  i * i * tanka );
        	}
    	}

    	return point;
	}

	@Override
	public ProperOutputForm createOutput(ProperSimuInputForm properInputForm) {
    	
    	ProperOutputForm properOutputForm = new ProperOutputForm();
    	List<ProperSimuDataForm> stepList = properInputForm.getProperStepList();
    	int length = stepList.size();
    	
    	// エラーメッセージの作成
    	List<String> errorList = new ArrayList();
    	for(ProperSimuDataForm form : stepList) {
    		errorList.addAll(form.getWarnStrList());
    	}
    	if(errorList.size() > 0) {
    		String errorStr = "【エラー】上記手順にエラーがあります。";
    		properOutputForm.setErrMsg(errorStr);
    	}
    	if(length <= 0) {
    		String errorStr = "【エラー】手順を設定してください。";
    		properOutputForm.setErrMsg(errorStr);
    	}
    	
    	// エラーがない場合に以下の処理を実施
    	if(StringUtils.isEmpty(properOutputForm.getErrMsg())) {
        	// 成功率の計算
        	int seikouRate = (int)Math.floor(0.00000000000009 + stepList.get(length - 1).getdSeikouRate());
        	if(seikouRate < 0) {
        		seikouRate = 0;
        	}else if(seikouRate > 100) {
        		seikouRate = 100;
        	}
        	properOutputForm.setSeikouRate("成功率:" + seikouRate + "%");
        	double seikou = (double)seikouRate / (double)100;
        	properOutputForm.setSeikou(seikou);
        	// 付与手順の作成
        	List<String> huyoStep = new ArrayList();
        	for(ProperSimuDataForm form : stepList) {
        		huyoStep.add(form.toString());
        	}
        	properOutputForm.setHuyoStep(huyoStep);
        	// プラスプロパの作成
        	StringBuilder plusProper = new StringBuilder();
        	int plusNum = 0;
        	for(ProperDataForm proper : stepList.get(length - 1).getProperList()) {
        		if(proper.getProperLv() > 0) {
        			plusProper.append("," + proper.getProperName() + " Lv." + proper.getProperLv());
        			plusNum ++ ;
        		}
        	}
        	plusProper.delete(0, 1);
        	properOutputForm.setHuyoProperPlus(plusProper.toString());
        	properOutputForm.setPlusNum(plusNum);
        	double seikouAll = 1;
        	// プラスプロパの数だけ成功する確率
        	for(int i = 0; i < plusNum; i++) {
        		seikouAll *= seikou;
        	}
        	properOutputForm.setPlusSeikou(seikouAll * 100);
        	// マイナスプロパの作成
        	StringBuilder minusProper = new StringBuilder();
        	for(ProperDataForm proper : stepList.get(length - 1).getProperList()) {
        		if(proper.getProperLv() < 0) {
        			minusProper.append("," + proper.getProperName() + " Lv." + proper.getProperLv());
        		}
        	}
        	minusProper.delete(0, 1);
        	properOutputForm.setHuyoProperMinus(minusProper.toString());
        	// 素材ポイントの計算
        	Map<String, Integer> sozaiMap = new HashMap();
        	for(String sozaiName : sozaiList) {
        		sozaiMap.put(sozaiName, 0);
        	}
        	for(ProperSimuDataForm stepForm : stepList) {
        		for(String sozaiName : stepForm.getSozaiMap().keySet()) {
        			Integer sozaiPt = stepForm.getSozaiMap().get(sozaiName);
        			Integer sozaiNow = sozaiMap.get(sozaiName);
        			sozaiPt = (sozaiPt == null ? 0 : sozaiPt);
        			sozaiNow = (sozaiNow == null ? 0 : sozaiNow);
        			sozaiMap.put(sozaiName, sozaiPt + sozaiNow);
        		}
        	}
        	StringBuilder sozaiData = new StringBuilder();
        	for(String sozaiName : sozaiList) {
        		sozaiData.append("," + sozaiName + ":");
        		sozaiData.append(sozaiMap.get(sozaiName) + "pt");
        	}
        	sozaiData.delete(0, 1);
        	properOutputForm.setSozaiPt(sozaiData.toString());
        	// 初期潜在の設定
        	properOutputForm.setSyokiSenzai("(初期潜在:" + properInputForm.getShokiSenzai() +"pt)");
    	}
    	return properOutputForm;
	}
	
}
