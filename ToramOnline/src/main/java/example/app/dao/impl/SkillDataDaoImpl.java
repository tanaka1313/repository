package example.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import example.app.dao.SkillDataDao;
import example.app.form.SkillDataForm;
import example.app.form.SkillGroupDataForm;

public class SkillDataDaoImpl extends NamedParameterJdbcDaoSupport implements SkillDataDao {

	@Override
	public List<SkillGroupDataForm> getSkillGroupData() {
		
		final String sql = 
				  "select "
				+ "v_skill_group_name,  "
				+ "v_skill_wiki_url,  "
				+ "v_skill_wikiwiki_url,  "
				+ "v_skill_sonota_url  "
				+ "from  "
				+ "trmbsdt.skill_group "
				+ "order by  "
				+ "i_index";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SkillGroupDataExtractor());

	}

	protected class SkillGroupDataExtractor implements ResultSetExtractor<List<SkillGroupDataForm>> {
		
		public List<SkillGroupDataForm> extractData(ResultSet rs) throws SQLException{
			List<SkillGroupDataForm> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				SkillGroupDataForm data = new SkillGroupDataForm();
				data.setSkillGroupName(rs.getString("v_skill_group_name"));
				data.setWikiUrl(rs.getString("v_skill_wiki_url"));
				data.setWikiwikiUrl(rs.getString("v_skill_wikiwiki_url"));
				data.setSonotaUrl(rs.getString("v_skill_sonota_url"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}

	
	@Override
	public List<SkillDataForm> getSkillData(String skillGroupName) {
		
		final String sql = 
				"select  "
				+ "v_skill_name,  "
				+ "v_skill_code  "
				+ "from  "
				+ "trmbsdt.skill_name "
				+ "where "
				+ "v_skill_group_name = '" + skillGroupName + "' "
				+ "order by  "
				+ "v_skill_code; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SkillDataExtractor());

	}


	protected class SkillDataExtractor implements ResultSetExtractor<List<SkillDataForm>> {
		
		public List<SkillDataForm> extractData(ResultSet rs) throws SQLException{
			List<SkillDataForm> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				SkillDataForm data = new SkillDataForm();
				data.setSkillName(rs.getString("v_skill_name"));
				data.setSkillCode(rs.getString("v_skill_code"));
				
				dataList.add(data);
			}
			return dataList;
		}
	}

}
