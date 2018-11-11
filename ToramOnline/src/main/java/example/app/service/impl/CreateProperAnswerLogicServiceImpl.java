package example.app.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import example.app.dto.ProperHuyoAnswerDto;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuInputForm;
import example.app.service.CreateProperAnswerLogicService;
import example.app.service.CreateProperHuyoLogicService;

@Service
public class CreateProperAnswerLogicServiceImpl implements CreateProperAnswerLogicService {
	
	@Autowired
	@Qualifier("AHuyoLogic")
	CreateProperHuyoLogicService aLogic;
	
	@Autowired
	@Qualifier("A2HuyoLogic")
	CreateProperHuyoLogicService a2Logic;
	
	@Autowired
	@Qualifier("BHuyoLogic")
	CreateProperHuyoLogicService bLogic;

	@Autowired
	@Qualifier("CHuyoLogic")
	CreateProperHuyoLogicService cLogic;

	@Autowired
	@Qualifier("C2HuyoLogic")
	CreateProperHuyoLogicService c2Logic;

	@Autowired
	@Qualifier("DHuyoLogic")
	CreateProperHuyoLogicService dLogic;

	@Autowired
	@Qualifier("D2HuyoLogic")
	CreateProperHuyoLogicService d2Logic;

	@Autowired
	@Qualifier("D3HuyoLogic")
	CreateProperHuyoLogicService d3Logic;

	@Autowired
	@Qualifier("E2HuyoLogic")
	CreateProperHuyoLogicService e2Logic;

	@Autowired
	@Qualifier("FHuyoLogic")
	CreateProperHuyoLogicService fLogic;

	@Autowired
	@Qualifier("XHuyoLogic")
	CreateProperHuyoLogicService xLogic;

	@Autowired
	@Qualifier("X2HuyoLogic")
	CreateProperHuyoLogicService x2Logic;

	@Autowired
	@Qualifier("YHuyoLogic")
	CreateProperHuyoLogicService yLogic;

	@Autowired
	@Qualifier("ZHuyoLogic")
	CreateProperHuyoLogicService zLogic;

	@Autowired
	@Qualifier("Z2HuyoLogic")
	CreateProperHuyoLogicService z2Logic;


	@Override
	public ProperOutputForm getProperAnswer(ProperInputForm properInputForm) {
		
		// 返却用フォーム
    	ProperOutputForm returnOutputForm = new ProperOutputForm();
    	
		// 使用するロジック
		ProperHuyoAnswerDto finalAnswer = new ProperHuyoAnswerDto();
		
		// ロジックのリスト
		List<ProperHuyoAnswerDto> answerList = new ArrayList();
		
		
		// Aロジックの結果
		answerList.add(aLogic.getProperHuyoTejun(properInputForm));
		// A2ロジックの結果
		answerList.add(a2Logic.getProperHuyoTejun(properInputForm));
		// Bロジックの結果
		answerList.add(bLogic.getProperHuyoTejun(properInputForm));
		// Cロジックの結果
		answerList.add(cLogic.getProperHuyoTejun(properInputForm));
		// C2ロジックの結果
		answerList.add(c2Logic.getProperHuyoTejun(properInputForm));
		// Dロジックの結果
		answerList.add(dLogic.getProperHuyoTejun(properInputForm));
		// D2ロジックの結果
		answerList.add(d2Logic.getProperHuyoTejun(properInputForm));
		// D3ロジックの結果
		answerList.add(d3Logic.getProperHuyoTejun(properInputForm));
		// E2ロジックの結果
		answerList.add(e2Logic.getProperHuyoTejun(properInputForm));
		// Fロジックの結果
		answerList.add(fLogic.getProperHuyoTejun(properInputForm));
		// Xロジックの結果
		answerList.add(xLogic.getProperHuyoTejun(properInputForm));
		// X2ロジックの結果
		answerList.add(x2Logic.getProperHuyoTejun(properInputForm));
		// Yロジックの結果
		answerList.add(yLogic.getProperHuyoTejun(properInputForm));
		// Zロジックの結果
		answerList.add(zLogic.getProperHuyoTejun(properInputForm));
		// Z2ロジックの結果
		answerList.add(z2Logic.getProperHuyoTejun(properInputForm));
		
		// エラーがある場合は成功率をマイナスとする
		for(ProperHuyoAnswerDto dto : answerList) {
			if(!"".equals(dto.getErrorMsg())) {
				dto.setSeikouRate(-99999);
			}
		}
		
		// 成功率が最大のものを決定する
		int maxSeikouRate = -99999;
		for(ProperHuyoAnswerDto dto : answerList) {
			maxSeikouRate = Math.max(maxSeikouRate, dto.getSeikouRate());
		}
		
		// 使用ロジックの決定
		for(ProperHuyoAnswerDto dto : answerList) {
			if(maxSeikouRate == dto.getSeikouRate()) {
				finalAnswer = dto;
			}
		}
		
		// 成功率の上限下限設定
		if(finalAnswer.getSeikouRate() > 100) {
			finalAnswer.setSeikouRate(100);
		}else if(finalAnswer.getSeikouRate() < 0){
			finalAnswer.setSeikouRate(0);
		}
		
    	// エラー発生時の設定
    	if(!"".equals(finalAnswer.getErrorMsg())) {
    		returnOutputForm.setErrMsg(finalAnswer.getErrorMsg());
    		return returnOutputForm;
    	}
    	
    	// 成功率の設定
    	double seikou = (double)finalAnswer.getSeikouRate() / (double)100;
    	Integer plusNum = finalAnswer.getHuyoMemberPlus().split(",").length;
    	double plusSeikou = 100;
    	for(int i = 0; i < plusNum; i++) {
    		plusSeikou *= seikou;
    	}
    	
		// ９．画面出力
		returnOutputForm.setSeikouRate("成功率： " + finalAnswer.getSeikouRate() + "%");
		returnOutputForm.setHuyoStep(finalAnswer.getHuyoStep());
		returnOutputForm.setHuyoProperPlus(finalAnswer.getHuyoMemberPlus());
		returnOutputForm.setHuyoProperMinus(finalAnswer.getHuyoMemberMinus());
		returnOutputForm.setSozaiPt(finalAnswer.getSozaiList());
		returnOutputForm.setSyokiSenzai("(初期潜在：" + properInputForm.getShokiSenzai() + "pt)");
		returnOutputForm.setErrMsg(finalAnswer.getWarnMsg());
		returnOutputForm.setSeikou(seikou);
		returnOutputForm.setPlusNum(plusNum);
		returnOutputForm.setPlusSeikou(plusSeikou);
		returnOutputForm.setProperSimuInputForm(finalAnswer.getProperSimuInputForm());

		
		// ロジックの返却
		return returnOutputForm;

	}

}
