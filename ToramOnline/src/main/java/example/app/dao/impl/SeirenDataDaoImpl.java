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
import example.app.dao.impl.ProperDataDaoImpl.ProperDataExtractor;
import example.app.dto.HojoItemDataDto;
import example.app.dto.KousekiDataDto;
import example.app.dto.ProperDataDto;

public class SeirenDataDaoImpl extends NamedParameterJdbcDaoSupport implements SeirenDataDao {

	@Override
	public List<KousekiDataDto> getKousekiData() {
		
		final String sql = "select "
				+ "v_kouseki_name, " 
				+ "i_kouseki " 
				+ "from seiren_kouseki "
				+ "order by i_index;";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SeirenKousekiExtractor());
	}
	
	protected class SeirenKousekiExtractor implements ResultSetExtractor<List<KousekiDataDto>> {
		
		public List<KousekiDataDto> extractData(ResultSet rs) throws SQLException{
			List<KousekiDataDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				KousekiDataDto data = new KousekiDataDto();
				data.setvKousekiName(rs.getString("v_kouseki_name"));
				data.setiKouseki(rs.getInt("i_kouseki"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}


	@Override
	public List<HojoItemDataDto> getHojoItemData() {
		
		final String sql = "select "
				+ "v_hojoitem_name, " 
				+ "i_rekka_saferate, " 
				+ "i_seikou_rate, " 
				+ "i_smith_seikou_rate, " 
				+ "i_ticket_bairitu " 
				+ "from seiren_hojoitem "
				+ "order by i_index;";
		
		SqlParameterSource prmSource = new MapSqlParameterSource();
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SeirenHojoitemExtractor());
		
	}

	protected class SeirenHojoitemExtractor implements ResultSetExtractor<List<HojoItemDataDto>> {
		
		public List<HojoItemDataDto> extractData(ResultSet rs) throws SQLException{
			List<HojoItemDataDto> dataList = new ArrayList();
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				HojoItemDataDto data = new HojoItemDataDto();
				data.setvHojoItemName(rs.getString("v_hojoitem_name"));
				data.setiRekkaSafeRate(rs.getInt("i_rekka_saferate"));
				data.setiSeikouRate(rs.getInt("i_seikou_rate"));
				data.setiSmithSeikouRate(rs.getInt("i_smith_seikou_rate"));
				data.setiTicketBairitu(rs.getInt("i_ticket_bairitu"));
				
				dataList.add(data);
			}
			
			return dataList;
		}
	}
	
	@Override
	public KousekiDataDto getKousekiDataDto(String kousekiName) {
		
		final String sql = "select "
				+ "v_kouseki_name, " 
				+ "i_kouseki " 
				+ "from seiren_kouseki "
				+ "where v_kouseki_name = :kousekiName; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("kousekiName", kousekiName);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SeirenKousekiDtoExtractor());
	}

	protected class SeirenKousekiDtoExtractor implements ResultSetExtractor<KousekiDataDto> {
		
		public KousekiDataDto extractData(ResultSet rs) throws SQLException{
			KousekiDataDto data = null;
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				data = new KousekiDataDto();
				data.setvKousekiName(rs.getString("v_kouseki_name"));
				data.setiKouseki(rs.getInt("i_kouseki"));
				
			}
			
			return data;
		}
	}

	@Override
	public HojoItemDataDto getHojoItemDataDto(String hojoItemName) {
		
		final String sql = "select "
				+ "v_hojoitem_name, " 
				+ "i_rekka_saferate, " 
				+ "i_seikou_rate, " 
				+ "i_smith_seikou_rate, " 
				+ "i_ticket_bairitu " 
				+ "from seiren_hojoitem "
				+ "where v_hojoitem_name = :hojoItemName; ";
		
		SqlParameterSource prmSource = new MapSqlParameterSource("hojoItemName", hojoItemName);
		
		return getNamedParameterJdbcTemplate().query(sql, prmSource, new SeirenHojoitemDtoExtractor());
		
	}

	protected class SeirenHojoitemDtoExtractor implements ResultSetExtractor<HojoItemDataDto> {
		
		public HojoItemDataDto extractData(ResultSet rs) throws SQLException{
			HojoItemDataDto data = null;
			//ResultSetからオブジェクトの詰め替え
			while(rs.next()) {
				data = new HojoItemDataDto();
				data.setvHojoItemName(rs.getString("v_hojoitem_name"));
				data.setiRekkaSafeRate(rs.getInt("i_rekka_saferate"));
				data.setiSeikouRate(rs.getInt("i_seikou_rate"));
				data.setiSmithSeikouRate(rs.getInt("i_smith_seikou_rate"));
				data.setiTicketBairitu(rs.getInt("i_ticket_bairitu"));
				
			}
			
			return data;
		}
	}
	



}
