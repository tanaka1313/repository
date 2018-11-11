package example.app.dao;

import java.util.List;

import org.springframework.dao.DataAccessException;

import example.app.dto.ProperDataDto;


public interface ProperBaseDataDao {
	
	List<ProperDataDto> getProperBaseDataList() throws DataAccessException;

}
