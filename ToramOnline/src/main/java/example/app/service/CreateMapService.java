package example.app.service;

import java.util.Map;

public interface CreateMapService {
	
	public Map<Integer,Integer>getCustomMap(int max, int mod, int start);
	
	public Map<String,String>getCustomAddMaxMap(int max, int mod, int start);
	
	public Map<Integer,String>getCustomSeirenMap(int max, int mod, int start);
	
	public Map<String,String>getProperBuiMap();
	
	public Map<String,String>getPermitLogMap();


}
