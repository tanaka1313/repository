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
import example.app.dao.impl.ProperDataDaoImpl.ProperDataExtractor;
import example.app.dao.impl.SeirenDataDaoImpl.SeirenKousekiExtractor;
import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.dto.ProperDataDto;

public class SeirenSeigenDaoImpl extends NamedParameterJdbcDaoSupport implements SeirenSeigenDao {

	@Override
	public void insertSeigen(int type, int kitaiti) {
		
		final String sql = "insert into trmbsdt.seiren_seigen values("
				+ "date(now()), " 
				+ type + ", " 
				+ kitaiti + ");"; 
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		getNamedParameterJdbcTemplate().update(sql, prmSource);
	}
	
	@Override
	public int countSeigen(int type) {
		
		final String sql = "select "
				+ "count(*) total " 
				+ "from seiren_seigen "
				+ "where d_insert_date = date(now()) "
				+ "and i_insert_type = :type";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("type", type);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SeirenSeigenExtractor());
	}
	
	protected class SeirenSeigenExtractor implements ResultSetExtractor<Integer> {
		
		public Integer extractData(ResultSet rs) throws SQLException{
			Integer total = 0;
			while(rs.next()) {
				total = rs.getInt("total");
			}
			
			return total;
		}
	}


}
