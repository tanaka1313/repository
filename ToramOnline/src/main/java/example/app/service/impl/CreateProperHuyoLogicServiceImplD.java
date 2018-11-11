package example.app.service.impl;

import example.app.dto.ProperHuyoAnswerDto;


import example.app.form.ProperInputForm;
import example.app.service.CreateProperHuyoLogicService;
import example.app.common.NameAndNumberComparator;
import example.app.common.ProperHuyoComparator;
import example.app.common.ProperStatusComparator;
import example.app.common.ProperStatusComparator2;
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

@Service("DHuyoLogic")
public class CreateProperHuyoLogicServiceImplD implements CreateProperHuyoLogicService {

	@Autowired
	ProperHelpService properHelpService;
	
	@Autowired
	CreateMapService createMapService;
	
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
        	errorStr = this.tekiyoCheck(reptPlusProperListFirst, reptPlusProperListAfter, nowSenzai);
    	}
    	if(!"".equals(errorStr)) {
    		ProperHuyoAnswerDto returnDto = new ProperHuyoAnswerDto();
    		returnDto.setErrorMsg(errorStr);

    		return returnDto;
    	}
    	
    	// ６－１．プロパ付与のロジック実行(マイナスプロパ付与以外を実装)
    	
		// 同項目をどのタイミングで付与するかの判断を行う
		Map<String, Integer> judgeMap = new HashMap();
		// マイナスプロパとマージするかどうかの判断
		for(ProperStatusDto dto : reptPlusProperListFirst) {
			Boolean x;
			if (maxTanka == 0) {
				x = false;
			}else {
	    		x = ((nowSenzai - 1) % maxTanka) - dto.getiProperSenzaiTanka() < 0
	    				&& (maxTanka * ( (double) (finalPenalty) / (double) 100)) >= 2;
			}
    		Boolean y = this.checkMinusMerge((int)Math.floor(0.00000000000009 + modoriSenzai), dto.getiProperSenzaiTanka(), finalPenalty, 0) == 0;
			if(y) {
				judgeMap.put(dto.getvProperName(), 3);
				modoriSenzai -= dto.getiProperSenzaiTanka();
			}else {
				judgeMap.put(dto.getvProperName(), 1);
			}
		}
//    	if(reptPlusProperListFirst.size() >= 1) {
    		// 必要潜在値を求める一時的なマップ
        	Map<String, Integer> tempCountPenalty = new HashMap();
    		// ６－１－１．同項目1個目の付与
    		for(ProperStatusDto dto : reptPlusProperListFirst) {
    			// judgeMapが1の場合のみ付与する
    			if(judgeMap.get(dto.getvProperName()) == 1) {
            		int syohiSenzai = this.checkHuyo(dto, nowSenzai, nowSenzai, properHuyoList, setti1);
            		nowSenzai -= syohiSenzai;
            		// のちの計算で必要になるためtempCountPenaltyの計算を行う
            		Integer i = tempCountPenalty.get(dto.getvProperGroupName());
            		tempCountPenalty.put(dto.getvProperGroupName(), i == null ? 1 : i + 1);
    			}
        		// 対象の1個目をplusProperListに追加
        		plusProperList.add(dto);
    		}
    		// プラスプロパリストの並び替え
    		Collections.sort(plusProperList, new ProperStatusComparator().reversed());
        	// 必要な最低潜在値を計算
        	int needSenzai = 0;
        	for(ProperStatusDto dto: reptPlusProperListAfter) {
            	int penaltyTemp = 0;
        		Integer i = tempCountPenalty.get(dto.getvProperGroupName());
        		tempCountPenalty.put(dto.getvProperGroupName(),i == null ? 1 : i + 1);
        		penaltyTemp = this.returnPenalty(tempCountPenalty);
        		// マイナスプロパとマージするかどうかの判断
        		Boolean x;
        		if (maxTanka == 0 ) {
        			x = false;
        		}else {
            		x = ((nowSenzai - 1) % maxTanka) - dto.getiProperSenzaiTanka() < 0
            				&& (maxTanka * ( (double) (finalPenalty) / (double) 100)) >= 2;
        		}
        		Boolean y = this.checkMinusMerge((int)Math.floor(0.00000000000009 + modoriSenzai), dto.getiProperSenzaiTanka(), finalPenalty, 0) == 0;
        		if(x || y) {
        			judgeMap.put(dto.getvProperName(), 3);
        			modoriSenzai -= dto.getiProperSenzaiTanka();
        		}else {
        			judgeMap.put(dto.getvProperName(), 2);
            		needSenzai += Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ( (double)(100 + penaltyTemp) / (double)100 ));
        		}
        	}
    		// もし最終ペナルティの影響を受ける場合はここでなるべく付与しておく
    		for(ProperStatusDto dto : reptPlusProperListFirst) {
    		}
    		// 結果の格納先
    		Map<String, Integer>huyoMap = new HashMap();
    		// 値の設定
    		for(ProperStatusDto dto : plusProperList) {
    			huyoMap.put(dto.getvProperName(), dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel());
    		}
    		for(ProperStatusDto dto : reptPlusProperListAfter) {
    			huyoMap.put(dto.getvProperName(), dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel());
    		}
    		
			// plusProperListの並び替え
	    	Collections.sort(plusProperList, new ProperStatusComparator().reversed());
        	// ６－１－２．同項目以外の付与
    		for(ProperStatusDto properDto : plusProperList) {
        		int sabun = (int) Math.floor(0.00000000000009 + properDto.getiProperSenzaiTanka() * ( (double) (100 + finalPenalty) / (double) 100) )
        				- properDto.getiProperSenzaiTanka();
        		if(sabun > 0) {
        			for(int i = 0; i < huyoMap.get(properDto.getvProperName()); i++) {
        				int syohiSenzai = 
        						this.checkHuyo(properDto, nowSenzai
        						, nowSenzai - needSenzai
        						, properHuyoList, setti1);
        				nowSenzai -= syohiSenzai;
        			}
        		}
        	}
    		// 同項目リストの内容をプロパリストへ移行
    		plusProperList.addAll(reptPlusProperListAfter);

    		// ６－１－３．同項目２個目の付与（ここからペナルティ発生）
    		// 必要な潜在地を計算
    		int tempNeedSenzai = 0;
    		for(ProperStatusDto huyoDto : reptPlusProperListAfter) {
        		if(huyoDto.getiProperMokuhyoLevel() > 0) {
        			// judgeMapが2の場合のみ付与
        			if(judgeMap.get(huyoDto.getvProperName()) == 2) {
        				tempNeedSenzai += huyoDto.getiProperSenzaiTanka();
        			}
        		}
    		}
    		// 付与
    		for(ProperStatusDto huyoDto : reptPlusProperListAfter) {
        		if(huyoDto.getiProperMokuhyoLevel() > 0) {
        			// judgeMapが2の場合のみ付与
        			if(judgeMap.get(huyoDto.getvProperName()) == 2) {
            			int syohiSenzai = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai, secProperHuyoList, 2, setti1);
            			nowSenzai -=syohiSenzai;
            			tempNeedSenzai -=huyoDto.getiProperSenzaiTanka();
        			}
        			// 余裕がある場合はさらに付与を行う。
        			for(int i = huyoMap.get(huyoDto.getvProperName()); i >= 0; i--) {
        				int nowPenalty = this.returnPenalty(penaltyMap);
                		int sabun = (int) (Math.floor(0.00000000000009 + huyoDto.getiProperSenzaiTanka() * ( (double) (100 + finalPenalty) / (double) 100) )
                				- Math.floor(0.00000000000009 + huyoDto.getiProperSenzaiTanka() * ( (double) (100 + nowPenalty) / (double) 100 )));
                		if(sabun > 0) {
                			int syohiSenzai = this.checkHuyoPenalty(huyoDto, nowSenzai, nowSenzai - tempNeedSenzai, secProperHuyoList, 2, setti1);
                			nowSenzai -=syohiSenzai;
                		}
        			}
        		}
    		}
//     	}
    	// ６－２．共通処理（マイナスプロパの付与） 残潜在ポイントは使用不可
		for(ProperStatusDto properDto : minusProperList) {
			for(int i = properDto.getiProperNowLevel(); i < properDto.getiProperMokuhyoLevel(); i++) {
				int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, 999, thrProperHuyoList, 3, setti2);
			}
		}
		// judgeMapが3となるものを付与
		for(ProperStatusDto dto : reptPlusProperListFirst) {
			if(judgeMap.get(dto.getvProperName()) == 3) {
				int syohiSenzai = this.checkHuyoPenalty(dto, nowSenzai, 999, thrProperHuyoList, 2, setti2);
			}
		}
		for(ProperStatusDto dto : reptPlusProperListAfter) {
			if(judgeMap.get(dto.getvProperName()) == 3) {
				int syohiSenzai = this.checkHuyoPenalty(dto, nowSenzai, 999, thrProperHuyoList, 2, setti2);
			}
		}
		// 潜在値の計算
		int penalty = this.returnPenalty(penaltyMap);
		nowSenzai += Math.floor(0.00000000000009 + Math.floor(0.00000000000009 + modoriSenzai) * ( (double) (100 + penalty) / (double) 100 ));
		thrSenzai = nowSenzai;
		
		// 同項目Firstを同項目Afterに追加する
		reptPlusProperListAfter.addAll(reptPlusProperListFirst);
		// ６－３．刻んで付与するプロパ
    	if(plusProperList.size() >= 1) {
    		// 同項目が存在する場合
    		// プロパリストのソート
    		Collections.sort(plusProperList, new ProperStatusComparator().reversed());
    		// 処理は二回実行する
    		for(int k = 0; k < 2; k++) {
    			for(ProperStatusDto properDto: plusProperList) {
    				// プロパの付与が刻めばペナルティを無視できる場合

    				// 消費潜在
    				int totalSyohiSenzai = 0;
    				// 消費潜在の算出
    				for(ProperStatusDto dto : plusProperList) {
    					totalSyohiSenzai += dto.getiProperSenzaiTanka() 
    							* (dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel());
    				}
    				// 禁止潜在の分を追加
    				totalSyohiSenzai += kinsiSenzai;
    				// ペナルティの取得
    				penalty = this.returnPenalty(penaltyMap);
    				// まとめ付与時の潜在
    				int afterSenzai = (int) (nowSenzai - Math.floor(0.00000000000009 + totalSyohiSenzai * ( (double) (100 + penalty) / (double) 100 )));
    				if(afterSenzai >= 0) {
    					// もともと成功率100%だからここの処理いらないね。
    				}else {
    					afterSenzai = -1 * afterSenzai;
    					Map<Integer,Double>serchMap = new HashMap();
    					for(int i=properDto.getiProperMokuhyoLevel() - properDto.getiProperNowLevel(); 
    							i >= 0; i--) {
    						// 各iについて成功率を計算
    						// 刻んでつける
    						int tanka = properDto.getiProperSenzaiTanka();
    						int nTantai = (int) Math.floor(0.00000000000009 + tanka * ( (double) (100 + penalty) / (double) 100 ));
    						int nSyohiSenzai = nTantai * i;
    						// まとめてつける場合
    						int nAfterSyohiSenzai = totalSyohiSenzai - tanka * i;
    						int nAfterSyohiSenzaiWithPenalty = (int) Math.floor(0.00000000000009 + nAfterSyohiSenzai * ( (double) (100 + penalty) / (double) 100 ));
    						// ふたつの差分
    						int nAfterSenzai = nowSenzai - nSyohiSenzai - nAfterSyohiSenzaiWithPenalty;
    						// 分母の計算
    						int tempBunbo = nowSenzai - nSyohiSenzai;
    						if(tempBunbo < kisoSenzai) {
    							tempBunbo = kisoSenzai;
    						}
    						// 刻んでつける場合の割合を設定
    						serchMap.put(i, 130 + 230 * ( (double) (nAfterSenzai) / (double) (tempBunbo) ));
    					} // i end
    					// Mapからvalueが最大であるkeyを取得する
    					Integer a = this.returnMaxKey(serchMap);
    					int maxKey = (a == null ? -1 : a);
    					// MaxKeyをもとにしてプロパ付与を行う
    					int nowLevel = properDto.getiProperNowLevel();
    					for(int j = maxKey; j > 0; j--) {
    						int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, nowSenzai, fourthProperHuyoList, 4, setti2);
    						nowSenzai -=syohiSenzai;
    					}// j end
    				}// if end
    			}// properDto end
    		}
    		// 同時に付与したほうがいい場合は付与を行う。
        	int totalSyohiSenzai = 0;
        	totalSyohiSenzai += kinsiSenzai;
    		for(ProperStatusDto dto : plusProperList) {
    			totalSyohiSenzai += dto.getiProperSenzaiTanka() 
    					* (dto.getiProperMokuhyoLevel() - dto.getiProperNowLevel());
    		}
    		// ペナルティの取得
			int tempPenalty = this.returnPenalty(penaltyMap);
    		// 消費潜在がnowSenzai - 1　以下の場合で成功率が最も高くなる組み合わせを算出（部分和問題）
    		List<NameAndNumber>availList = new ArrayList();
    		// 使用可能なリストの作成
    		for(ProperStatusDto dto : plusProperList) {
    			for(int i = dto.getiProperNowLevel(); i < dto.getiProperMokuhyoLevel(); i++) {
    				availList.add(new NameAndNumber(dto.getvProperName()
    						, (int)Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ( (double) ( 100 + tempPenalty) / (double)100))
    						, dto.getiProperSenzaiTanka()));
    			}
    		}
    		// 結果の格納先(使用潜在to成功率)
    		Map<Integer, Double> senzaiToRate = new HashMap();
    		// 結果の格納先(使用潜在to付与手順)
    		Map<Integer, Map<String, Integer>> senzaiToTejun = new HashMap();
    		// ロジックの実行
    		for(int target = nowSenzai - 1; target >= 0; target--) {
    			// 初期化
        		Map<String, Integer>tempMap = new HashMap();
        		NameAndNumber checkAvail[] = new NameAndNumber[target + 1];
        		for(int i = 0; i <= target; i++) {
        			checkAvail[i] = new NameAndNumber("noData", -1, 0);
        		}
        		if(checkAvail.length > 0) {
        			checkAvail[0] = new NameAndNumber("", 0, 0);
        		}
        		// ロジックの実行
        		for(NameAndNumber dto : availList) {
        			// すでに部分和が作成できている場合は対応なし
        			if(checkAvail[target].getiNumber() == -1) {
        				int tempNumber = dto.getiNumber();
        				for(int i = target - 1; i >= 0; i--) {
        					if(checkAvail[i].getiNumber() != -1 && tempNumber + i <= target 
        							&& checkAvail[tempNumber + i].getiNumber() == -1) {

        						checkAvail[tempNumber + i].setvName(dto.getvName());
        						checkAvail[tempNumber + i].setiNumber(tempNumber);
        						checkAvail[tempNumber + i].setiTanka(dto.getiTanka());

        					}
        				}
        			}
        		}
        		// 結果の取得
        		if(checkAvail[target].getiNumber() != -1) {
        			// 消費潜在
        			int tempSyohi = 0;
        			int tempSyohiPenalty = 0;
        			
        			for(int i = target; i > 0; i -= checkAvail[i].getiNumber()) {
        				Integer temp = tempMap.get(checkAvail[i].getvName());
        				tempMap.put(checkAvail[i].getvName(), temp == null ? 1 : temp + 1);
        				tempSyohi += checkAvail[i].getiTanka();
        				tempSyohiPenalty += checkAvail[i].getiNumber();
        			}
        			// 結果の設定
        			int tempAfter = nowSenzai - tempSyohiPenalty - (int) Math.floor(0.00000000000009 + (totalSyohiSenzai - tempSyohi) * ( (double)(100 + tempPenalty) / (double)100));
        			int tempBunbo = nowSenzai - tempSyohiPenalty;
        			if(tempBunbo < kisoSenzai) {
        				tempBunbo = kisoSenzai;
        			}
        			senzaiToRate.put(target, 130 + 230 * ( (double)(tempAfter) / (double) (tempBunbo)));
        			senzaiToTejun.put(target, tempMap);
        		}else {
        			senzaiToRate.put(target, (double)-1);
        			senzaiToTejun.put(target, new HashMap<String, Integer>());
        		}
    		}
    		// 最も成功率を高くするtargetを取得する
    		int wantTarget = this.returnMaxKey(senzaiToRate);
    		// 最も成功率を高くする付与手順を取得する
    		Map<String, Integer> wantHuyoTejun = senzaiToTejun.get(wantTarget);
    		// 設定されていない値も設定
    		for(ProperStatusDto dto : plusProperList) {
    			Integer temp = wantHuyoTejun.get(dto.getvProperName());
    			wantHuyoTejun.put(dto.getvProperName(), temp == null ? 0 : temp);
    		}
    		// 付与の開始
			for(ProperStatusDto dto : plusProperList) {
				for(int k = 0; k < wantHuyoTejun.get(dto.getvProperName()); k++) {
					int syohiSenzai = this.checkHuyoPenalty(dto, nowSenzai, nowSenzai, fourthProperHuyoList, 4, setti2);
					nowSenzai -=syohiSenzai;
				}
			}
    		
    		
    	}// reptPlusProperList.size() == 1 end
		
    	// ６－４．まとめて付与
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
				int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, 999, fiveProperHuyoList, 5, settiJougen);
			}
		}
		// マイナスプロパ禁止がある場合
		if(kinsiMinusProperList.size() >= 1) {
			totalSyohiSenzai += kinsiSenzai;
			for(ProperStatusDto properDto : kinsiMinusProperList) {
				for(int i = properDto.getiProperNowLevel(); i < properDto.getiProperMokuhyoLevel(); i++) {
					int syohiSenzai = this.checkHuyoPenalty(properDto, nowSenzai, 999, fiveProperHuyoList, 3, settiJougen);
				}
			}
			minusProperList.addAll(kinsiMinusProperList);
		}
		// ペナルティ込みの消費潜在計算
		penalty = this.returnPenalty(penaltyMap);
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
    	
    	// 出力の整理
		// 出力用にソートを実行（同項目実施の２番のみソートしてはいけない）
		Collections.sort(properHuyoList, new ProperHuyoComparator());
		Collections.sort(thrProperHuyoList, new ProperHuyoComparator());
		Collections.sort(fourthProperHuyoList, new ProperHuyoComparator());
		Collections.sort(fiveProperHuyoList, new ProperHuyoComparator());
		
		// ７．出力の整理
		// 付与手順のリスト
		List<String> huyoStepList = new ArrayList();
		ProperSimuInputForm properSimuInput = new ProperSimuInputForm();
		List<ProperSimuDataForm> stepList = new ArrayList();
		// ProperSimuInputFormの整理
		List<ProperDataForm> totalProperList = properHelpService.createTotalProperData(plusProperList, minusProperList);
		properSimuInput.setProperList(totalProperList);
		// 付与手順１
		if(properHuyoList.size() > 0) {
			StringBuffer huyoStep1 = new StringBuffer();
			huyoStep1.append("まとめて");
			Map<String, Integer>maxProper = new HashMap();
			int restSenzai = properInputForm.getShokiSenzai();
			for(ProperHuyoDto dto : properHuyoList) {
				maxProper.put(dto.getvProperName(), dto.getiProperNowLevel());
				restSenzai = Math.min(restSenzai, dto.getiProperRestSenzai());
			}
			for(String key : maxProper.keySet()) {
				huyoStep1.append(", " + key + " Lv" + maxProper.get(key));
			}
			huyoStep1.append("を付与する。(残潜在：");
			huyoStep1.append(restSenzai);
			huyoStep1.append(")");
			huyoStepList.add(huyoStep1.toString());
			
			// ProperSimuDataFormを使用
			ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
			form.setMatometeStr("matomete");
			for(ProperDataForm proper : form.getProperList()) {
				Integer i = maxProper.get(proper.getProperName());
				if(i != null) {
					proper.setProperLv(i);
				}
			}
			stepList.add(form);
			

		}
		// 付与手順2
		List<String> huyoStep2 = new ArrayList();
		for(ProperHuyoDto dto : secProperHuyoList) {
			huyoStep2.add(dto.getvProperName() + " Lv" + dto.getiProperNowLevel() + "を付与する。"
					+ "(残潜在：" + dto.getiProperRestSenzai() + ")");
		}
		huyoStepList.addAll(huyoStep2);
		
		// ProperSimuDataFormの作成
		Map<String, Integer>maxMap = new HashMap();
		List<String> sortList = new ArrayList();
		for(ProperHuyoDto dto : secProperHuyoList) {
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
					Integer i = maxMap.get(proper.getProperName());
					if(i != null) {
						proper.setProperLv(i);
					}
				}
			}
			stepList.add(form);
		}
		

		// 付与手順3.マイナスプロパの付与
		if(minusProperList.size() > 0) {
			StringBuffer huyoStep3 = new StringBuffer();
			huyoStep3.append("まとめて");
			Map<String, Integer>maxProper = new HashMap();
			int restSenzai = thrSenzai;
			for(ProperHuyoDto dto : thrProperHuyoList) {
				maxProper.put(dto.getvProperName(), dto.getiProperNowLevel());
			}
			for(String key : maxProper.keySet()) {
				if(judgeMap.get(key) == null) {
					huyoStep3.append(", " + key + " Lv-" + maxProper.get(key));
				}else {
					huyoStep3.append(", " + key + " Lv" + maxProper.get(key));
				}
			}
			huyoStep3.append("を付与する。(残潜在：");
			huyoStep3.append(restSenzai);
			huyoStep3.append(")");
			huyoStepList.add(huyoStep3.toString());
			
			// ProperSimuDataFormを使用
			ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
			form.setMatometeStr("matomete");
			for(ProperDataForm proper : form.getProperList()) {
				Integer i = maxProper.get(proper.getProperName());
				if(i != null) {
					if(judgeMap.get(proper.getProperName()) == null) {
						proper.setProperLv(i * -1);
					}else {
						proper.setProperLv(i);
					}
				}
			}
			stepList.add(form);
			

		}
		// 付与手順4.刻んで追加する付与
		if(fourthProperHuyoList.size() > 0) {
			StringBuffer huyoStep4 = new StringBuffer();
			huyoStep4.append("ペナルティを最小にするために1ずつ増やしていくことで");
			Map<String, Integer>maxProper = new HashMap();
			int restSenzai = thrSenzai;
			for(ProperHuyoDto dto : fourthProperHuyoList) {
				maxProper.put(dto.getvProperName(), dto.getiProperNowLevel());
				restSenzai = Math.min(restSenzai, dto.getiProperRestSenzai());
			}
			for(String key : maxProper.keySet()) {
				huyoStep4.append(", " + key + " Lv" + maxProper.get(key));
			}
			huyoStep4.append("を順不同で付与する。(残潜在：");
			huyoStep4.append(restSenzai);
			huyoStep4.append(")");
			huyoStepList.add(huyoStep4.toString());
			
			// ProperSimuDataFormを使用
			ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
			form.setMatometeStr("tandoku");
			for(ProperDataForm proper : form.getProperList()) {
				Integer i = maxProper.get(proper.getProperName());
				if(i != null) {
					proper.setProperLv(i);
				}
			}
			stepList.add(form);

		}
		// 付与手順5.まとめて付与
		StringBuffer huyoStep5 = new StringBuffer();
		huyoStep5.append("まとめて");
		Map<String, Integer>maxProper = new HashMap();
		int restSenzai = afterSenzai;
		for(ProperHuyoDto dto : fiveProperHuyoList) {
			if(dto.getiProperSortNum() == 3) {
				maxProper.put(dto.getvProperName(), -1 * dto.getiProperNowLevel());
			}else {
				maxProper.put(dto.getvProperName(), dto.getiProperNowLevel());
			}
		}
		for(String key : maxProper.keySet()) {
			huyoStep5.append(", " + key + " Lv" + maxProper.get(key));
		}
		huyoStep5.append("を付与する。(残潜在：");
		huyoStep5.append(restSenzai);
		huyoStep5.append(")");
		huyoStepList.add(huyoStep5.toString());
		
		// ProperSimuDataFormを使用
		ProperSimuDataForm form = properHelpService.createSimuData(stepList, totalProperList);
		form.setMatometeStr("matomete");
		for(ProperDataForm proper : form.getProperList()) {
			Integer i = maxProper.get(proper.getProperName());
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

		
		// 付与リスト
		StringBuffer huyoMemberPlus = new StringBuffer();
		StringBuffer huyoMemberMinus = new StringBuffer();
		for(ProperStatusDto dto : plusProperList) {
			huyoMemberPlus.append(",");
			huyoMemberPlus.append(dto.getvProperName());
			huyoMemberPlus.append(" Lv.");
			huyoMemberPlus.append(dto.getiProperMokuhyoLevel());
		}
		huyoMemberPlus.delete(0, 1);
		for(ProperStatusDto dto : minusProperList) {
			huyoMemberMinus.append(",");
			huyoMemberMinus.append(dto.getvProperName());
			huyoMemberMinus.append(" Lv.-");
			huyoMemberMinus.append(dto.getiProperMokuhyoLevel());
		}
		huyoMemberMinus.delete(0, 1);
		
		// ８．消費素材ポイントの算出
		List<ProperHuyoDto> totalList = new ArrayList();
		// 全リストの追加
		totalList.addAll(properHuyoList);
		totalList.addAll(secProperHuyoList);
		totalList.addAll(thrProperHuyoList);
		totalList.addAll(fourthProperHuyoList);
		totalList.addAll(fiveProperHuyoList);
		// 素材ポイント格納用のMAP
		Map<String, Integer> sozaiMap = new HashMap();
		// 初期値格納
		for(String str : sozaiList) {
			sozaiMap.put(str, 0);
		}
		for(ProperHuyoDto dto : totalList) {
			String str = dto.getvProperSozaiName();
			Integer i = sozaiMap.get(str) + dto.getiProperSozaiPt();
			sozaiMap.put(str, i);
		}
		// 文字列作成
		StringBuffer sozaiPt = new StringBuffer();
		for(String str : sozaiList) {
			sozaiPt.append(",");
			sozaiPt.append(str);
			sozaiPt.append(":");
			sozaiPt.append(sozaiMap.get(str));
			sozaiPt.append("pt");
		}
		sozaiPt.delete(0,  1);
		
		
		// 返却値の作成
		ProperHuyoAnswerDto returnDto = new ProperHuyoAnswerDto();
		returnDto.setSeikouRate(seikouRate);
		returnDto.setHuyoStep(huyoStepList);
		returnDto.setHuyoMemberPlus(huyoMemberPlus.toString());
		returnDto.setHuyoMemberMinus(huyoMemberMinus.toString());
		returnDto.setSozaiList(sozaiPt.toString());
		returnDto.setWarnMsg(warnStr);
		returnDto.setErrorMsg(errorStr);
		returnDto.setProperSimuInputForm(properSimuInput);

		return returnDto;
	}
	    
	private String tekiyoCheck(List<ProperStatusDto>reptPlusProperListFirst
			, List<ProperStatusDto>reptPlusProperListAfter
			, Integer shokiSenzai) {
		// エラー文字列の作成
		String errorStr = "";
		// 必要な最低潜在値を計算
		int needSenzai = 0;
		Map<String, Integer> tempCountPenalty = new HashMap();
		List<ProperStatusDto> statusTempList = new ArrayList();
		statusTempList.addAll(reptPlusProperListFirst);
		statusTempList.addAll(reptPlusProperListAfter);
		for(ProperStatusDto dto: statusTempList) {
			int penaltyTemp = 0;
			Integer i = tempCountPenalty.get(dto.getvProperGroupName());
			tempCountPenalty.put(dto.getvProperGroupName(),i == null ? 1 : i + 1);
			penaltyTemp = this.returnPenalty(tempCountPenalty);
			needSenzai += Math.floor(0.00000000000009 + dto.getiProperSenzaiTanka() * ( (double)(100 + penaltyTemp) / (double)100));
		}
		// 潜在値不足の場合は非対応
		if(reptPlusProperListFirst.size() > 0 &&
				shokiSenzai <= needSenzai) {
			errorStr = "【エラー】初期潜在値が選択されたプロパに対して小さすぎます。";
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
}