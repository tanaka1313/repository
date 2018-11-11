package example.app.service;

import example.app.form.SeirenInputForm;
import example.app.form.SeirenOutputForm;

public interface SeirenHelpService {
	
	public SeirenOutputForm getSeirenAve(SeirenInputForm seirenInputForm);
	
	public SeirenOutputForm getSeirenSimu(SeirenInputForm seirenInputForm, SeirenOutputForm seirenOutputForm);

}
