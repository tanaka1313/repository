package example.app.service.impl;

import example.app.dto.ProperHuyoAnswerDto;


import example.app.form.ProperInputForm;
import example.app.service.CreateProperHuyoLogicService;
import example.app.common.NameAndNumberComparator;
import example.app.common.ProperHuyoComparator;
import example.app.common.ProperStatusComparator;
import example.app.common.ProperStatusComparator2;
import example.app.common.ProperStatusComparator3;
import example.app.dao.ProperBaseDataDao;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperHuyoDto;
import example.app.dto.ProperListDto;
import example.app.dto.ProperStatusDto;
import example.app.dto.NameAndNumber;
import example.app.form.ProperDataForm;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuDataForm;
import example.app.form.ProperSimuInputForm;
import example.app.service.CreateMapService;
import example.app.service.ProperDataService;
import example.app.service.ProperHelpService;
import example.app.service.ProperSimuHelpService;

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
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Service("FHuyoLogic")
public class CreateProperHuyoLogicServiceImplF implements CreateProperHuyoLogicService {

	@Autowired
	ProperHelpService properHelpService;
	
	@Autowired
	CreateMapService createMapService;
	
	@Autowired
	ProperSimuHelpService properSimuHelpService;
	
	// ペナルティマップ
	Map<String, Integer>penaltyMap = new HashMap();

	// 付与済プロパリスト
	HashSet<String> properNameSet = new HashSet();

	@Override
	public ProperHuyoAnswerDto getProperHuyoTejun(ProperInputForm properInputForm) {
		
		// 潜在値格納用
		int nowSenzai = properInputForm.getShokiSenzai();
    	// プラスプロパ格納用
    	List<ProperStatusDto> plusProperList = new ArrayList();
    	// マイナスプロパ格納用
    	List<ProperStatusDto> minusProperList = new ArrayList();
    	// プロパの項目個数のMap
    	Map<String, Integer> reptNumMap = new HashMap<>();
    	// プロパ名→同じグループに属するプロパのリスト
    	Map<String, List<ProperStatusDto>> properGroupToDtoMap = new HashMap();
    	// プロスプロパの重複項目リスト
    	List<ProperStatusDto> reptPlusProperListFirst = new ArrayList();
    	// プロスプロパの重複項目リスト
    	List<ProperStatusDto> reptPlusProperListAfter = new ArrayList();
    	// 付与禁止リスト(通常は0または1個)
    	List<ProperStatusDto>kinsiProperList = new ArrayList();
    	// 付与禁止リストに必要な潜在値
    	int kinsiSenzai = 0;
    	// マイナス付与禁止リスト（通常は0または1個）
    	List<ProperStatusDto>kinsiMinusProperList = new ArrayList();
    	// エラー文字列
    	String errorStr = "";
    	// 警告メッセージ
    	String warnStr = "";
    	// 付与リスト(前半：マイナスプロパ付与まで)
    	List<ProperHuyoDto>properHuyoList = new ArrayList();
    	// 付与リスト(同項目リスト付与まで) 並び替え禁止
    	List<ProperHuyoDto>secProperHuyoList = new ArrayList();
    	// 付与リスト（マイナスプロパ付与） 残潜在値などは正確でないので使用禁止
    	List<ProperHuyoDto>thrProperHuyoList = new ArrayList();
    	// 付与リスト（後半：まとめて付与まで）
    	List<ProperHuyoDto>fourthProperHuyoList = new ArrayList();
    	// 付与リスト（まとめて付与） 残潜在値などは正確でないので使用禁止
    	List<ProperHuyoDto>fiveProperHuyoList = new ArrayList();
    	// 同項目リスト付与からマイナスプロパ付与へ持っていけるリスト
    	List<ProperHuyoDto>secToThrProperHuyoList = new ArrayList();
		// 成功率
		int SeikouRate = -1;
		// マイナスプロパ付与時の残り潜在値
		int thrSenzai = 0;
		// 素材リスト
		String sozaiList[] = {"金属", "布地", "獣品", "木材", "薬品", "魔素"};
		// 最終ペナルティ
		int finalPenalty = 0;
		// マイナス付与時の戻り値(ペナルティ付与前)
		double modoriSenzai = 0;
		// プロパ付与上限
		int settiJougen = 0;
		// マイナス付与前プロパ設置可能数
		int setti1 = 0;
		// マイナス付与後プロパ設置可能数
		int setti2 = 0;
		// 基礎潜在
		int kisoSenzai = properInputForm.getKisoSenzai();
		// プロパ付与総数
		int numProper = 0;
		// 最大単価
		int maxTanka = 0;

		// 初期化
		penaltyMap = new HashMap();
		properNameSet = new HashSet();

		// データの取得
		ProperListDto properListDto = properHelpService.getProperListDto(properInputForm);
		plusProperList = properListDto.getPlusProperList();
		reptPlusProperListFirst = properListDto.getReptPlusProperListFirst();
		reptPlusProperListAfter = properListDto.getReptPlusProperListAfter();
		minusProperList = properListDto.getMinusProperList();
		modoriSenzai = properListDto.getModoriSenzai();
		kinsiProperList = properListDto.getKinsiProperList();
		kinsiMinusProperList = properListDto.getKinsiMinusProperList();
		kinsiSenzai = properListDto.getKinsiSenzai();
		reptNumMap = properListDto.getReptNumMap();
		properGroupToDtoMap = properListDto.getProperGroupToDtoMap();
		finalPenalty = properListDto.getFinalPenalty();
		settiJougen = properListDto.getSettiJougen();
		setti1 = properListDto.getSetti1();
		setti2 = properListDto.getSetti2();
		warnStr = properListDto.getWarnStr();
		maxTanka = properListDto.getMaxTanka();
		
    	// ４．プラスプロパの同項目リストのソート
    	Collections.sort(reptPlusProperListFirst, new ProperStatusComparator().reversed());
    	Collections.sort(reptPlusProperListAfter, new ProperStatusComparator().reversed());
    	
    	// ５．入力エラーチェック
    	errorStr = properHelpService.errorCheckCommon(plusProperList, reptPlusProperListFirst, reptPlusProperListAfter
    			, minusProperList, nowSenzai, kinsiProperList, kinsiMinusProperList);
    	if("".equals(errorStr)) {
        	errorStr = this.tekiyoCheck(finalPenalty);
    	}
    	if(!"".equals(errorStr)) {
    		ProperHuyoAnswerDto returnDto = new ProperHuyoAnswerDto();
    		returnDto.setErrorMsg(errorStr);

    		return returnDto;
    	}
    	
    	// プラスプロパリスト
    	List<ProperStatusDto> totalPlusProperList = new ArrayList();
    	totalPlusProperList.addAll(plusProperList);
    	totalPlusProperList.addAll(reptPlusProperListFirst);
    	totalPlusProperList.addAll(reptPlusProperListAfter);
    	
    	
    	// プロパ付与
    	int roopNum = 0;
    	
    	Boolean checkContinue = true;
    	
    	// プロパ付与手順リスト 刻み用
    	List<List<ProperHuyoDto>>properHuyoList1 = new ArrayList();
    	// プロパ付与手順リスト ペナルティ増加用
    	List<List<ProperHuyoDto>>properHuyoList2 = new ArrayList();
    	// プロパ付与手順リスト マイナスプロパ用
    	List<List<ProperHuyoDto>>properHuyoList3 = new ArrayList();
    	
    	// 循環ロジックの開始
    	do {
    		// 使用可能プロパリスト
    		List<ProperStatusDto> usableList = new ArrayList();
    		// 使用不可プロパリスト
    		List<ProperStatusDto> unUsableList = new ArrayList();
    		// 付与リストの追加
    		properHuyoList1.add(new ArrayList());
    		properHuyoList2.add(new ArrayList());
    		properHuyoList3.add(new ArrayList());
    		List<ProperHuyoDto> huyoList1 = properHuyoList1.get(roopNum);
    		List<ProperHuyoDto> huyoList2 = properHuyoList2.get(roopNum);
    		List<ProperHuyoDto> huyoList3 = properHuyoList3.get(roopNum);
    		
    		// ６－１－１．使用可能プロパリスト、使用不可プロパ（どちらも消費潜在の降順）の取得を行う。
    		// 使用可能リストは付与済プロパリストに含まれるプロパは無条件で追加する。付与済プロパリストのプロパレベルがすべて最大の場合は潜在単価が最大のものを追加する。
    		usableList = this.getUsableList(totalPlusProperList);
    		// 使用不可プロパリストの作成
    		for(ProperStatusDto dto : totalPlusProperList) {
    			if(!usableList.contains(dto)) {
    				unUsableList.add(dto);
    			}
    		}
    		


    		// ６－１－２．可能な限り潜在を消費する
			// 最低1回は実行する
    		for(ProperStatusDto huyoDto : usableList) {
    			if(huyoDto.getiProperMokuhyoLevel() > 0 && !properNameSet.contains(huyoDto.getvProperName())) {
					int syohiSenzaiz = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai, huyoList1, 3 * roopNum + 1, setti1);
					nowSenzai -=syohiSenzaiz;
    			}
    		}
    		
    		// needSenzaiの取得
    		int needSenzaiFirst = 0;
			// ペナルティマップtmp
			Map<String, Integer>tempPenaltyMap2 = new HashMap();
			for(String key : penaltyMap.keySet()) {
				tempPenaltyMap2.put(key, (int)penaltyMap.get(key));
			}
			int senzaiTanka2 = 0;
			int syohiTanka2 = 0;
			if(unUsableList.size() > 0) {
				ProperStatusDto dto = unUsableList.get(0);
				// 潜在単価の取得
				senzaiTanka2 = dto.getiProperSenzaiTanka();
				// 消費単価の取得
				Integer lv2 = tempPenaltyMap2.get(dto.getvProperGroupName());
				tempPenaltyMap2.put(dto.getvProperGroupName(), lv2 == null ? 1 : lv2 + 1);
				int tempPenalty2 = this.returnPenalty(tempPenaltyMap2);
				syohiTanka2 = (int)Math.floor(0.00000000000009 + senzaiTanka2 * ( (double) (100 + tempPenalty2) / (double)100 ));
				// tempPenaltyMapをもとに戻す
				tempPenaltyMap2.put(dto.getvProperGroupName(), lv2);
			}
			// 付与プロパの決定、必要潜在の取得
			int needSenzaiTemp2 = 0;
			for(ProperStatusDto dto : unUsableList) {
				Integer lv2 = tempPenaltyMap2.get(dto.getvProperGroupName());
				tempPenaltyMap2.put(dto.getvProperGroupName(), lv2 == null ? 1 : lv2 + 1);
				int tempPenalty2 = this.returnPenalty(tempPenaltyMap2);
				int syohiTankaTemp2 = (int)Math.floor(0.00000000000009 + senzaiTanka2 * ( (double) (100 + tempPenalty2) / (double)100 ));
				if(syohiTankaTemp2 == syohiTanka2) {
					needSenzaiFirst += syohiTankaTemp2;
				}else {
					tempPenaltyMap2.put(dto.getvProperGroupName(), lv2);
				}
			}
			
			// 可能な限り潜在を消費する
    		for(ProperStatusDto huyoDto : usableList) {
    			if(huyoDto.getiProperMokuhyoLevel() > 0) {
    				for(int i = huyoDto.getiProperMokuhyoLevel() - huyoDto.getiProperNowLevel(); i > 0; i--) {
    					int syohiSenzaiz = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai - needSenzaiFirst, huyoList1, 3 * roopNum + 1, setti1);
    					nowSenzai -=syohiSenzaiz;
    				}
    			}
    		}
    		

    		// ６－１－３．必要があればペナルティを増加させるためにプロパを付与する
    		// 条件は１．すでに最大まで現在の使用可能プロパを付与し終えていること
    		// 条件２．付与しても消費潜在が変化しない最大のプロパを付与すること

    		// 条件1の確認
    		Boolean checkLv = true; // すでに最大まで付与し終えている場合はtrue
//    		for(ProperStatusDto dto : usableList) {
//    			if(dto.getiProperNowLevel() < dto.getiProperMokuhyoLevel()
  //  					&& dto.getiProperSenzaiTanka() < 5) {
    //				checkLv = false;
    	//		}
    		//}

    		// 付与プロパリスト
    		List<ProperStatusDto> tempHuyoList = new ArrayList();
    		if(checkLv == true) {
    			// 条件２の確認
    			// ペナルティマップtmp
    			Map<String, Integer>tempPenaltyMap = new HashMap();
    			for(String key : penaltyMap.keySet()) {
    				tempPenaltyMap.put(key, (int)penaltyMap.get(key));
    			}
    			// 潜在単価の取得、消費単価の取得
    			int senzaiTanka = 0;
    			int syohiTanka = 0;
    			if(unUsableList.size() > 0) {
    				ProperStatusDto dto = unUsableList.get(0);
    				// 潜在単価の取得
    				senzaiTanka = dto.getiProperSenzaiTanka();
    				// 消費単価の取得
    				Integer lv = tempPenaltyMap.get(dto.getvProperGroupName());
    				tempPenaltyMap.put(dto.getvProperGroupName(), lv == null ? 1 : lv + 1);
    				int tempPenalty = this.returnPenalty(tempPenaltyMap);
    				syohiTanka = (int)Math.floor(0.00000000000009 + senzaiTanka * ( (double) (100 + tempPenalty) / (double)100 ));
    				// tempPenaltyMapをもとに戻す
    				tempPenaltyMap.put(dto.getvProperGroupName(), lv);

    			}
    			// 付与プロパの決定、必要潜在の取得
    			int needSenzaiTemp = 0;
    			for(ProperStatusDto dto : unUsableList) {
    				Integer lv = tempPenaltyMap.get(dto.getvProperGroupName());
    				tempPenaltyMap.put(dto.getvProperGroupName(), lv == null ? 1 : lv + 1);
    				int tempPenalty = this.returnPenalty(tempPenaltyMap);
    				int syohiTankaTemp = (int)Math.floor(0.00000000000009 + senzaiTanka * ( (double) (100 + tempPenalty) / (double)100 ));
    				if(syohiTankaTemp == syohiTanka) {
    					tempHuyoList.add(dto);
    					needSenzaiTemp += syohiTankaTemp;
    					usableList.add(dto);
    				}else {
    					tempPenaltyMap.put(dto.getvProperGroupName(), lv);
    				}
    			}

    			// 付与
    			for(ProperStatusDto huyoDto : tempHuyoList) {
    				if(huyoDto.getiProperMokuhyoLevel() > 0) {
    					// 1ずつ付与
    					int syohiSenzaiz = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai, huyoList2, 3 * roopNum + 2, setti1);
    					nowSenzai -=syohiSenzaiz;
    					needSenzaiTemp -= syohiSenzaiz;
    					// 可能な限り付与する
    					for(int i = huyoDto.getiProperMokuhyoLevel() - huyoDto.getiProperNowLevel(); i > 0; i--) {
    						syohiSenzaiz = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai - needSenzaiTemp, huyoList2, 3 * roopNum + 2, setti1);
    						nowSenzai -=syohiSenzaiz;
    					}
    				}
    			}

    		}

    		// ６－１－４．マイナスプロパの付与
    		// 付与条件：nowSenzaiがusableListの潜在単価のどれよりも小さい
    		Boolean checkMinus = true; // どれよりも小さい場合はtrue
    		for(ProperStatusDto dto : usableList) {
    			if(dto.getiProperNowLevel() < dto.getiProperMokuhyoLevel()) {
    				if(nowSenzai > dto.getiProperSenzaiTanka()) {
    					checkMinus = false;
    				}
    			}
    		}
    		if(checkMinus == true) {
    			// 必要潜在の取得
    			int needSenzai = 0;
    			int penalty = this.returnPenalty(penaltyMap);
    			for(ProperStatusDto dto : usableList) {
    				if(dto.getiProperSenzaiTanka() < 5) {
        				needSenzai += (dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel())
        						* Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ((double) (100 + penalty) / (double)100));
    				}
    			}
    			// 可能な限りneedSenzaiに近いマイナスプロパを付与する
    			// minusプロパの付与は最大レベルに達していないものについて行う
    			if(minusProperList.size() > 0) {
    				// 使用するマイナスプロパを選択
    				ProperStatusDto dto = minusProperList.get(0);
    				for(ProperStatusDto minus :minusProperList) {
    					// 最大レベルに達していないプロパを選択
    					if(dto.getiProperNowLevel() < minus.getiProperMokuhyoLevel()) {
    						dto = minus;
    						break;
    					}
    				}

    				// 付与するレベルを決定
    				int huyoLv = 0;
    				penalty = this.returnPenalty(penaltyMap);
    				double modoriSenzaiTanka = ( (double) dto.getiProperSenzaiTanka() * 0.3);
    				modoriSenzaiTanka = modoriSenzaiTanka * ((double)(100 + penalty) / (double)100);
    				huyoLv = (int)Math.ceil(-0.00000000000009 + needSenzai / modoriSenzaiTanka );
    				if(huyoLv > dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel()) {
    					huyoLv = dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel();
    				}
    				
    				if(huyoLv > 5) {
    					huyoLv = 5;
    				}
    				int iModoriSenzaiMax = (int)Math.floor(0.00000000000009 + huyoLv * modoriSenzaiTanka);
    				
    				

    				// マイナスプロパの付与を行う。
    				for(int i = huyoLv; i > 0; i--) {
    					int syohiSenzai = this.checkHuyoPenalty(dto, nowSenzai, 999, huyoList3, 3 * roopNum + 3, setti2);
    				}
    				nowSenzai += iModoriSenzaiMax;
    			}
    		}

    		// ６－１－５．継続処理を行うかどうかの判断
    		// 以下のいずれかを満たすとき、処理を終了する。
    		// 1.現在潜在がすべてのtotalPlusProperListの潜在単価よりも小さい
    		// 2.totalPlusProperListのすべてが最大レベルまで達している
    		// 3.roopNumが100以上である(セーフティー機能)
    		Boolean checkContinue1 = true; // すべての潜在単価以下の場合はtrue
    		for(ProperStatusDto dto : totalPlusProperList) {
    			int penalty = this.returnPenalty(penaltyMap);
    			double senzaiTankaTemp = dto.getiProperSenzaiTanka();
    			int iSenzaiTankaTemp = (int) Math.floor(0.00000000000009 + senzaiTankaTemp * ((double)(100 + penalty) / (double)100));
    			// 潜在単価以下の場合はfalse
    			if(nowSenzai > iSenzaiTankaTemp) {
    				checkContinue1 = false;
    			}
    		}
    		Boolean checkContinue2 = true; // すべてが最大レベルに達していればtrue
    		for(ProperStatusDto dto : totalPlusProperList) {
    			// 最大レベルに達していないものがあればfalse
    			if(dto.getiProperNowLevel() < dto.getiProperMokuhyoLevel()) {
    				checkContinue2 = false;
    			}
    		}
    		Boolean checkContinue3 = (roopNum >= 100);

    		checkContinue = !(checkContinue1 || checkContinue2 || checkContinue3);
    		
    		// roopNumの加算
    		roopNum++;

    	}while(checkContinue);
    	
    	
    	// ６－５．まとめて付与
    	plusProperList.addAll(reptPlusProperListFirst);
    	plusProperList.addAll(reptPlusProperListAfter);
    	// 禁止付与リストを追加
    	plusProperList.addAll(kinsiProperList);
    	// 消費潜在値
    	int totalSyohiSenzai = 0;
		for(ProperStatusDto dto : plusProperList) {
			totalSyohiSenzai += dto.getiProperSenzaiTanka() 
					* (dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel());
		}
		// まとめて付与
		for(ProperStatusDto properDto : plusProperList) {
			for(int i = properDto.getiProperNowLevel(); i < properDto.getiProperMokuhyoLevel(); i++) {
				int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, 999, fiveProperHuyoList, 999, settiJougen);
			}
		}
		// マイナスプロパ禁止がある場合
		if(kinsiMinusProperList.size() >= 1) {
			totalSyohiSenzai += kinsiSenzai;
			for(ProperStatusDto properDto : kinsiMinusProperList) {
				for(int i = properDto.getiProperNowLevel(); i < properDto.getiProperMokuhyoLevel(); i++) {
					int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, 999, fiveProperHuyoList, 9999, settiJougen);
				}
			}
			minusProperList.addAll(kinsiMinusProperList);
		}
		// ペナルティ込みの消費潜在計算
		int penalty = this.returnPenalty(penaltyMap);
		totalSyohiSenzai = (int) Math.floor(0.00000000000009 + totalSyohiSenzai * ( (double) (100 + penalty) / (double) 100 ));
		// 付与後の潜在値
		int afterSenzai = nowSenzai - totalSyohiSenzai;
		// 成功率の計算
		int tempFinBunbo = nowSenzai;
		if(tempFinBunbo < kisoSenzai) {
			tempFinBunbo = kisoSenzai;
			warnStr = "【警告】基礎潜在(" + properInputForm.getKisoSenzai() +")を使用する強化です。";
		}
		int seikouRate = (int) Math.floor(0.00000000000009 + 130 + 230 * ( (double) afterSenzai / (double)tempFinBunbo ));
    	
		// ７．出力の整理
		ProperSimuInputForm properSimuInput = new ProperSimuInputForm();
		List<ProperSimuDataForm> stepList = new ArrayList();
		// ProperSimuInputFormの整理
		List<ProperDataForm> totalProperList = properHelpService.createTotalProperData(plusProperList, minusProperList);
		properSimuInput.setProperList(totalProperList);
		for(int i = 0; i < properHuyoList1.size(); i++) {
			List<ProperHuyoDto>huyoList1 = properHuyoList1.get(i);
			List<ProperHuyoDto>huyoList2 = properHuyoList2.get(i);
			List<ProperHuyoDto>huyoList3 = properHuyoList3.get(i);
			
			// 刻み用の付与
			if(huyoList1.size() > 0) {
				ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
				form.setMatometeStr("tandoku");
				Map<String,Integer> maxMap = new HashMap();
				// 付与レベルを設定
				for(ProperHuyoDto dto : huyoList1) {
					maxMap.put(dto.getvProperName(), dto.getiProperNowLevel());
				}
				// 付与レベルの設定
				for(ProperDataForm proper : form.getProperList()) {
					Integer lv = maxMap.get(proper.getProperName());
					if(lv != null) {
						proper.setProperLv(lv);
					}
				}
				stepList.add(form);
			}
			
			// ペナルティ増加用の付与
			if(huyoList2.size() > 0) {
				Map<String, Integer>maxMap = new HashMap();
				List<String> sortList = new ArrayList();
				for(ProperHuyoDto dto : huyoList2) {
					maxMap.put(dto.getvProperName(), dto.getiProperNowLevel());
					if(!sortList.contains(dto.getvProperName())) {
						sortList.add(dto.getvProperName());
					}
				}
				for(String properName : sortList) {
					ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
					form.setMatometeStr("tandoku");
					for(ProperDataForm proper : form.getProperList()) {
						if(properName.equals(proper.getProperName())) {
							Integer lv = maxMap.get(proper.getProperName());
							if(lv != null) {
								proper.setProperLv(lv);
							}
						}
					}
					stepList.add(form);
				}
			}
			// マイナスプロパの付与
			if(huyoList3.size() > 0) {
				ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
				form.setMatometeStr("matomete");
				Map<String, Integer>maxMap = new HashMap();
				for(ProperHuyoDto dto : huyoList3) {
					maxMap.put(dto.getvProperName(), dto.getiProperNowLevel() * -1);
				}
				for(ProperDataForm proper : form.getProperList()) {
					Integer lv = maxMap.get(proper.getProperName());
					if(lv != null) {
						proper.setProperLv(lv);
					}
				}
				stepList.add(form);
				
			}
		}
			
		// 付与手順5.まとめて付与
		Map<String, Integer>maxMap = new HashMap();
		for(ProperHuyoDto dto : fiveProperHuyoList) {
			if(dto.getiProperSortNum() == 9999) {
				maxMap.put(dto.getvProperName(), -1 * dto.getiProperNowLevel());
			}else {
				maxMap.put(dto.getvProperName(), dto.getiProperNowLevel());
			}
		}
		// ProperSimuDataFormを使用
		ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
		form.setMatometeStr("matomete");
		for(ProperDataForm proper : form.getProperList()) {
			Integer i = maxMap.get(proper.getProperName());
			if(i != null) {
				proper.setProperLv(i);
			}
		}
		stepList.add(form);
		
		// stepListをSimuInputFormに設定
		properSimuInput.setProperStepList(stepList);
		// 初期潜在をSimuInputFormに設定
		properSimuInput.setShokiSenzai(properInputForm.getShokiSenzai());
		properSimuInput.setKisoSenzai(properInputForm.getKisoSenzai());
		properSimuInput.setParamLevel(properInputForm.getParamLevel());
		properSimuInput.setProperBui(properInputForm.getProperBui());
		
		// 計算処理実施
		properSimuHelpService.calcurateStep(properSimuInput);
		// Outputを作成
		ProperOutputForm properOutputForm = properSimuHelpService.createOutput(properSimuInput);
		
		// 返却値の作成
		ProperHuyoAnswerDto returnDto = new ProperHuyoAnswerDto();
		returnDto.setSeikouRate(seikouRate);
		returnDto.setHuyoStep(properOutputForm.getHuyoStep());
		returnDto.setHuyoMemberPlus(properOutputForm.getHuyoProperPlus());
		returnDto.setHuyoMemberMinus(properOutputForm.getHuyoProperMinus());
		returnDto.setSozaiList(properOutputForm.getSozaiPt());
		returnDto.setWarnMsg(warnStr);
		returnDto.setErrorMsg(errorStr);
		returnDto.setProperSimuInputForm(properSimuInput);


		return returnDto;
	}
	    
	private String tekiyoCheck(int finalPenalty) {
		// エラー文字列の作成
		String errorStr = "";
		// 潜在値不足の場合は非対応
		if(finalPenalty <= 70) {
			errorStr = "【エラー】ロジック対応外です。";
		}

		return errorStr;
	}

	// 付与の判断, returnが0の場合は付与失敗
	private int checkHuyo(ProperStatusDto dto, int nowSenzai, int condSenzai, List<ProperHuyoDto>list, int setti) {
		// 消費潜在値
		int syohiSenzai = 0;
		// 潜在値が足りているかの確認
		if(condSenzai - dto.getiProperSenzaiTanka() > 0
				&& properNameSet.size() <= setti) {
			// ペナルティの取得＆設定
			String groupName = dto.getvProperGroupName();
			Integer i = this.penaltyMap.get(groupName);
			if(!properNameSet.contains(dto.getvProperName())) {
				penaltyMap.put(groupName, i == null ? 1 : i + 1);
				properNameSet.add(dto.getvProperName());
			}
			// データ入力
			syohiSenzai = dto.getiProperSenzaiTanka();
			nowSenzai -= syohiSenzai;
			dto.setiProperNowLevel(dto.getiProperNowLevel() + 1);
			ProperHuyoDto huyo = new ProperHuyoDto(dto, 1, 0, nowSenzai);
			list.add(huyo);
		}
		return syohiSenzai;
	}

	// ペナルティ付き付与の判断, returnが0の場合は付与失敗
	private int checkHuyoPenalty(ProperStatusDto dto, int nowSenzai, int condSenzai, List<ProperHuyoDto>list, int sortNum, int setti) {
		// 消費潜在値
		int syohiSenzai = 0;
		// ペナルティの取得＆設定
		String groupName = dto.getvProperGroupName();
		Integer i = this.penaltyMap.get(groupName);
		int flgCheck = 0;
		if(!properNameSet.contains(dto.getvProperName())) {
			penaltyMap.put(groupName, i == null ? 1 : i + 1);
			properNameSet.add(dto.getvProperName());
			flgCheck = 1;
		}
		// ペナルティの計算
		int penalty = this.returnPenalty(penaltyMap);
		// 潜在値が足りているかの確認
		if(condSenzai - Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ( (double) (100 + penalty) / (double) 100) ) > 0
				&& properNameSet.size() <= setti) {
			// データ入力
			syohiSenzai = (int) Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ( (double) (100 + penalty) / (double) 100 ));
			nowSenzai -= syohiSenzai;
			dto.setiProperNowLevel(dto.getiProperNowLevel() + 1);
			ProperHuyoDto huyo = new ProperHuyoDto(dto, sortNum, 1, nowSenzai);
			list.add(huyo);
		}else {
			if(flgCheck == 1) {
				// 付与できなかった場合は結果をもとに戻す
				penaltyMap.put(groupName, i);
				properNameSet.remove(dto.getvProperName());
			}
		}
		return syohiSenzai;
	}

	// ペナルティの計算
	private int returnPenalty(Map<String, Integer> map) {
		int sumPenalty = 0;
		for(String str : map.keySet()) {
			Integer j = map.get(str);
			int i = (j == null ? 0 : j);
			if( i > 1) {
				sumPenalty += i * i * 5;
			}
		}
		return sumPenalty;
	}

	// mapからvalueが最大となるkeyを返却する
	private Integer returnMaxKey(Map<Integer, Double>map) {
		// Map.Entryのリストを作成
		List<Entry<Integer, Double>>list = new ArrayList(map.entrySet());
		// Map.Entryの値を比較
		Collections.sort(list, 
				new Comparator<Entry<Integer, Double>>(){
			public int compare(Entry<Integer, Double> obj1, Entry<Integer, Double> obj2) {
				// 降順
				return obj2.getValue().compareTo(obj1.getValue());
			}
		});
		if(list.size() > 0) {
			return list.get(0).getKey();
		}else {
			return null;
		}
	}

	// 同項目付与→ペナルティ、ペナルティにマージ、のどちらが残り潜在が大きくなるか
	// 前者：戻り値×最終ぺなんるてぃ - 同項目×現在ペナルティ
	// 後者：(戻り値-同項目)×最終ペナルティ
	// 後者-前者は0以下の値（これを戻り値とする）
	// この値が0の場合は順不同となる
	private int checkMinusMerge(int modoriti, int doukoumokuTanka, int finalPenalty, int nowPenalty) {
		int former = (int) (Math.floor(0.00000000000009 + modoriti * ( (double) (100 + finalPenalty) / (double) 100 ))
				- Math.floor(0.00000000000009 + doukoumokuTanka * ( (double) (100 + nowPenalty) / (double) 100 )));
		int latter = (int) (Math.floor(0.00000000000009 + 
				( modoriti - doukoumokuTanka) * ( (double) (100 + finalPenalty) / (double) 100 )));
		return latter - former;
	}
	
	// 使用可能プロパリストを生成する
	// 付与済プロパリストのプロパは無条件で追加する。
	// 追加済プロパリストの付与レベルがすべて上限であれば、そのまま返す。そうでない場合は、追加されていないプロパのうち潜在単価が最大のものを1個のみ追加して返す。
	private List<ProperStatusDto> getUsableList(List<ProperStatusDto> totalPlusProperList){
		// return用リスト
		List<ProperStatusDto> resultList = new ArrayList();
		// 付与していないリスト
		List<ProperStatusDto> unHuyoList = new ArrayList();
		
		// プロパリストの並び替え（降順）
    	Collections.sort(totalPlusProperList, new ProperStatusComparator().reversed());
		
		// 付与済プロパリストのプロパを追加
		for(ProperStatusDto dto : totalPlusProperList) {
			if(properNameSet.contains(dto.getvProperName())) {
				resultList.add(dto);
			}else {
				unHuyoList.add(dto);
			}
		}
		
		// プロパレベルがすべて上限であるかどうかを確認
		Boolean checkLv = true; // trueの場合にすべて上限
		for(ProperStatusDto dto : resultList) {
			if(dto.getiProperNowLevel() < dto.getiProperMokuhyoLevel() 
					&& dto.getiProperSenzaiTanka() < 5) {
				checkLv = false;
			}
		}
		
		//プロパレベルがすべて上限の場合は潜在単価が最大のものを1つ加える
		if(checkLv == true) {
			if(unHuyoList.size() > 0) {
				resultList.add(unHuyoList.get(0));
			}
		}
		
		int penalty = this.returnPenalty(penaltyMap);
		Collections.sort(resultList, new ProperStatusComparator3(penalty).reversed());
		
		return resultList;
		
	}

	
}