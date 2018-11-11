package example.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import example.app.dao.ProperBaseDataDao;
import example.app.dto.ProperDataDto;

public class ProperBaseDataDaoImpl extends JdbcDaoSupport implements ProperBaseDataDao {

	@Override
	public List<ProperDataDto> getProperBaseDataList() throws DataAccessException {
		
		RowMapper<ProperDataDto> rowMapper = new ProperRowMapper();
		return getJdbcTemplate().query("select v_proper_name, v_proper_group_name, "
				+ "i_proper_lv_max, i_proper_lv_point from trmbsdt.proper_basedata" , rowMapper);
	}
	
	protected class ProperRowMapper implements RowMapper<ProperDataDto> {
		
		private List<ProperDataDto> properList = new ArrayList<>();
		
		public List<ProperDataDto> getResults(){
			return properList;
		}
		
		public ProperDataDto mapRow(ResultSet rs, int rowNum) throws SQLException{
			//ResultSetからオブジェクトの詰め替え
			ProperDataDto data = new ProperDataDto();
			data.setvProperName(rs.getString("v_proper_name"));
			data.setvProperGroupName(rs.getString("v_proper_group_name"));
			data.setiProperMaxLv(rs.getInt("i_proper_lv_max"));
			data.setiProperLvPoint(rs.getInt("i_proper_lv_point"));
			
			return data;
		}
	}

}
