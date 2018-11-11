package example.app.service.impl;

import example.app.common.ProperStatusComparator;
import example.app.common.ProperStatusComparator2;
import example.app.dto.ProperListDto;
import example.app.dto.ProperStatusDto;
import example.app.form.ProperDataForm;
import example.app.form.ProperInputForm;
import example.app.form.ProperSimuDataForm;
import example.app.service.ProperDataService;
import example.app.service.ProperHelpService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class ProperHelpServiceImpl implements ProperHelpService{
	
	static ProperListDto properListDto;
	
	// プロパ付与上限
	int settiJougen = 6;
	
	@Autowired
	ProperDataService properDataService;
	
	@Override
	public ProperListDto getProperListDto(ProperInputForm properInputForm) {
//		if(properListDto == null) {
			// プラスプロパリスト
			List<ProperStatusDto> plusProperList = new ArrayList();
			// 同項目プロパリストFIRST
			List<ProperStatusDto> reptPlusProperListFirst = new ArrayList();
			// 同項目プロパリストAfter
			List<ProperStatusDto> reptPlusProperListAfter = new ArrayList();
			// マイナスプロパリスト
			List<ProperStatusDto> minusProperList = new ArrayList();
			// 戻り潜在
			double modoriSenzai = 0;
			// 禁止プロパリスト
			List<ProperStatusDto>kinsiProperList = new ArrayList();
			// 禁止プロパリストマイナス
			List<ProperStatusDto>kinsiMinusProperList = new ArrayList();
			// 付与禁止リストに必要な潜在値
			int kinsiSenzai = 0;
			// 重複度MAP
			Map<String, Integer> reptNumMap = new HashMap<>();
			// プロパ名→同じグループに属するプロパのリスト
			Map<String, List<ProperStatusDto>> properGroupToDtoMap = new HashMap();
			// 最終ペナルティ
			int finalPenalty = 0;
			// マイナス付与前プロパ設置可能数
			int setti1 = 0;
			// マイナス付与後プロパ設置可能数
			int setti2 = 0;
			// 警告メッセージ
			String warnStr = "";
			
			// 潜在値格納用
			int nowSenzai = properInputForm.getShokiSenzai();
			// プロパ付与総数
			int numProper = 0;
			
	    	// １．データ入力処理実行
	    	this.properDataInput(plusProperList, minusProperList, properInputForm, properInputForm.getProperBui());
	    	// 最終ペナルティの計算
	    	finalPenalty = this.returnFinalPenalty(plusProperList, minusProperList);
	    	// マイナス付与時の戻り値計算
	    	for(ProperStatusDto dto : minusProperList) {
	    		modoriSenzai += dto.getiProperSenzaiTanka() * dto.getiProperMokuhyoLevel();
	    	}
			modoriSenzai = modoriSenzai * 0.3;
	    	// 消費潜在の最大値
	    	int maxTanka = 0;
	    	for(ProperStatusDto dto : plusProperList) {
	    		if(dto.getiProperSenzaiTanka() < nowSenzai) {
	    			maxTanka = Math.max(maxTanka, dto.getiProperSenzaiTanka());
	    		}
	    	}
	    	// マイナス消費潜在の最大値
	    	int minusMaxTanka = 0;
	    	for(ProperStatusDto dto : minusProperList) {
	    		minusMaxTanka = Math.max(minusMaxTanka, dto.getiProperSenzaiTanka());
	    	}
	    	minusMaxTanka = (int) Math.floor(0.00000000000009 + minusMaxTanka * 0.3);
	    	// プロパ総数の計算
	    	numProper = plusProperList.size() + minusProperList.size();
	    	
	    	
	    	// ２．プロパの項目個数のMAP作成
	    	List<ProperStatusDto> totalProperList = new ArrayList();
	    	totalProperList.addAll(plusProperList);
	    	totalProperList.addAll(minusProperList);
	    	reptNumMap = this.createReptNumMap(totalProperList);
	    	
	    	
	    	// ２－０．プラスプロパの並び替え
	    	Collections.sort(plusProperList, new ProperStatusComparator().reversed());
	    	
	    	// ２－１．グループ名→Dtoのリスト作成
	    	for(ProperStatusDto dto : plusProperList) {
	    		String properGroupName = dto.getvProperGroupName();
	    		List<ProperStatusDto> temp = properGroupToDtoMap.get(properGroupName);
	    		if(temp == null) {
	    			temp = new ArrayList();
	    		}
	    		temp.add(dto);
	    		properGroupToDtoMap.put(properGroupName, temp);
	    	}
	    	
	    	// ３．プラスプロパの同項目リストの作成
	    	this.createReptProperList(plusProperList, reptPlusProperListFirst, reptPlusProperListAfter
	    			,  reptNumMap, properGroupToDtoMap);
	    	
	    	// ３－１．この時点でplusProperList.size()が0の場合はエラーメッセージを設定
	    	if(numProper >= settiJougen) {
	        	if(plusProperList.size() == 0) {
	        		// 消費潜在(単価×レベル)が低い順に整理
	        		Collections.sort(minusProperList, new ProperStatusComparator2());
	        		for(ProperStatusDto dto : minusProperList) {
	        			if(reptNumMap.get(dto.getvProperGroupName()) <= 1) {
	        				kinsiMinusProperList.add(dto);
	        				minusProperList.remove(dto);
	        				break;
	        			}
	        		}
	        		if(kinsiMinusProperList.size() == 0) {
	        			kinsiMinusProperList.add(minusProperList.get(0));
	        			minusProperList.remove(0);
	        		}
	        		
	        		if(kinsiMinusProperList.size() >= 1) {
	            		int tempSenzai = kinsiMinusProperList.get(0).getiProperSenzaiTanka() * kinsiMinusProperList.get(0).getiProperMokuhyoLevel();
	            		modoriSenzai -= tempSenzai * 0.3;
	            		kinsiSenzai = -1 * (int) Math.floor(0.00000000000009 + tempSenzai * 0.3);
	        		}else {
	        			warnStr = "マイナス・プラスプロパともに同項目しか無く、当サイトではロジック対応外のため成功率が低い可能性があります。";
	        		}
	        	}else {
	        		// 消費潜在(単価×レベル)が低い順に整理
	        		Collections.sort(plusProperList, new ProperStatusComparator2());
	        		int index = 0;
	        		int tempTanka = plusProperList.get(0).getiProperSenzaiTanka();
	        		int sabun = (int) Math.floor(0.00000000000009 + tempTanka * ( (double) (100 + finalPenalty) / (double) 100) ) - tempTanka;
	        		if(sabun == 0 && plusProperList.size() > 1) {
	        			index = 1;
	        		}
	        		kinsiProperList.add(plusProperList.get(index));
	        		kinsiSenzai = kinsiProperList.get(0).getiProperSenzaiTanka() * kinsiProperList.get(0).getiProperMokuhyoLevel();
	        		plusProperList.remove(index);
	        	}
	    	}
	    	
	    	// 上限値の設定
	    	setti1 = settiJougen - 1;
	    	setti2 = settiJougen - 1;

	    	// 返却値の作成
	    	properListDto = new ProperListDto();
	    	properListDto.setPlusProperList(plusProperList);
	    	properListDto.setReptPlusProperListFirst(reptPlusProperListFirst);
	    	properListDto.setReptPlusProperListAfter(reptPlusProperListAfter);
	    	properListDto.setMinusProperList(minusProperList);
	    	properListDto.setModoriSenzai(modoriSenzai);
	    	properListDto.setKinsiProperList(kinsiProperList);
	    	properListDto.setKinsiMinusProperList(kinsiMinusProperList);
	    	properListDto.setKinsiSenzai(kinsiSenzai);
	    	properListDto.setReptNumMap(reptNumMap);
	    	properListDto.setProperGroupToDtoMap(properGroupToDtoMap);
	    	properListDto.setFinalPenalty(finalPenalty);
	    	properListDto.setSettiJougen(settiJougen);
	    	properListDto.setSetti1(setti1);
	    	properListDto.setSetti2(setti2);
	    	properListDto.setWarnStr(warnStr);
	    	properListDto.setMaxTanka(maxTanka);
	    	properListDto.setMinusMaxTanka(minusMaxTanka);


//		}
		
		// 返却値はコピーを返す
//		 ProperListDto returnDto = this.properListDto.clone();
		 
        	// 処理負荷軽減処理
        	try {
        		TimeUnit.MILLISECONDS.sleep(65);
        	}catch(Exception e) {
        		
        	}
        	
		return properListDto;
	}
	
	// フォームからのデータを入力する
	private void properDataInput(List<ProperStatusDto> plusProperList, List<ProperStatusDto> minusProperList, ProperInputForm properInputForm, String properBui) {

		for(ProperDataForm plusForm : properInputForm.getPlusProperList()) {
			// プロパレベルを設定する
			Integer properLv;
			try {
				properLv = Integer.parseInt(plusForm.getProperLvHyoji());
			}catch(Exception e) {
				properLv = 0;
			}
			// MAX文字列の場合は0でない数字を設定
			if("MAX".equals(plusForm.getProperLvHyoji())) {
				properLv = 1;
			}
			plusForm.setProperLv(properLv);
			if(!StringUtils.isEmpty(plusForm.getProperName())
					&& ((Integer) 0).compareTo(plusForm.getProperLv()) != 0){
				// プロパ名称が空でない場合プロパ情報を取得しリストに追加
				ProperStatusDto data = properDataService.getProperStatusDto(
						plusForm.getProperName() , properInputForm.getParamLevel()
						, properBui);
				// MAX文字列の場合は最大値を設定
				if("MAX".equals(plusForm.getProperLvHyoji())) {
					plusForm.setProperLv(data.getiProperMaxLevel());
				}
				// 表示レベルを設定レベルへ反映
				plusForm.setProperLvHyoji(String.valueOf(plusForm.getProperLv()));
				// データの設定
				data.setiProperMokuhyoLevel(plusForm.getProperLv());
				data.setiProperNowLevel(0);
				plusProperList.add(data);
			}
		}
		for(ProperDataForm minusForm : properInputForm.getMinusProperList()) {
			// プロパレベルを設定する
			Integer properLv;
			try {
				properLv = Integer.parseInt(minusForm.getProperLvHyoji());
			}catch(Exception e) {
				properLv = 0;
			}
			// MAX文字列の場合は0でない数字を設定
			if("MAX".equals(minusForm.getProperLvHyoji())) {
				properLv = 1;
			}
			minusForm.setProperLv(properLv);
			if(!StringUtils.isEmpty(minusForm.getProperName())
					&& ((Integer) 0).compareTo(minusForm.getProperLv()) != 0) {
				// プロパ名称が空でない場合にプロパ情報を取得しリストに追加
				ProperStatusDto data = properDataService.getProperStatusDto(
						minusForm.getProperName() , properInputForm.getParamLevel()
						, properBui);
				// MAX文字列の場合は最大値を設定
				if("MAX".equals(minusForm.getProperLvHyoji())) {
					minusForm.setProperLv(data.getiProperMaxLevel());
				}
				// データの設定
				data.setiProperMokuhyoLevel(minusForm.getProperLv());
				data.setiProperNowLevel(0);
				minusProperList.add(data);
			}
		}

	}
	
	// 最終ペナルティの算出
	private int returnFinalPenalty(List<ProperStatusDto> plusList, List<ProperStatusDto> minusList) {
		// マージ
		List<ProperStatusDto>totalList = new ArrayList();
		totalList.addAll(plusList);
		totalList.addAll(minusList);
		// 重複度の算出
		Map<String, Integer>map = new HashMap();
		for(ProperStatusDto dto : totalList) {
			Integer i = map.get(dto.getvProperGroupName());
			map.put(dto.getvProperGroupName(), i == null ? 1 : i + 1);
		}
		int penalty = this.returnPenalty(map);
		return penalty;
	}
	
	// ペナルティの計算
	private int returnPenalty(Map<String, Integer> map) {
		int sumPenalty = 0;
		for(String str : map.keySet()) {
			int i = map.get(str);
			if( i > 1) {
				sumPenalty += i * i * 5;
			}
		}
		return sumPenalty;
	}

	// リストの重複度を計算する
	private Map<String,Integer> createReptNumMap(List<ProperStatusDto> list){

		Map<String, Integer> map = new HashMap<>();

		for(ProperStatusDto dto : list) {
			String str = dto.getvProperGroupName();
			Integer i = map.get(str);
			map.put(str, i == null ? 1 : i + 1 );
		}

		return map;
	}

	// 重複同項目リストの作成
	private void createReptProperList(List<ProperStatusDto> plusProperList
			, List<ProperStatusDto> reptPlusProperListFirst
			, List<ProperStatusDto> reptPlusProperListAfter
			, Map<String, Integer> reptNumMap
			, Map<String, List<ProperStatusDto>>properGroupToDtoMap) {

		for(String str : properGroupToDtoMap.keySet()) {
			Integer i = reptNumMap.get(str);
			if(i > 1) {
				List<ProperStatusDto> groupProperList = properGroupToDtoMap.get(str);
				// 消費潜在が一番大きなものを同項目Firstに追加
				Collections.sort(groupProperList, new ProperStatusComparator().reversed());
				reptPlusProperListFirst.add(groupProperList.get(0));
				groupProperList.remove(0);
				// 上記以外を同項目Afterに追加
				for(ProperStatusDto dto : groupProperList) {
					reptPlusProperListAfter.add(dto);
				}
			}
		}

		for(ProperStatusDto dto : reptPlusProperListFirst) {
			plusProperList.remove(dto);
		}
		for(ProperStatusDto dto : reptPlusProperListAfter) {
			plusProperList.remove(dto);
		}

	}
	
	public String errorCheckCommon(List<ProperStatusDto>plusProperList
			, List<ProperStatusDto>reptPlusProperListFirst
			, List<ProperStatusDto>reptPlusProperListAfter
			, List<ProperStatusDto>minusProperList
			, Integer shokiSenzai
			, List<ProperStatusDto>kinsiProperList
			, List<ProperStatusDto>kinsiMinusProperList
			 ) {
		// エラー文字列の作成
		String errorStr = "";
		List<ProperStatusDto> tempList = new ArrayList();
		// プロパレベルが最大値を超えていたらエラー
		tempList.addAll(plusProperList);
		tempList.addAll(reptPlusProperListFirst);
		tempList.addAll(reptPlusProperListAfter);
		tempList.addAll(minusProperList);
		tempList.addAll(kinsiProperList);
		tempList.addAll(kinsiMinusProperList);
		for(ProperStatusDto dto : tempList) {
			if(dto.getiProperMaxLevel() < dto.getiProperMokuhyoLevel()) {
				errorStr = "【エラー】プロパ " + dto.getvProperName() + " のプロパレベルが最大値を超えています。";
			}
		}
		// 同じプロパを含んでいる場合はエラー
		Map<String, Integer> tempMap = new HashMap();
		for(ProperStatusDto dto : tempList) {
			String str = dto.getvProperName();
			Integer i = tempMap.get(str);
			tempMap.put(str, i == null ? 1 : i + 1 );
		}
		for(ProperStatusDto dto : tempList) {
			String str = dto.getvProperName();
			Integer i = tempMap.get(str);
			if(i > 1) {
				errorStr = "【エラー】プロパ" + str + " が2箇所以上で使用されています。";
			}
		}
		// プロパの個数が上限を超えている場合はエラー
		int settiNum = tempList.size();
		if(settiNum > settiJougen) {
			errorStr = "【エラー】プロパの個数が付与可能数である" + settiJougen + "個を超えています。";
		}
		return errorStr;
	}
	
	// ProperSimuDataFormを作成する
	public List<ProperDataForm> createTotalProperData(List<ProperStatusDto> plusProperList, List<ProperStatusDto> minusProperList) {
		List<ProperDataForm> dataList = new ArrayList();
		for(ProperStatusDto proper : plusProperList) {
			ProperDataForm data = new ProperDataForm();
			data.setProperName(proper.getvProperName());
			data.setProperLv(0);
			dataList.add(data);
		}
		for(ProperStatusDto proper : minusProperList) {
			ProperDataForm data = new ProperDataForm();
			data.setProperName(proper.getvProperName());
			data.setProperLv(0);
			dataList.add(data);
		}
		return dataList;
	}
	
	// 長さが0でないリストからProperSimuDataFormを作成する
	public ProperSimuDataForm createSimuData(List<ProperSimuDataForm> stepList, List<ProperDataForm> totalProperList) {
		int length = stepList.size();
		ProperSimuDataForm form = new ProperSimuDataForm();
		List<ProperDataForm> dataList = new ArrayList();
		if(length <= 0) {
			for(ProperDataForm proper : totalProperList) {
				ProperDataForm data = new ProperDataForm();
				data.setProperName(proper.getProperName());
				data.setProperLv(0);
				dataList.add(data);
			}
		}else {
			for(ProperDataForm proper : stepList.get(length - 1).getProperList()) {
				ProperDataForm data = new ProperDataForm();
				data.setProperName(proper.getProperName());
				data.setProperLv(proper.getProperLv());
				dataList.add(data);
			}
		}
		form.setProperList(dataList);
		return form;
	}




}
