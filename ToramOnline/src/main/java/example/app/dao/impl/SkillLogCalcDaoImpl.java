package example.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import example.app.dao.SkillLogCalcDao;
import example.app.dao.impl.SkillDataDaoImpl.SkillGroupDataExtractor;
import example.app.dto.SkillLogCalcDto;
import example.app.dto.SkillLogTypeCalcDto;
import example.app.form.SkillGroupDataForm;

public class SkillLogCalcDaoImpl extends NamedParameterJdbcDaoSupport implements SkillLogCalcDao {

	@Override
	public List<SkillLogCalcDto> selectLogCalc(List<String> paramList) {
		
		// 条件式の作成
		String whereStr = "";
		if(paramList == null) {
			whereStr = "0 = 1 ";
		}else if(paramList.size() == 0) {
			whereStr = "v_skill_type_name <> ' ' ";
		}else {
			StringBuffer tempStr = new StringBuffer();
			tempStr.append("v_skill_type_name in (");
			for(String str : paramList) {
				tempStr.append("'");
				tempStr.append(str);
				tempStr.append("',");
			}
			tempStr.append("' '");
			tempStr.append(") ");
			
			whereStr = tempStr.toString();
		}
		
		final String sql = 
				  "select "
				+ "v_skill_code,  "
				+ "sum(i_number) i_number  "
				+ "from  "
				+ "trmbsdt.skill_log_calc "
				+ "where "
				+ "(case when "
				+ whereStr
				+ "then 1 else 0 end) = 1 "
				+ "group by v_skill_code "
				+ "order by  "
				+ "v_skill_code; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SkillLogCalcExtractor());
	}
	
	protected class SkillLogCalcExtractor implements ResultSetExtractor<List<SkillLogCalcDto>> {
		
		public List<SkillLogCalcDto> extractData(ResultSet rs) throws SQLException{
			List<SkillLogCalcDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				SkillLogCalcDto data = new SkillLogCalcDto();
				data.setvSkillCode(rs.getString("v_skill_code"));
//				data.setiSkillLv(rs.getInt(0));
				data.setiNumber(rs.getInt("i_number"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}

	@Override
	public List<SkillLogCalcDto> selectLogCalcPerLv(List<String> paramList, String skillCode) {
		
		// 条件式の作成
		String whereStr = "";
		if(paramList == null) {
			whereStr = "0 = 1 ";
		}else if(paramList.size() == 0) {
			whereStr = "v_skill_type_name <> ' ' ";
		}else {
			StringBuffer tempStr = new StringBuffer();
			tempStr.append("v_skill_type_name in (");
			for(String str : paramList) {
				tempStr.append("'");
				tempStr.append(str);
				tempStr.append("',");
			}
			tempStr.append("' '");
			tempStr.append(") ");
			
			whereStr = tempStr.toString();
		}
		
		final String sql = 
				  "select "
				+ "v_skill_code,  "
				+ "i_skill_lv,  "
				+ "sum(i_number) i_number  "
				+ "from  "
				+ "trmbsdt.skill_log_calc "
				+ "where "
				+ "(case when "
				+ whereStr
				+ "then 1 else 0 end) = 1 "
				+ "and v_skill_code = '" + skillCode + "' "
				+ "group by v_skill_code, i_skill_lv "
				+ "order by  "
				+ "v_skill_code, i_number desc; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SkillLogCalcPerLvExtractor());
	}
	
	protected class SkillLogCalcPerLvExtractor implements ResultSetExtractor<List<SkillLogCalcDto>> {
		
		public List<SkillLogCalcDto> extractData(ResultSet rs) throws SQLException{
			List<SkillLogCalcDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				SkillLogCalcDto data = new SkillLogCalcDto();
				data.setvSkillCode(rs.getString("v_skill_code"));
				data.setiSkillLv(rs.getInt("i_skill_lv"));
				data.setiNumber(rs.getInt("i_number"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}



	@Override
	public List<SkillLogTypeCalcDto> selectLogTypeCalc(List<String> paramList) {
		
		// 条件式の作成
		String whereStr = "";
		if(paramList == null || paramList.size() == 0) {
			whereStr = "skill_type_detail_name <> ' ' ";
		}else {
			StringBuffer tempStr = new StringBuffer();
			tempStr.append("skill_type_detail_name in (");
			for(String str : paramList) {
				tempStr.append("'");
				tempStr.append(str);
				tempStr.append("',");
			}
			tempStr.append("' '");
			tempStr.append(") ");
			
			whereStr = tempStr.toString();
		}
		
		final String sql = 
				  "select "
				+ "skill_type_detail_code,  "
				+ "skill_type_detail_name,  "
				+ "sum(i_number) i_number  "
				+ "from  "
				+ "trmbsdt.skill_logtype_calc "
				+ "where "
				+ "(case when "
				+ whereStr
				+ "then 1 else 0 end) = 1 "
				+ "group by skill_type_detail_code, skill_type_detail_name, i_sort "
				+ "order by  "
				+ "i_sort, skill_type_detail_code; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SkillLogTypeCalcExtractor());
	}
	
	protected class SkillLogTypeCalcExtractor implements ResultSetExtractor<List<SkillLogTypeCalcDto>> {
		
		public List<SkillLogTypeCalcDto> extractData(ResultSet rs) throws SQLException{
			List<SkillLogTypeCalcDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				SkillLogTypeCalcDto data = new SkillLogTypeCalcDto();
				data.setvSkillTypeDetailCode(rs.getString("skill_type_detail_code"));
				data.setvSkillTypeDetailName(rs.getString("skill_type_detail_name"));
				data.setiNumber(rs.getInt("i_number"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}



}
