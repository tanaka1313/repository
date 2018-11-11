package example.app.dao;

import java.util.List;

import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;

public interface SeirenDataDao {
	
	public List<KousekiDataDto> getKousekiData();
	
	public List<HojoItemDataDto> getHojoItemData();
	
	public KousekiDataDto getKousekiDataDto(String kousekiName);
	
	public HojoItemDataDto getHojoItemDataDto(String hojoItemName);
	
}
