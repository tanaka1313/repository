package example.app.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;

import example.app.dao.SeirenDataDao;
import example.app.dao.SeirenSeigenDao;
import example.app.dao.SkillLogDao;
import example.app.dao.impl.ProperDataDaoImpl.ProperDataExtractor;
import example.app.dao.impl.SeirenDataDaoImpl.SeirenKousekiExtractor;
import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.dto.ProperDataDto;

public class SkillLogDaoImpl extends NamedParameterJdbcDaoSupport implements SkillLogDao {

	@Override
	public void insertSkillLog(String randNum, int totalSp) {
		
		final String sql = "insert into trmbsdt.skill_logdata values("
				+ "'" + randNum + "', "
				+ totalSp + ", "
				+ "'tanaka', "
				+ "now(), null, null, null, null, null, null, null, null);";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		getNamedParameterJdbcTemplate().update(sql, prmSource);
	}
	
	@Override
	public void insertSkillDetail(String skillCode, String skillGroupName, String randNum, int skillLv, String skillName) {
		
		final String sql = "insert into trmbsdt.skill_log_detail values("
				+ "'" + skillCode + "', "
				+ "'" + skillGroupName + "', "
				+ "'" + randNum + "', "
				+ skillLv + ", "
				+ "'" + skillName + "', "
				+ "'tanaka', "
				+ "now());";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		getNamedParameterJdbcTemplate().update(sql, prmSource);
	}
	
}
