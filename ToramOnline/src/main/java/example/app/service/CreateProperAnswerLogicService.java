package example.app.service;

import example.app.dto.ProperHuyoAnswerDto;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;

public interface CreateProperAnswerLogicService {
	
	public ProperOutputForm getProperAnswer(ProperInputForm properInputForm);

}
