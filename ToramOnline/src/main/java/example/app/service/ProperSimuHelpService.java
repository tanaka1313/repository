package example.app.service;

import java.util.List;
import java.util.Map;

import example.app.dto.ProperListDto;
import example.app.dto.ProperStatusDto;
import example.app.form.ProperInputForm;
import example.app.form.ProperOutputForm;
import example.app.form.ProperSimuInputForm;

public interface ProperSimuHelpService {
	
    public void calcurateStep(ProperSimuInputForm properInputForm);
    
    public int calcurateSozaiPt(int start, int end, double tanka);
    
    public ProperOutputForm createOutput(ProperSimuInputForm properInputForm);

}
