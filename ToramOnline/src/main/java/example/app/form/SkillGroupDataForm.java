package example.app.form;

import java.io.Serializable;
import java.util.List;

public class SkillGroupDataForm implements Serializable {
	
	private static final long serialVersionUID = 1L;
	
	// スキルグループ名
	private String skillGroupName;
	
	// WikiURL
	private String wikiUrl;
	
	// WikiWikiURL
	private String wikiwikiUrl;
	
	// sonotaURL
	private String sonotaUrl;
	
	// スキルリスト
	private List<SkillDataForm> skillDataList;

	public String getSkillGroupName() {
		return skillGroupName;
	}

	public void setSkillGroupName(String skillGroupName) {
		this.skillGroupName = skillGroupName;
	}

	public String getWikiUrl() {
		return wikiUrl;
	}

	public void setWikiUrl(String wikiUrl) {
		this.wikiUrl = wikiUrl;
	}

	public String getWikiwikiUrl() {
		return wikiwikiUrl;
	}

	public void setWikiwikiUrl(String wikiwikiUrl) {
		this.wikiwikiUrl = wikiwikiUrl;
	}

	public List<SkillDataForm> getSkillDataList() {
		return skillDataList;
	}

	public void setSkillDataList(List<SkillDataForm> skillDataList) {
		this.skillDataList = skillDataList;
	}

	public String getSonotaUrl() {
		return sonotaUrl;
	}

	public void setSonotaUrl(String sonotaUrl) {
		this.sonotaUrl = sonotaUrl;
	}

}
