package example.app.service;

import java.util.List;
import java.util.Map;

import example.app.dto.ProperListDto;
import example.app.dto.ProperStatusDto;
import example.app.form.ProperDataForm;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuDataForm;
import example.app.form.ProperSimuInputForm;

public interface ProperHelpService {
	
	public ProperListDto getProperListDto(ProperInputForm properInputForm);

	public String errorCheckCommon(List<ProperStatusDto>plusProperList
			, List<ProperStatusDto>reptPlusProperListFirst
			, List<ProperStatusDto>reptPlusProperListAfter
			, List<ProperStatusDto>minusProperList
			, Integer shokiSenzai
			, List<ProperStatusDto>kinsiProperList
			, List<ProperStatusDto>kinsiMinusProperList
			);
	
	public List<ProperDataForm> createTotalProperData(List<ProperStatusDto> plusProperList, List<ProperStatusDto> minusProperList);
	
	public ProperSimuDataForm createSimuData(List<ProperSimuDataForm> stepList, List<ProperDataForm> totalProperList);


	
}
