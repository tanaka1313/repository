package example.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import example.app.dao.ProperDataDao;
import example.app.dto.ProperDataDto;
import example.app.dto.ProperStatusDto;

public class ProperDataDaoImpl extends NamedParameterJdbcDaoSupport implements ProperDataDao {

	@Override
	public List<ProperDataDto> getMinusProperData(Integer lv, String properBui) {
		
		final String sql = 
				"select * from "
				+ "(select  "
				+ "t1.v_proper_name,  "
				+ "t1.v_proper_group_name,  "
				+ "case when "
				+ "t2.i_proper_max_lv = 0 "
				+ "then truncate( :lv / t2.i_proper_mod,0) "
				+ "else least( :lv / 10 , t2.i_proper_max_lv) end i_proper_max_lv,  "
				+ "t1.i_proper_lv_point,  "
				+ "t1.v_proper_sozai,  "
				+ "t1.d_proper_sozai_point,  "
				+ "t1.v_proper_bikou  "
				+ "from  "
				+ "trmbsdt.proper_data t1, "
				+ "trmbsdt.proper_type t2 "
				+ "where t1.v_proper_type = t2.v_proper_type_name "
				+ "and t1.v_proper_type <> 'H'"
				+ "and t1.v_proper_bui = '" + properBui + "') as h1 " 
				+ "order by  "
				+ "h1.i_proper_max_lv * h1.i_proper_lv_point desc, "
				+ "h1.v_proper_group_name, "
				+ "h1.v_proper_name";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("lv", lv);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new ProperDataExtractor());

	}

	protected class ProperDataExtractor implements ResultSetExtractor<List<ProperDataDto>> {
		
		public List<ProperDataDto> extractData(ResultSet rs) throws SQLException{
			List<ProperDataDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				ProperDataDto data = new ProperDataDto();
				data.setvProperName(rs.getString("v_proper_name"));
				data.setvProperGroupName(rs.getString("v_proper_group_name"));
				data.setiProperMaxLv(rs.getInt("i_proper_max_lv"));
				data.setiProperLvPoint(rs.getInt("i_proper_lv_point"));
				data.setvProperSozai(rs.getString("v_proper_sozai"));
				data.setdProperSozaiPoint(rs.getDouble("d_proper_sozai_point"));
				data.setvProperBikou(rs.getString("v_proper_bikou"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}

	
	@Override
	public List<ProperDataDto> getPlusProperData(Integer lv, String properBui) {
		
		final String sql = 
				"select  "
				+ "t1.v_proper_name,  "
				+ "t1.v_proper_group_name,  "
				+ "case when "
				+ "t2.i_proper_max_lv = 0 "
				+ "then truncate( :lv / t2.i_proper_mod,0) "
				+ "else least( :lv / 10 , t2.i_proper_max_lv) end i_proper_max_lv,  "
				+ "t1.i_proper_lv_point,  "
				+ "t1.v_proper_sozai,  "
				+ "t1.d_proper_sozai_point,  "
				+ "t1.v_proper_bikou  "
				+ "from  "
				+ "trmbsdt.proper_data t1, "
				+ "trmbsdt.proper_type t2 "
				+ "where t1.v_proper_type = t2.v_proper_type_name "
				+ "and t1.v_proper_bui = '" + properBui + "' "
				+ "order by  "
				+ "t1.i_index; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("lv", lv);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new ProperDataExtractor());

	}


	@Override
	public ProperStatusDto getProperStatusDto(String properName, int lv, String properBui) {
		final String sql = 
				"select  "
				+ "t1.v_proper_name,  "
				+ "t1.v_proper_group_name,  "
				+ "case when "
				+ "t2.i_proper_max_lv = 0 "
				+ "then truncate( :lv / t2.i_proper_mod,0) "
				+ "else least( :lv / 10 , t2.i_proper_max_lv) end i_proper_max_lv,  "
				+ "t1.i_proper_lv_point,  "
				+ "t1.v_proper_sozai,  "
				+ "t1.d_proper_sozai_point,  "
				+ "t1.v_proper_bikou  "
				+ "from  "
				+ "trmbsdt.proper_data t1, "
				+ "trmbsdt.proper_type t2 "
				+ "where t1.v_proper_type = t2.v_proper_type_name "
				+ "and t1.v_proper_name = '" + properName+ " ' "
				+ "and t1.v_proper_bui = '" + properBui + "';";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("lv", lv);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new ProperStatusExtractor());
	}

	protected class ProperStatusExtractor implements ResultSetExtractor<ProperStatusDto> {
		
		public ProperStatusDto extractData(ResultSet rs) throws SQLException{
			if(rs.next()) {
				ProperStatusDto data = new ProperStatusDto();
				//ResultSetからオブジェクトの詰め替え
				data.setvProperName(rs.getString("v_proper_name"));
				data.setvProperGroupName(rs.getString("v_proper_group_name"));
				data.setiProperMaxLevel(rs.getInt("i_proper_max_lv"));
				data.setiProperMokuhyoLevel(rs.getInt("i_proper_max_lv"));
				data.setiProperSenzaiTanka(rs.getInt("i_proper_lv_point"));
				data.setvProperSozaiName(rs.getString("v_proper_sozai"));
				data.setdProperSozaiTanka(rs.getDouble("d_proper_sozai_point"));
				
				return data;
			}else {
				return null;
			}
		}
	}

}
